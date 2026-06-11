package net.labymod.v1_20_6.mixins.cosmetics;

import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.client.render.item.PlayerItemRenderContextEvent;
import net.labymod.core.event.client.render.item.PlayerItemRenderContextEventCaller;
import net.labymod.v1_20_6.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/mixins/cosmetics/MixinCustomItemRenderer.class */
@Mixin({gjx.class})
public class MixinCustomItemRenderer {
    private btr labyMod$livingEntity;

    @Inject(method = {"renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V"}, at = {@At("HEAD")})
    private void labyMod$beginRenderStatic(btr livingEntity, cur $$1, cuo $$2, boolean $$3, faa $$4, gdq $$5, dca $$6, int $$7, int $$8, int $$9, CallbackInfo ci) {
        this.labyMod$livingEntity = livingEntity;
    }

    @Inject(method = {"renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V"}, at = {@At("TAIL")})
    private void labyMod$endRenderStatic(btr livingEntity, cur $$1, cuo $$2, boolean $$3, faa $$4, gdq $$5, dca $$6, int $$7, int $$8, int $$9, CallbackInfo ci) {
        this.labyMod$livingEntity = null;
    }

    @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V", shift = At.Shift.AFTER)}, cancellable = true)
    private void labyMod$callPlayerItemRenderContext(cur itemStack, cuo displayContext, boolean leftHand, faa poseStack, gdq bufferSource, int packedLightCoords, int packedOverlayCoords, grc model, CallbackInfo ci) {
        if (displayContext == cuo.h) {
            return;
        }
        gcs gcsVar = this.labyMod$livingEntity;
        if (gcsVar == null) {
            gcsVar = ffh.Q().s;
        }
        ItemStack apiItemStack = MinecraftUtil.fromMinecraft(itemStack);
        if (!itemStack.e() && (gcsVar instanceof cmz)) {
            Player player = (cmz) gcsVar;
            goe goeVarA = ffh.Q().ap().a(player);
            if (!(goeVarA instanceof goe)) {
                return;
            }
            goe playerRenderer = goeVarA;
            PlayerItemRenderContextEvent event = PlayerItemRenderContextEventCaller.call(((VanillaStackAccessor) poseStack).stack(), apiItemStack, MinecraftUtil.fromMinecraft(displayContext), player, playerRenderer.a(), packedLightCoords);
            if (event.isCancelled()) {
                poseStack.b();
                ci.cancel();
            }
        }
    }
}
