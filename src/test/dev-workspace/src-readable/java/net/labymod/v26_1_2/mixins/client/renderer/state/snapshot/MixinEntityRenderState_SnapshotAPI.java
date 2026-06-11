package net.labymod.v26_1_2.mixins.client.renderer.state.snapshot;

import net.labymod.api.client.render.state.entity.EntitySnapshot;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/client/renderer/state/snapshot/MixinEntityRenderState_SnapshotAPI.class */
@Mixin({EntityRenderState.class})
public abstract class MixinEntityRenderState_SnapshotAPI implements EntitySnapshot {

    @Shadow
    public boolean isDiscrete;

    @Shadow
    public boolean isInvisible;

    @Shadow
    public float eyeHeight;

    @Shadow
    public double x;

    @Shadow
    public double y;

    @Shadow
    public double z;

    @Shadow
    public int lightCoords;

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public double x() {
        return this.x;
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public double y() {
        return this.y;
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public double z() {
        return this.z;
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public float eyeHeight() {
        return this.eyeHeight;
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public boolean isInvisible() {
        return this.isInvisible;
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public boolean isDiscrete() {
        return this.isDiscrete;
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public int lightCoords() {
        return this.lightCoords;
    }
}
