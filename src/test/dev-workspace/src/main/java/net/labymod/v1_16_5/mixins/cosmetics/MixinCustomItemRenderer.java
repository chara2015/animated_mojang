package net.labymod.v1_16_5.mixins.cosmetics;

import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.client.render.item.PlayerItemRenderContextEvent;
import net.labymod.core.event.client.render.item.PlayerItemRenderContextEventCaller;
import net.labymod.v1_16_5.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/cosmetics/MixinCustomItemRenderer.class */
@Mixin({efo.class})
public class MixinCustomItemRenderer {
    private aqm labyMod$livingEntity;

    @Inject(method = {"renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemTransforms$TransformType;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;II)V"}, at = {@At("HEAD")})
    private void labyMod$beginRenderStatic(aqm livingEntity, bmb $$1, b $$2, boolean $$3, dfm $$4, eag $$5, brx $$6, int $$7, int $$8, CallbackInfo ci) {
        this.labyMod$livingEntity = livingEntity;
    }

    @Inject(method = {"renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemTransforms$TransformType;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;II)V"}, at = {@At("TAIL")})
    private void labyMod$endRenderStatic(aqm livingEntity, bmb $$1, b $$2, boolean $$3, dfm $$4, eag $$5, brx $$6, int $$7, int $$8, CallbackInfo ci) {
        this.labyMod$livingEntity = null;
    }

    @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(DDD)V", shift = At.Shift.AFTER)}, cancellable = true)
    private void labyMod$callPlayerItemRenderContext(bmb itemStack, b transformType, boolean leftHand, dfm poseStack, eag bufferSource, int packedLightCoords, int packedOverlayCoords, elo model, CallbackInfo ci) {
        if (transformType == b.h) {
            return;
        }
        dzm dzmVar = this.labyMod$livingEntity;
        if (dzmVar == null) {
            dzmVar = djz.C().s;
        }
        ItemStack apiItemStack = MinecraftUtil.fromMinecraft(itemStack);
        if (!itemStack.a() && (dzmVar instanceof bfw)) {
            Player player = (bfw) dzmVar;
            ejk ejkVarA = djz.C().ac().a(player);
            if (!(ejkVarA instanceof ejk)) {
                return;
            }
            ejk playerRenderer = ejkVarA;
            PlayerItemRenderContextEvent event = PlayerItemRenderContextEventCaller.call(((VanillaStackAccessor) poseStack).stack(), apiItemStack, MinecraftUtil.fromMinecraft(transformType), player, playerRenderer.c(), packedLightCoords);
            if (event.isCancelled()) {
                poseStack.b();
                ci.cancel();
            }
        }
    }
}
