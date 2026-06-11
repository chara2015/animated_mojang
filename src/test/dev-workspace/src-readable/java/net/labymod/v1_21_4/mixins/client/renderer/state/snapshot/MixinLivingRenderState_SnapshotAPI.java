package net.labymod.v1_21_4.mixins.client.renderer.state.snapshot;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.EntityPose;
import net.labymod.api.client.render.state.entity.LivingEntitySnapshot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/client/renderer/state/snapshot/MixinLivingRenderState_SnapshotAPI.class */
@Mixin({gzl.class})
public abstract class MixinLivingRenderState_SnapshotAPI extends MixinEntityRenderState_SnapshotAPI implements LivingEntitySnapshot {

    @Shadow
    public float Z;

    @Shadow
    public float aa;

    @Shadow
    public float ab;

    @Shadow
    public boolean an;

    @Shadow
    public bvu ar;

    @Override // net.labymod.api.client.render.state.entity.LivingEntitySnapshot
    public float bodyRot() {
        return this.Z;
    }

    @Override // net.labymod.api.client.render.state.entity.LivingEntitySnapshot
    public float yRot() {
        return this.aa;
    }

    @Override // net.labymod.api.client.render.state.entity.LivingEntitySnapshot
    public float xRot() {
        return this.ab;
    }

    @Override // net.labymod.api.client.render.state.entity.LivingEntitySnapshot
    public boolean isInvisibleToPlayer() {
        return this.an;
    }

    @Override // net.labymod.api.client.render.state.entity.LivingEntitySnapshot
    public EntityPose entityPose() {
        return Laby.references().entityPoseMapper().fromMinecraft(this.ar);
    }
}
