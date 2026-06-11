package net.labymod.api.configuration.settings.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.labymod.api.configuration.settings.SwitchableHandler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/annotation/SettingElement.class */
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SettingElement {
    boolean extended() default false;

    Class<? extends SwitchableHandler> switchable() default SwitchableHandler.class;
}
