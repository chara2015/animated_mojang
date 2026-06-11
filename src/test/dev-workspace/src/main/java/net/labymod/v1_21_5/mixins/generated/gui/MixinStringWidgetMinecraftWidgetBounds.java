package net.labymod.v1_21_5.mixins.generated.gui;

import net.labymod.api.client.gui.MinecraftWidgetBounds;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/generated/gui/MixinStringWidgetMinecraftWidgetBounds.class */
@Mixin({fvf.class})
public abstract class MixinStringWidgetMinecraftWidgetBounds extends MixinAbstractWidgetMinecraftWidgetBounds implements MinecraftWidgetBounds {

    @Shadow
    private float a;

    @Override // net.labymod.v1_21_5.mixins.generated.gui.MixinAbstractWidgetMinecraftWidgetBounds, net.labymod.api.client.gui.MinecraftWidgetBounds
    public int getBoundsX() {
        return super.getBoundsX() + Math.round(this.a * (super.getBoundsWidth() - fqq.Q().h.a(B())));
    }
}
