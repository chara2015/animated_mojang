package net.labymod.api.client.entity.player;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.abilities.PlayerAbilities;
import net.labymod.api.client.network.ClientPacketListener;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.food.FoodData;
import net.labymod.api.mojang.GameProfile;
import net.labymod.api.user.GameUser;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.position.Position;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/player/Player.class */
public interface Player extends LivingEntity {
    boolean isSwingingHand();

    float getArmSwingProgress();

    float getPreviousArmSwingProgress();

    boolean isDead();

    String getName();

    Position chasingPosition();

    Position previousChasingPosition();

    float getCameraYaw();

    float getPreviousCameraYaw();

    float getPreviousWalkDistance();

    float getWalkDistance();

    float getPreviousRotationHeadYaw();

    float getRotationHeadYaw();

    float getPreviousLimbSwingStrength();

    float getLimbSwingStrength();

    float getLimbSwing();

    float getRenderTick(float f);

    boolean isShownModelPart(PlayerClothes playerClothes);

    double getSprintingSpeed();

    ResourceLocation skinTexture();

    @Nullable
    ResourceLocation getCloakTexture();

    boolean isSlim();

    GameMode gameMode();

    boolean isWearingTop();

    GameProfile profile();

    FoodData foodData();

    PlayerAbilities abilities();

    int getExperienceLevel();

    int getTotalExperience();

    float getExperienceProgress();

    int getExperienceNeededForNextLevel();

    @ApiStatus.Internal
    @ApiStatus.Experimental
    PlayerModel playerModel();

    GameUser gameUser();

    @Deprecated(forRemoval = true, since = "4.2.53")
    default float getChasingPosX() {
        return (float) chasingPosition().getX();
    }

    @Deprecated(forRemoval = true, since = "4.2.53")
    default float getPreviousChasingPosX() {
        return (float) previousChasingPosition().getX();
    }

    @Deprecated(forRemoval = true, since = "4.2.53")
    default float getChasingPosY() {
        return (float) chasingPosition().getY();
    }

    @Deprecated(forRemoval = true, since = "4.2.53")
    default float getPreviousChasingPosY() {
        return (float) previousChasingPosition().getY();
    }

    @Deprecated(forRemoval = true, since = "4.2.53")
    default float getChasingPosZ() {
        return (float) chasingPosition().getZ();
    }

    @Deprecated(forRemoval = true, since = "4.2.53")
    default float getPreviousChasingPosZ() {
        return (float) previousChasingPosition().getZ();
    }

    default float getAgeTick() {
        return getRenderTick(1.0f);
    }

    default float getMovementFactor() {
        return getMovementFactor(1.0f);
    }

    default float getMovementFactor(float partialTicks) {
        return getLimbSwing() - (getLimbSwingStrength() * (1.0f - partialTicks));
    }

    default float getWalkingSpeed() {
        return getWalkingSpeed(1.0f);
    }

    default float getWalkingSpeed(float partialTicks) {
        return MathHelper.lerp(getLimbSwingStrength(), getPreviousLimbSwingStrength(), partialTicks);
    }

    default float getArmSwingProgress(float partialTicks) {
        float prevSwingProgress = getPreviousArmSwingProgress();
        float swingProgressDiff = getArmSwingProgress() - prevSwingProgress;
        if (swingProgressDiff < 0.0d) {
            swingProgressDiff += 1.0f;
        }
        return prevSwingProgress + (swingProgressDiff * partialTicks);
    }

    default boolean canDestroyBlocks() {
        return gameMode() == GameMode.SURVIVAL || gameMode() == GameMode.CREATIVE;
    }

    @Nullable
    default NetworkPlayerInfo getNetworkPlayerInfo() {
        ClientPacketListener clientPacketListener = Laby.labyAPI().minecraft().getClientPacketListener();
        if (clientPacketListener == null) {
            return null;
        }
        return clientPacketListener.getNetworkPlayerInfo(getUniqueId());
    }

    @Deprecated
    @Nullable
    default NetworkPlayerInfo networkPlayerInfo() {
        return getNetworkPlayerInfo();
    }
}
