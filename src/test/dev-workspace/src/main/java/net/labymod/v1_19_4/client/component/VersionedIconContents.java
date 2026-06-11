package net.labymod.v1_19_4.client.component;

import java.util.Optional;
import net.labymod.api.client.gui.icon.Icon;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/client/component/VersionedIconContents.class */
public class VersionedIconContents implements tk {
    private Icon icon;
    private int width;
    private int height;
    private String placeholder;

    public VersionedIconContents(Icon icon) {
        this.icon = icon;
    }

    @NotNull
    public Icon icon() {
        return this.icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public int width() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int height() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @NotNull
    public String placeholder() {
        return this.placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    @NotNull
    public <T> Optional<T> a(@NotNull a<T> consumer) {
        return consumer.accept(this.placeholder);
    }

    @NotNull
    public <T> Optional<T> a(@NotNull b<T> consumer, @NotNull uf style) {
        return consumer.accept(style, this.placeholder);
    }

    @NotNull
    public String toString() {
        return "icon{icon=" + String.valueOf(this.icon) + ", width=" + this.width + ", height=" + this.height + ", placeholder='" + this.placeholder + "'}";
    }
}
