package net.labymod.v26_2_snapshot_8.mixins.client.renderer.state;

import net.labymod.v26_2_snapshot_8.client.util.EntityRenderStateAccessor;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/renderer/state/MixinEntityRenderState.class */
@Mixin({EntityRenderState.class})
public class MixinEntityRenderState implements EntityRenderStateAccessor<Entity> {
    private Entity labyMod$entity;

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.v26_2_snapshot_8.client.util.EntityRenderStateAccessor
    public Entity labyMod$getEntity() {
        return this.labyMod$entity;
    }

    @Override // net.labymod.v26_2_snapshot_8.client.util.EntityRenderStateAccessor
    public void labyMod$setEntity(Entity entity) {
        this.labyMod$entity = entity;
    }
}
