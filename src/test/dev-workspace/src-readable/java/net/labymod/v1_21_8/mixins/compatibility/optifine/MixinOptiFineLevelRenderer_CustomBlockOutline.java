package net.labymod.v1_21_8.mixins.compatibility.optifine;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import javax.annotation.Nullable;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.v1_21_8.client.util.BlockOutlineUtil;
import net.labymod.v1_21_8.mixinplugin.optifine.OptiFineDynamicMixinApplier;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/compatibility/optifine/MixinOptiFineLevelRenderer_CustomBlockOutline.class */
@Pseudo
@DynamicMixin(value = OptiFine.NAMESPACE, applier = OptiFineDynamicMixinApplier.class)
@Mixin({gxh.class})
public class MixinOptiFineLevelRenderer_CustomBlockOutline {

    @Shadow
    @Nullable
    private grk q;

    @Shadow
    @Final
    private gxw l;

    @WrapOperation(method = {"renderBlockOutline(Lnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;Lcom/mojang/blaze3d/vertex/PoseStack;ZF)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderHitOutline(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/world/entity/Entity;DDDLnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)V")})
    @Dynamic
    private void labyMod$renderCustomBlockOutline(gxh instance, fod poseStack, fog lineConsumer, bzm entity, double x, double y, double z, jb blockPos, eeb blockState, int color, Operation<Void> original) {
        BlockOutlineUtil.renderBlockOutline(this.q, this.l.c(), lineConsumer, poseStack, entity, x, y, z, blockPos, blockState, color);
    }
}
