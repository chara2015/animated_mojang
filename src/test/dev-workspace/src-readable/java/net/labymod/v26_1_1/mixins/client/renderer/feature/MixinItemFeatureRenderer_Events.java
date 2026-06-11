package net.labymod.v26_1_1.mixins.client.renderer.feature;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.entity.layers.ItemInHandLayerRenderEvent;
import net.labymod.api.util.CastUtil;
import net.labymod.v26_1_1.client.renderer.ItemStackRenderStateAccessor;
import net.labymod.v26_1_1.client.util.MinecraftUtil;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/client/renderer/feature/MixinItemFeatureRenderer_Events.class */
@Mixin({ItemStackRenderState.class})
public class MixinItemFeatureRenderer_Events {
    /* JADX WARN: Multi-variable type inference failed */
    @WrapOperation(method = {"submit"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/item/ItemStackRenderState$LayerRenderState;submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;III)V")})
    private void labyMod$submitItem(ItemStackRenderState.LayerRenderState instance, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, int outlineColor, Operation<Void> original) {
        ItemStackRenderStateAccessor accessor = (ItemStackRenderStateAccessor) this;
        LivingEntity livingEntity = accessor.labyMod$getLivingEntity();
        if (livingEntity == null) {
            original.call(new Object[]{instance, poseStack, submitNodeCollector, Integer.valueOf(lightCoords), Integer.valueOf(overlayCoords), Integer.valueOf(outlineColor)});
            return;
        }
        ItemStack itemStack = accessor.labyMod$getItemStack();
        boolean leftHand = accessor.labyMod$isLeftHand();
        ItemDisplayContext displayContext = accessor.labyMod$getItemDisplayContext();
        ItemInHandLayerRenderEvent event = labyMod$fireItemInHandLayerRenderEvent(Phase.PRE, poseStack, livingEntity, itemStack, displayContext, leftHand, lightCoords);
        if (event.isCancelled()) {
            return;
        }
        original.call(new Object[]{instance, poseStack, submitNodeCollector, Integer.valueOf(lightCoords), Integer.valueOf(overlayCoords), Integer.valueOf(outlineColor)});
        labyMod$fireItemInHandLayerRenderEvent(Phase.POST, poseStack, livingEntity, itemStack, displayContext, leftHand, lightCoords);
    }

    private ItemInHandLayerRenderEvent labyMod$fireItemInHandLayerRenderEvent(Phase phase, PoseStack poseStack, LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext type, boolean leftHand, int combinedLight) {
        return (ItemInHandLayerRenderEvent) Laby.fireEvent(new ItemInHandLayerRenderEvent(((VanillaStackAccessor) poseStack).stack(), phase, (net.labymod.api.client.entity.LivingEntity) CastUtil.cast(livingEntity), (net.labymod.api.client.world.item.ItemStack) CastUtil.cast(itemStack), MinecraftUtil.fromMinecraft(type), leftHand ? LivingEntity.HandSide.LEFT : LivingEntity.HandSide.RIGHT, combinedLight));
    }
}
