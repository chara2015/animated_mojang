package net.labymod.v1_21_8.mixins.client.renderer.state.snapshot;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.EntityPose;
import net.labymod.api.client.render.state.entity.LivingEntitySnapshot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/client/renderer/state/snapshot/MixinLivingRenderState_SnapshotAPI.class */
@Mixin({hlq.class})
public abstract class MixinLivingRenderState_SnapshotAPI extends MixinEntityRenderState_SnapshotAPI implements LivingEntitySnapshot {

    @Shadow
    public float ac;

    @Shadow
    public float ad;

    @Shadow
    public float ae;

    @Shadow
    public boolean aq;

    @Shadow
    public cay au;

    @Override // net.labymod.api.client.render.state.entity.LivingEntitySnapshot
    public float bodyRot() {
        return this.ac;
    }

    @Override // net.labymod.api.client.render.state.entity.LivingEntitySnapshot
    public float yRot() {
        return this.ad;
    }

    @Override // net.labymod.api.client.render.state.entity.LivingEntitySnapshot
    public float xRot() {
        return this.ae;
    }

    @Override // net.labymod.api.client.render.state.entity.LivingEntitySnapshot
    public boolean isInvisibleToPlayer() {
        return this.aq;
    }

    @Override // net.labymod.api.client.render.state.entity.LivingEntitySnapshot
    public EntityPose entityPose() {
        return Laby.references().entityPoseMapper().fromMinecraft(this.au);
    }
}
