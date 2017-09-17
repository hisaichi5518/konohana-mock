package com.github.hisaichi5518.konohana.processor.model;

import android.content.SharedPreferences;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Cancellable;

import static com.squareup.javapoet.MethodSpec.methodBuilder;

public class StoreMethodsBuilder {

    private final StoreDefinition storeDefinition;

    public StoreMethodsBuilder(StoreDefinition storeDefinition) {
        this.storeDefinition = storeDefinition;
    }

    public List<MethodSpec> build() {
        List<MethodSpec> methods = new ArrayList<>();

        CodeBlock.Builder codeBlockBuilder = CodeBlock.builder()
                .addStatement("$T entity = new $T()", storeDefinition.getClassName(), storeDefinition.getClassName());

        storeDefinition.keyDefinitionStream().forEach(
                keyDefinition -> codeBlockBuilder.addStatement("entity.$L = $L()", keyDefinition.getFieldName(), keyDefinition.getGetterName()));
        codeBlockBuilder.addStatement("emitter.onNext(entity)");

        // Create changes method
        //@formatter:off
        methods.add(methodBuilder("changes")
                .addModifiers(Modifier.PUBLIC)
                .returns(ParameterizedTypeName.get(ClassName.get(Observable.class), storeDefinition.getClassName()))
                .beginControlFlow("return $T.create(new $T<$T>()", Observable.class, ObservableOnSubscribe.class, storeDefinition.getClassName())
                    .addCode("@$T\n", Override.class)
                    .beginControlFlow("public void subscribe(final $T<$T> emitter) throws $T", ObservableEmitter.class, storeDefinition.getClassName(), Exception.class)
                        .beginControlFlow("final $T listener = new $T()", SharedPreferences.OnSharedPreferenceChangeListener.class, SharedPreferences.OnSharedPreferenceChangeListener.class)
                            .addCode("@$T\n", Override.class)
                            .beginControlFlow("public void onSharedPreferenceChanged($T preferences, $T key)", SharedPreferences.class, String.class)
                            .addCode(codeBlockBuilder.build())
                            .endControlFlow()
                        .endControlFlow("")
                        .beginControlFlow("emitter.setCancellable(new $T()", Cancellable.class)
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

    private MethodSpec buildRemoverSpec(KeyDefinition keyDefinition) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder(keyDefinition.getRemoverName())
                .addModifiers(Modifier.PUBLIC)
                .addStatement("prefs.edit().remove($S).apply()", keyDefinition.getPrefsKeyName());

        return builder.build();
    }

    private MethodSpec buildHasMethodSpec(KeyDefinition keyDefinition) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder(keyDefinition.getContainsName())
                .returns(boolean.class)
                .addModifiers(Modifier.PUBLIC)
                .addStatement("return prefs.contains($S)", keyDefinition.getPrefsKeyName());

        return builder.build();
    }

    private MethodSpec buildSetterSpec(KeyDefinition keyDefinition) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder(keyDefinition.getSetterName())
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ParameterSpec.builder(keyDefinition.getFieldClassName(), "value").build())
                .addStatement("new $T().set(prefs, $S, value)", keyDefinition.getTypeAdapterClassName(), keyDefinition.getPrefsKeyName()); // TODO

        return builder.build();
    }

    private MethodSpec buildGetterSpec(KeyDefinition keyDefinition) {

        MethodSpec.Builder builder = MethodSpec.methodBuilder(keyDefinition.getGetterName())
                .returns(keyDefinition.getFieldClassName())
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ParameterSpec.builder(keyDefinition.getFieldClassName(), "defaultValue").build())
                .addStatement("return new $T().get(prefs, $S, defaultValue)", keyDefinition.getTypeAdapterClassName(), keyDefinition.getPrefsKeyName()); // TODO

        return builder.build();
    }

    private MethodSpec buildGetterWithDefaultSpec(KeyDefinition keyDefinition) {

        MethodSpec.Builder builder = MethodSpec.methodBuilder(keyDefinition.getGetterName())
                .returns(keyDefinition.getFieldClassName())
                .addModifiers(Modifier.PUBLIC)
                .addStatement("return new $T().get(prefs, $S)", keyDefinition.getTypeAdapterClassName(), keyDefinition.getPrefsKeyName()); // TODO

        return builder.build();
    }
}
