package com.github.hisaichi5518.konohana.processor;

import android.content.Context;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;

class KonohanaWriter {
    private final ProcessingEnvironment env;
    private final KonohanaContext konohanaContext;

    KonohanaWriter(ProcessingEnvironment processingEnv, KonohanaContext konohanaContext) {
        this.env = processingEnv;
        this.konohanaContext = konohanaContext;
    }

    void write() throws Exception {

        FieldSpec contextField = FieldSpec.builder(Context.class, "applicationContext")
                .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                .build();

        TypeSpec typeSpec = TypeSpec.classBuilder("Konohana")
                .addField(contextField)
                .addMethods(new KonohanaMethods(konohanaContext).build())
                .build();

        JavaFile.builder(konohanaContext.getPackageName(), typeSpec)
                .build()
                .writeTo(env.getFiler());
    }
}
