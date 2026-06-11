package net.labymod.v1_8_9.mixins.generated.gui;

import net.labymod.api.client.gui.MinecraftWidgetBounds;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/generated/gui/MixinGuiButtonMinecraftWidgetBounds.class */
@Mixin({avs.class})
public class MixinGuiButtonMinecraftWidgetBounds implements MinecraftWidgetBounds {

    @Shadow
    public int h;

    @Shadow
    public int i;

    @Shadow
    public int f;

    @Shadow
    public int g;

    @Override // net.labymod.api.client.gui.MinecraftWidgetBounds
    public int getBoundsX() {
        return this.h;
    }

    @Override // net.labymod.api.client.gui.MinecraftWidgetBounds
    public void setBoundsX(int x) {
        this.h = x;
    }

    @Override // net.labymod.api.client.gui.MinecraftWidgetBounds
    public int getBoundsY() {
        return this.i;
    }

    @Override // net.labymod.api.client.gui.MinecraftWidgetBounds
    public void setBoundsY(int y) {
        this.i = y;
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
