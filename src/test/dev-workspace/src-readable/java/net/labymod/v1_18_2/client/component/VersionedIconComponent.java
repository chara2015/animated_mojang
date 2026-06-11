package net.labymod.v1_18_2.client.component;

import net.labymod.api.client.gui.icon.Icon;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/client/component/VersionedIconComponent.class */
public class VersionedIconComponent extends qx {
    private final Icon icon;
    private int width;
    private int height;
    private String placeholder;

    public VersionedIconComponent(Icon icon) {
        super("");
        this.icon = icon;
    }

    @NotNull
    public Icon icon() {
        return this.icon;
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    @NotNull
    public String placeholder() {
        return this.placeholder;
    }

    /* JADX INFO: renamed from: i, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public qx g() {
        VersionedIconComponent component = new VersionedIconComponent(this.icon);
        component.width = this.width;
        component.height = this.height;
        component.placeholder = this.placeholder;
        return component;
    }

    @NotNull
    public String toString() {
        return "IconComponent{icon=" + String.valueOf(this.icon) + ", width=" + this.width + ", height=" + this.height + ", placeholder='" + this.placeholder + "', siblings=" + String.valueOf(this.a) + ", style=" + String.valueOf(c()) + "}";
    }
}
