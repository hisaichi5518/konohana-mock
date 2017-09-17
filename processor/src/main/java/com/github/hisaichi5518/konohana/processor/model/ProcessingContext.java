package com.github.hisaichi5518.konohana.processor.model;

import com.github.hisaichi5518.konohana.annotation.Store;
import com.github.hisaichi5518.konohana.annotation.TypeAdapter;
import com.squareup.javapoet.TypeName;

import java.util.stream.Stream;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

public class ProcessingContext {

    private final ProcessingEnvironment processingEnv;
    private final RoundEnvironment roundEnv;

    public ProcessingContext(ProcessingEnvironment processingEnv, RoundEnvironment roundEnv) {
        this.processingEnv = processingEnv;
        this.roundEnv = roundEnv;
    }

    public Stream<StoreDefinition> storeDefinitionStream() {
        return roundEnv.getElementsAnnotatedWith(Store.class)
                .stream()
                .filter(element -> element.getKind() == ElementKind.CLASS)
                .map(element -> new StoreDefinition(this, (TypeElement) element));
    }

    public Filer getFiler() {
        return processingEnv.getFiler();
    }

    public void error(String message, TypeElement element) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, element);
    }

    public void error(String message) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message);
    }

    public String getPackageName() {
        return storeDefinitionStream()
                .map(StoreDefinition::getPackageName)
                .findFirst()
                .orElse(null);
    }

    TypeAdapterDefinition getTypeAdapterDefinition(TypeName typeName) {
        Stream<TypeAdapterDefinition> typeAdapterDefinitionStream = roundEnv.getElementsAnnotatedWith(TypeAdapter.class)
                .stream()
                .filter(element -> element.getKind() == ElementKind.CLASS)
                .map(element -> TypeAdapterDefinition.create((TypeElement) element));

        return Stream.concat(typeAdapterDefinitionStream, Stream.of(TypeAdapterDefinition.BUILD_IN))
                .filter(typeAdapterDefinition -> typeAdapterDefinition.match(typeName))
                .findFirst()
                .orElse(null);
    }
}
