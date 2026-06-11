package net.labymod.v1_21_5.mixins.client.renderer.state.snapshot;

import net.labymod.api.client.render.state.entity.EntitySnapshot;
import net.labymod.core.client.render.state.entity.EntitySnapshotExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/renderer/state/snapshot/MixinEntityRenderState_SnapshotAPI.class */
@Mixin({hec.class})
public abstract class MixinEntityRenderState_SnapshotAPI implements EntitySnapshot, EntitySnapshotExtension {

    @Shadow
    public boolean B;

    @Shadow
    public boolean A;

    @Shadow
    public float y;

    @Shadow
    public double s;

    @Shadow
    public double t;

    @Shadow
    public double u;

    @Unique
    private int labyMod$lightCoords;

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public double x() {
        return this.s;
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public double y() {
        return this.t;
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public double z() {
        return this.u;
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public float eyeHeight() {
        return this.y;
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public boolean isInvisible() {
        return this.A;
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public boolean isDiscrete() {
        return this.B;
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public int lightCoords() {
        return labyMod$getLightCoords();
    }

    @Override // net.labymod.core.client.render.state.entity.EntitySnapshotExtension
    public int labyMod$getLightCoords() {
        return this.labyMod$lightCoords;
    }

    @Override // net.labymod.core.client.render.state.entity.EntitySnapshotExtension
    public void labyMod$setLightCoords(int lightCoords) {
        this.labyMod$lightCoords = lightCoords;
    }
}
