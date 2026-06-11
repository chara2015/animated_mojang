package net.labymod.v1_21_11.mixins.client.renderer.state;

import net.labymod.v1_21_11.client.util.EntityRenderStateAccessor;
import org.spongepowered.asm.mixin.Mixin;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/renderer/state/MixinEntityRenderState.class */
@Mixin({idf.class})
public class MixinEntityRenderState implements EntityRenderStateAccessor<cgk> {
    private cgk labyMod$entity;

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.v1_21_11.client.util.EntityRenderStateAccessor
    public cgk labyMod$getEntity() {
        return this.labyMod$entity;
    }

    @Override // net.labymod.v1_21_11.client.util.EntityRenderStateAccessor
    public void labyMod$setEntity(cgk entity) {
        this.labyMod$entity = entity;
    }
}
