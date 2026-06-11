package net.labymod.v1_18_2.mixins.client.renderer.entity.layers;

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
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/renderer/entity/layers/MixinCapeLayer.class */
@Mixin({eyo.class})
public class MixinCapeLayer {
    private static final String RENDER_METHOD = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;FFFFFF)V";
    private final CapeParticleController labyMod$capeParticleController = LabyMod.references().capeParticleController();
    private float labyMod$partialTicks;
    private ept labyMod$player;
    private era labyMod$renderType;

    @Insert(method = {RENDER_METHOD}, at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(DDD)V", shift = At.Shift.AFTER), cancellable = true)
    private void labyMod$firePreCapeRenderEvent(dtm poseStack, eqs param1, int param2, ept player, float param4, float param5, float partialTicks, float param7, float param8, float param9, InsertInfo ci) {
        this.labyMod$partialTicks = partialTicks;
        this.labyMod$player = player;
        if (labyMod$fireCapeRenderEvent(poseStack, player, Phase.PRE)) {
            poseStack.b();
            ci.cancel();
        }
    }

    @Insert(method = {RENDER_METHOD}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/PlayerModel;renderCloak(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V", shift = At.Shift.AFTER))
    private void labyMod$firePostCapeRenderEvent(dtm poseStack, eqs param1, int param2, ept player, float param4, float param5, float partialTicks, float param7, float param8, float param9, InsertInfo ci) {
        labyMod$fireCapeRenderEvent(poseStack, player, Phase.POST);
    }

    @WrapOperation(method = {RENDER_METHOD}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;entitySolid(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/RenderType;")})
    private era labyMod$attach(yt location, Operation<era> original) {
        era eraVar = (era) original.call(new Object[]{location});
        this.labyMod$renderType = eraVar;
        return eraVar;
    }

    @Insert(method = {RENDER_METHOD}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/PlayerModel;renderCloak(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V", shift = At.Shift.AFTER))
    private void labyMod$renderCapeParticles(dtm param0, eqs param1, int param2, ept player, float param4, float param5, float partialTicks, float param7, float param8, float param9, InsertInfo ci) {
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

    @ModifyVariable(method = {RENDER_METHOD}, ordinal = 6, at = @At("STORE"))
    private float interpolate(float yaw) {
        return ((yaw - this.labyMod$player.aY) * this.labyMod$partialTicks) + this.labyMod$player.aY;
    }

    private boolean labyMod$fireCapeRenderEvent(dtm poseStack, ept clientPlayer, Phase phase) {
        Player player = (Player) clientPlayer;
        PlayerModel playerModel = ((ezo) this).c();
        Stack stack = ((VanillaStackAccessor) poseStack).stack();
        return ((PlayerCapeRenderEvent) Laby.fireEvent(new PlayerCapeRenderEvent(player, playerModel, stack, phase))).isCancelled();
    }
}
