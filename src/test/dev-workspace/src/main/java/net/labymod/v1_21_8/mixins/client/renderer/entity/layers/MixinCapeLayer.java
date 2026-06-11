package net.labymod.v1_21_8.mixins.client.renderer.entity.layers;

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
import net.labymod.core.client.gfx.pipeline.renderer.cape.particle.CapeParticleController;
import net.labymod.core.main.LabyMod;
import net.labymod.v1_21_8.client.util.EntityRenderStateAccessor;
import net.labymod.v1_21_8.client.util.MinecraftUtil;
import net.labymod.v1_21_8.client.util.PoseStackVisitor;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/client/renderer/entity/layers/MixinCapeLayer.class */
@Mixin({hht.class})
public class MixinCapeLayer {

    @Shadow
    @Final
    private gnx<hmc> a;
    private final CapeParticleController labyMod$capeParticleController = LabyMod.references().capeParticleController();
    private gxz labyMod$renderType;

    @Inject(method = {"render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/PlayerRenderState;FF)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/MultiBufferSource;getBuffer(Lnet/minecraft/client/renderer/RenderType;)Lcom/mojang/blaze3d/vertex/VertexConsumer;", shift = At.Shift.BEFORE)}, cancellable = true)
    private void labyMod$firePreCapeRenderEvent(fod poseStack, gxn bufferSource, int $$2, hmc state, float $$4, float $$5, CallbackInfo ci) {
        if (labyMod$fireCapeRenderEvent(poseStack, state, Phase.PRE)) {
            poseStack.b();
            ci.cancel();
        }
    }

    @Inject(method = {"render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/PlayerRenderState;FF)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/model/HumanoidModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V", shift = At.Shift.AFTER)})
    private void labyMod$firePostCapeRenderEvent(fod poseStack, gxn bufferSource, int $$2, hmc state, float $$4, float $$5, CallbackInfo ci) {
        labyMod$fireCapeRenderEvent(poseStack, state, Phase.POST);
    }

    @WrapOperation(method = {"render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/PlayerRenderState;FF)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;entitySolid(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/RenderType;")})
    private gxz labyMod$attach(ame location, Operation<gxz> original) {
        gxz gxzVar = (gxz) original.call(new Object[]{location});
        this.labyMod$renderType = gxzVar;
        return gxzVar;
    }

    @Inject(method = {"render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/PlayerRenderState;FF)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/model/HumanoidModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V", shift = At.Shift.AFTER)})
    private void labyMod$renderCapeParticles(fod poseStack, gxn $$1, int $$2, hmc state, float $$4, float $$5, CallbackInfo ci) {
        gwf gwfVarLabyMod$getEntity;
        EntityRenderStateAccessor<gwf> playerState = EntityRenderStateAccessor.self(state);
        if (playerState == null || (gwfVarLabyMod$getEntity = playerState.labyMod$getEntity()) == null) {
            return;
        }
        if (gwfVarLabyMod$getEntity instanceof OptiFinePlayer) {
            OptiFinePlayer optiFinePlayer = (OptiFinePlayer) gwfVarLabyMod$getEntity;
            if (optiFinePlayer.getOptiFineCapeLocation() != null) {
                return;
            }
        }
        Stack stack = ((VanillaStackAccessor) poseStack).stack();
        poseStack.a();
        this.a.e().a(MinecraftUtil.VISITOR_STACK, new PoseStackVisitor(poseStack));
        Matrix4f modelViewMatrix = new Matrix4f(stack.getProvider().getPose());
        RenderStateShardAttachment.addAttachment(this.labyMod$renderType, () -> {
            this.labyMod$capeParticleController.spawn(modelViewMatrix, (Player) gwfVarLabyMod$getEntity, Laby.labyAPI().minecraft().getPartialTicks());
        });
        poseStack.b();
    }

    private boolean labyMod$fireCapeRenderEvent(fod poseStack, hmc state, Phase phase) {
        gwf gwfVarLabyMod$getEntity;
        EntityRenderStateAccessor<gwf> playerState = EntityRenderStateAccessor.self(state);
        if (playerState == null || (gwfVarLabyMod$getEntity = playerState.labyMod$getEntity()) == null) {
            return false;
        }
        Player player = (Player) gwfVarLabyMod$getEntity;
        PlayerModel playerModel = ((hit) this).d();
        Stack stack = ((VanillaStackAccessor) poseStack).stack();
        return ((PlayerCapeRenderEvent) Laby.fireEvent(new PlayerCapeRenderEvent(player, playerModel, stack, phase))).isCancelled();
    }
}
