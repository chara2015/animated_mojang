package net.labymod.api.configuration.settings.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/annotation/SettingListener.class */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SettingListener {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/annotation/SettingListener$EventType.class */
    public enum EventType {
        INITIALIZE,
        RESET
    }

    String target();

    EventType type();
}
