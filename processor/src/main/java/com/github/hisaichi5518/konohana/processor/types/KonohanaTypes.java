package com.github.hisaichi5518.konohana.processor.types;

import com.squareup.javapoet.ClassName;

public class KonohanaTypes {

    private static final String PACKAGE = "com.github.hisaichi5518.konohana";

    private static final String TYPE_ADAPTER_PACKAGE = PACKAGE + ".prefsadapter";

    public static final ClassName IntegerPrefsAdapter = ClassName.get(TYPE_ADAPTER_PACKAGE, "IntegerPrefsAdapter");

    public static final ClassName FloatPrefsAdapter = ClassName.get(TYPE_ADAPTER_PACKAGE, "FloatPrefsAdapter");

    public static final ClassName LongPrefsAdapter = ClassName.get(TYPE_ADAPTER_PACKAGE, "LongPrefsAdapter");

    public static final ClassName BooleanPrefsAdapter = ClassName.get(TYPE_ADAPTER_PACKAGE, "BooleanPrefsAdapter");

    public static final ClassName StringPrefsAdapter = ClassName.get(TYPE_ADAPTER_PACKAGE, "StringPrefsAdapter");

    public static final ClassName StringSetPrefsAdapter = ClassName.get(TYPE_ADAPTER_PACKAGE, "StringSetPrefsAdapter");

    public static final ClassName UseBuildInPrefsAdapter = ClassName.get(PACKAGE + ".annotation", "Key", "UseBuildInPrefsAdapter");

}
