package net.labymod.v1_17_1.mixins.client.chat;

import net.labymod.api.client.component.IconComponent;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.v1_17_1.client.component.VersionedIconComponent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/client/chat/MixinIconComponent.class */
@Mixin({VersionedIconComponent.class})
@Implements({@Interface(iface = IconComponent.class, prefix = "iconComponent$", remap = Interface.Remap.NONE)})
public abstract class MixinIconComponent extends MixinBaseComponent<IconComponent> implements IconComponent {

    @Mutable
    @Shadow(remap = false)
    @Final
    private Icon icon;

    @Shadow(remap = false)
    private int width;

    @Shadow(remap = false)
    private int height;

    @Shadow(remap = false)
    private String placeholder;

    @Intrinsic
    @NotNull
    public Icon iconComponent$getIcon() {
        return this.icon;
    }

    @Intrinsic
    @NotNull
    public IconComponent iconComponent$setIcon(@NotNull Icon icon) {
        this.icon = icon;
        return this;
    }

    @Intrinsic
    public int iconComponent$getWidth() {
        return this.width;
    }

    @Intrinsic
    public int iconComponent$getHeight() {
        return this.height;
    }

    @Intrinsic
    @NotNull
    public IconComponent iconComponent$setWidth(int width) {
        this.width = width;
        return this;
    }

    @Intrinsic
    @NotNull
    public IconComponent iconComponent$setHeight(int height) {
        this.height = height;
        return this;
    }

    @Intrinsic
    @NotNull
    public String iconComponent$getPlaceholder() {
        return this.placeholder;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Intrinsic
    public IconComponent iconComponent$setPlaceholder(@NotNull String placeholder) {
        this.placeholder = placeholder;
        ((TextComponent) this).text(placeholder);
        return this;
    }

    @Intrinsic
    public IconComponent iconComponent$plainCopy() {
        IconComponent component = (IconComponent) new VersionedIconComponent(this.icon);
        component.setWidth(this.width);
        component.setHeight(this.height);
        component.setPlaceholder(this.placeholder);
        return component;
    }
}
