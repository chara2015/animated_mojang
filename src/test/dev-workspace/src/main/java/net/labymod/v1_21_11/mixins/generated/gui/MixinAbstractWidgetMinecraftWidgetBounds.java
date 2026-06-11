package net.labymod.v1_21_11.mixins.generated.gui;

import net.labymod.api.client.gui.MinecraftWidgetBounds;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/generated/gui/MixinAbstractWidgetMinecraftWidgetBounds.class */
@Mixin({gjc.class})
public abstract class MixinAbstractWidgetMinecraftWidgetBounds implements MinecraftWidgetBounds {

    @Shadow
    public int a;

    @Shadow
    public int b;

    @Shadow
    public int g;

    @Shadow
    public int h;

    @Shadow
    public abstract yh B();

    @Override // net.labymod.api.client.gui.MinecraftWidgetBounds
    public int getBoundsX() {
        return this.a;
    }

    @Override // net.labymod.api.client.gui.MinecraftWidgetBounds
    public void setBoundsX(int x) {
        this.a = x;
    }

    @Override // net.labymod.api.client.gui.MinecraftWidgetBounds
    public int getBoundsY() {
        return this.b;
    }

    @Override // net.labymod.api.client.gui.MinecraftWidgetBounds
    public void setBoundsY(int y) {
        this.b = y;
    }

    @Override // net.labymod.api.client.gui.MinecraftWidgetBounds
    public int getBoundsWidth() {
        return this.g;
    }

    @Override // net.labymod.api.client.gui.MinecraftWidgetBounds
    public void setBoundsWidth(int width) {
        this.g = width;
    }

    @Override // net.labymod.api.client.gui.MinecraftWidgetBounds
    public int getBoundsHeight() {
        return this.h;
    }

    @Override // net.labymod.api.client.gui.MinecraftWidgetBounds
    public void setBoundsHeight(int height) {
        this.h = height;
    }
}
