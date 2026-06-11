package net.labymod.v1_21_10.mixins.client.renderer.state.snapshot;

import net.labymod.api.client.render.state.entity.EntitySnapshot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/renderer/state/snapshot/MixinEntityRenderState_SnapshotAPI.class */
@Mixin({huk.class})
public abstract class MixinEntityRenderState_SnapshotAPI implements EntitySnapshot {

    @Shadow
    public boolean N;

    @Shadow
    public boolean M;

    @Shadow
    public float K;

    @Shadow
    public double E;

    @Shadow
    public double F;

    @Shadow
    public double G;

    @Shadow
    public int P;

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public double x() {
        return this.E;
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public double y() {
        return this.F;
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public double z() {
        return this.G;
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public float eyeHeight() {
        return this.K;
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public boolean isInvisible() {
        return this.M;
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public boolean isDiscrete() {
        return this.N;
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public int lightCoords() {
        return this.P;
    }
}
