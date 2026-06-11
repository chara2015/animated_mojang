package net.labymod.v1_8_9.mixins.client.renderer.entity.layers;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.ModelTransformType;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.entity.layers.ItemInHandLayerRenderEvent;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.v1_8_9.client.render.matrix.VersionedStackProvider;
import net.labymod.v1_8_9.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/renderer/entity/layers/MixinLayerHeldItem.class */
@Mixin({bky.class})
public class MixinLayerHeldItem {
    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Redirect(method = {"doRenderLayer"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;renderItem(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V"))
    private void labyMod$callItemInHandLayerEvent(bfn renderer, pr livingEntity, zx itemStack, b type) throws MatchException {
        Stack stack = VersionedStackProvider.DEFAULT_STACK;
        ItemStack labyItemStack = MinecraftUtil.fromMinecraft(itemStack);
        ModelTransformType labyTransformType = MinecraftUtil.fromMinecraft(type);
        Laby3D laby3D = Laby.references().laby3D();
        laby3D.storeStates();
        ItemInHandLayerRenderEvent event = (ItemInHandLayerRenderEvent) Laby.fireEvent(new ItemInHandLayerRenderEvent(stack, Phase.PRE, (LivingEntity) livingEntity, labyItemStack, labyTransformType, LivingEntity.HandSide.RIGHT, -1));
        laby3D.restoreStates();
        if (event.isCancelled()) {
            return;
        }
        renderer.a(livingEntity, itemStack, type);
        laby3D.storeStates();
        Laby.fireEvent(new ItemInHandLayerRenderEvent(stack, Phase.POST, (LivingEntity) livingEntity, labyItemStack, labyTransformType, LivingEntity.HandSide.RIGHT, -1));
        laby3D.restoreStates();
    }
}
