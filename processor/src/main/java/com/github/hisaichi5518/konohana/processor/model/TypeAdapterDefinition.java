package com.github.hisaichi5518.konohana.processor.model;

import android.support.annotation.NonNull;

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
            create(TypeName.LONG, KonohanaTypes.LongTypeAdapter),

            create(JavaTypes.String, KonohanaTypes.StringTypeAdapter),
            create(JavaTypes.getSet(JavaTypes.String), KonohanaTypes.StringSetTypeAdapter)
    };

    private final TypeName target;
    private final TypeName typeAdapter;

    private TypeAdapterDefinition(@NonNull TypeName target, @NonNull TypeName typeAdapter) {
        this.target = target;
        this.typeAdapter = typeAdapter;
    }

    @NonNull
    private static TypeAdapterDefinition create(@NonNull TypeName target, @NonNull TypeName typeAdapter) {
        return new TypeAdapterDefinition(target, typeAdapter);
    }

    @NonNull
    static TypeAdapterDefinition create(@NonNull TypeElement typeElement) {
        TypeAdapter adapter = typeElement.getAnnotation(TypeAdapter.class);
        return new TypeAdapterDefinition(ClassName.get(adapter.value()), ClassName.get(typeElement));
    }

    boolean match(@NonNull TypeName typeName) {
        return target.toString().equals(typeName.toString());
    }

    @NonNull
    TypeName getTypeAdapter() {
        return typeAdapter;
    }
}
