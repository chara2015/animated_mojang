package net.labymod.v1_20_4.mixins.client.renderer.entity.nametag;

import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.util.RenderUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/client/renderer/entity/nametag/MixinPlayerRendererNameTag.class */
@Mixin({gdq.class})
public class MixinPlayerRendererNameTag {
    @Inject(method = {"renderNameTag(Lnet/minecraft/client/player/AbstractClientPlayer;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;renderNameTag(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", shift = At.Shift.BEFORE, ordinal = 0)})
    private void labyMod$setScoreboardNameTagType(fsg entity, vf displayName, eqb poseStack, fth bufferSource, int packedLight, CallbackInfo callbackInfo) {
        RenderUtil.setNameTagType(TagType.SCOREBOARD);
    }

    @Inject(method = {"renderNameTag(Lnet/minecraft/client/player/AbstractClientPlayer;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;renderNameTag(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", shift = At.Shift.BEFORE, ordinal = 1)})
    private void labyMod$setMainNameTagType(fsg entity, vf displayName, eqb poseStack, fth bufferSource, int packedLight, CallbackInfo callbackInfo) {
        RenderUtil.setNameTagType(TagType.MAIN_TAG);
    }
}
