package net.labymod.core.main.user.shop.cosmetic.pet;

import net.laby.lib.cosmetics.MoveType;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.EntityPose;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import net.labymod.api.client.render.model.animation.meta.AnimationTrigger;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.Transformation;
import net.labymod.api.util.math.position.Position;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.entity.player.DummyPlayerModel;
import net.labymod.core.main.user.shop.cosmetic.CosmeticDefinition;
import net.labymod.core.main.user.shop.item.CosmeticDetails;
import net.labymod.core.main.user.shop.spray.SprayConstants;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/pet/AbstractShoulderPetAI.class */
public abstract class AbstractShoulderPetAI implements PetAI {
    protected final CosmeticDefinition definition;
    private final float flightAltitude = 0.5f;

    protected abstract long getFakeIdleDelay();

    protected abstract long getFakeMoveDelay();

    protected abstract void onAbsoluteMovement(Stack stack, PetDataStorage petDataStorage, double d, double d2, double d3, double d4, double d5, double d6, float f);

    protected AbstractShoulderPetAI(CosmeticDefinition definition) {
        this.definition = definition;
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.PetAI
    public void earlyRender(PlayerModel playerModel, Player player, PetDataStorage petDataStorage, Stack stack, float partialTicks) {
        float currentTick = player.getRenderTick(partialTicks);
        float walkingSpeed = player.getWalkingSpeed(partialTicks);
        DoubleVector3 offset = petDataStorage.getOffset();
        DoubleVector3 previousPosition = petDataStorage.getPreviousPosition();
        DoubleVector3 position = petDataStorage.getPosition();
        if (previousPosition.distanceSquared(position) > 10.0d) {
            tick(playerModel, player, petDataStorage, currentTick, partialTicks);
        }
        float renderYaw = MathHelper.lerp(player.getBodyRotationY(), player.getPreviousBodyRotationY(), partialTicks);
        Position playerPosition = player.position();
        Position playerPreviousPosition = player.previousPosition();
        double playerX = playerPosition.lerpX(playerPreviousPosition, partialTicks);
        double playerY = playerPosition.lerpY(playerPreviousPosition, partialTicks);
        double playerZ = playerPosition.lerpZ(playerPreviousPosition, partialTicks);
        double x = position.lerpX(previousPosition, partialTicks);
        double y = position.lerpY(previousPosition, partialTicks);
        double z = position.lerpZ(previousPosition, partialTicks);
        stack.translate(offset.getX(), offset.getY(), offset.getZ());
        boolean rightShoulder = petDataStorage.isRightShoulder();
        ModelPart armPart = rightShoulder ? playerModel.getRightArm() : playerModel.getLeftArm();
        float positionScale = 1.0f / Laby.labyAPI().renderPipeline().renderConstants().modelScale();
        FloatVector3 translation = armPart.getModelPartTransform().getTranslation();
        stack.translate(translation.getX() * positionScale, (translation.getY() + (playerModel instanceof DummyPlayerModel ? 6.25f : 0.0f)) * positionScale, translation.getZ() * positionScale);
        if (isAlwaysAttachedToArm() || petDataStorage.isAttachedToOwner()) {
            Transformation transformation = armPart.getAnimationTransformation();
            FloatVector3 animationTranslation = transformation.getTranslation();
            stack.translate(animationTranslation.getX() * positionScale, animationTranslation.getY() * positionScale, animationTranslation.getZ() * positionScale);
            FloatVector3 rotation = transformation.getRotation();
            float transition = Math.min(1.0f, 1.0f - player.getWalkingSpeed(partialTicks));
            stack.rotateRadians(rotation.getZ() * transition, 0.0f, 0.0f, 1.0f);
            stack.rotateRadians(rotation.getY() * transition, 0.0f, 1.0f, 0.0f);
            stack.rotateRadians(rotation.getX() * transition, 1.0f, 0.0f, 0.0f);
            FloatVector3 scale = transformation.getScale();
            stack.scale(scale.getX(), scale.getY(), scale.getZ());
        }
        stack.translate((rightShoulder ? -1.0f : 1.0f) * positionScale, (-2.24f) * positionScale, (-1.0f) * positionScale);
        stack.translate(0.0f, 0.015f, 0.0f);
        if (player.isWearingTop() && !player.isWearingElytra()) {
            stack.translate(0.0f, -0.06f, 0.0f);
        }
        onAbsoluteMovement(stack, petDataStorage, x, y, z, playerX, playerY, playerZ, renderYaw);
        if (!player.isCrouching() && shouldBounce(petDataStorage)) {
            double uniqueTick = ((double) currentTick) + (((double) this.definition.id()) * 3.141592653589793d);
            stack.translate(0.0f, (float) (-Math.abs(((Math.cos(uniqueTick / 2.0d) / 3.0d) * ((double) walkingSpeed)) / 2.0d)), 0.0f);
            stack.rotateRadians((float) Math.toRadians(Math.cos(uniqueTick / 2.0d) * 10.0d * ((double) walkingSpeed)), 0.0f, 0.0f, 1.0f);
            double chasingY = player.chasingPosition().lerpY(player.previousChasingPosition(), partialTicks);
            double velocityY = playerY > chasingY ? 0.0d : playerY - chasingY;
            double bounce = Math.abs(Math.cos((uniqueTick / 2.0d) + velocityY) / 20.0d);
            stack.translate(0.0f, (float) (bounce * velocityY), 0.0f);
        }
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.PetAI
    public void tick(PlayerModel playerModel, Player player, PetDataStorage petDataStorage, float currentTick, float partialTicks) {
        float walkingSpeed = player.getWalkingSpeed(partialTicks);
        boolean isMoving = walkingSpeed > 0.3f || (shouldTriggerOnSwing() && player.isSwingingHand());
        CosmeticDetails details = this.definition.details();
        MoveType moveType = details.getMoveType();
        if (!petDataStorage.isMoving() && moveType == MoveType.MOVE_ONLY) {
            petDataStorage.updateMovingState(true);
        }
        if (details.canIdle()) {
            if (isMoving && petDataStorage.isAttachedToOwner() && details.canMove()) {
                petDataStorage.updateMovingState(true);
                playAnimation(AnimationTrigger.START_MOVING, petDataStorage, player);
            }
        } else if (isMoving && petDataStorage.isFakeIdle() && details.canMove()) {
            petDataStorage.setFakeIdle(false);
            playAnimation(AnimationTrigger.START_MOVING, petDataStorage, player);
        }
        if (petDataStorage.isAttachedToOwner() || isMoving) {
            petDataStorage.setTimeMovedChangedAt(TimeUtil.getMillis());
        }
        long timePassedSinceChange = TimeUtil.getMillis() - petDataStorage.getFlyingChangedAt();
        float sigmoidInput = 0.006f * Math.min(1000L, timePassedSinceChange);
        float increasing = (float) (((1.0d - Math.exp(-sigmoidInput)) / (1.0d + Math.exp(-sigmoidInput))) * 1.0d);
        float decreasing = 1.0f - increasing;
        Position chasingPosition = player.chasingPosition();
        double chasingX = chasingPosition.getX();
        double chasingY = chasingPosition.getY() + ((double) this.flightAltitude);
        double chasingZ = chasingPosition.getZ();
        boolean fallFlying = player.entityPose() == EntityPose.FALL_FLYING;
        if (fallFlying != petDataStorage.wasFallFlying()) {
            petDataStorage.updateFallFlyingState(fallFlying);
        }
        if (fallFlying) {
            float yawRad = (float) Math.toRadians(player.getBodyRotationY());
            chasingX += Math.sin(yawRad) * 0.25d;
            chasingY = chasingPosition.getY() + 0.15d;
            chasingZ -= Math.cos(yawRad) * 0.25d;
        }
        Position position = player.position();
        double posX = position.getX();
        double posY = position.getY();
        double posZ = position.getZ();
        double positionFactor = petDataStorage.isMoving() ? decreasing : increasing;
        double chasingFactor = petDataStorage.isMoving() ? increasing : decreasing;
        double x = (posX * positionFactor) + (chasingX * chasingFactor);
        double y = (posY * positionFactor) + (chasingY * chasingFactor);
        double z = (posZ * positionFactor) + (chasingZ * chasingFactor);
        float renderYaw = player.getBodyRotationY();
        float lookYaw = (float) (-Math.toDegrees(Math.atan2((-x) + posX, (-z) + posZ)));
        float sigmoidInputTransition = (Math.min(SprayConstants.LABYMOD_PLUS_NEXT_SPRAY, TimeUtil.getMillis() - petDataStorage.getAboveShoulderChangedAt()) / 2000.0f) * 6.0f;
        float transition = (float) (((1.0d - Math.exp(-sigmoidInputTransition)) / (1.0d + Math.exp(-sigmoidInputTransition))) * 1.0d);
        float factor = petDataStorage.isAboveShoulder() ? 1.0f - transition : transition;
        float modDiff = (renderYaw - lookYaw) % 360.0f;
        float shortestDistance = 180.0f - Math.abs(Math.abs(modDiff) - 180.0f);
        float distance = (modDiff + 360.0f) % 360.0f < 180.0f ? shortestDistance : shortestDistance * (-1.0f);
        float rotationY = renderYaw - (distance * factor);
        petDataStorage.teleport(x, y, z, 0.0f, rotationY, 0.0f);
        if (details.canIdle()) {
            if (petDataStorage.isMoving()) {
                boolean aboveShoulder = walkingSpeed < 0.1f || chasingFactor < 0.8999999761581421d;
                if (aboveShoulder != petDataStorage.isAboveShoulder()) {
                    petDataStorage.setAboveShoulder(aboveShoulder);
                    petDataStorage.setAboveShoulderChangedAt(TimeUtil.getMillis());
                }
                if (petDataStorage.isAboveShoulder() && petDataStorage.getTimeMovedChangedAt() + getFakeIdleDelay() < TimeUtil.getMillis() && details.canIdle()) {
                    petDataStorage.updateMovingState(false);
                    playAnimation(AnimationTrigger.STOP_MOVING, petDataStorage, player);
                    return;
                }
                return;
            }
            petDataStorage.setAboveShoulder(true);
            return;
        }
        boolean aboveShoulder2 = walkingSpeed < 0.1f || chasingFactor < 0.8999999761581421d;
        if (aboveShoulder2 != petDataStorage.isAboveShoulder()) {
            petDataStorage.setAboveShoulder(aboveShoulder2);
            petDataStorage.setAboveShoulderChangedAt(TimeUtil.getMillis());
        }
        if (!petDataStorage.isFakeIdle() && petDataStorage.getTimeMovedChangedAt() + getFakeMoveDelay() < TimeUtil.getMillis()) {
            petDataStorage.setFakeIdle(true);
            playAnimation(AnimationTrigger.STOP_MOVING, petDataStorage, player);
        }
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.PetAI
    public boolean canAttach() {
        return this.definition.details().canIdle();
    }

    protected void playAnimation(AnimationTrigger trigger, PetDataStorage petDataStorage, Player player) {
        ModelAnimation animation = this.definition.animationContainer().handleAnimationTrigger(trigger, petDataStorage.getAnimationController(), player);
        if (animation != null) {
            petDataStorage.setAttached(canAttach() && trigger.isIdle());
        }
    }

    protected boolean shouldBounce(PetDataStorage storage) {
        return storage.isAttachedToOwner();
    }

    protected boolean isAlwaysAttachedToArm() {
        return false;
    }

    protected boolean shouldTriggerOnSwing() {
        return true;
    }
}
