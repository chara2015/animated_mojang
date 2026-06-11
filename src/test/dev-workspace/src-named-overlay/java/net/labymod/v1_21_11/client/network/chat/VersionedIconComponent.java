package net.labymod.v1_21_11.client.network.chat;

import net.labymod.api.client.component.ComponentService;
import net.labymod.api.client.component.IconComponent;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.v1_21_11.client.component.VersionedIconContents;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/network/chat/VersionedIconComponent.class */
public class VersionedIconComponent extends VersionedBaseComponent<IconComponent, VersionedIconContents> implements IconComponent {
    public VersionedIconComponent(MutableComponent holder) {
        super(holder);
    }

    @NotNull
    public Icon getIcon() {
        return getContents().icon();
    }

    @NotNull
    public IconComponent setIcon(@NotNull Icon icon) {
        getContents().setIcon(icon);
        return this;
    }

    public int getWidth() {
        return getContents().width();
    }

    public int getHeight() {
        return getContents().height();
    }

    public IconComponent setWidth(int width) {
        getContents().setWidth(width);
        return this;
    }

    public IconComponent setHeight(int height) {
        getContents().setHeight(height);
        return this;
    }

    @NotNull
    public String getPlaceholder() {
        return getContents().placeholder();
    }

    public IconComponent setPlaceholder(@NotNull String placeholder) {
        getContents().setPlaceholder(placeholder);
        return this;
    }

    /* JADX INFO: renamed from: plainCopy, reason: merged with bridge method [inline-methods] */
    public IconComponent m18plainCopy() {
        return ComponentService.iconComponent(getIcon()).setWidth(getWidth()).setHeight(getHeight()).setPlaceholder(getPlaceholder());
    }
}
