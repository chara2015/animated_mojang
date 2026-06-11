package net.labymod.v1_21_11.mixins.client.renderer.feature;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.entity.layers.ItemInHandLayerRenderEvent;
import net.labymod.api.util.CastUtil;
import net.labymod.v1_21_11.client.renderer.ItemStackRenderStateAccessor;
import net.labymod.v1_21_11.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/renderer/feature/MixinItemFeatureRenderer_Events.class */
@Mixin({ihm.class})
public class MixinItemFeatureRenderer_Events {
    /* JADX WARN: Multi-variable type inference failed */
    @WrapOperation(method = {"submit"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/item/ItemStackRenderState$LayerRenderState;submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;III)V")})
    private void labyMod$submitItem(b instance, fzm poseStack, hpo submitNodeCollector, int lightCoords, int overlayCoords, int outlineColor, Operation<Void> original) {
        ItemStackRenderStateAccessor accessor = (ItemStackRenderStateAccessor) this;
        chl livingEntity = accessor.labyMod$getLivingEntity();
        if (livingEntity == null) {
            original.call(new Object[]{instance, poseStack, submitNodeCollector, Integer.valueOf(lightCoords), Integer.valueOf(overlayCoords), Integer.valueOf(outlineColor)});
            return;
        }
        dlt itemStack = accessor.labyMod$getItemStack();
        boolean leftHand = accessor.labyMod$isLeftHand();
        dlr displayContext = accessor.labyMod$getItemDisplayContext();
        ItemInHandLayerRenderEvent event = labyMod$fireItemInHandLayerRenderEvent(Phase.PRE, poseStack, livingEntity, itemStack, displayContext, leftHand, lightCoords);
        if (event.isCancelled()) {
            return;
        }
        original.call(new Object[]{instance, poseStack, submitNodeCollector, Integer.valueOf(lightCoords), Integer.valueOf(overlayCoords), Integer.valueOf(outlineColor)});
        labyMod$fireItemInHandLayerRenderEvent(Phase.POST, poseStack, livingEntity, itemStack, displayContext, leftHand, lightCoords);
    }

    private ItemInHandLayerRenderEvent labyMod$fireItemInHandLayerRenderEvent(Phase phase, fzm poseStack, chl livingEntity, dlt itemStack, dlr type, boolean leftHand, int combinedLight) {
        return (ItemInHandLayerRenderEvent) Laby.fireEvent(new ItemInHandLayerRenderEvent(((VanillaStackAccessor) poseStack).stack(), phase, (LivingEntity) CastUtil.cast(livingEntity), (ItemStack) CastUtil.cast(itemStack), MinecraftUtil.fromMinecraft(type), leftHand ? LivingEntity.HandSide.LEFT : LivingEntity.HandSide.RIGHT, combinedLight));
    }
}
