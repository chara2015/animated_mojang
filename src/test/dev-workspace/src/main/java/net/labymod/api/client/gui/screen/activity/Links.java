package net.labymod.api.client.gui.screen.activity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/activity/Links.class */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface Links {
    Link[] value();
}
