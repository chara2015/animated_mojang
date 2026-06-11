package net.labymod.v1_21_5.mixins.client.renderer.entity.layers;

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
import net.labymod.v1_21_5.client.util.EntityRenderStateAccessor;
import net.labymod.v1_21_5.client.util.MinecraftUtil;
import net.labymod.v1_21_5.client.util.PoseStackVisitor;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/renderer/entity/layers/MixinCapeLayer.class */
@Mixin({hbj.class})
public class MixinCapeLayer {

    @Shadow
    @Final
    private gib<hfq> a;
    private final CapeParticleController labyMod$capeParticleController = LabyMod.references().capeParticleController();
    private gry labyMod$renderType;

    @Inject(method = {"render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/PlayerRenderState;FF)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/MultiBufferSource;getBuffer(Lnet/minecraft/client/renderer/RenderType;)Lcom/mojang/blaze3d/vertex/VertexConsumer;", shift = At.Shift.BEFORE)}, cancellable = true)
    private void labyMod$firePreCapeRenderEvent(fld poseStack, grn bufferSource, int $$2, hfq state, float $$4, float $$5, CallbackInfo ci) {
        if (labyMod$fireCapeRenderEvent(poseStack, state, Phase.PRE)) {
            poseStack.b();
            ci.cancel();
        }
    }

    @Inject(method = {"render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/PlayerRenderState;FF)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/model/HumanoidModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V", shift = At.Shift.AFTER)})
    private void labyMod$firePostCapeRenderEvent(fld poseStack, grn bufferSource, int $$2, hfq state, float $$4, float $$5, CallbackInfo ci) {
        labyMod$fireCapeRenderEvent(poseStack, state, Phase.POST);
    }

    @WrapOperation(method = {"render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/PlayerRenderState;FF)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;entitySolid(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/RenderType;")})
    private gry labyMod$attach(alr location, Operation<gry> original) {
        gry gryVar = (gry) original.call(new Object[]{location});
        this.labyMod$renderType = gryVar;
        return gryVar;
    }

    @Inject(method = {"render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/PlayerRenderState;FF)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/model/HumanoidModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V", shift = At.Shift.AFTER)})
    private void labyMod$renderCapeParticles(fld poseStack, grn $$1, int $$2, hfq state, float $$4, float $$5, CallbackInfo ci) {
        gqj gqjVarLabyMod$getEntity;
        EntityRenderStateAccessor<gqj> playerState = EntityRenderStateAccessor.self(state);
        if (playerState == null || (gqjVarLabyMod$getEntity = playerState.labyMod$getEntity()) == null) {
            return;
        }
        if (gqjVarLabyMod$getEntity instanceof OptiFinePlayer) {
            OptiFinePlayer optiFinePlayer = (OptiFinePlayer) gqjVarLabyMod$getEntity;
            if (optiFinePlayer.getOptiFineCapeLocation() != null) {
                return;
            }
        }
        Stack stack = ((VanillaStackAccessor) poseStack).stack();
        poseStack.a();
        this.a.e().a(MinecraftUtil.VISITOR_STACK, new PoseStackVisitor(poseStack));
        Matrix4f modelViewMatrix = new Matrix4f(stack.getProvider().getPose());
        RenderStateShardAttachment.addAttachment(this.labyMod$renderType, () -> {
            this.labyMod$capeParticleController.spawn(modelViewMatrix, (Player) gqjVarLabyMod$getEntity, Laby.labyAPI().minecraft().getPartialTicks());
        });
        poseStack.b();
    }

    private boolean labyMod$fireCapeRenderEvent(fld poseStack, hfq state, Phase phase) {
        gqj gqjVarLabyMod$getEntity;
        EntityRenderStateAccessor<gqj> playerState = EntityRenderStateAccessor.self(state);
        if (playerState == null || (gqjVarLabyMod$getEntity = playerState.labyMod$getEntity()) == null) {
            return false;
        }
        Player player = (Player) gqjVarLabyMod$getEntity;
        PlayerModel playerModel = ((hcj) this).d();
        Stack stack = ((VanillaStackAccessor) poseStack).stack();
        return ((PlayerCapeRenderEvent) Laby.fireEvent(new PlayerCapeRenderEvent(player, playerModel, stack, phase))).isCancelled();
    }
}
