package net.labymod.v1_19_4.mixins.cosmetics;

import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.client.render.item.PlayerItemRenderContextEvent;
import net.labymod.core.event.client.render.item.PlayerItemRenderContextEventCaller;
import net.labymod.v1_19_4.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/cosmetics/MixinCustomItemRenderer.class */
@Mixin({foc.class})
public class MixinCustomItemRenderer {
    private bfx labyMod$livingEntity;

    @Inject(method = {"renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V"}, at = {@At("HEAD")})
    private void labyMod$beginRenderStatic(bfx livingEntity, cfv $$1, cfs $$2, boolean $$3, ehe $$4, fig $$5, cmi $$6, int $$7, int $$8, int $$9, CallbackInfo ci) {
        this.labyMod$livingEntity = livingEntity;
    }

    @Inject(method = {"renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V"}, at = {@At("TAIL")})
    private void labyMod$endRenderStatic(bfx livingEntity, cfv $$1, cfs $$2, boolean $$3, ehe $$4, fig $$5, cmi $$6, int $$7, int $$8, int $$9, CallbackInfo ci) {
        this.labyMod$livingEntity = null;
    }

    @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V", shift = At.Shift.AFTER)}, cancellable = true)
    private void labyMod$callPlayerItemRenderContext(cfv itemStack, cfs displayContext, boolean leftHand, ehe poseStack, fig bufferSource, int packedLightCoords, int packedOverlayCoords, fuy model, CallbackInfo ci) {
        if (displayContext == cfs.h) {
            return;
        }
        fhk fhkVar = this.labyMod$livingEntity;
        if (fhkVar == null) {
            fhkVar = emh.N().t;
        }
        ItemStack apiItemStack = MinecraftUtil.fromMinecraft(itemStack);
        if (!itemStack.b() && (fhkVar instanceof bym)) {
            Player player = (bym) fhkVar;
            fsf fsfVarA = emh.N().an().a(player);
            if (!(fsfVarA instanceof fsf)) {
                return;
            }
            fsf playerRenderer = fsfVarA;
            PlayerItemRenderContextEvent event = PlayerItemRenderContextEventCaller.call(((VanillaStackAccessor) poseStack).stack(), apiItemStack, MinecraftUtil.fromMinecraft(displayContext), player, playerRenderer.a(), packedLightCoords);
            if (event.isCancelled()) {
                poseStack.b();
                ci.cancel();
            }
        }
    }
}
