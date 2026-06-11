package net.labymod.v26_2_snapshot_8.mixins.generated.gui;

import net.labymod.api.client.gui.MinecraftWidgetBounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.StringWidget;
import org.spongepowered.asm.mixin.Mixin;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/generated/gui/MixinStringWidgetMinecraftWidgetBounds.class */
@Mixin({StringWidget.class})
public abstract class MixinStringWidgetMinecraftWidgetBounds extends MixinAbstractWidgetMinecraftWidgetBounds implements MinecraftWidgetBounds {
    @Override // net.labymod.v26_2_snapshot_8.mixins.generated.gui.MixinAbstractWidgetMinecraftWidgetBounds, net.labymod.api.client.gui.MinecraftWidgetBounds
    public int getBoundsX() {
        return super.getBoundsX() + (super.getBoundsWidth() - Minecraft.getInstance().font.width(getMessage()));
    }
}
