package net.labymod.v1_21_11.mixins.client.renderer.state.snapshot;

import net.labymod.api.client.render.state.entity.EntitySnapshot;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/renderer/state/snapshot/MixinEntityRenderState_SnapshotAPI.class */
@Mixin({EntityRenderState.class})
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

    public double x() {
        return this.I;
    }

    public double y() {
        return this.J;
    }

    public double z() {
        return this.K;
    }

    public float eyeHeight() {
        return this.O;
    }

    public boolean isInvisible() {
        return this.Q;
    }

    public boolean isDiscrete() {
        return this.R;
    }

    public int lightCoords() {
        return this.T;
    }
}
