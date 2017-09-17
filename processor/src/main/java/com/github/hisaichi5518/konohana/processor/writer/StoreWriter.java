package com.github.hisaichi5518.konohana.processor.writer;

import android.support.annotation.NonNull;

import com.github.hisaichi5518.konohana.processor.model.StoreDefinition;
import com.github.hisaichi5518.konohana.processor.model.StoreMethodsBuilder;
import com.github.hisaichi5518.konohana.processor.types.AndroidTypes;
import com.github.hisaichi5518.konohana.processor.types.Annotations;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;

public class StoreWriter {
    private final StoreDefinition storeDefinition;

    public StoreWriter(@NonNull StoreDefinition storeDefinition) {
        this.storeDefinition = storeDefinition;
    }

    public void write() {

        TypeSpec typeSpec = TypeSpec.classBuilder(storeDefinition.getStoreClassName())
                .addFields(buildFields())
                .addMethods(buildConstructors())
                .addMethods(new StoreMethodsBuilder(storeDefinition).build())
                .build();

        try {
            JavaFile.builder(storeDefinition.getClassName().packageName(), typeSpec)
                    .build()
                    .writeTo(storeDefinition.context.getFiler());
        } catch (IOException e) {
            storeDefinition.context.error(e.getMessage(), storeDefinition.element);
        }
    }

    @NonNull
    private List<FieldSpec> buildFields() {
        List<FieldSpec> fieldSpecs = new ArrayList<>();

        fieldSpecs.add(FieldSpec.builder(AndroidTypes.SharedPreferences, "prefs")
                .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                .build());

        return fieldSpecs;
    }

    @NonNull
    private List<MethodSpec> buildConstructors() {
        List<MethodSpec> methodSpecs = new ArrayList<>();

        methodSpecs.add(MethodSpec.constructorBuilder()
                .addParameter(ParameterSpec.builder(AndroidTypes.Context, "context").addAnnotation(Annotations.NonNull).build())
                .addStatement("this.prefs = context.getSharedPreferences($S, $L)", storeDefinition.getPrefsFileName(), storeDefinition.getPrefsMode())
                .build());

        return methodSpecs;
    }
}
