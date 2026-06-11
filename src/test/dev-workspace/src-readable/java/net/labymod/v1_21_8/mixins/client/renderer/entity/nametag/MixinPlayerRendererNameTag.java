package net.labymod.v1_21_8.mixins.client.renderer.entity.nametag;

import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.util.RenderUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/client/renderer/entity/nametag/MixinPlayerRendererNameTag.class */
@Mixin({hjn.class})
public class MixinPlayerRendererNameTag {
    @Inject(method = {"renderNameTag(Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;renderNameTag(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", shift = At.Shift.BEFORE, ordinal = 0)})
    private void labyMod$setScoreboardNameTagType(hmc state, xo displayName, fod poseStack, gxn bufferSource, int par5, CallbackInfo ci) {
        RenderUtil.setNameTagType(TagType.SCOREBOARD);
    }

    @Inject(method = {"renderNameTag(Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;renderNameTag(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", shift = At.Shift.BEFORE, ordinal = 1)})
    private void labyMod$setMainNameTagType(hmc state, xo displayName, fod poseStack, gxn bufferSource, int par5, CallbackInfo ci) {
        RenderUtil.setNameTagType(TagType.MAIN_TAG);
    }
}
