package net.labymod.v1_17_1.mixins.client.renderer.entity.layers;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.entity.layers.ItemInHandLayerRenderEvent;
import net.labymod.v1_17_1.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/client/renderer/entity/layers/MixinItemInHandLayer.class */
@Mixin({evv.class})
public class MixinItemInHandLayer {
    private Stack labyMod$stack;

    @Redirect(method = {"renderArmWithItem"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemTransforms$TransformType;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"))
    private void labyMod$callItemInHandLayerRenderEvent(ene renderer, atu livingEntity, bqq itemStack, b type, boolean leftHand, dql poseStack, eni bufferSource, int combinedLight) {
        this.labyMod$stack = ((VanillaStackAccessor) poseStack).stack(bufferSource);
        ItemInHandLayerRenderEvent event = labyMod$fireItemInHandLayerRenderEvent(Phase.PRE, livingEntity, itemStack, type, leftHand, combinedLight);
        if (event.isCancelled()) {
            return;
        }
        renderer.a(livingEntity, itemStack, type, leftHand, poseStack, bufferSource, combinedLight);
        labyMod$fireItemInHandLayerRenderEvent(Phase.POST, livingEntity, itemStack, type, leftHand, combinedLight);
    }

    private ItemInHandLayerRenderEvent labyMod$fireItemInHandLayerRenderEvent(Phase phase, atu livingEntity, bqq itemStack, b type, boolean leftHand, int combinedLight) {
        return (ItemInHandLayerRenderEvent) Laby.fireEvent(new ItemInHandLayerRenderEvent(this.labyMod$stack, phase, (LivingEntity) livingEntity, MinecraftUtil.fromMinecraft(itemStack), MinecraftUtil.fromMinecraft(type), leftHand ? LivingEntity.HandSide.LEFT : LivingEntity.HandSide.RIGHT, combinedLight));
    }
}
