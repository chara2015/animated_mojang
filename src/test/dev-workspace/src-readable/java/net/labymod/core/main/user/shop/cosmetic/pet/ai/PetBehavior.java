package net.labymod.core.main.user.shop.cosmetic.pet.ai;

import java.util.Random;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.util.math.position.Position;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.main.user.shop.cosmetic.pet.ai.controller.LookController;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/pet/ai/PetBehavior.class */
public interface PetBehavior {
    void tick();

    void setPosition(double d, double d2, double d3);

    void addDeltaMovement(float f, float f2, float f3);

    void setDeltaMovement(float f, float f2, float f3);

    FloatVector3 deltaMovement();

    Transform transform();

    LookController lookController();

    Model petModel();

    AxisAlignedBoundingBox boundingBox();

    boolean onGround();

    void setOnGround(boolean z);

    void move(FloatVector3 floatVector3);

    float getMovementSpeed();

    boolean isWalking();

    void teleportTo(double d, double d2, double d3);

    int getPetIndex();

    int getMaxPets();

    void updatePetIndex(int i, int i2);

    default void setPosition(FloatVector3 position) {
        setPosition(position.getX(), position.getY(), position.getZ());
    }

    default void setPosition(DoubleVector3 position) {
        setPosition(position.getX(), position.getY(), position.getZ());
    }

    default void addDeltaMovement(FloatVector3 movement) {
        addDeltaMovement(movement.getX(), movement.getY(), movement.getZ());
    }

    default void addDeltaMovement(DoubleVector3 movement) {
        addDeltaMovement((float) movement.getX(), (float) movement.getY(), (float) movement.getZ());
    }

    default void setDeltaMovement(FloatVector3 movement) {
        setDeltaMovement(movement.getX(), movement.getY(), movement.getZ());
    }

    default DoubleVector3 position() {
        return transform().position();
    }

    default DoubleVector3 previousPosition() {
        return transform().previousPosition();
    }

    default WalkType findWalkType(Player player) {
        if (player.abilities().flying().get()) {
            return WalkType.FLYING;
        }
        if (player.isSprinting()) {
            return WalkType.SPRINT;
        }
        return WalkType.WALK;
    }

    default DoubleVector3 findRandomPointOfInterest(Random random) {
        DoubleVector3 currentPosition = position();
        double x = currentPosition.getX() + ((double) ((random.nextFloat() * 10.0f) - 5.0f));
        double y = currentPosition.getY();
        double z = currentPosition.getZ() + ((double) ((random.nextFloat() * 10.0f) - 5.0f));
        return new DoubleVector3(x, y, z);
    }

    default float getStepHeight() {
        return 0.5f;
    }

    default void teleportTo(Player player) {
        Position position = player.position();
        teleportTo(position.getX(), position.getY(), position.getZ());
    }
}
