package com.github.hisaichi5518.konohana.processor.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.hisaichi5518.konohana.processor.types.JavaTypes;
import com.github.hisaichi5518.konohana.processor.types.KonohanaTypes;
import com.squareup.javapoet.TypeName;

import java.util.stream.Stream;

class PrefsAdapterDefinition {

    static final PrefsAdapterDefinition[] BUILD_IN = {
            create(TypeName.INT, KonohanaTypes.IntegerPrefsAdapter),
            create(TypeName.BOOLEAN, KonohanaTypes.BooleanPrefsAdapter),
            create(TypeName.FLOAT, KonohanaTypes.FloatPrefsAdapter),
            create(TypeName.LONG, KonohanaTypes.LongPrefsAdapter),

            create(JavaTypes.String, KonohanaTypes.StringPrefsAdapter),
            create(JavaTypes.getSet(JavaTypes.String), KonohanaTypes.StringSetPrefsAdapter)
    };

    private final TypeName target;
    private final TypeName prefsAdapter;

    PrefsAdapterDefinition(@NonNull TypeName target, @NonNull TypeName prefsAdapter) {
        this.target = target;
        this.prefsAdapter = prefsAdapter;
    }

    @NonNull
    private static PrefsAdapterDefinition create(@NonNull TypeName target, @NonNull TypeName prefsAdapter) {
        return new PrefsAdapterDefinition(target, prefsAdapter);
    }

    boolean match(@NonNull ProcessingContext context, @NonNull TypeName typeName) {
        return target.equals(typeName);
    }

    @NonNull
    TypeName getPrefsAdapter() {
        return prefsAdapter;
    }

    @Nullable
    static PrefsAdapterDefinition getPrefsAdapterDefinitionFromBuildIn(@NonNull ProcessingContext context, @NonNull TypeName typeName) {
        return Stream.of(PrefsAdapterDefinition.BUILD_IN)
                .filter(prefsAdapterDefinition -> prefsAdapterDefinition.match(context, typeName))
                .findFirst()
                .orElse(null);
    }
}
