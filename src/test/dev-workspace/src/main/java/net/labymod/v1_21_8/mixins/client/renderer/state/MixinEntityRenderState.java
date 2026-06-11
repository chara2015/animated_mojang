package net.labymod.v1_21_8.mixins.client.renderer.state;

import net.labymod.v1_21_8.client.util.EntityRenderStateAccessor;
import org.spongepowered.asm.mixin.Mixin;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/client/renderer/state/MixinEntityRenderState.class */
@Mixin({hkn.class})
public class MixinEntityRenderState implements EntityRenderStateAccessor<bzm> {
    private bzm labyMod$entity;

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.v1_21_8.client.util.EntityRenderStateAccessor
    public bzm labyMod$getEntity() {
        return this.labyMod$entity;
    }

    @Override // net.labymod.v1_21_8.client.util.EntityRenderStateAccessor
    public void labyMod$setEntity(bzm entity) {
        this.labyMod$entity = entity;
    }
}
