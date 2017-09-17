package com.github.hisaichi5518.konohana.processor.types;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

public class JavaTypes {

    private static final ClassName Set = ClassName.get(java.util.Set.class);

    public static final ClassName String = ClassName.get(String.class);

    public static ParameterizedTypeName getSet(TypeName typeName) {
        return ParameterizedTypeName.get(Set, typeName);
    }
}
