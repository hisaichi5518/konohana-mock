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
    private final Key key;
    private final TypeName fieldTypeName;
    private final String capitalizedName;
    private final TypeName prefsAdapterTypeName;

    KeyDefinition(@NonNull ProcessingContext context, @NonNull VariableElement element) {
        this.context = context;
        this.element = element;

        key = element.getAnnotation(Key.class);
        fieldTypeName = TypeName.get(element.asType());
        capitalizedName = upperFirst(element.getSimpleName().toString());
        prefsAdapterTypeName = _getPrefsAdapterTypeName();

        // Validation
        if (fieldTypeName.isBoxedPrimitive()) {
            throw new ProcessingException("Cannot use boxed primitive type!", element);
        }
    }

    @NonNull
    String getPrefsKeyName() {
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
    TypeName getFieldTypeName() {
        return fieldTypeName;
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
        return prefsAdapterTypeName;
    }

    @NonNull
    private String getCapitalizedName() {
        return capitalizedName;
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

    @NonNull
    private TypeName _getPrefsAdapterTypeName() {
        TypeName customPrefsAdapter = getCustomPrefsAdapter();
        PrefsAdapterDefinition definition;
        if (customPrefsAdapter.equals(KonohanaTypes.UseBuildInPrefsAdapter)) {
            // Use BuildIn PrefsAdapter
            definition = context.getPrefsAdapterDefinition(getFieldTypeName());
        } else {
            // Use Custom PrefsAdapter
            definition = new PrefsAdapterDefinition(getFieldTypeName(), customPrefsAdapter);
        }

        if (definition == null) {
            // ex) Can not find available PrefsAdapter for admin field(type: Boolean) of User class
            throw new ProcessingException(
                    "Can not find available PrefsAdapter for "
                            + element.getSimpleName() + " field(type: " + getFieldTypeName().toString() + ")"
                            + " of " + element.getEnclosingElement().getSimpleName() + " class.", element);
        }

        return definition.getPrefsAdapter();
    }

}
