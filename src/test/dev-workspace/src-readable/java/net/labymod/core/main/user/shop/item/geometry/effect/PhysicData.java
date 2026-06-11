package net.labymod.core.main.user.shop.item.geometry.effect;

import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.position.Position;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/geometry/effect/PhysicData.class */
public class PhysicData {
    private float forward;
    private float strafe;
    private float gravity;
    private float renderYawOffset;
    private float pitch;
    private int walkingPetIndex;

    public void update(Player player, PlayerModel playerModel, float partialTicks) {
        Position position = player.position();
        Position previousPosition = player.previousPosition();
        Position chasingPosition = player.chasingPosition();
        Position previousChasingPosition = player.previousChasingPosition();
        double motionX = chasingPosition.lerpX(previousChasingPosition, partialTicks) - position.lerpX(previousPosition, partialTicks);
        double motionY = chasingPosition.lerpY(previousChasingPosition, partialTicks) - position.lerpY(previousPosition, partialTicks);
        double motionZ = chasingPosition.lerpZ(previousChasingPosition, partialTicks) - position.lerpZ(previousPosition, partialTicks);
        float cameraMotionYaw = MathHelper.lerp(player.getCameraYaw(), player.getPreviousCameraYaw(), partialTicks);
        float gravity = MathHelper.clamp(((float) motionY) * 10.0f, -6.0f, 32.0f);
        float gravity2 = gravity + ((float) (Math.sin(MathHelper.lerp(player.getWalkDistance(), player.getPreviousWalkDistance(), partialTicks) * 6.0f) * 32.0d * ((double) cameraMotionYaw)));
        float renderYawOffset = MathHelper.lerp(player.getBodyRotationY(), player.getPreviousBodyRotationY(), partialTicks);
        double yawSin = Math.sin(MathHelper.toRadiansDouble(renderYawOffset));
        double yawCos = -Math.cos(MathHelper.toRadiansDouble(renderYawOffset));
        float forward = ((float) ((motionX * yawSin) + (motionZ * yawCos))) * 100.0f;
        float forward2 = MathHelper.clamp(forward, 0.0f, 150.0f);
        float strafe = ((float) ((motionX * yawCos) - (motionZ * yawSin))) * 100.0f;
        float strafe2 = MathHelper.clamp(strafe, -20.0f, 20.0f);
        float pitch = MathHelper.toDegreesFloat(playerModel.getHead().getAnimationTransformation().getRotation().getX());
        this.forward = MathHelper.toRadiansFloat(forward2 / 2.0f);
        this.gravity = MathHelper.toRadiansFloat(gravity2);
        this.strafe = MathHelper.toRadiansFloat(strafe2 / 2.0f);
        this.renderYawOffset = renderYawOffset;
        this.pitch = pitch;
    }

    public float getForward() {
        return this.forward;
    }

    public float getStrafe() {
        return this.strafe;
    }

    public float getGravity() {
        return this.gravity;
    }

    public float getRenderYawOffset() {
        return this.renderYawOffset;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void resetWalkingPetIndex() {
        this.walkingPetIndex = 0;
    }

    public int getAndIncrementWalkingPetIndex() {
        int i = this.walkingPetIndex;
        this.walkingPetIndex = i + 1;
        return i;
    }
}
