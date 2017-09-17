package com.github.hisaichi5518.konohana.processor.types;

import com.squareup.javapoet.ClassName;

public class Annotations {

    public static final ClassName NonNull = ClassName.get(android.support.annotation.NonNull.class);

    public static final ClassName Nullable = ClassName.get(android.support.annotation.Nullable.class);
}
