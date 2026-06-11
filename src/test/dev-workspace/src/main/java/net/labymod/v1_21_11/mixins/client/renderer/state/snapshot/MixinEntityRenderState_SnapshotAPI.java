package net.labymod.v1_21_11.mixins.client.renderer.state.snapshot;

import net.labymod.api.client.render.state.entity.EntitySnapshot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/renderer/state/snapshot/MixinEntityRenderState_SnapshotAPI.class */
@Mixin({idf.class})
public abstract class MixinEntityRenderState_SnapshotAPI implements EntitySnapshot {

    @Shadow
    public boolean R;

    @Shadow
    public boolean Q;

    @Shadow
    public float O;

    @Shadow
    public double I;

    @Shadow
    public double J;

    @Shadow
    public double K;

    @Shadow
    public int T;

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public double x() {
        return this.I;
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public double y() {
        return this.J;
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public double z() {
        return this.K;
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public float eyeHeight() {
        return this.O;
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public boolean isInvisible() {
        return this.Q;
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public boolean isDiscrete() {
        return this.R;
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public int lightCoords() {
        return this.T;
    }
}
