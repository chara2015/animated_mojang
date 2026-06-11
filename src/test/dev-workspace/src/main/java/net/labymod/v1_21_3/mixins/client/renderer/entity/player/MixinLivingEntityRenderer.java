package net.labymod.v1_21_3.mixins.client.renderer.entity.player;

import bwg;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import gbm;
import gyt;
import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.model.entity.player.PlayerModelRenderEvent;
import net.labymod.core.main.LabyMod;
import net.labymod.v1_21_3.client.render.LivingEntityRendererAccessor;
import net.labymod.v1_21_3.client.util.EntityRenderStateAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixins/client/renderer/entity/player/MixinLivingEntityRenderer.class */
@Mixin({gsr.class})
public abstract class MixinLivingEntityRenderer<T extends bwg, S extends gyt, M extends gbm<? super S>> extends grp<T, S> implements gtn<S, M>, LivingEntityRendererAccessor {

    @Shadow
    protected M h;
    private T labyMod$entity;
    private fgs labyMod$poseStack;
    private int labyMod$packedLight;

    @Shadow
    protected abstract boolean a(gwf<S, M> gwfVar);

    protected MixinLivingEntityRenderer(a $$0) {
        super($$0);
    }

    @Inject(method = {"shouldShowName(Lnet/minecraft/world/entity/LivingEntity;D)Z"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$showOwnName(bwg entity, double $$1, CallbackInfoReturnable<Boolean> cir) {
        if (entity == fmg.Q().t) {
            cir.setReturnValue(LabyMod.getInstance().config().ingame().showMyName().get());
        }
    }

    @Override // net.labymod.v1_21_3.client.render.LivingEntityRendererAccessor
    public void addCustomLayer(gwf layer) {
        a(layer);
    }

    @Inject(method = {"render(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"}, at = {@At("HEAD")})
    public void labyMod$preRender(gyt state, fgs poseStack, gll bufferSource, int packedLightCoords, CallbackInfo ci) {
        T t;
        EntityRenderStateAccessor<bwg> entityState = EntityRenderStateAccessor.self(state);
        if (entityState == null || (t = (T) entityState.labyMod$getEntity()) == null) {
            return;
        }
        this.labyMod$entity = t;
        this.labyMod$poseStack = poseStack;
        this.labyMod$packedLight = packedLightCoords;
    }

    @WrapOperation(method = {"render(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;getRenderType(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;ZZZ)Lnet/minecraft/client/renderer/RenderType;")})
    public glv labyMod$preRender(gsr instance, gyt livingEntityRenderState, boolean flag0, boolean flag1, boolean glowing, Operation<glv> original) {
        glv renderType = (glv) original.call(new Object[]{instance, livingEntityRenderState, Boolean.valueOf(flag0), Boolean.valueOf(flag1), Boolean.valueOf(glowing)});
        if (renderType != null && firePlayerModelRenderEvent(Phase.PRE)) {
            return null;
        }
        return renderType;
    }

    @Inject(method = {"render(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"}, at = {@At(value = "INVOKE", target = "net/minecraft/client/model/EntityModel.renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V", shift = At.Shift.AFTER)})
    public void labyMod$postRender(gyt $$0, fgs $$1, gll $$2, int $$3, CallbackInfo ci) {
        firePlayerModelRenderEvent(Phase.POST);
    }

    private boolean firePlayerModelRenderEvent(Phase phase) {
        if (!(this.labyMod$entity instanceof gke)) {
            return false;
        }
        Stack stack = this.labyMod$poseStack.stack();
        Laby.references().renderEnvironmentContext().setPackedLight(this.labyMod$packedLight);
        return ((PlayerModelRenderEvent) Laby.fireEvent(new PlayerModelRenderEvent(this.labyMod$entity, this.h, stack, phase, this.labyMod$packedLight))).isCancelled();
    }
}
