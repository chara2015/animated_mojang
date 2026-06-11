package net.labymod.api.user.badge;

import java.awt.Color;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.client.gui.icon.Icon;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/user/badge/ServerBadge.class */
public final class ServerBadge extends Record {
    private final Icon icon;
    private final Color color;
    public static final int SPACING = 2;

    public ServerBadge(Icon icon, Color color) {
        this.icon = icon;
        this.color = color;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ServerBadge.class), ServerBadge.class, "icon;color", "FIELD:Lnet/labymod/api/user/badge/ServerBadge;->icon:Lnet/labymod/api/client/gui/icon/Icon;", "FIELD:Lnet/labymod/api/user/badge/ServerBadge;->color:Ljava/awt/Color;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ServerBadge.class), ServerBadge.class, "icon;color", "FIELD:Lnet/labymod/api/user/badge/ServerBadge;->icon:Lnet/labymod/api/client/gui/icon/Icon;", "FIELD:Lnet/labymod/api/user/badge/ServerBadge;->color:Ljava/awt/Color;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ServerBadge.class, Object.class), ServerBadge.class, "icon;color", "FIELD:Lnet/labymod/api/user/badge/ServerBadge;->icon:Lnet/labymod/api/client/gui/icon/Icon;", "FIELD:Lnet/labymod/api/user/badge/ServerBadge;->color:Ljava/awt/Color;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public Icon icon() {
        return this.icon;
    }

    public Color color() {
        return this.color;
    }

    public int width() {
        return this.icon.getResolutionWidth();
    }

    public int height() {
        return 8;
    }

    public boolean isVisible() {
        return width() != 256;
    }
}
