package com.github.hisaichi5518.konohana.processor.model;

import com.github.hisaichi5518.konohana.annotation.Key;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.VariableElement;

class KeyDefinition {
    private final ProcessingContext context;
    private final VariableElement element;

    KeyDefinition(ProcessingContext context, VariableElement element) {
        this.context = context;
        this.element = element;
    }

    String getPrefsKeyName() {
        Key key = element.getAnnotation(Key.class);
        if (key.name().isEmpty()) {
            return element.getSimpleName().toString();
        }

        return key.name();
    }

    String getFieldName() {
        return element.getSimpleName().toString();
    }

    TypeName getFieldClassName() {
        return TypeName.get(element.asType());
    }

    String getGetterName() {
        return "get" + getCapitalizedName();
    }

    String getSetterName() {
        return "set" + getCapitalizedName();
    }

    String getRemoverName() {
        return "remove" + getCapitalizedName();
    }

    String getContainsName() {
        return "has" + getCapitalizedName();
    }

    TypeName getTypeAdapterClassName() {
        TypeAdapterDefinition definition = context.getTypeAdapterDefinition(getFieldClassName());
        if (definition == null) {
            throw new RuntimeException("TypeAdapterが見つかりませんでした");
        }

        return definition.getTypeAdapter();
    }

    private String getCapitalizedName() {
        return upperFirst(element.getSimpleName().toString());
    }

    private String upperFirst(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
