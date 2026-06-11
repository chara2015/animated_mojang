package net.labymod.api.configuration.settings.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/annotation/SettingOrder.class */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SettingOrder {
    byte value();

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/annotation/SettingOrder$Order.class */
    public static class Order {
        public static final byte FIRST = -127;
        public static final byte SOON = -64;
        public static final byte NORMAL = 0;
        public static final byte LATE = 64;
        public static final byte LAST = 126;

        private Order() {
        }
    }
}
