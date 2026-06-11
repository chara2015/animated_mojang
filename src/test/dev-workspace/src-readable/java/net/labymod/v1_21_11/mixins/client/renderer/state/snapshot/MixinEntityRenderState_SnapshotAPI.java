package net.labymod.v1_21_11.mixins.client.renderer.state.snapshot;

import net.labymod.api.client.render.state.entity.EntitySnapshot;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/renderer/state/snapshot/MixinEntityRenderState_SnapshotAPI.class */
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

    public double x() {
        return this.x;
    }

    public double y() {
        return this.y;
    }

    public double z() {
        return this.z;
    }

    public float eyeHeight() {
        return this.eyeHeight;
    }

    public boolean isInvisible() {
        return this.isInvisible;
    }

    public boolean isDiscrete() {
        return this.isDiscrete;
    }

    public int lightCoords() {
        return this.lightCoords;
    }
}

