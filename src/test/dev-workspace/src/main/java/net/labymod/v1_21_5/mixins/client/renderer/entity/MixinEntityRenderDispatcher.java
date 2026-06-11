package net.labymod.v1_21_5.mixins.client.renderer.entity;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.EntityRenderDispatcher;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.entity.EntityRenderEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/renderer/entity/MixinEntityRenderDispatcher.class */
@Mixin({gxt.class})
public abstract class MixinEntityRenderDispatcher implements EntityRenderDispatcher {
    @Shadow
    public abstract boolean a();

    @Shadow
    public abstract void b(boolean z);

    @Override // net.labymod.api.client.entity.EntityRenderDispatcher
    public boolean isRenderDebugHitBoxes() {
        return a();
    }

    @Override // net.labymod.api.client.entity.EntityRenderDispatcher
    public void setRenderDebugHitBoxes(boolean renderDebugHitBoxes) {
        b(renderDebugHitBoxes);
    }

    @Inject(method = {"render(Lnet/minecraft/world/entity/Entity;DDDFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/EntityRenderer;)V"}, at = {@At("HEAD")})
    private <E extends bxe, S extends hec> void labyMod$fireEntityRenderPre(bxe entity, double x, double y, double z, float partialTicks, fld poseStack, grn bufferSource, int $$7, gxu<? super E, S> $$8, CallbackInfo ci) {
        Laby.fireEvent(new EntityRenderEvent((Entity) entity, Phase.PRE));
    }

    @Inject(method = {"render(Lnet/minecraft/world/entity/Entity;DDDFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/EntityRenderer;)V"}, at = {@At("TAIL")})
    private <E extends bxe, S extends hec> void labyMod$fireEntityRenderPost(bxe entity, double x, double y, double z, float partialTicks, fld poseStack, grn bufferSource, int $$7, gxu<? super E, S> $$8, CallbackInfo ci) {
        Laby.fireEvent(new EntityRenderEvent((Entity) entity, Phase.POST));
    }
}
