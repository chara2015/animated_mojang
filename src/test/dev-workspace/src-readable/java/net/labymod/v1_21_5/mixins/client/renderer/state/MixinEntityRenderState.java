package net.labymod.v1_21_5.mixins.client.renderer.state;

import net.labymod.v1_21_5.client.util.EntityRenderStateAccessor;
import org.spongepowered.asm.mixin.Mixin;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/renderer/state/MixinEntityRenderState.class */
@Mixin({hec.class})
public class MixinEntityRenderState implements EntityRenderStateAccessor<bxe> {
    private bxe labyMod$entity;

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.v1_21_5.client.util.EntityRenderStateAccessor
    public bxe labyMod$getEntity() {
        return this.labyMod$entity;
    }

    @Override // net.labymod.v1_21_5.client.util.EntityRenderStateAccessor
    public void labyMod$setEntity(bxe entity) {
        this.labyMod$entity = entity;
    }
}
