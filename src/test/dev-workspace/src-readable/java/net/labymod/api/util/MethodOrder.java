package net.labymod.api.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/MethodOrder.class */
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodOrder {
    String before() default "";

    String after() default "";
}
