package net.labymod.v1_21_8.mixins.client.renderer.state;

import bzm;
import hkn;
import net.labymod.v1_21_8.client.util.EntityRenderStateAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/client/renderer/state/MixinEntityRenderer_StateContext.class */
@Mixin({hed.class})
public class MixinEntityRenderer_StateContext<T extends bzm, S extends hkn> {
    @Inject(method = {"createRenderState(Lnet/minecraft/world/entity/Entity;F)Lnet/minecraft/client/renderer/entity/state/EntityRenderState;"}, at = {@At("TAIL")})
    private void labyMod$setEntity(T entity, float $$1, CallbackInfoReturnable<S> cir) {
        EntityRenderStateAccessor entityRenderStateAccessor = (hkn) cir.getReturnValue();
        if (!(entityRenderStateAccessor instanceof EntityRenderStateAccessor)) {
            return;
        }
        EntityRenderStateAccessor accessor = entityRenderStateAccessor;
        accessor.labyMod$setEntity(entity);
    }
}
