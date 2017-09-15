package com.github.hisaichi5518.konohana.processor;

import android.content.Context;
import android.content.SharedPreferences;

import com.github.hisaichi5518.konohana.annotation.Key;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Cancellable;

import static com.squareup.javapoet.MethodSpec.methodBuilder;

class StoreMethods {
    private final StoreContext storeContext;

    StoreMethods(StoreContext storeContext) {
        this.storeContext = storeContext;
    }

    List<MethodSpec> build() {
        List<MethodSpec> methods = new ArrayList<>();

        // Create constructor
        methods.add(MethodSpec.constructorBuilder()
                .addParameter(ParameterSpec.builder(Context.class, "context").build())
                .addStatement("this.prefs = context.getSharedPreferences($S, $L)", storeContext.getFileName(), storeContext.getMode())
                .build());

        CodeBlock.Builder codeBlockBuilder = CodeBlock.builder()
                .addStatement("$T entity = new $T()", storeContext.getClassName(), storeContext.getClassName());

        keys().forEach(keyContext -> codeBlockBuilder.addStatement("entity.$L = get$L()", keyContext.element.getSimpleName(), keyContext.getCapitalizedName()));
        codeBlockBuilder.addStatement("emitter.onNext(entity)");

        //@formatter:off
        // Create keyChanges method
        methods.add(methodBuilder("keyChanges")
                .addModifiers(Modifier.PUBLIC)
                .returns(ParameterizedTypeName.get(ClassName.get(Observable.class), storeContext.getClassName()))
                .beginControlFlow("return $T.create(new $T<$T>()", Observable.class, ObservableOnSubscribe.class, storeContext.getClassName())
                    .addCode("@$T\n", Override.class)
                    .beginControlFlow("public void subscribe(final $T<$T> emitter) throws $T", ObservableEmitter.class, storeContext.getClassName(), Exception.class)
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
        keys().forEach(keyContext -> {
            methods.add(buildGetterWithDefaultSpec(keyContext));
            methods.add(buildGetterSpec(keyContext));
            methods.add(buildSetterSpec(keyContext));
            methods.add(buildHasMethodSpec(keyContext));
            methods.add(buildRemoverSpec(keyContext));
        });

        methods.add(MethodSpec.methodBuilder("removeAll")
                .addModifiers(Modifier.PUBLIC)
                .addStatement("prefs.edit().clear().apply()")
                .build());

        return methods;
    }

    private MethodSpec buildRemoverSpec(KeyContext keyContext) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("remove" + keyContext.getCapitalizedName())
                .addModifiers(Modifier.PUBLIC)
                .addStatement("prefs.edit().remove($S).apply()", keyContext.getRealKeyName());

        return builder.build();
    }

    private MethodSpec buildHasMethodSpec(KeyContext keyContext) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("has" + keyContext.getCapitalizedName())
                .returns(boolean.class)
                .addModifiers(Modifier.PUBLIC)
                .addStatement("return prefs.contains($S)", keyContext.getRealKeyName());

        return builder.build();
    }

    private MethodSpec buildSetterSpec(KeyContext keyContext) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("set" + keyContext.getCapitalizedName())
                .addModifiers(Modifier.PUBLIC);

        keyContext.injectForSetter(builder);

        return builder.build();
    }

    private MethodSpec buildGetterSpec(KeyContext keyContext) {

        MethodSpec.Builder builder = MethodSpec.methodBuilder("get" + keyContext.getCapitalizedName())
                .returns(ClassName.get(keyContext.element.asType()))
                .addModifiers(Modifier.PUBLIC);

        keyContext.injectForGetter(builder);

        return builder.build();
    }

    private MethodSpec buildGetterWithDefaultSpec(KeyContext keyContext) {

        MethodSpec.Builder builder = MethodSpec.methodBuilder("get" + keyContext.getCapitalizedName())
                .returns(ClassName.get(keyContext.element.asType()))
                .addModifiers(Modifier.PUBLIC);

        keyContext.injectForGetterWithDefault(builder);

        return builder.build();
    }

    private Stream<KeyContext> keys() {
        return storeContext.element.getEnclosedElements()
                .stream()
                .filter(element -> element.getAnnotation(Key.class) != null)
                .map(element -> new KeyContext((VariableElement) element));
    }
}
