package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/ai/goal/RandomStrollGoal.class */
public class RandomStrollGoal extends Goal {
    public static final int DEFAULT_INTERVAL = 120;
    protected final PathfinderMob mob;
    protected double wantedX;
    protected double wantedY;
    protected double wantedZ;
    protected final double speedModifier;
    protected int interval;
    protected boolean forceTrigger;
    private final boolean checkNoActionTime;

    public RandomStrollGoal(PathfinderMob $$0, double $$1) {
        this($$0, $$1, 120);
    }

    public RandomStrollGoal(PathfinderMob $$0, double $$1, int $$2) {
        this($$0, $$1, $$2, true);
    }

    public RandomStrollGoal(PathfinderMob $$0, double $$1, int $$2, boolean $$3) {
        this.mob = $$0;
        this.speedModifier = $$1;
        this.interval = $$2;
        this.checkNoActionTime = $$3;
        setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override // net.minecraft.world.entity.ai.goal.Goal
    public boolean canUse() {
        Vec3 $$0;
        if (this.mob.hasControllingPassenger()) {
            return false;
        }
        if ((!this.forceTrigger && ((this.checkNoActionTime && this.mob.getNoActionTime() >= 100) || this.mob.getRandom().nextInt(reducedTickDelay(this.interval)) != 0)) || ($$0 = getPosition()) == null) {
            return false;
        }
        this.wantedX = $$0.x;
        this.wantedY = $$0.y;
        this.wantedZ = $$0.z;
        this.forceTrigger = false;
        return true;
    }

    protected Vec3 getPosition() {
        return DefaultRandomPos.getPos(this.mob, 10, 7);
    }

    @Override // net.minecraft.world.entity.ai.goal.Goal
    public boolean canContinueToUse() {
        return (this.mob.getNavigation().isDone() || this.mob.hasControllingPassenger()) ? false : true;
    }

    @Override // net.minecraft.world.entity.ai.goal.Goal
    public void start() {
        this.mob.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
    }

    @Override // net.minecraft.world.entity.ai.goal.Goal
    public void stop() {
        this.mob.getNavigation().stop();
        super.stop();
    }

    public void trigger() {
        this.forceTrigger = true;
    }

    public void setInterval(int $$0) {
        this.interval = $$0;
    }
}
