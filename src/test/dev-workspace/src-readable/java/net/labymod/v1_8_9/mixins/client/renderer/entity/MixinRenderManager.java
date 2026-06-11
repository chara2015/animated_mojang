package net.labymod.v1_8_9.mixins.client.renderer.entity;

import net.labymod.api.client.entity.EntityRenderDispatcher;
import net.labymod.v1_8_9.client.renderer.EntityRendererAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/renderer/entity/MixinRenderManager.class */
@Mixin({biu.class})
public abstract class MixinRenderManager implements EntityRenderDispatcher {
    @Shadow
    protected abstract void b(pk pkVar, double d, double d2, double d3, float f, float f2);

    @Shadow
    public abstract void b(boolean z);

    @Shadow
    public abstract boolean b();

    @Override // net.labymod.api.client.entity.EntityRenderDispatcher
    public boolean isRenderDebugHitBoxes() {
        return b();
    }

    @Override // net.labymod.api.client.entity.EntityRenderDispatcher
    public void setRenderDebugHitBoxes(boolean renderDebugHitBoxes) {
        b(renderDebugHitBoxes);
    }

    @Inject(method = {"setPlayerViewY"}, at = {@At("TAIL")})
    public void labyMod$setPlayerViewY(float playerViewY, CallbackInfo ci) {
        EntityRendererAccessor accessor = ave.A().o;
        accessor.setCameraYaw(playerViewY);
    }
}
