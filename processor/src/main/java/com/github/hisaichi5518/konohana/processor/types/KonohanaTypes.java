package com.github.hisaichi5518.konohana.processor.types;

import com.squareup.javapoet.ClassName;

public class KonohanaTypes {

    private static final String PACKAGE = "com.github.hisaichi5518.konohana";

    private static final String TYPE_ADAPTER_PACKAGE = PACKAGE + ".typeadapter";

    public static final ClassName IntegerTypeAdapter = ClassName.get(TYPE_ADAPTER_PACKAGE, "IntegerTypeAdapter");

    public static final ClassName FloatTypeAdapter = ClassName.get(TYPE_ADAPTER_PACKAGE, "FloatTypeAdapter");

    public static final ClassName BooleanTypeAdapter = ClassName.get(TYPE_ADAPTER_PACKAGE, "BooleanTypeAdapter");

    public static final ClassName StringTypeAdapter = ClassName.get(TYPE_ADAPTER_PACKAGE, "StringTypeAdapter");

    public static final ClassName StringSetTypeAdapter = ClassName.get(TYPE_ADAPTER_PACKAGE, "StringSetTypeAdapter");

}
