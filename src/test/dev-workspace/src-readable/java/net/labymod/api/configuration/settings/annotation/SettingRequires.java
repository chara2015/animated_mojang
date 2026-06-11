package net.labymod.api.configuration.settings.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/annotation/SettingRequires.class */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SettingRequires {
    String value();

    boolean invert() default false;

    String required() default "true";
}
