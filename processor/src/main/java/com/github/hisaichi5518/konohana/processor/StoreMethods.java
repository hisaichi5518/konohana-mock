package com.github.hisaichi5518.konohana.processor;

import android.content.Context;
import android.content.SharedPreferences;

import com.github.hisaichi5518.konohana.annotation.Key;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Cancellable;

import static com.squareup.javapoet.MethodSpec.methodBuilder;

public class StoreMethods {
    private final StoreContext context;
    private final List<MethodSpec> methods;

    StoreMethods(StoreContext context) {
        this.context = context;
        this.methods = new ArrayList<>();
    }

    public List<MethodSpec> build() {

        // Create constructor
        methods.add(MethodSpec.constructorBuilder()
                .addParameter(ParameterSpec.builder(Context.class, "context").build())
                .addStatement("this.prefs = context.getSharedPreferences($S, $L)", context.getFileName(), context.getMode())
                .build());


        // Create observable method
        methods.add(methodBuilder("observable")
                .returns(ParameterizedTypeName.get(ClassName.get(Observable.class), context.getClassName()))
                .beginControlFlow("return $T.create(new $T<$T>()", Observable.class, ObservableOnSubscribe.class, context.getClassName())
                    .addCode("@$T\n", Override.class)
                    .beginControlFlow("public void subscribe(final $T<$T> emitter) throws $T", ObservableEmitter.class, context.getClassName(), Exception.class)
                        .beginControlFlow("final $T listener = new $T()", SharedPreferences.OnSharedPreferenceChangeListener.class, SharedPreferences.OnSharedPreferenceChangeListener.class)
                            .addCode("@$T\n", Override.class)
                            .beginControlFlow("public void onSharedPreferenceChanged($T preferences, $T key)", SharedPreferences.class, String.class)
                            .endControlFlow()
                        .endControlFlow(";")
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


        context.element.getEnclosedElements()
                .stream()
                .filter(element -> element.getAnnotation(Key.class) != null)
                .map(element -> (VariableElement) element)
                .forEach(element -> methods.add(buildGetterSpec(element)));



        return methods;
    }

    private MethodSpec buildGetterSpec(VariableElement element) {

        KeyContext keyContext = new KeyContext(element);

        MethodSpec.Builder builder = MethodSpec.methodBuilder("get" + element.getSimpleName())
                .returns(ClassName.get(element.asType()))
                .addModifiers(Modifier.PUBLIC);

        keyContext.injectMethodSpec(builder);

        return builder.build();
    }
}
