package com.github.hisaichi5518.konohana.processor.model;

import com.github.hisaichi5518.konohana.annotation.Key;
import com.github.hisaichi5518.konohana.annotation.Store;
import com.squareup.javapoet.ClassName;

import java.util.stream.Stream;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

public class StoreDefinition {
    public final ProcessingContext context;
    public final TypeElement element;

    StoreDefinition(ProcessingContext context, TypeElement element) {
        this.context = context;
        this.element = element;
    }

    public ClassName getClassName() {
        return ClassName.get(element);
    }

    public String getPackageName() {
        return getClassName().packageName();
    }

    public String getPrefsFileName() {
        Store store = element.getAnnotation(Store.class);
        if (store.name().isEmpty()) {
            return element.getSimpleName().toString();
        }

        return store.name();
    }

    public int getPrefsMode() {
        Store store = element.getAnnotation(Store.class);
        if (store.mode() < 0) {
            throw new RuntimeException("Invalid model!");
        }

        return store.mode();
    }

    public ClassName getStoreClassName() {
        return ClassName.get(getClassName().packageName(), getClassName().simpleName() + "_Store");
    }

    Stream<KeyDefinition> keyDefinitionStream() {
        return element.getEnclosedElements()
                .stream()
                .filter(element -> element.getAnnotation(Key.class) != null)
                .map(element -> new KeyDefinition(context, (VariableElement) element));
    }
}