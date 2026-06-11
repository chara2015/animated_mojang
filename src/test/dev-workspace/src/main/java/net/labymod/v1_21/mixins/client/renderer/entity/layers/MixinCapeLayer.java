package net.labymod.v1_21.mixins.client.renderer.entity.layers;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.OptiFinePlayer;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.gfx.pipeline.util.RenderStateShardAttachment;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.model.entity.player.PlayerCapeRenderEvent;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.gfx.pipeline.renderer.cape.particle.CapeParticleController;
import net.labymod.core.main.LabyMod;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/mixins/client/renderer/entity/layers/MixinCapeLayer.class */
@Mixin({gnv.class})
public class MixinCapeLayer {
    private final CapeParticleController labyMod$capeParticleController = LabyMod.references().capeParticleController();
    private gfh labyMod$renderType;

    @Insert(method = {"render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;FFFFFF)V"}, at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V", shift = At.Shift.AFTER), cancellable = true)
    private void labyMod$firePreCapeRenderEvent(fbi poseStack, gez param1, int param2, gdy clientPlayer, float param4, float param5, float partialTicks, float param7, float param8, float param9, InsertInfo ci) {
        if (labyMod$fireCapeRenderEvent(poseStack, clientPlayer, Phase.PRE)) {
            poseStack.b();
            ci.cancel();
        }
    }

    @Insert(method = {"render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;FFFFFF)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/PlayerModel;renderCloak(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V", shift = At.Shift.AFTER))
    private void labyMod$firePostCapeRenderEvent(fbi poseStack, gez param1, int param2, gdy clientPlayer, float param4, float param5, float partialTicks, float param7, float param8, float param9, InsertInfo ci) {
        labyMod$fireCapeRenderEvent(poseStack, clientPlayer, Phase.POST);
    }

    @WrapOperation(method = {"render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;FFFFFF)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;entitySolid(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/RenderType;")})
    private gfh labyMod$attach(akr location, Operation<gfh> original) {
        gfh gfhVar = (gfh) original.call(new Object[]{location});
        this.labyMod$renderType = gfhVar;
        return gfhVar;
    }

    @Insert(method = {"render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;FFFFFF)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/PlayerModel;renderCloak(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V", shift = At.Shift.AFTER))
    private void labyMod$renderCapeParticles(fbi param0, gez param1, int param2, gdy player, float param4, float param5, float partialTicks, float param7, float param8, float param9, InsertInfo ci) {
        if (player instanceof OptiFinePlayer) {
            OptiFinePlayer optiFinePlayer = (OptiFinePlayer) player;
            if (optiFinePlayer.getOptiFineCapeLocation() != null) {
                return;
            }
        }
        Stack stack = ((VanillaStackAccessor) param0).stack();
        Matrix4f modelViewMatrix = new Matrix4f(stack.getProvider().getPose());
        RenderStateShardAttachment.addAttachment(this.labyMod$renderType, () -> {
            this.labyMod$capeParticleController.spawn(modelViewMatrix, (Player) player, partialTicks);
        });
    }

    private boolean labyMod$fireCapeRenderEvent(fbi poseStack, gdy clientPlayer, Phase phase) {
        Player player = (Player) clientPlayer;
        PlayerModel playerModel = ((gov) this).c();
        Stack stack = ((VanillaStackAccessor) poseStack).stack();
        return ((PlayerCapeRenderEvent) Laby.fireEvent(new PlayerCapeRenderEvent(player, playerModel, stack, phase))).isCancelled();
    }
}
