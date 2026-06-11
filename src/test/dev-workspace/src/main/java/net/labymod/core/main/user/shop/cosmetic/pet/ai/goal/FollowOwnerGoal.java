package net.labymod.core.main.user.shop.cosmetic.pet.ai.goal;

import java.util.function.Supplier;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.position.Position;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.core.main.user.shop.cosmetic.pet.ai.PetBehavior;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/pet/ai/goal/FollowOwnerGoal.class */
public class FollowOwnerGoal extends Goal {
    private final Supplier<Player> owner;
    private final float startDistance;
    private final float stopDistance;
    private int ticks;

    public FollowOwnerGoal(PetBehavior behavior, Supplier<Player> owner, float startDistance, float stopDistance) {
        super(behavior);
        setActions(GoalAction.MOVE);
        this.owner = owner;
        this.startDistance = startDistance;
        this.stopDistance = stopDistance;
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.goal.Goal
    public boolean canUse() {
        Player player = this.owner.get();
        return (player == null || player.gameMode() == GameMode.SPECTATOR || getDistanceSquared() < ((double) getStartDistance(player.abilities().flying().get()))) ? false : true;
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.goal.Goal
    public boolean canContinueToUse() {
        return getDistanceSquared() > ((double) (this.stopDistance * this.stopDistance));
    }

    public float getStartDistance(boolean flying) {
        float distance = this.startDistance;
        if (flying) {
            distance /= 2.0f;
        }
        return distance * distance;
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.goal.Goal
    public void tick() {
        Player player = this.owner.get();
        PetBehavior behavior = behavior();
        DoubleVector3 targetPosition = getTargetPosition();
        DoubleVector3 currentPosition = behavior().position();
        DoubleVector3 direction = targetPosition.sub(currentPosition).normalize();
        direction.setY(0.0d);
        float timePassed = this.ticks * 20.0f;
        float strength = ((float) Math.cos(0.006283185307179587d * ((double) timePassed))) / 5.0f;
        DoubleVector3 prevDirection = behavior.lookController().lookAt().copy().normalize();
        double pX = prevDirection.getZ();
        double pZ = prevDirection.getX();
        DoubleVector3 direction2 = direction.add(pX * ((double) strength), 0.0d, pZ * ((double) strength));
        float speed = behavior.getMovementSpeed() * behavior.findWalkType(player).getMultiplier();
        DoubleVector3 offset = direction2.multiply(speed);
        behavior.addDeltaMovement(offset);
        this.ticks++;
        double targetX = currentPosition.getX() + offset.getX();
        double targetY = currentPosition.getY() + offset.getY() + ((double) player.getEyeHeight());
        double targetZ = currentPosition.getZ() + offset.getZ();
        behavior().lookController().setLookAt(targetX, targetY, targetZ, 7.5f);
    }

    private DoubleVector3 getTargetPosition() {
        int petIndex = behavior().getPetIndex();
        int maxPets = behavior().getMaxPets();
        Player player = this.owner.get();
        if (player == null) {
            return behavior().position();
        }
        Position chasingPos = player.chasingPosition();
        Position prevChasingPos = player.previousChasingPosition();
        double angle = MathHelper.toDegreesDouble(Math.atan2(prevChasingPos.getZ() - chasingPos.getZ(), prevChasingPos.getX() - chasingPos.getX()));
        double areaRotation = MathHelper.toRadiansDouble((maxPets > 1 ? angle + ((180.0d / ((double) (maxPets - 1))) * ((double) petIndex)) : angle) - 90.0d);
        double petOffsetX = Math.cos(areaRotation) * 1.5d;
        double petOffsetZ = Math.sin(areaRotation) * 1.5d;
        double x = prevChasingPos.getX() + petOffsetX;
        double y = prevChasingPos.getY();
        double z = prevChasingPos.getZ() + petOffsetZ;
        return new DoubleVector3(x, y, z);
    }

    private double getDistanceSquared() {
        DoubleVector3 current = behavior().position();
        double currentX = current.getX();
        double currentZ = current.getZ();
        DoubleVector3 targetPosition = getTargetPosition();
        double goalX = targetPosition.getX();
        double goalZ = targetPosition.getZ();
        return ((goalX - currentX) * (goalX - currentX)) + ((goalZ - currentZ) * (goalZ - currentZ));
    }
}
