package net.labymod.api.client.render.state.entity;

import net.labymod.api.client.entity.EntityPose;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/state/entity/LivingEntitySnapshot.class */
public interface LivingEntitySnapshot extends EntitySnapshot {
    float bodyRot();

    float yRot();

    float xRot();

    boolean isInvisibleToPlayer();

    EntityPose entityPose();
}
