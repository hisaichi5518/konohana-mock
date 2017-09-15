package com.github.hisaichi5518.konohana.processor;

import com.github.hisaichi5518.konohana.annotation.Key;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.VariableElement;

public class KeyContext {
    final VariableElement element;

    KeyContext(VariableElement element) {
        this.element = element;
    }

    void injectForGetterWithDefault(MethodSpec.Builder builder) {
        TypeName methodType = ClassName.get(element.asType());
        if (methodType.equals(TypeName.BOOLEAN)) {
            builder.addStatement("return prefs.getBoolean($S, false)", getRealKeyName());

        } else if (methodType.equals(TypeName.INT)) {
            builder.addStatement("return prefs.getInt($S, 0)", getRealKeyName());

        } else if (methodType.equals(TypeName.FLOAT)) {
            builder.addStatement("return prefs.getFloat($S, 0F)", getRealKeyName());

        } else if (methodType.equals(TypeName.LONG)) {
            builder.addStatement("return prefs.getLong($S, OL)", getRealKeyName());

        } else if (methodType.equals(ClassName.get(String.class))) {
            builder.addStatement("return prefs.getString($S, $S)", getRealKeyName(), "");

        } else {
            throw new RuntimeException("対応してません");
        }
    }

    void injectForGetter(MethodSpec.Builder builder) {
        TypeName methodType = ClassName.get(element.asType());
        if (methodType.equals(TypeName.BOOLEAN)) {
            builder.addParameter(ParameterSpec.builder(boolean.class, "defaultValue").build())
                    .addStatement("return prefs.getBoolean($S, defaultValue)", getRealKeyName());

        } else if (methodType.equals(TypeName.INT)) {
            builder.addParameter(ParameterSpec.builder(int.class, "defaultValue").build())
                    .addStatement("return prefs.getInt($S, defaultValue)", getRealKeyName());

        } else if (methodType.equals(TypeName.FLOAT)) {
            builder.addParameter(ParameterSpec.builder(float.class, "defaultValue").build())
                    .addStatement("return prefs.getFloat($S, defaultValue)", getRealKeyName());

        } else if (methodType.equals(TypeName.LONG)) {
            builder.addParameter(ParameterSpec.builder(long.class, "defaultValue").build())
                    .addStatement("return prefs.getLong($S, defaultValue)", getRealKeyName());

        } else if (methodType.equals(ClassName.get(String.class))) {
            builder.addParameter(ParameterSpec.builder(String.class, "defaultValue").build())
                    .addStatement("return prefs.getString($S, defaultValue)", getRealKeyName());

        } else {
            throw new RuntimeException("対応してません");
        }
    }

    void injectForSetter(MethodSpec.Builder builder) {
        TypeName methodType = ClassName.get(element.asType());
        if (methodType.equals(TypeName.BOOLEAN)) {
            builder.addParameter(ParameterSpec.builder(boolean.class, "v").build())
                    .addStatement("prefs.edit().putBoolean($S, v).apply()", getRealKeyName());

        } else if (methodType.equals(TypeName.INT)) {
            builder.addParameter(ParameterSpec.builder(int.class, "v").build())
                    .addStatement("prefs.edit().putInt($S, v).apply()", getRealKeyName());

        } else if (methodType.equals(TypeName.FLOAT)) {
            builder.addParameter(ParameterSpec.builder(float.class, "v").build())
                    .addStatement("prefs.edit().putFloat($S, v).apply()", getRealKeyName());

        } else if (methodType.equals(TypeName.LONG)) {
            builder.addParameter(ParameterSpec.builder(long.class, "v").build())
                    .addStatement("prefs.edit().putLong($S, v).apply()", getRealKeyName());

        } else if (methodType.equals(ClassName.get(String.class))) {
            builder.addParameter(ParameterSpec.builder(String.class, "v").build())
                    .addStatement("prefs.edit().putString($S, v).apply()", getRealKeyName());

        } else {
            throw new RuntimeException("対応してません");
        }
    }

    String getRealKeyName() {
        Key key = element.getAnnotation(Key.class);
        if (key.name().isEmpty()) {
            return element.getSimpleName().toString();
        }

        return key.name();
    }

    String getCapitalizedName() {
        return StringUtils.capitalize(element.getSimpleName().toString());
    }

}
