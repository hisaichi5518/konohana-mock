package com.github.hisaichi5518.konohana.processor.model;

import com.squareup.javapoet.ClassName;

public class Types {
    static final ClassName IntegerTypeAdapter = ClassName.get("com.github.hisaichi5518.konohana.typeadapter", "IntegerTypeAdapter");

    static final ClassName BooleanTypeAdapter = ClassName.get("com.github.hisaichi5518.konohana.typeadapter", "BooleanTypeAdapter");

    static final ClassName StringTypeAdapter = ClassName.get("com.github.hisaichi5518.konohana.typeadapter", "StringTypeAdapter");
}
