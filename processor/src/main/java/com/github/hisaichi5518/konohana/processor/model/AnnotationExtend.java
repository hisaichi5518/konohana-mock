package com.github.hisaichi5518.konohana.processor.model;

import android.support.annotation.Nullable;

import java.lang.annotation.Annotation;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.AnnotationValueVisitor;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleAnnotationValueVisitor6;

import static com.google.auto.common.AnnotationMirrors.getAnnotationValue;
import static com.google.auto.common.MoreElements.getAnnotationMirror;

public class AnnotationExtend {
    private static final AnnotationValueVisitor<TypeMirror, Void> TO_TYPE = new SimpleAnnotationValueVisitor6<TypeMirror, Void>() {
        @Override
        public TypeMirror visitType(TypeMirror t, Void aVoid) {
            return t;
        }
    };

    @Nullable
    public static TypeMirror getValue(Element element, Class<? extends Annotation> annotationType, String argName) {
        AnnotationMirror am = getAnnotationMirror(element, annotationType).get();
        AnnotationValue av = getAnnotationValue(am, argName);
        return TO_TYPE.visit(av);
    }
}
