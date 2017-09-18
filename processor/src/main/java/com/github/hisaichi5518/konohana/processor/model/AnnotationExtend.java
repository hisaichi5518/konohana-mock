package com.github.hisaichi5518.konohana.processor.model;

import android.support.annotation.Nullable;

import com.google.auto.common.AnnotationMirrors;
import com.google.auto.common.MoreElements;

import java.lang.annotation.Annotation;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.AnnotationValueVisitor;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleAnnotationValueVisitor6;

class AnnotationExtend {
    private static final AnnotationValueVisitor<TypeMirror, Void> TO_TYPE = new SimpleAnnotationValueVisitor6<TypeMirror, Void>() {
        @Override
        public TypeMirror visitType(TypeMirror t, Void aVoid) {
            return t;
        }
    };

    @Nullable
    static TypeMirror getValue(Element element, Class<? extends Annotation> annotationType, String argName) {
        AnnotationMirror am = MoreElements.getAnnotationMirror(element, annotationType).get();
        AnnotationValue av = AnnotationMirrors.getAnnotationValue(am, argName);
        return TO_TYPE.visit(av);
    }
}
