package net.labymod.core.client.render.state.entity.mutable;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.EntityPose;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.render.state.entity.LivingEntitySnapshot;
import net.labymod.api.util.math.MathHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/state/entity/mutable/LiveLivingEntitySnapshot.class */
public class LiveLivingEntitySnapshot<E extends LivingEntity> extends LiveEntitySnapshot<E> implements LivingEntitySnapshot {
    public LiveLivingEntitySnapshot(E entity, float partialTicks) {
        super(entity, partialTicks);
    }

    @Override // net.labymod.api.client.render.state.entity.LivingEntitySnapshot
    public float bodyRot() {
        return MathHelper.rotLerp(((LivingEntity) this.entity).getBodyRotationY(), ((LivingEntity) this.entity).getPreviousBodyRotationY(), this.partialTicks);
    }

    @Override // net.labymod.api.client.render.state.entity.LivingEntitySnapshot
    public float yRot() {
        return MathHelper.rotLerp(((LivingEntity) this.entity).getHeadRotationY(), ((LivingEntity) this.entity).getPreviousHeadRotationY(), this.partialTicks);
    }

    @Override // net.labymod.api.client.render.state.entity.LivingEntitySnapshot
    public float xRot() {
        if (this.partialTicks == 1.0f) {
            return ((LivingEntity) this.entity).getHeadRotationX();
        }
        return MathHelper.rotLerp(((LivingEntity) this.entity).getHeadRotationX(), ((LivingEntity) this.entity).getPreviousHeadRotationX(), this.partialTicks);
    }

    @Override // net.labymod.api.client.render.state.entity.LivingEntitySnapshot
    public boolean isInvisibleToPlayer() {
        ClientPlayer player = Laby.labyAPI().minecraft().getClientPlayer();
        if (player == null) {
            return false;
        }
        return ((LivingEntity) this.entity).isInvisibleFor(player);
    }

    @Override // net.labymod.api.client.render.state.entity.LivingEntitySnapshot
    public EntityPose entityPose() {
        return ((LivingEntity) this.entity).entityPose();
    }
}
