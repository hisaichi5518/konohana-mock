package com.github.hisaichi5518.konohana.processor;

import com.github.hisaichi5518.konohana.annotation.Store;
import com.squareup.javapoet.ClassName;

import javax.lang.model.element.TypeElement;

class StoreContext {
    final TypeElement element;

    StoreContext(TypeElement element) {
        this.element = element;
    }

    ClassName getClassName() {
        return ClassName.get(element);
    }

    String getFileName() {
        Store store = element.getAnnotation(Store.class);
        if (store.name().isEmpty()) {
            return element.getSimpleName().toString();
        }

        return store.name();
    }

    int getMode() {
        Store store = element.getAnnotation(Store.class);
        if (store.mode() < 0) {
            throw new RuntimeException("Invalid model!");
        }

        return store.mode();
    }

    ClassName getStoreClassName() {
        return ClassName.get(getClassName().packageName(), getClassName().simpleName() + "_Store");
    }
}
