package net.labymod.v1_21_3.mixins.client.renderer.state;

import net.labymod.v1_21_3.client.util.EntityRenderStateAccessor;
import org.spongepowered.asm.mixin.Mixin;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixins/client/renderer/state/MixinEntityRenderState.class */
@Mixin({gxv.class})
public class MixinEntityRenderState implements EntityRenderStateAccessor<bvk> {
    private bvk labyMod$entity;

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.v1_21_3.client.util.EntityRenderStateAccessor
    public bvk labyMod$getEntity() {
        return this.labyMod$entity;
    }

    @Override // net.labymod.v1_21_3.client.util.EntityRenderStateAccessor
    public void labyMod$setEntity(bvk entity) {
        this.labyMod$entity = entity;
    }
}
