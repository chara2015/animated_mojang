package net.labymod.api.test;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/test/IntegrationTest.class */
@Target({ElementType.METHOD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface IntegrationTest {
    TestPhase phase() default TestPhase.AFTER_STARTUP;

    long timeout() default 30000;

    int priority() default 0;

    String description() default "";
}
