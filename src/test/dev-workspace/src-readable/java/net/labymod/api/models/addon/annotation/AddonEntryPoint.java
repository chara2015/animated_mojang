package net.labymod.api.models.addon.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/models/addon/annotation/AddonEntryPoint.class */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AddonEntryPoint {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/models/addon/annotation/AddonEntryPoint$Point.class */
    public enum Point {
        EARLY_INITIALIZATION,
        LOAD,
        ENABLE
    }

    Point value() default Point.EARLY_INITIALIZATION;

    int priority() default 1000;
}
