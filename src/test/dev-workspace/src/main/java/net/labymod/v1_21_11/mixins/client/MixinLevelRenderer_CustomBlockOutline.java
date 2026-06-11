package net.labymod.v1_21_11.mixins.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.v1_21_11.client.util.BlockOutlineUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/MixinLevelRenderer_CustomBlockOutline.class */
@Mixin({hoh.class})
public class MixinLevelRenderer_CustomBlockOutline {

    @Shadow
    private hif s;

    @Shadow
    @Final
    private hoz m;

    @WrapOperation(method = {"renderBlockOutline"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderHitOutline(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;DDDLnet/minecraft/client/renderer/state/BlockOutlineRenderState;IF)V")})
    private void labyMod$renderCustomBlockOutline(hoh instance, fzm poseStack, fzp lineConsumer, double x, double y, double z, iko state, int color, float lineWidth, Operation<Void> original) {
        BlockOutlineUtil.renderBlockOutline(this.s, this.m.c(), lineConsumer, poseStack, x, y, z, state, color, lineWidth);
    }
}
