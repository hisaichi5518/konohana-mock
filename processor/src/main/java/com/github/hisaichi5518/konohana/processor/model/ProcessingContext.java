package com.github.hisaichi5518.konohana.processor.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.hisaichi5518.konohana.annotation.Store;

import java.util.stream.Stream;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

public class ProcessingContext {

    private final ProcessingEnvironment processingEnv;
    private final RoundEnvironment roundEnv;

    public ProcessingContext(@NonNull ProcessingEnvironment processingEnv, @NonNull RoundEnvironment roundEnv) {
        this.processingEnv = processingEnv;
        this.roundEnv = roundEnv;
    }

    @NonNull
    public Stream<StoreDefinition> storeDefinitionStream() {
        return roundEnv.getElementsAnnotatedWith(Store.class)
                .stream()
                .filter(element -> element.getKind() == ElementKind.INTERFACE)
                .map(element -> new StoreDefinition(this, (TypeElement) element));
    }

    @NonNull
    public Filer getFiler() {
        return processingEnv.getFiler();
    }

    public void error(@NonNull String message, @NonNull Element element) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, element);
    }

    public void error(@NonNull String message) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message);
    }

    @Nullable
    public String getPackageName() {
        // FIXME: throw Exception if package is null
        return storeDefinitionStream()
                .map(StoreDefinition::getPackageName)
                .findFirst()
                .orElse(null);
    }

    public void note(String message) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);
    }
}
