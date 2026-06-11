package net.labymod.v1_17_1.mixins.client.renderer.entity.player;

import atu;
import ega;
import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.model.entity.player.PlayerModelRenderEvent;
import net.labymod.core.main.LabyMod;
import net.labymod.v1_17_1.client.render.LivingEntityRendererAccessor;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/client/renderer/entity/player/MixinLivingEntityRenderer.class */
@Mixin({esy.class})
public abstract class MixinLivingEntityRenderer<T extends atu, M extends ega<T>> implements LivingEntityRendererAccessor {

    @Shadow
    protected M f;
    private T labyMod$entity;
    private dql labyMod$poseStack;
    private int labyMod$packedLight;

    @Shadow
    protected abstract boolean a(ewc<T, M> ewcVar);

    @Shadow
    @Nullable
    protected abstract enq a(T t, boolean z, boolean z2, boolean z3);

    @Inject(method = {"shouldShowName"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$showOwnName(T entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity == dvp.C().s) {
            cir.setReturnValue(LabyMod.getInstance().config().ingame().showMyName().get());
        }
    }

    @Override // net.labymod.v1_17_1.client.render.LivingEntityRendererAccessor
    public void addCustomLayer(ewc layer) {
        a(layer);
    }

    @Inject(method = {"render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"}, at = {@At("HEAD")})
    public void labyMod$preRender(T entity, float param1, float param2, dql stack, eni multiBufferSource, int packedLight, CallbackInfo callbackInfo) {
        this.labyMod$entity = entity;
        this.labyMod$poseStack = stack;
        this.labyMod$packedLight = packedLight;
    }

    @Redirect(method = {"render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;getRenderType(Lnet/minecraft/world/entity/LivingEntity;ZZZ)Lnet/minecraft/client/renderer/RenderType;"))
    public enq labyMod$preRender(esy instance, T $$0, boolean $$1, boolean $$2, boolean $$3) {
        enq renderType = a($$0, $$1, $$2, $$3);
        if (renderType != null && firePlayerModelRenderEvent(Phase.PRE)) {
            return null;
        }
        return renderType;
    }

    @Inject(method = {"render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"}, at = {@At(value = "INVOKE", target = "net/minecraft/client/model/EntityModel.renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;IIFFFF)V", shift = At.Shift.AFTER)})
    public void labyMod$postRender(T entity, float param1, float param2, dql stack, eni param4, int packedLight, CallbackInfo callbackInfo) {
        firePlayerModelRenderEvent(Phase.POST);
    }

    private boolean firePlayerModelRenderEvent(Phase phase) {
        if (!(this.labyMod$entity instanceof emj)) {
            return false;
        }
        Stack stack = this.labyMod$poseStack.stack();
        Laby.references().renderEnvironmentContext().setPackedLight(this.labyMod$packedLight);
        return ((PlayerModelRenderEvent) Laby.fireEvent(new PlayerModelRenderEvent(this.labyMod$entity, this.f, stack, phase, this.labyMod$packedLight))).isCancelled();
    }
}
