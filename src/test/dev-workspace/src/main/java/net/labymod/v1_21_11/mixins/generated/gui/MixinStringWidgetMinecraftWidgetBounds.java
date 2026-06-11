package net.labymod.v1_21_11.mixins.generated.gui;

import net.labymod.api.client.gui.MinecraftWidgetBounds;
import org.spongepowered.asm.mixin.Mixin;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/generated/gui/MixinStringWidgetMinecraftWidgetBounds.class */
@Mixin({gko.class})
public abstract class MixinStringWidgetMinecraftWidgetBounds extends MixinAbstractWidgetMinecraftWidgetBounds implements MinecraftWidgetBounds {
    @Override // net.labymod.v1_21_11.mixins.generated.gui.MixinAbstractWidgetMinecraftWidgetBounds, net.labymod.api.client.gui.MinecraftWidgetBounds
    public int getBoundsX() {
        return super.getBoundsX() + (super.getBoundsWidth() - gfj.V().g.a(B()));
    }
}
