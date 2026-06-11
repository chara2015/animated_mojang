package net.labymod.v1_21_11.mixins.client.gui.components;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.CastUtil;
import net.labymod.core.client.accessor.gui.ImageButtonAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/gui/components/MixinStatSortButton.class */
@Mixin({a.class})
public abstract class MixinStatSortButton implements ImageButtonAccessor {

    @Unique
    private amo labymod4$sprite;

    @Inject(method = {"<init>"}, at = {@At("RETURN")})
    public void labyMod$init(b headerEntry, int column, amo sprite, CallbackInfo ci) {
        this.labymod4$sprite = sprite;
    }

    @Override // net.labymod.core.client.accessor.gui.ImageButtonAccessor
    public ResourceLocation getResourceLocation() {
        return (ResourceLocation) CastUtil.cast(this.labymod4$sprite);
    }
}
