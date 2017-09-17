package com.github.hisaichi5518.konohana.processor.model;

import android.support.annotation.NonNull;

import com.github.hisaichi5518.konohana.annotation.Key;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.VariableElement;

class KeyDefinition {
    private final ProcessingContext context;
    private final VariableElement element;

    KeyDefinition(@NonNull ProcessingContext context, @NonNull VariableElement element) {
        this.context = context;
        this.element = element;
    }

    @NonNull
    String getPrefsKeyName() {
        Key key = element.getAnnotation(Key.class);
        if (key.name().isEmpty()) {
            return element.getSimpleName().toString();
        }

        return key.name();
    }

    @NonNull
    String getFieldName() {
        return element.getSimpleName().toString();
    }

    @NonNull
    TypeName getFieldClassName() {
        return TypeName.get(element.asType());
    }

    @NonNull
    String getGetterName() {
        return "get" + getCapitalizedName();
    }

    @NonNull
    String getSetterName() {
        return "set" + getCapitalizedName();
    }

    @NonNull
    String getRemoverName() {
        return "remove" + getCapitalizedName();
    }

    @NonNull
    String getContainsName() {
        return "has" + getCapitalizedName();
    }

    @NonNull
    TypeName getTypeAdapterTypeName() {
        TypeAdapterDefinition definition = context.getTypeAdapterDefinition(getFieldClassName());
        if (definition == null) {
            // FIXME: error message
            throw new RuntimeException("TypeAdapterが見つかりませんでした");
        }

        return definition.getTypeAdapter();
    }

    @NonNull
    private String getCapitalizedName() {
        return upperFirst(element.getSimpleName().toString());
    }

    @NonNull
    private String upperFirst(@NonNull String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
