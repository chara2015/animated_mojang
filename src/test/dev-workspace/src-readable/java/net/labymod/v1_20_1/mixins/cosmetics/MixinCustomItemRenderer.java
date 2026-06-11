package net.labymod.v1_20_1.mixins.cosmetics;

import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.client.render.item.PlayerItemRenderContextEvent;
import net.labymod.core.event.client.render.item.PlayerItemRenderContextEventCaller;
import net.labymod.v1_20_1.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixins/cosmetics/MixinCustomItemRenderer.class */
@Mixin({fpw.class})
public class MixinCustomItemRenderer {
    private bfz labyMod$livingEntity;

    @Inject(method = {"renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V"}, at = {@At("HEAD")})
    private void labyMod$beginRenderStatic(bfz livingEntity, cfz $$1, cfw $$2, boolean $$3, eij $$4, fjx $$5, cmm $$6, int $$7, int $$8, int $$9, CallbackInfo ci) {
        this.labyMod$livingEntity = livingEntity;
    }

    @Inject(method = {"renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V"}, at = {@At("TAIL")})
    private void labyMod$endRenderStatic(bfz livingEntity, cfz $$1, cfw $$2, boolean $$3, eij $$4, fjx $$5, cmm $$6, int $$7, int $$8, int $$9, CallbackInfo ci) {
        this.labyMod$livingEntity = null;
    }

    @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V", shift = At.Shift.AFTER)}, cancellable = true)
    private void labyMod$callPlayerItemRenderContext(cfz itemStack, cfw displayContext, boolean leftHand, eij poseStack, fjx bufferSource, int packedLightCoords, int packedOverlayCoords, fwr model, CallbackInfo ci) {
        if (displayContext == cfw.h) {
            return;
        }
        fiy fiyVar = this.labyMod$livingEntity;
        if (fiyVar == null) {
            fiyVar = enn.N().t;
        }
        ItemStack apiItemStack = MinecraftUtil.fromMinecraft(itemStack);
        if (!itemStack.b() && (fiyVar instanceof byo)) {
            Player player = (byo) fiyVar;
            fty ftyVarA = enn.N().an().a(player);
            if (!(ftyVarA instanceof fty)) {
                return;
            }
            fty playerRenderer = ftyVarA;
            PlayerItemRenderContextEvent event = PlayerItemRenderContextEventCaller.call(((VanillaStackAccessor) poseStack).stack(), apiItemStack, MinecraftUtil.fromMinecraft(displayContext), player, playerRenderer.a(), packedLightCoords);
            if (event.isCancelled()) {
                poseStack.b();
                ci.cancel();
            }
        }
    }
}
