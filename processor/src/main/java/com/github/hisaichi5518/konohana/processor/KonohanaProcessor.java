package com.github.hisaichi5518.konohana.processor;

import com.github.hisaichi5518.konohana.annotation.Store;
import com.google.auto.service.AutoService;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.github.hisaichi5518.konohana.annotation.Store"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class KonohanaProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        roundEnv.getElementsAnnotatedWith(Store.class)
                .stream()
                .filter(element -> element.getKind() == ElementKind.CLASS)
                .map(element -> (TypeElement) element)
                .forEach(element -> {
                    try {
                        new StoreWriter(processingEnv, element).write();
                    } catch (Exception e) {
                        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage(), element);
                    }
                });

        return true;
    }
}
