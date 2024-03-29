package com.github.hisaichi5518.konohana.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;


@Target(ElementType.TYPE)
public @interface Store {
    String name() default "";

    int mode() default 0; // Context.MODE_PRIVATE
}
