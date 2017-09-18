package com.github.hisaichi5518.konohana.processor.model;

import android.support.annotation.NonNull;

import com.github.hisaichi5518.konohana.processor.types.AndroidTypes;
import com.github.hisaichi5518.konohana.processor.types.Annotations;
import com.github.hisaichi5518.konohana.processor.types.JavaTypes;
import com.github.hisaichi5518.konohana.processor.types.RxJavaTypes;
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

        // Create changes method
        //@formatter:off
        methods.add(MethodSpec.methodBuilder("changes")
                .addAnnotation(Annotations.NonNull)
                .addModifiers(Modifier.PUBLIC)
                .returns(ParameterizedTypeName.get(RxJavaTypes.Observable, JavaTypes.String))
                .beginControlFlow("return $T.create(new $T<$T>()", RxJavaTypes.Observable, RxJavaTypes.ObservableOnSubscribe, JavaTypes.String)
                    .addCode("@$T\n", Override.class)
                    .beginControlFlow("public void subscribe(final $T<$T> emitter) throws $T", RxJavaTypes.ObservableEmitter, JavaTypes.String, Exception.class)
                        .beginControlFlow("final $T listener = new $T()", AndroidTypes.OnSharedPreferenceChangeListener, AndroidTypes.OnSharedPreferenceChangeListener)
                            .addCode("@$T\n", Override.class)
                            .beginControlFlow("public void onSharedPreferenceChanged($T preferences, $T key)", AndroidTypes.SharedPreferences, JavaTypes.String)
                            .addStatement("emitter.onNext(key)")
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
                .addStatement("$T.set(prefs, $S, value)", keyDefinition.getPrefsAdapterTypeName(), keyDefinition.getPrefsKeyName());

        return builder.build();
    }

    @NonNull
    private MethodSpec buildGetterWithDefaultSpec(@NonNull KeyDefinition keyDefinition) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder(keyDefinition.getGetterName())
                .returns(keyDefinition.getFieldClassName())
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Annotations.NonNull)
                .addStatement("return $T.get(prefs, $S, $L)",
                        keyDefinition.getPrefsAdapterTypeName(), keyDefinition.getPrefsKeyName(), keyDefinition.getFieldName());

        return builder.build();
    }
}
