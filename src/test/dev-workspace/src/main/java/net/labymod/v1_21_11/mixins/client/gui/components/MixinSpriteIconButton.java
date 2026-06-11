package net.labymod.v1_21_11.mixins.client.gui.components;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.CastUtil;
import net.labymod.core.client.accessor.gui.SpriteIconButtonAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/gui/components/MixinSpriteIconButton.class */
@Mixin({gkn.class})
public abstract class MixinSpriteIconButton extends MixinAbstractWidget implements SpriteIconButtonAccessor {

    @Unique
    private gku labymod4$sprites;

    @Unique
    private yh labymod4$message;

    @Unique
    private yh labymod4$tooltip;

    @Inject(method = {"<init>"}, at = {@At("RETURN")})
    public void init(int width, int height, yh message, int spriteWidth, int spriteHeight, gku sprite, c onPress, yh tooltip, b narration, CallbackInfo ci) {
        this.labymod4$sprites = sprite;
        this.labymod4$message = message;
        this.labymod4$tooltip = tooltip;
    }

    @Override // net.labymod.core.client.accessor.gui.SpriteIconButtonAccessor
    public ResourceLocation getResourceLocation() {
        return (ResourceLocation) CastUtil.cast(this.labymod4$sprites.a(((gjc) this).b(), ((gjc) this).D()));
    }

    @Override // net.labymod.core.client.accessor.gui.SpriteIconButtonAccessor
    public Component getMessage() {
        return Laby.references().componentMapper().fromMinecraftComponent(this.labymod4$message);
    }

    @Override // net.labymod.core.client.accessor.gui.SpriteIconButtonAccessor
    public Component getTooltip() {
        return Laby.references().componentMapper().fromMinecraftComponent(this.labymod4$tooltip);
    }

    @Override // net.labymod.core.client.accessor.gui.SpriteIconButtonAccessor
    public boolean iconOnly() {
        return this instanceof b;
    }
}
