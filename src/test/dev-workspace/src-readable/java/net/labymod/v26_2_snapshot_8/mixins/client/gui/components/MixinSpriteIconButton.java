package net.labymod.v26_2_snapshot_8.mixins.client.gui.components;

import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.CastUtil;
import net.labymod.core.client.accessor.gui.SpriteIconButtonAccessor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.SpriteIconButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/gui/components/MixinSpriteIconButton.class */
@Mixin({SpriteIconButton.class})
public abstract class MixinSpriteIconButton extends MixinAbstractWidget implements SpriteIconButtonAccessor {

    @Unique
    private WidgetSprites labymod4$sprites;

    @Unique
    private Component labymod4$message;

    @Unique
    private Component labymod4$tooltip;

    @Inject(method = {"<init>"}, at = {@At("RETURN")})
    public void init(int width, int height, Component message, int spriteWidth, int spriteHeight, int spriteOffsetX, int spriteOffsetY, WidgetSprites sprite, Button.OnPress onPress, Component tooltip, Button.CreateNarration narration, boolean switchToLoadingAfterPress, CallbackInfo ci) {
        this.labymod4$sprites = sprite;
        this.labymod4$message = message;
        this.labymod4$tooltip = tooltip;
    }

    @Override // net.labymod.core.client.accessor.gui.SpriteIconButtonAccessor
    public ResourceLocation getResourceLocation() {
        return (ResourceLocation) CastUtil.cast(this.labymod4$sprites.get(((AbstractWidget) this).isActive(), ((AbstractWidget) this).isHoveredOrFocused()));
    }

    @Override // net.labymod.core.client.accessor.gui.SpriteIconButtonAccessor
    public net.labymod.api.client.component.Component getMessage() {
        return Laby.references().componentMapper().fromMinecraftComponent(this.labymod4$message);
    }

    @Override // net.labymod.core.client.accessor.gui.SpriteIconButtonAccessor
    public net.labymod.api.client.component.Component getTooltip() {
        return Laby.references().componentMapper().fromMinecraftComponent(this.labymod4$tooltip);
    }

    @Override // net.labymod.core.client.accessor.gui.SpriteIconButtonAccessor
    public boolean iconOnly() {
        return this instanceof SpriteIconButton.CenteredIcon;
    }
}
