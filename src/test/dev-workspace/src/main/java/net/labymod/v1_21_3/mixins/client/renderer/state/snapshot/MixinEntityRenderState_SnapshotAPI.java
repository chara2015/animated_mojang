package net.labymod.v1_21_3.mixins.client.renderer.state.snapshot;

import net.labymod.api.client.render.state.entity.EntitySnapshot;
import net.labymod.core.client.render.state.entity.EntitySnapshotExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixins/client/renderer/state/snapshot/MixinEntityRenderState_SnapshotAPI.class */
@Mixin({gxv.class})
public abstract class MixinEntityRenderState_SnapshotAPI implements EntitySnapshot, EntitySnapshotExtension {

    @Shadow
    public boolean v;

    @Shadow
    public boolean u;

    @Shadow
    public float s;

    @Shadow
    public double m;

    @Shadow
    public double n;

    @Shadow
    public double o;

    @Unique
    private int labyMod$lightCoords;

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public double x() {
        return this.m;
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public double y() {
        return this.n;
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public double z() {
        return this.o;
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public float eyeHeight() {
        return this.s;
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public boolean isInvisible() {
        return this.u;
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public boolean isDiscrete() {
        return this.v;
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
