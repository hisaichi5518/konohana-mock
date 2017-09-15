package com.github.hisaichi5518.konohana.processor;

import com.github.hisaichi5518.konohana.annotation.Store;
import com.google.auto.service.AutoService;

import java.util.ArrayList;
import java.util.List;
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

        List<StoreContext> storeContexts = new ArrayList<>();
        roundEnv.getElementsAnnotatedWith(Store.class)
                .stream()
                .filter(element -> element.getKind() == ElementKind.CLASS)
                .map(element -> (TypeElement) element)
                .forEach(element -> {
                    try {

                        StoreContext storeContext = new StoreContext(element);
                        new StoreWriter(processingEnv, storeContext).write();
                        storeContexts.add(storeContext);
                    } catch (Exception e) {
                        // FIXME: error element
                        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage(), element);
                    }
                });

        if (storeContexts.size() != 0) {
            try {
                new KonohanaWriter(processingEnv, new KonohanaContext(storeContexts)).write();
            } catch (Exception e) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
            }
        }

        return false;
    }
}
