package net.labymod.v1_12_2.mixins.client.renderer.entity.layers;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.entity.layers.ItemInHandLayerRenderEvent;
import net.labymod.v1_12_2.client.render.matrix.VersionedStackProvider;
import net.labymod.v1_12_2.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/renderer/entity/layers/MixinLayerHeldItem.class */
@Mixin({ccc.class})
public abstract class MixinLayerHeldItem {
    @Redirect(method = {"renderHeldItem"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;renderItemSide(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;Z)V"))
    private void labyMod$callItemInHandLayerRenderEvent(buu renderer, vp livingEntity, aip itemStack, b type, boolean leftHand) {
        ItemInHandLayerRenderEvent event = labyMod$fireItemInHandLayerRenderEvent(Phase.PRE, livingEntity, itemStack, type, leftHand, 0);
        if (event.isCancelled()) {
            return;
        }
        renderer.a(livingEntity, itemStack, type, leftHand);
        labyMod$fireItemInHandLayerRenderEvent(Phase.POST, livingEntity, itemStack, type, leftHand, 0);
    }

    private ItemInHandLayerRenderEvent labyMod$fireItemInHandLayerRenderEvent(Phase phase, vp livingEntity, aip itemStack, b type, boolean leftHand, int combinedLight) {
        return (ItemInHandLayerRenderEvent) Laby.fireEvent(new ItemInHandLayerRenderEvent(VersionedStackProvider.DEFAULT_STACK, phase, (LivingEntity) livingEntity, MinecraftUtil.fromMinecraft(itemStack), MinecraftUtil.fromMinecraft(type), leftHand ? LivingEntity.HandSide.LEFT : LivingEntity.HandSide.RIGHT, combinedLight));
    }
}
