package net.labymod.api.client.entity.player;

import net.labymod.api.client.entity.LivingEntity;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/player/ClientPlayer.class */
public interface ClientPlayer extends Player {
    boolean isAbilitiesFlying();

    float getAbilitiesWalkingSpeed();

    boolean isHandActive();

    boolean isUsingBow();

    void swingArm(LivingEntity.Hand hand);

    void swingArm(LivingEntity.Hand hand, boolean z);

    Inventory inventory();

    void setDistanceWalked(float f);

    float getForwardMovingSpeed();

    float getStrafeMovingSpeed();
}
