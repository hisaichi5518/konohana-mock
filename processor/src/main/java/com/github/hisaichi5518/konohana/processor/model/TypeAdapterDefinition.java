package com.github.hisaichi5518.konohana.processor.model;

import android.support.annotation.NonNull;

import com.github.hisaichi5518.konohana.processor.types.JavaTypes;
import com.github.hisaichi5518.konohana.processor.types.KonohanaTypes;
import com.squareup.javapoet.TypeName;

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

    boolean match(@NonNull TypeName typeName) {
        return target.equals(typeName);
    }

    @NonNull
    TypeName getTypeAdapter() {
        return typeAdapter;
    }
}
