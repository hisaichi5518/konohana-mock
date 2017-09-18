package com.github.hisaichi5518.konohana.processor.model;

import android.support.annotation.NonNull;

import com.github.hisaichi5518.konohana.processor.types.AndroidTypes;
import com.github.hisaichi5518.konohana.processor.types.Annotations;
import com.github.hisaichi5518.konohana.processor.types.RxJavaTypes;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;

public class StoreMethodsBuilder {

    private final StoreDefinition storeDefinition;

    public StoreMethodsBuilder(@NonNull StoreDefinition storeDefinition) {
        this.storeDefinition = storeDefinition;
    }

    @NonNull
    public List<MethodSpec> build() {
        List<MethodSpec> methods = new ArrayList<>();

        CodeBlock.Builder codeBlockBuilder = CodeBlock.builder()
                .addStatement("$T entity = new $T()", storeDefinition.getClassName(), storeDefinition.getClassName());

        storeDefinition.keyDefinitionStream().forEach(
                keyDefinition -> codeBlockBuilder.addStatement("entity.$L = $L()", keyDefinition.getFieldName(), keyDefinition.getGetterName()));
        codeBlockBuilder.addStatement("emitter.onNext(entity)");

        // Create changes method
        //@formatter:off
        methods.add(MethodSpec.methodBuilder("changes")
                .addAnnotation(Annotations.NonNull)
                .addModifiers(Modifier.PUBLIC)
                .returns(ParameterizedTypeName.get(RxJavaTypes.Observable, storeDefinition.getClassName()))
                .beginControlFlow("return $T.create(new $T<$T>()", RxJavaTypes.Observable, RxJavaTypes.ObservableOnSubscribe, storeDefinition.getClassName())
                    .addCode("@$T\n", Override.class)
                    .beginControlFlow("public void subscribe(final $T<$T> emitter) throws $T", RxJavaTypes.ObservableEmitter, storeDefinition.getClassName(), Exception.class)
                        .beginControlFlow("final $T listener = new $T()", AndroidTypes.OnSharedPreferenceChangeListener, AndroidTypes.OnSharedPreferenceChangeListener)
                            .addCode("@$T\n", Override.class)
                            .beginControlFlow("public void onSharedPreferenceChanged($T preferences, $T key)", AndroidTypes.SharedPreferences, String.class)
                            .addCode(codeBlockBuilder.build())
                            .endControlFlow()
                        .endControlFlow("")
                        .beginControlFlow("emitter.setCancellable(new $T()", RxJavaTypes.Cancellable)
                            .addCode("@$T\n", Override.class)
                            .beginControlFlow("public void cancel() throws $T", Exception.class)
                                .addStatement("prefs.unregisterOnSharedPreferenceChangeListener(listener)")
                            .endControlFlow()
                        .endControlFlow(")")
                        .addStatement("prefs.registerOnSharedPreferenceChangeListener(listener)")
                    .endControlFlow()
                .endControlFlow(")")
                .build());
        //@formatter:on

        // Create Getters, Setters and more
        storeDefinition.keyDefinitionStream().forEach(keyDefinition -> {
            methods.add(buildGetterWithDefaultSpec(keyDefinition));
            methods.add(buildGetterSpec(keyDefinition));
            methods.add(buildSetterSpec(keyDefinition));
            methods.add(buildHasMethodSpec(keyDefinition));
            methods.add(buildRemoverSpec(keyDefinition));

            // TODO: keyChanges method
        });

        methods.add(MethodSpec.methodBuilder("removeAll")
                .addModifiers(Modifier.PUBLIC)
                .addStatement("prefs.edit().clear().apply()")
                .build());

        // TODO: getAll method
        // TODO: setAll method

        return methods;
    }

    @NonNull
    private MethodSpec buildRemoverSpec(@NonNull KeyDefinition keyDefinition) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder(keyDefinition.getRemoverName())
                .addModifiers(Modifier.PUBLIC)
                .addStatement("prefs.edit().remove($S).apply()", keyDefinition.getPrefsKeyName());

        return builder.build();
    }

    @NonNull
    private MethodSpec buildHasMethodSpec(@NonNull KeyDefinition keyDefinition) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder(keyDefinition.getContainsName())
                .returns(boolean.class)
                .addModifiers(Modifier.PUBLIC)
                .addStatement("return prefs.contains($S)", keyDefinition.getPrefsKeyName());

        return builder.build();
    }

    @NonNull
    private MethodSpec buildSetterSpec(@NonNull KeyDefinition keyDefinition) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder(keyDefinition.getSetterName())
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ParameterSpec.builder(keyDefinition.getFieldClassName(), "value").addAnnotation(Annotations.NonNull).build())
                .addStatement("$T.set(prefs, $S, value)", keyDefinition.getTypeAdapterTypeName(), keyDefinition.getPrefsKeyName());

        return builder.build();
    }

    @NonNull
    private MethodSpec buildGetterSpec(@NonNull KeyDefinition keyDefinition) {

        MethodSpec.Builder builder = MethodSpec.methodBuilder(keyDefinition.getGetterName())
                .returns(keyDefinition.getFieldClassName())
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Annotations.NonNull)
                .addParameter(ParameterSpec.builder(keyDefinition.getFieldClassName(), "defaultValue").addAnnotation(Annotations.NonNull).build())
                .addStatement("return $T.get(prefs, $S, defaultValue)", keyDefinition.getTypeAdapterTypeName(), keyDefinition.getPrefsKeyName());

        return builder.build();
    }

    @NonNull
    private MethodSpec buildGetterWithDefaultSpec(@NonNull KeyDefinition keyDefinition) {

        MethodSpec.Builder builder = MethodSpec.methodBuilder(keyDefinition.getGetterName())
                .returns(keyDefinition.getFieldClassName())
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Annotations.NonNull)
                .addStatement("return $T.get(prefs, $S)", keyDefinition.getTypeAdapterTypeName(), keyDefinition.getPrefsKeyName());

        return builder.build();
    }
}
