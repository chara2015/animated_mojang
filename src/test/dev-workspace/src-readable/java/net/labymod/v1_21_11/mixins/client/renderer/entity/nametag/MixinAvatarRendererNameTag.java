package net.labymod.v1_21_11.mixins.client.renderer.entity.nametag;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.blaze3d.vertex.PoseStack;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.util.RenderUtil;
import net.labymod.v1_21_11.client.renderer.entity.NameTagTransformer;
import net.labymod.v1_21_11.client.util.MinecraftUtil;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/renderer/entity/nametag/MixinAvatarRendererNameTag.class */
@Mixin({AvatarRenderer.class})
public class MixinAvatarRendererNameTag {
    @Inject(method = {"submitNameTag(Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V"}, at = {@At("HEAD")})
    private void labyMod$setNameTagRenderState(AvatarRenderState $$0, PoseStack $$1, SubmitNodeCollector $$2, CameraRenderState $$3, CallbackInfo ci) {
        MinecraftUtil.setNameTagRenderState($$0);
    }

    @Inject(method = {"submitNameTag(Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V"}, at = {@At("TAIL")})
    private void labyMod$resetNameTagRenderState(AvatarRenderState $$0, PoseStack $$1, SubmitNodeCollector $$2, CameraRenderState $$3, CallbackInfo ci) {
        MinecraftUtil.resetNameTagRenderState();
    }

    @Inject(method = {"submitNameTag(Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitNameTag(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/phys/Vec3;ILnet/minecraft/network/chat/Component;ZIDLnet/minecraft/client/renderer/state/CameraRenderState;)V", shift = At.Shift.BEFORE, ordinal = 0)})
    private void labyMod$setScoreboardNameTagType(AvatarRenderState $$0, PoseStack $$1, SubmitNodeCollector $$2, CameraRenderState $$3, CallbackInfo ci) {
        RenderUtil.setNameTagType(TagType.SCOREBOARD);
    }

    @Inject(method = {"submitNameTag(Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitNameTag(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/phys/Vec3;ILnet/minecraft/network/chat/Component;ZIDLnet/minecraft/client/renderer/state/CameraRenderState;)V", shift = At.Shift.BEFORE, ordinal = 1)})
    private void labyMod$setMainNameTagType(AvatarRenderState $$0, PoseStack $$1, SubmitNodeCollector $$2, CameraRenderState $$3, CallbackInfo ci) {
        RenderUtil.setNameTagType(TagType.MAIN_TAG);
    }

    @WrapOperation(method = {"submitNameTag"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitNameTag(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/phys/Vec3;ILnet/minecraft/network/chat/Component;ZIDLnet/minecraft/client/renderer/state/CameraRenderState;)V")})
    private void labyMod$transformNameTag(SubmitNodeCollector instance, PoseStack poseStack, Vec3 vec3, int i0, Component component, boolean b, int i1, double v, CameraRenderState cameraRenderState, Operation<Void> original, @Local(argsOnly = true) LocalRef<AvatarRenderState> entityRenderStateRef) {
        NameTagTransformer.transform(instance, poseStack, vec3, i0, component, b, i1, v, cameraRenderState, original, (EntityRenderState) entityRenderStateRef.get());
    }
}
