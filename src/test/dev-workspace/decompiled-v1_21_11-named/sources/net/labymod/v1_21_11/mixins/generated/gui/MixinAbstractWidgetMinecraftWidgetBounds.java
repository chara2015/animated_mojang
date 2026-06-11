package net.labymod.v1_21_11.mixins.generated.gui;

import net.labymod.api.client.gui.MinecraftWidgetBounds;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/generated/gui/MixinAbstractWidgetMinecraftWidgetBounds.class */
@Mixin({AbstractWidget.class})
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
    public abstract Component B();

    public int getBoundsX() {
        return this.a;
    }

    public void setBoundsX(int x) {
        this.a = x;
    }

    public int getBoundsY() {
        return this.b;
    }

    public void setBoundsY(int y) {
        this.b = y;
    }

    public int getBoundsWidth() {
        return this.g;
    }

    public void setBoundsWidth(int width) {
        this.g = width;
    }

    public int getBoundsHeight() {
        return this.h;
    }

    public void setBoundsHeight(int height) {
        this.h = height;
    }
}
