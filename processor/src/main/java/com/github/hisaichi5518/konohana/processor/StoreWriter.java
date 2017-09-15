package com.github.hisaichi5518.konohana.processor;

import android.content.SharedPreferences;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

class StoreWriter {
    private final ProcessingEnvironment env;
    private final StoreContext storeContext;

    StoreWriter(ProcessingEnvironment env, TypeElement element) {
        this.env = env;
        this.storeContext = new StoreContext(element);
    }

    void write() throws Exception {

        FieldSpec prefsField = FieldSpec.builder(SharedPreferences.class, "prefs")
                .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                .build();

        TypeSpec typeSpec = TypeSpec.classBuilder(storeContext.element.getSimpleName().toString() + "_Store")
                .addField(prefsField)
                .addMethods(new StoreMethods(storeContext).build())
                .build();


        JavaFile.builder(storeContext.getClassName().packageName(), typeSpec)
                .build()
                .writeTo(env.getFiler());
    }
}
