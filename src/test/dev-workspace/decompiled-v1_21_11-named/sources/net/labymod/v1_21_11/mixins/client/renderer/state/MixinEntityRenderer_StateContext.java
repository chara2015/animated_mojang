package net.labymod.v1_21_11.mixins.client.renderer.state;

import net.labymod.v1_21_11.client.util.EntityRenderStateAccessor;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/renderer/state/MixinEntityRenderer_StateContext.class */
@Mixin({EntityRenderer.class})
public class MixinEntityRenderer_StateContext<T extends Entity, S extends EntityRenderState> {
    @Inject(method = {"createRenderState(Lnet/minecraft/world/entity/Entity;F)Lnet/minecraft/client/renderer/entity/state/EntityRenderState;"}, at = {@At("TAIL")})
    private void labyMod$setEntity(T entity, float $$1, CallbackInfoReturnable<S> cir) {
        EntityRenderStateAccessor entityRenderStateAccessor = (EntityRenderState) cir.getReturnValue();
        if (!(entityRenderStateAccessor instanceof EntityRenderStateAccessor)) {
            return;
        }
        EntityRenderStateAccessor accessor = entityRenderStateAccessor;
        accessor.labyMod$setEntity(entity);
    }
}
