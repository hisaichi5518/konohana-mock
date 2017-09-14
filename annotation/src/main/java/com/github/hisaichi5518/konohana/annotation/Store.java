package com.github.hisaichi5518.konohana.annotation;

import android.content.Context;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;


@Target(ElementType.TYPE)
public @interface Store {
    String name() default "";

    int mode() default Context.MODE_PRIVATE;
}
