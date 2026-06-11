package net.labymod.api.client.gui.screen.activity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/activity/Link.class */
@Target({ElementType.TYPE})
@Repeatable(Links.class)
@Retention(RetentionPolicy.CLASS)
public @interface Link {
    String value();

    byte priority() default 0;
}
