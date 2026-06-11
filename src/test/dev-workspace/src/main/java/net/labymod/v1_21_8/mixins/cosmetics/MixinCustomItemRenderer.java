package net.labymod.v1_21_8.mixins.cosmetics;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/cosmetics/MixinCustomItemRenderer.class */
@Mixin({hfd.class})
public class MixinCustomItemRenderer {
    private cam labyMod$livingEntity;

    @Inject(method = {"renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V"}, at = {@At("HEAD")})
    private void labyMod$beginRenderStatic(cam livingEntity, dcv $$1, dct $$2, fod $$3, gxn $$4, dmu $$5, int $$6, int $$7, int $$8, CallbackInfo ci) {
        this.labyMod$livingEntity = livingEntity;
    }

    @Inject(method = {"renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V"}, at = {@At("TAIL")})
    private void labyMod$endRenderStatic(cam $$0, dcv $$1, dct $$2, fod $$3, gxn $$4, dmu $$5, int $$6, int $$7, int $$8, CallbackInfo ci) {
        this.labyMod$livingEntity = null;
    }
}
