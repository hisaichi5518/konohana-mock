package com.github.hisaichi5518.konohana.processor;

import com.github.hisaichi5518.konohana.annotation.Key;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.VariableElement;

public class KeyContext {
    final VariableElement element;

    KeyContext(VariableElement element) {
        this.element = element;
    }

    void injectMethodSpec(MethodSpec.Builder builder) {
        TypeName methodType = ClassName.get(element.asType());
        if (methodType.equals(TypeName.BOOLEAN)) {
            builder.addStatement("return prefs.getBoolean($S, false)", getName());
        } else if (methodType.equals(TypeName.INT)) {
            builder.addStatement("return prefs.getInt($S, 0)", getName());
        } else if (methodType.equals(TypeName.FLOAT)) {
            builder.addStatement("return prefs.getFloat($S, 0)", getName());
        } else if (methodType.equals(TypeName.LONG)) {
            builder.addStatement("return prefs.getLong($S, 0)", getName());
        } else if (methodType.equals(ClassName.get(String.class))) {
            builder.addStatement("return prefs.getString($S, null)", getName());
        } else {
            throw new RuntimeException("対応してません");
        }
    }

    private String getName() {
        Key key = element.getAnnotation(Key.class);
        if (key.name().isEmpty()) {
            return element.getSimpleName().toString();
        }

        return key.name();
    }
}
