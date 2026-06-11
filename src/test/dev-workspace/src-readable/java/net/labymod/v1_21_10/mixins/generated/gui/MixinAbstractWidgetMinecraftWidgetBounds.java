package net.labymod.v1_21_10.mixins.generated.gui;

import net.labymod.api.client.gui.MinecraftWidgetBounds;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/generated/gui/MixinAbstractWidgetMinecraftWidgetBounds.class */
@Mixin({gdn.class})
public abstract class MixinAbstractWidgetMinecraftWidgetBounds implements MinecraftWidgetBounds {

    @Shadow
    public int c;

    @Shadow
    public int d;

    @Shadow
    public int f;

    @Shadow
    public int g;

    @Shadow
    public abstract xx A();

    @Override // net.labymod.api.client.gui.MinecraftWidgetBounds
    public int getBoundsX() {
        return this.c;
    }

    @Override // net.labymod.api.client.gui.MinecraftWidgetBounds
    public void setBoundsX(int x) {
        this.c = x;
    }

    @Override // net.labymod.api.client.gui.MinecraftWidgetBounds
    public int getBoundsY() {
        return this.d;
    }

    @Override // net.labymod.api.client.gui.MinecraftWidgetBounds
    public void setBoundsY(int y) {
        this.d = y;
    }

    @Override // net.labymod.api.client.gui.MinecraftWidgetBounds
    public int getBoundsWidth() {
        return this.f;
    }

    @Override // net.labymod.api.client.gui.MinecraftWidgetBounds
    public void setBoundsWidth(int width) {
        this.f = width;
    }

    @Override // net.labymod.api.client.gui.MinecraftWidgetBounds
    public int getBoundsHeight() {
        return this.g;
    }

    @Override // net.labymod.api.client.gui.MinecraftWidgetBounds
    public void setBoundsHeight(int height) {
        this.g = height;
    }
}
