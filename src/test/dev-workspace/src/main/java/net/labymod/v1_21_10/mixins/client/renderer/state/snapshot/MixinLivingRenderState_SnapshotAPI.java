package net.labymod.v1_21_10.mixins.client.renderer.state.snapshot;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.EntityPose;
import net.labymod.api.client.render.state.entity.LivingEntitySnapshot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/renderer/state/snapshot/MixinLivingRenderState_SnapshotAPI.class */
@Mixin({hvn.class})
public abstract class MixinLivingRenderState_SnapshotAPI extends MixinEntityRenderState_SnapshotAPI implements LivingEntitySnapshot {

    @Shadow
    public float as;

    @Shadow
    public float at;

    @Shadow
    public float au;

    @Shadow
    public boolean aG;

    @Shadow
    public cfi aI;

    @Override // net.labymod.api.client.render.state.entity.LivingEntitySnapshot
    public float bodyRot() {
        return this.as;
    }

    @Override // net.labymod.api.client.render.state.entity.LivingEntitySnapshot
    public float yRot() {
        return this.at;
    }

    @Override // net.labymod.api.client.render.state.entity.LivingEntitySnapshot
    public float xRot() {
        return this.au;
    }

    @Override // net.labymod.api.client.render.state.entity.LivingEntitySnapshot
    public boolean isInvisibleToPlayer() {
        return this.aG;
    }

    @Override // net.labymod.api.client.render.state.entity.LivingEntitySnapshot
    public EntityPose entityPose() {
        return Laby.references().entityPoseMapper().fromMinecraft(this.aI);
    }
}
