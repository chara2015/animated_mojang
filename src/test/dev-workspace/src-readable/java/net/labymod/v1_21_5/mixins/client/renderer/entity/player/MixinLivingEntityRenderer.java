package net.labymod.v1_21_5.mixins.client.renderer.entity.player;

import byf;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import ghn;
import hfe;
import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.model.entity.player.PlayerModelRenderEvent;
import net.labymod.core.main.LabyMod;
import net.labymod.v1_21_5.client.render.LivingEntityRendererAccessor;
import net.labymod.v1_21_5.client.util.EntityRenderStateAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/renderer/entity/player/MixinLivingEntityRenderer.class */
@Mixin({gyw.class})
public abstract class MixinLivingEntityRenderer<T extends byf, S extends hfe, M extends ghn<? super S>> extends gxu<T, S> implements gzs<S, M>, LivingEntityRendererAccessor {

    @Shadow
    protected M g;
    private T labyMod$entity;
    private fld labyMod$poseStack;
    private int labyMod$packedLight;

    @Shadow
    protected abstract boolean a(hcj<S, M> hcjVar);

    protected MixinLivingEntityRenderer(a $$0) {
        super($$0);
    }

    @Inject(method = {"shouldShowName(Lnet/minecraft/world/entity/LivingEntity;D)Z"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$showOwnName(byf entity, double $$1, CallbackInfoReturnable<Boolean> cir) {
        if (entity == fqq.Q().t) {
            cir.setReturnValue(LabyMod.getInstance().config().ingame().showMyName().get());
        }
    }

    @Override // net.labymod.v1_21_5.client.render.LivingEntityRendererAccessor
    public void addCustomLayer(hcj layer) {
        a(layer);
    }

    @Inject(method = {"render(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"}, at = {@At("HEAD")})
    public void labyMod$preRender(hfe state, fld poseStack, grn bufferSource, int packedLightCoords, CallbackInfo ci) {
        T t;
        EntityRenderStateAccessor<byf> entityState = EntityRenderStateAccessor.self(state);
        if (entityState == null || (t = (T) entityState.labyMod$getEntity()) == null) {
            return;
        }
        this.labyMod$entity = t;
        this.labyMod$poseStack = poseStack;
        this.labyMod$packedLight = packedLightCoords;
    }

    @WrapOperation(method = {"render(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;getRenderType(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;ZZZ)Lnet/minecraft/client/renderer/RenderType;")})
    public gry labyMod$preRender(gyw instance, hfe livingEntityRenderState, boolean flag0, boolean flag1, boolean glowing, Operation<gry> original) {
        gry renderType = (gry) original.call(new Object[]{instance, livingEntityRenderState, Boolean.valueOf(flag0), Boolean.valueOf(flag1), Boolean.valueOf(glowing)});
        if (renderType != null && firePlayerModelRenderEvent(Phase.PRE)) {
            return null;
        }
        return renderType;
    }

    @Inject(method = {"render(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"}, at = {@At(value = "INVOKE", target = "net/minecraft/client/model/EntityModel.renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V", shift = At.Shift.AFTER)})
    public void labyMod$postRender(hfe $$0, fld $$1, grn $$2, int $$3, CallbackInfo ci) {
        firePlayerModelRenderEvent(Phase.POST);
    }

    private boolean firePlayerModelRenderEvent(Phase phase) {
        if (!(this.labyMod$entity instanceof gqj)) {
            return false;
        }
        Stack stack = this.labyMod$poseStack.stack();
        Laby.references().renderEnvironmentContext().setPackedLight(this.labyMod$packedLight);
        return ((PlayerModelRenderEvent) Laby.fireEvent(new PlayerModelRenderEvent(this.labyMod$entity, this.g, stack, phase, this.labyMod$packedLight))).isCancelled();
    }
}
