package com.github.hisaichi5518.konohana.processor.model;

import android.support.annotation.NonNull;

import com.github.hisaichi5518.konohana.processor.types.JavaTypes;
import com.github.hisaichi5518.konohana.processor.types.KonohanaTypes;
import com.squareup.javapoet.TypeName;

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

    private PrefsAdapterDefinition(@NonNull TypeName target, @NonNull TypeName prefsAdapter) {
        this.target = target;
        this.prefsAdapter = prefsAdapter;
    }

    @NonNull
    private static PrefsAdapterDefinition create(@NonNull TypeName target, @NonNull TypeName prefsAdapter) {
        return new PrefsAdapterDefinition(target, prefsAdapter);
    }

    boolean match(@NonNull TypeName typeName) {
        return target.equals(typeName);
    }

    @NonNull
    TypeName getPrefsAdapter() {
        return prefsAdapter;
    }
}
