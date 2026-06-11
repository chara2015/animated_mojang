package net.labymod.v1_18_2.mixins.cosmetics;

import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.client.render.item.PlayerItemRenderContextEvent;
import net.labymod.core.event.client.render.item.PlayerItemRenderContextEventCaller;
import net.labymod.v1_18_2.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/cosmetics/MixinCustomItemRenderer.class */
@Mixin({ewh.class})
public class MixinCustomItemRenderer {
    private axy labyMod$livingEntity;

    @Inject(method = {"renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemTransforms$TransformType;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V"}, at = {@At("HEAD")})
    private void labyMod$beginRenderStatic(axy livingEntity, buw $$1, b $$2, boolean $$3, dtm $$4, eqs $$5, cav $$6, int $$7, int $$8, int $$9, CallbackInfo ci) {
        this.labyMod$livingEntity = livingEntity;
    }

    @Inject(method = {"renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemTransforms$TransformType;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V"}, at = {@At("TAIL")})
    private void labyMod$endRenderStatic(axy livingEntity, buw $$1, b $$2, boolean $$3, dtm $$4, eqs $$5, cav $$6, int $$7, int $$8, int $$9, CallbackInfo ci) {
        this.labyMod$livingEntity = null;
    }

    @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(DDD)V", shift = At.Shift.AFTER)}, cancellable = true)
    private void labyMod$callPlayerItemRenderContext(buw itemStack, b transformType, boolean leftHand, dtm poseStack, eqs bufferSource, int packedLightCoords, int packedOverlayCoords, fck model, CallbackInfo ci) {
        if (transformType == b.h) {
            return;
        }
        epw epwVar = this.labyMod$livingEntity;
        if (epwVar == null) {
            epwVar = dyr.D().s;
        }
        ItemStack apiItemStack = MinecraftUtil.fromMinecraft(itemStack);
        if (!itemStack.b() && (epwVar instanceof boj)) {
            Player player = (boj) epwVar;
            faf fafVarA = dyr.D().ab().a(player);
            if (!(fafVarA instanceof faf)) {
                return;
            }
            faf playerRenderer = fafVarA;
            PlayerItemRenderContextEvent event = PlayerItemRenderContextEventCaller.call(((VanillaStackAccessor) poseStack).stack(), apiItemStack, MinecraftUtil.fromMinecraft(transformType), player, playerRenderer.a(), packedLightCoords);
            if (event.isCancelled()) {
                poseStack.b();
                ci.cancel();
            }
        }
    }
}
