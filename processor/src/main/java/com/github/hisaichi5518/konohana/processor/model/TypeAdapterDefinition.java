package com.github.hisaichi5518.konohana.processor.model;

import com.github.hisaichi5518.konohana.annotation.TypeAdapter;
import com.github.hisaichi5518.konohana.processor.types.JavaTypes;
import com.github.hisaichi5518.konohana.processor.types.KonohanaTypes;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.TypeElement;

class TypeAdapterDefinition {

    static final TypeAdapterDefinition[] BUILD_IN = {
            create(TypeName.INT, KonohanaTypes.IntegerTypeAdapter),
            create(TypeName.BOOLEAN, KonohanaTypes.BooleanTypeAdapter),
            create(TypeName.FLOAT, KonohanaTypes.FloatTypeAdapter),

            create(ClassName.get(String.class), KonohanaTypes.StringTypeAdapter),
            create(JavaTypes.getSet(JavaTypes.String), KonohanaTypes.StringSetTypeAdapter)
    };

    private final TypeName target;
    private final TypeName typeAdapter;

    private TypeAdapterDefinition(TypeName target, TypeName typeAdapter) {
        this.target = target;
        this.typeAdapter = typeAdapter;
    }

    private static TypeAdapterDefinition create(TypeName target, TypeName typeAdapter) {
        return new TypeAdapterDefinition(target, typeAdapter);
    }

    static TypeAdapterDefinition create(TypeElement typeElement) {
        TypeAdapter adapter = typeElement.getAnnotation(TypeAdapter.class);
        return new TypeAdapterDefinition(ClassName.get(adapter.value()), ClassName.get(typeElement));
    }

    boolean match(TypeName typeName) {
        return target.toString().equals(typeName.toString());
    }

    TypeName getTypeAdapter() {
        return typeAdapter;
    }
}
