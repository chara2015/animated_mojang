package net.labymod.v1_21_3.mixins.client.renderer.state.snapshot;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.EntityPose;
import net.labymod.api.client.render.state.entity.LivingEntitySnapshot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixins/client/renderer/state/snapshot/MixinLivingRenderState_SnapshotAPI.class */
@Mixin({gyt.class})
public abstract class MixinLivingRenderState_SnapshotAPI extends MixinEntityRenderState_SnapshotAPI implements LivingEntitySnapshot {

    @Shadow
    public float T;

    @Shadow
    public float U;

    @Shadow
    public float V;

    @Shadow
    public boolean ai;

    @Shadow
    public bws am;

    @Override // net.labymod.api.client.render.state.entity.LivingEntitySnapshot
    public float bodyRot() {
        return this.T;
    }

    @Override // net.labymod.api.client.render.state.entity.LivingEntitySnapshot
    public float yRot() {
        return this.U;
    }

    @Override // net.labymod.api.client.render.state.entity.LivingEntitySnapshot
    public float xRot() {
        return this.V;
    }

    @Override // net.labymod.api.client.render.state.entity.LivingEntitySnapshot
    public boolean isInvisibleToPlayer() {
        return this.ai;
    }

    @Override // net.labymod.api.client.render.state.entity.LivingEntitySnapshot
    public EntityPose entityPose() {
        return Laby.references().entityPoseMapper().fromMinecraft(this.am);
    }
}
