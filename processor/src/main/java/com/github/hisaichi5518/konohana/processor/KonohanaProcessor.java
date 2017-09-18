package com.github.hisaichi5518.konohana.processor;

import com.github.hisaichi5518.konohana.processor.exception.ProcessingException;
import com.github.hisaichi5518.konohana.processor.model.ProcessingContext;
import com.github.hisaichi5518.konohana.processor.writer.KonohanaWriter;
import com.github.hisaichi5518.konohana.processor.writer.StoreWriter;
import com.google.auto.service.AutoService;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.github.hisaichi5518.konohana.annotation.Store"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class KonohanaProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        long t0 = System.nanoTime();

        ProcessingContext context = new ProcessingContext(processingEnv, roundEnv);
        try {
            context.storeDefinitionStream().forEach(
                    storeDefinition -> new StoreWriter(storeDefinition).write());

            new KonohanaWriter(context).write();
        } catch (ProcessingException e) {
            context.error(e.getMessage(), e.getElement());
        }

        context.note("Finished: " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - t0) + "ms");

        return true;
    }
}
