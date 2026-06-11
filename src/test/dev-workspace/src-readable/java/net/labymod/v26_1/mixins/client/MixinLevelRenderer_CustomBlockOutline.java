package net.labymod.v26_1.mixins.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.labymod.v26_1.client.util.BlockOutlineUtil;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.state.level.BlockOutlineRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/client/MixinLevelRenderer_CustomBlockOutline.class */
@Mixin({LevelRenderer.class})
public class MixinLevelRenderer_CustomBlockOutline {

    @Shadow
    private ClientLevel level;

    @Shadow
    @Final
    private RenderBuffers renderBuffers;

    @WrapOperation(method = {"renderBlockOutline"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderHitOutline(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;DDDLnet/minecraft/client/renderer/state/level/BlockOutlineRenderState;IF)V")})
    private void labyMod$renderCustomBlockOutline(LevelRenderer instance, PoseStack poseStack, VertexConsumer lineConsumer, double x, double y, double z, BlockOutlineRenderState state, int color, float lineWidth, Operation<Void> original) {
        BlockOutlineUtil.renderBlockOutline(this.level, this.renderBuffers.bufferSource(), lineConsumer, poseStack, x, y, z, state, color, lineWidth);
    }
}
