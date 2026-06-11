package net.labymod.api.configuration.settings.creator;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/creator/MemberInspector.class */
public class MemberInspector {
    private final AnnotatedElement member;
    private final String name;
    private final Map<Class<? extends Annotation>, AnnotationHolder> annotationCache = new HashMap();

    public MemberInspector(AnnotatedElement member) {
        if (!(member instanceof Member)) {
            throw new IllegalArgumentException(member.getClass().getName() + " is not an instance of " + Member.class.getName());
        }
        Member reflectiveMember = (Member) member;
        this.member = member;
        this.name = reflectiveMember.getName();
    }

    public String getName() {
        return this.name;
    }

    public <A extends Annotation> A getAnnotation(Class<A> cls) {
        AnnotationHolder annotationHolderComputeIfAbsent = this.annotationCache.computeIfAbsent(cls, cls2 -> {
            return AnnotationHolder.of(this.member, cls2);
        });
        if (annotationHolderComputeIfAbsent.isEmpty()) {
            return null;
        }
        return (A) annotationHolderComputeIfAbsent.getAnnotation();
    }

    public <A extends Annotation, T> T get(Class<A> cls, Function<A, T> function) {
        return (T) getOrElse(cls, function, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <A extends Annotation, T> T getOrElse(Class<A> cls, Function<A, T> function, T t) {
        Annotation annotation = getAnnotation(cls);
        if (annotation == null) {
            return t;
        }
        return (T) function.apply(annotation);
    }

    public <A extends Annotation> boolean isAnnotationPresent(Class<A> annotationClass) {
        return getAnnotation(annotationClass) != null;
    }

    public <A extends Annotation> A orElse(Class<A> cls, A a) {
        A a2 = (A) getAnnotation(cls);
        return a2 == null ? a : a2;
    }

    public AnnotatedElement member() {
        return this.member;
    }

    public String toString() {
        return this.name;
    }
}
