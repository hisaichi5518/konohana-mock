package com.github.hisaichi5518.konohana.processor.types;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

public class AndroidTypes {

    public static final TypeName Context = ClassName.get("android.content", "Context");

    public static final TypeName SharedPreferences = ClassName.get("android.content", "SharedPreferences");

    public static final TypeName OnSharedPreferenceChangeListener = ClassName.get("android.content", "SharedPreferences", "OnSharedPreferenceChangeListener");

}
