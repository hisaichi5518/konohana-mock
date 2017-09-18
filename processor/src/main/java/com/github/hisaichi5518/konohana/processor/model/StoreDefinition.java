package com.github.hisaichi5518.konohana.processor.model;

import android.support.annotation.NonNull;

import com.github.hisaichi5518.konohana.annotation.Key;
import com.github.hisaichi5518.konohana.annotation.Store;
import com.squareup.javapoet.ClassName;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

public class StoreDefinition {
    public final ProcessingContext context;
    public final TypeElement element;

    private final ClassName interfaceName;
    private final String packageName;
    private final ClassName storeClassName;
    private final Store store;
    private final List<KeyDefinition> keys;

    StoreDefinition(@NonNull ProcessingContext context, @NonNull TypeElement element) {
        this.context = context;
        this.element = element;

        interfaceName = ClassName.get(element);
        packageName = interfaceName.packageName();
        storeClassName = ClassName.get(interfaceName.packageName(), interfaceName.simpleName() + "_Store");

        store = element.getAnnotation(Store.class);

        keys = element.getEnclosedElements()
                .stream()
                .filter(e -> e.getAnnotation(Key.class) != null)
                .map(e -> new KeyDefinition(context, (VariableElement) e)).collect(Collectors.toList());
    }

    @NonNull
    public ClassName getInterfaceName() {
        return interfaceName;
    }

    @NonNull
    String getPackageName() {
        return packageName;
    }

    @NonNull
    public ClassName getStoreClassName() {
        return storeClassName;
    }

    @NonNull
    public String getPrefsFileName() {
        if (store.name().isEmpty()) {
            return element.getSimpleName().toString();
        }

        return store.name();
    }

    public int getPrefsMode() {
        if (store.mode() < 0) {
            throw new RuntimeException("Invalid model!");
        }

        return store.mode();
    }

    @NonNull
    Stream<KeyDefinition> keyDefinitionStream() {
        return keys.stream();
    }
}
