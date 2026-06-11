package net.labymod.v1_16_5.mixins.client.renderer.skipcolors;

import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util.GlColorAlphaModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Slice;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/renderer/skipcolors/MixinVertexConsumerSkipColors.class */
@Mixin({dfq.class})
public interface MixinVertexConsumerSkipColors {
    @ModifyConstant(method = {"putBulkData(Lcom/mojang/blaze3d/vertex/PoseStack$Pose;Lnet/minecraft/client/renderer/block/model/BakedQuad;[FFFF[IIZ)V"}, constant = {@Constant(floatValue = 1.0f)}, slice = {@Slice(from = @At(value = "INVOKE", target = "Lcom/mojang/math/Vector4f;transform(Lcom/mojang/math/Matrix4f;)V"))})
    private default float labyMod$al(float oldValue) {
        return GlColorAlphaModifier.isModifiedAlpha() ? GlColorAlphaModifier.getAlpha() : oldValue;
    }
}
