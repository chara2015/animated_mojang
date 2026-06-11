package net.labymod.v1_21_8.mixins.generated.gui;

import net.labymod.api.client.gui.MinecraftWidgetBounds;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/generated/gui/MixinStringWidgetMinecraftWidgetBounds.class */
@Mixin({fyx.class})
public abstract class MixinStringWidgetMinecraftWidgetBounds extends MixinAbstractWidgetMinecraftWidgetBounds implements MinecraftWidgetBounds {

    @Shadow
    private float a;

    @Override // net.labymod.v1_21_8.mixins.generated.gui.MixinAbstractWidgetMinecraftWidgetBounds, net.labymod.api.client.gui.MinecraftWidgetBounds
    public int getBoundsX() {
        return super.getBoundsX() + Math.round(this.a * (super.getBoundsWidth() - fue.R().h.a(A())));
    }
}
