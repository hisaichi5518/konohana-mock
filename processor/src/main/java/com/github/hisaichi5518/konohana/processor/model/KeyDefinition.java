package com.github.hisaichi5518.konohana.processor.model;

import android.support.annotation.NonNull;

import com.github.hisaichi5518.konohana.annotation.Key;
import com.github.hisaichi5518.konohana.processor.exception.ProcessingException;
import com.github.hisaichi5518.konohana.processor.types.KonohanaTypes;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

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
    TypeName getPrefsAdapterTypeName() {
        TypeName customPrefsAdapter = getCustomPrefsAdapter();
        PrefsAdapterDefinition definition;
        if (customPrefsAdapter.equals(KonohanaTypes.UseBuildInPrefsAdapter)) {
            // Use BuildIn PrefsAdapter
            definition = context.getPrefsAdapterDefinition(getFieldClassName());
        } else {
            // Use Custom PrefsAdapter
            definition = new PrefsAdapterDefinition(getFieldClassName(), customPrefsAdapter);
        }

        if (definition == null) {
            // ex) Can not find available PrefsAdapter for admin field(type: Boolean) of User class
            throw new ProcessingException(
                    "Can not find available PrefsAdapter for "
                            + element.getSimpleName() + " field(type: " + getFieldClassName().toString() + ")"
                            + " of " + element.getEnclosingElement().getSimpleName() + " class.", element);
        }

        return definition.getPrefsAdapter();
    }

    @NonNull
    private String getCapitalizedName() {
        return upperFirst(element.getSimpleName().toString());
    }

    @NonNull
    private String upperFirst(@NonNull String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private TypeName getCustomPrefsAdapter() {
        TypeMirror typeMirror = AnnotationExtend.getValue(element, Key.class, "prefsAdapter");
        if (typeMirror == null) {
            throw new ProcessingException("prefsAdapter is null", element);
        }

        return ClassName.get(typeMirror);
    }
}
