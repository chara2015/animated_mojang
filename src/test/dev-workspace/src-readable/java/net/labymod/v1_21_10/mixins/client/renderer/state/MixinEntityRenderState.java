package net.labymod.v1_21_10.mixins.client.renderer.state;

import net.labymod.v1_21_10.client.util.EntityRenderStateAccessor;
import org.spongepowered.asm.mixin.Mixin;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/renderer/state/MixinEntityRenderState.class */
@Mixin({huk.class})
public class MixinEntityRenderState implements EntityRenderStateAccessor<cdv> {
    private cdv labyMod$entity;

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.v1_21_10.client.util.EntityRenderStateAccessor
    public cdv labyMod$getEntity() {
        return this.labyMod$entity;
    }

    @Override // net.labymod.v1_21_10.client.util.EntityRenderStateAccessor
    public void labyMod$setEntity(cdv entity) {
        this.labyMod$entity = entity;
    }
}
