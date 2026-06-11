package net.labymod.v1_17_1.mixins.cosmetics;

import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.client.render.item.PlayerItemRenderContextEvent;
import net.labymod.core.event.client.render.item.PlayerItemRenderContextEventCaller;
import net.labymod.v1_17_1.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/cosmetics/MixinCustomItemRenderer.class */
@Mixin({esv.class})
public class MixinCustomItemRenderer {
    private atu labyMod$livingEntity;

    @Inject(method = {"renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemTransforms$TransformType;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V"}, at = {@At("HEAD")})
    private void labyMod$beginRenderStatic(atu livingEntity, bqq $$1, b $$2, boolean $$3, dql $$4, eni $$5, bwq $$6, int $$7, int $$8, int $$9, CallbackInfo ci) {
        this.labyMod$livingEntity = livingEntity;
    }

    @Inject(method = {"renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemTransforms$TransformType;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V"}, at = {@At("TAIL")})
    private void labyMod$endRenderStatic(atu livingEntity, bqq $$1, b $$2, boolean $$3, dql $$4, eni $$5, bwq $$6, int $$7, int $$8, int $$9, CallbackInfo ci) {
        this.labyMod$livingEntity = null;
    }

    @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(DDD)V", shift = At.Shift.AFTER)}, cancellable = true)
    private void labyMod$callPlayerItemRenderContext(bqq itemStack, b transformType, boolean leftHand, dql poseStack, eni bufferSource, int packedLightCoords, int packedOverlayCoords, eyy model, CallbackInfo ci) {
        if (transformType == b.h) {
            return;
        }
        emm emmVar = this.labyMod$livingEntity;
        if (emmVar == null) {
            emmVar = dvp.C().s;
        }
        ItemStack apiItemStack = MinecraftUtil.fromMinecraft(itemStack);
        if (!itemStack.b() && (emmVar instanceof bke)) {
            Player player = (bke) emmVar;
            ewt ewtVarA = dvp.C().ac().a(player);
            if (!(ewtVarA instanceof ewt)) {
                return;
            }
            ewt playerRenderer = ewtVarA;
            PlayerItemRenderContextEvent event = PlayerItemRenderContextEventCaller.call(((VanillaStackAccessor) poseStack).stack(), apiItemStack, MinecraftUtil.fromMinecraft(transformType), player, playerRenderer.a(), packedLightCoords);
            if (event.isCancelled()) {
                poseStack.b();
                ci.cancel();
            }
        }
    }
}
