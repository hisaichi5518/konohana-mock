package com.github.hisaichi5518.konohana.processor;

import android.content.Context;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;

class KonohanaMethods {
    private final KonohanaContext konohanaContext;

    KonohanaMethods(KonohanaContext konohanaContext) {
        this.konohanaContext = konohanaContext;
    }

    Iterable<MethodSpec> build() {
        List<MethodSpec> methods = new ArrayList<>();

        // Create constructor
        methods.add(MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ParameterSpec.builder(Context.class, "context").build())
                .addStatement("this.applicationContext = context.getApplicationContext()")
                .build());

        konohanaContext.storeContexts.forEach(storeContext -> {
            methods.add(MethodSpec.methodBuilder("get" + storeContext.getClassName().simpleName())
                    .addModifiers(Modifier.PUBLIC)
                    .returns(storeContext.getStoreClassName())
                    .addStatement("return new $T(applicationContext)", storeContext.getStoreClassName())
                    .build());
        });

        return methods;
    }
}
