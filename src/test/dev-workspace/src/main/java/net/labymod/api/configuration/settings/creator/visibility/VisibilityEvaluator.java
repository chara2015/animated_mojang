package net.labymod.api.configuration.settings.creator.visibility;

import java.lang.annotation.Annotation;
import java.util.function.BooleanSupplier;
import net.labymod.api.configuration.settings.creator.MemberInspector;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/creator/visibility/VisibilityEvaluator.class */
public abstract class VisibilityEvaluator<A extends Annotation> {
    private final Class<A> annotationClass;

    public abstract BooleanSupplier canSeeElement(A a, MemberInspector memberInspector);

    public VisibilityEvaluator(Class<A> annotationClass) {
        this.annotationClass = annotationClass;
    }

    public boolean hasAnnotation(A a) {
        return a != null;
    }

    public Class<A> getAnnotationClass() {
        return this.annotationClass;
    }
}
