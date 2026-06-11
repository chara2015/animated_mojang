package net.labymod.v1_21_11.mixins.client.renderer.state.snapshot;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.EntityPose;
import net.labymod.api.client.render.state.entity.LivingEntitySnapshot;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.Pose;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/renderer/state/snapshot/MixinLivingRenderState_SnapshotAPI.class */
@Mixin({LivingEntityRenderState.class})
public abstract class MixinLivingRenderState_SnapshotAPI extends MixinEntityRenderState_SnapshotAPI implements LivingEntitySnapshot {

    @Shadow
    public float bodyRot;

    @Shadow
    public float yRot;

    @Shadow
    public float xRot;

    @Shadow
    public boolean isInvisibleToPlayer;

    @Shadow
    public Pose pose;

    public float bodyRot() {
        return this.bodyRot;
    }

    public float yRot() {
        return this.yRot;
    }

    public float xRot() {
        return this.xRot;
    }

    public boolean isInvisibleToPlayer() {
        return this.isInvisibleToPlayer;
    }

    public EntityPose entityPose() {
        return Laby.references().entityPoseMapper().fromMinecraft(this.pose);
    }
}

