package net.labymod.v1_21_10.mixins.client.renderer.feature;

import net.labymod.v1_21_10.client.renderer.ItemStackRenderStateAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/renderer/feature/MixinItemModelResolver_Accessor.class */
@Mixin({hyq.class})
public class MixinItemModelResolver_Accessor {
    @Inject(method = {"updateForLiving"}, at = {@At("HEAD")})
    private void labyMod$updateForLiving(hys $$0, dhp $$1, dhn $$2, cew $$3, CallbackInfo ci) {
        ItemStackRenderStateAccessor accessor = (ItemStackRenderStateAccessor) $$0;
        accessor.labyMod$setLivingEntity($$3);
        accessor.labyMod$setItemStack($$1);
    }

    @Inject(method = {"appendItemLayers"}, at = {@At("HEAD")})
    private void labyMod$appendItemLayers(hys $$0, dhp $$1, dhn $$2, drq $$3, ces $$4, int $$5, CallbackInfo ci) {
        ItemStackRenderStateAccessor accessor = (ItemStackRenderStateAccessor) $$0;
        accessor.labyMod$setItemStack($$1);
    }
}
