package net.labymod.core.main.user.shop.cosmetic.pet.ai.goal;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.util.math.position.Position;
import net.labymod.core.main.user.shop.cosmetic.pet.ai.PetBehavior;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/pet/ai/goal/LookAtPlayerGoal.class */
public class LookAtPlayerGoal extends Goal {
    private static final String HEAD_NAME = "head";
    private final float lookDistance;
    private Player lookAt;
    private int lookTime;

    public LookAtPlayerGoal(PetBehavior behavior, float lookDistance) {
        super(behavior);
        setActions(GoalAction.LOOK);
        this.lookDistance = lookDistance;
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.goal.Goal
    public void start() {
        this.lookTime = adjustedTickDelay(40 + RANDOM.nextInt(40));
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.goal.Goal
    public void stop() {
        this.lookAt = null;
        behavior().lookController().setLookAt(behavior().findRandomPointOfInterest(RANDOM));
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.goal.Goal
    public void tick() {
        if (this.lookAt.isDead()) {
            return;
        }
        Position position = this.lookAt.position();
        double targetX = position.getX();
        double targetY = position.getY() + ((double) this.lookAt.getEyeHeight());
        double targetZ = position.getZ();
        behavior().lookController().setLookAt(targetX, targetY, targetZ);
        this.lookTime--;
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.goal.Goal
    public boolean canUse() {
        if (behavior().isWalking() || RANDOM.nextFloat() >= 0.02f) {
            return false;
        }
        ClientWorld level = Laby.labyAPI().minecraft().clientWorld();
        this.lookAt = (Player) level.getNearestEntity(level.getPlayers(), behavior().position());
        return this.lookAt != null;
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.goal.Goal
    public boolean canContinueToUse() {
        return !this.lookAt.isDead() && this.lookAt.getDistanceSquared(behavior().position()) <= ((double) (this.lookDistance * this.lookDistance)) && this.lookTime > 0;
    }
}
