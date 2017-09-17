package com.github.hisaichi5518.konohana.processor.model;

import com.github.hisaichi5518.konohana.annotation.TypeAdapter;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.TypeElement;

class TypeAdapterDefinition {

    static final TypeAdapterDefinition[] BUILD_IN = {
            create(TypeName.INT, Types.IntegerTypeAdapter),
            create(TypeName.BOOLEAN, Types.BooleanTypeAdapter),

            create(ClassName.get(String.class), Types.StringTypeAdapter),
    };

    private final TypeName target;
    private final ClassName typeAdapter;

    public TypeAdapterDefinition(TypeName target, ClassName typeAdapter) {
        this.target = target;
        this.typeAdapter = typeAdapter;
    }

    public static TypeAdapterDefinition create(TypeName target, ClassName typeAdapter) {
        return new TypeAdapterDefinition(target, typeAdapter);
    }

    public static TypeAdapterDefinition create(TypeElement typeElement) {
        TypeAdapter adapter = typeElement.getAnnotation(TypeAdapter.class);
        return new TypeAdapterDefinition(ClassName.get(adapter.value()), ClassName.get(typeElement));
    }

    public boolean match(TypeName typeName) {

        System.out.print(target);

        System.out.print("/");

        System.out.print(typeName);

        System.out.print(",");


        return target.toString().equals(typeName.toString());
    }

    ClassName getTypeAdapter() {
        return typeAdapter;
    }
}
