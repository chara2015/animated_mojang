package net.labymod.api.configuration.settings.creator;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/creator/AnnotationHolder.class */
public class AnnotationHolder {
    private static final AnnotationHolder EMPTY = new AnnotationHolder(null);
    private final Annotation annotation;

    private AnnotationHolder(Annotation annotation) {
        this.annotation = annotation;
    }

    public Annotation getAnnotation() {
        return this.annotation;
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }

    public static AnnotationHolder of(AnnotatedElement element, Class<? extends Annotation> annotationClass) {
        Annotation annotation = element.getAnnotation(annotationClass);
        return annotation == null ? EMPTY : new AnnotationHolder(annotation);
    }
}
