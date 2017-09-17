package com.github.hisaichi5518.konohana.processor.writer;

import android.support.annotation.NonNull;

import com.github.hisaichi5518.konohana.processor.model.ProcessingContext;
import com.github.hisaichi5518.konohana.processor.types.AndroidTypes;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;

public class KonohanaWriter {
    private final ProcessingContext context;

    public KonohanaWriter(@NonNull ProcessingContext context) {
        this.context = context;
    }

    public void write() {
        if (context.getPackageName() == null) {
            return;
        }

        TypeSpec typeSpec = TypeSpec.classBuilder("Konohana")
                .addFields(buildFieldSpecs())
                .addMethods(buildConstructors())
                .addMethods(buildMethods())
                .build();

        try {
            JavaFile.builder(context.getPackageName(), typeSpec)
                    .build()
                    .writeTo(context.getFiler());
        } catch (IOException e) {
            context.error(e.getMessage());
        }
    }


    @NonNull
    private List<FieldSpec> buildFieldSpecs() {
        List<FieldSpec> specs = new ArrayList<>();

        specs.add(FieldSpec.builder(AndroidTypes.Context, "applicationContext")
                .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                .build());

        return specs;
    }

    @NonNull
    private List<MethodSpec> buildConstructors() {
        List<MethodSpec> specs = new ArrayList<>();

        specs.add(MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ParameterSpec.builder(AndroidTypes.Context, "context").build())
                .addStatement("this.applicationContext = context.getApplicationContext()")
                .build());

        return specs;
    }

    @NonNull
    private List<MethodSpec> buildMethods() {
        List<MethodSpec> specs = new ArrayList<>();

        context.storeDefinitionStream().forEach(storeContext -> {
            specs.add(MethodSpec.methodBuilder("storeOf" + storeContext.getClassName().simpleName())
                    .addModifiers(Modifier.PUBLIC)
                    .returns(storeContext.getStoreClassName())
                    .addStatement("return new $T(applicationContext)", storeContext.getStoreClassName())
                    .build());
        });

        return specs;
    }
}
