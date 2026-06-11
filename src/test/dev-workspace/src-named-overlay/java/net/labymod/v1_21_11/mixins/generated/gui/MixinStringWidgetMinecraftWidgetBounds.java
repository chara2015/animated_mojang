package net.labymod.v1_21_11.mixins.generated.gui;

import net.labymod.api.client.gui.MinecraftWidgetBounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.StringWidget;
import org.spongepowered.asm.mixin.Mixin;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/generated/gui/MixinStringWidgetMinecraftWidgetBounds.class */
@Mixin({StringWidget.class})
public abstract class MixinStringWidgetMinecraftWidgetBounds extends MixinAbstractWidgetMinecraftWidgetBounds implements MinecraftWidgetBounds {
    @Override // net.labymod.v1_21_11.mixins.generated.gui.MixinAbstractWidgetMinecraftWidgetBounds
    public int getBoundsX() {
        return super.getBoundsX() + (super.getBoundsWidth() - Minecraft.getInstance().font.width(B()));
    }
}
