package net.labymod.v1_21_5.mixins.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.v1_21_5.client.util.BlockOutlineUtil;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/MixinLevelRenderer_CustomBlockOutline.class */
@Mixin({gri.class})
public class MixinLevelRenderer_CustomBlockOutline {

    @Shadow
    private glo p;

    @Shadow
    @Final
    private grv k;

    @WrapOperation(method = {"renderBlockOutline"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderHitOutline(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/world/entity/Entity;DDDLnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)V")})
    @Dynamic
    private void labyMod$renderCustomBlockOutline(gri instance, fld poseStack, flg lineConsumer, bxe entity, double x, double y, double z, iw blockPos, ebq blockState, int color, Operation<Void> original) {
        BlockOutlineUtil.renderBlockOutline(this.p, this.k.c(), lineConsumer, poseStack, entity, x, y, z, blockPos, blockState, color);
    }
}
