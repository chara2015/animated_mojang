package net.labymod.v1_12_2.mixins.generated.gui;

import net.labymod.api.client.gui.MinecraftWidgetBounds;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/generated/gui/MixinGuiTextFieldMinecraftWidgetBounds.class */
@Mixin({bje.class})
public class MixinGuiTextFieldMinecraftWidgetBounds implements MinecraftWidgetBounds {

    @Shadow
    public int a;

    @Shadow
    public int f;

    @Shadow
    @Mutable
    @Final
    public int i;

    @Shadow
    @Mutable
    @Final
    public int j;

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
        return this.f;
    }

    @Override // net.labymod.api.client.gui.MinecraftWidgetBounds
    public void setBoundsY(int y) {
        this.f = y;
    }

    @Override // net.labymod.api.client.gui.MinecraftWidgetBounds
    public int getBoundsWidth() {
        return this.i;
    }

    @Override // net.labymod.api.client.gui.MinecraftWidgetBounds
    public void setBoundsWidth(int width) {
        this.i = width;
    }

    @Override // net.labymod.api.client.gui.MinecraftWidgetBounds
    public int getBoundsHeight() {
        return this.j;
    }

    @Override // net.labymod.api.client.gui.MinecraftWidgetBounds
    public void setBoundsHeight(int height) {
        this.j = height;
    }
}
