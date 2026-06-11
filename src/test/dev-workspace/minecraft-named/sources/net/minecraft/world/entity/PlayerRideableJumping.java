package net.minecraft.world.entity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/PlayerRideableJumping.class */
public interface PlayerRideableJumping extends PlayerRideable {
    void onPlayerJump(int i);

    boolean canJump();

    void handleStartJump(int i);

    void handleStopJump();

    default int getJumpCooldown() {
        return 0;
    }

    default float getPlayerJumpPendingScale(int $$0) {
        if ($$0 >= 90) {
            return 1.0f;
        }
        return 0.4f + ((0.4f * $$0) / 90.0f);
    }
}
