package net.labymod.v1_21_4.mixins.client.renderer.state;

import net.labymod.v1_21_4.client.util.EntityRenderStateAccessor;
import org.spongepowered.asm.mixin.Mixin;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/client/renderer/state/MixinEntityRenderState.class */
@Mixin({gyl.class})
public class MixinEntityRenderState implements EntityRenderStateAccessor<bum> {
    private bum labyMod$entity;

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.v1_21_4.client.util.EntityRenderStateAccessor
    public bum labyMod$getEntity() {
        return this.labyMod$entity;
    }

    @Override // net.labymod.v1_21_4.client.util.EntityRenderStateAccessor
    public void labyMod$setEntity(bum entity) {
        this.labyMod$entity = entity;
    }
}
