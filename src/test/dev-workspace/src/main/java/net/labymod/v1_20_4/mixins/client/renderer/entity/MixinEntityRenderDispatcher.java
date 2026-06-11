package net.labymod.v1_20_4.mixins.client.renderer.entity;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/client/renderer/entity/MixinEntityRenderDispatcher.class */
@Mixin({fyl.class})
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

    @Inject(method = {"render"}, at = {@At("HEAD")})
    private void labyMod$fireEntityRenderPre(blv param0, double param1, double param2, double param3, float param4, float param5, eqb param6, fth param7, int param8, CallbackInfo ci) {
        Laby.fireEvent(new EntityRenderEvent((Entity) param0, Phase.PRE));
    }

    @Inject(method = {"render"}, at = {@At("TAIL")})
    private void labyMod$fireEntityRenderPost(blv param0, double param1, double param2, double param3, float param4, float param5, eqb param6, fth param7, int param8, CallbackInfo ci) {
        Laby.fireEvent(new EntityRenderEvent((Entity) param0, Phase.POST));
    }
}
