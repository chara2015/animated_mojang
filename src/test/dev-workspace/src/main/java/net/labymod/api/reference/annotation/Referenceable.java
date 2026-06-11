package net.labymod.api.reference.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/reference/annotation/Referenceable.class */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface Referenceable {
    boolean named() default false;
}
