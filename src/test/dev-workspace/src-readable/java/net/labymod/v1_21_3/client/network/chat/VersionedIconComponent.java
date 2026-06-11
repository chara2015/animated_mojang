package net.labymod.v1_21_3.client.network.chat;

import net.labymod.api.client.component.ComponentService;
import net.labymod.api.client.component.IconComponent;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.v1_21_3.client.component.VersionedIconContents;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/client/network/chat/VersionedIconComponent.class */
public class VersionedIconComponent extends VersionedBaseComponent<IconComponent, VersionedIconContents> implements IconComponent {
    public VersionedIconComponent(yj holder) {
        super(holder);
    }

    @Override // net.labymod.api.client.component.IconComponent
    @NotNull
    public Icon getIcon() {
        return getContents().icon();
    }

    @Override // net.labymod.api.client.component.IconComponent
    @NotNull
    public IconComponent setIcon(@NotNull Icon icon) {
        getContents().setIcon(icon);
        return this;
    }

    @Override // net.labymod.api.client.component.IconComponent
    public int getWidth() {
        return getContents().width();
    }

    @Override // net.labymod.api.client.component.IconComponent
    public int getHeight() {
        return getContents().height();
    }

    @Override // net.labymod.api.client.component.IconComponent
    public IconComponent setWidth(int width) {
        getContents().setWidth(width);
        return this;
    }

    @Override // net.labymod.api.client.component.IconComponent
    public IconComponent setHeight(int height) {
        getContents().setHeight(height);
        return this;
    }

    @Override // net.labymod.api.client.component.IconComponent
    @NotNull
    public String getPlaceholder() {
        return getContents().placeholder();
    }

    @Override // net.labymod.api.client.component.IconComponent
    public IconComponent setPlaceholder(@NotNull String placeholder) {
        getContents().setPlaceholder(placeholder);
        return this;
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public IconComponent plainCopy() {
        return ComponentService.iconComponent(getIcon()).setWidth(getWidth()).setHeight(getHeight()).setPlaceholder(getPlaceholder());
    }
}
