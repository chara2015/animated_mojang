package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.Goal;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/ai/goal/WrappedGoal.class */
public class WrappedGoal extends Goal {
    private final Goal goal;
    private final int priority;
    private boolean isRunning;

    public WrappedGoal(int $$0, Goal $$1) {
        this.priority = $$0;
        this.goal = $$1;
    }

    public boolean canBeReplacedBy(WrappedGoal $$0) {
        return isInterruptable() && $$0.getPriority() < getPriority();
    }

    @Override // net.minecraft.world.entity.ai.goal.Goal
    public boolean canUse() {
        return this.goal.canUse();
    }

    @Override // net.minecraft.world.entity.ai.goal.Goal
    public boolean canContinueToUse() {
        return this.goal.canContinueToUse();
    }

    @Override // net.minecraft.world.entity.ai.goal.Goal
    public boolean isInterruptable() {
        return this.goal.isInterruptable();
    }

    @Override // net.minecraft.world.entity.ai.goal.Goal
    public void start() {
        if (this.isRunning) {
            return;
        }
        this.isRunning = true;
        this.goal.start();
    }

    @Override // net.minecraft.world.entity.ai.goal.Goal
    public void stop() {
        if (!this.isRunning) {
            return;
        }
        this.isRunning = false;
        this.goal.stop();
    }

    @Override // net.minecraft.world.entity.ai.goal.Goal
    public boolean requiresUpdateEveryTick() {
        return this.goal.requiresUpdateEveryTick();
    }

    @Override // net.minecraft.world.entity.ai.goal.Goal
    protected int adjustedTickDelay(int $$0) {
        return this.goal.adjustedTickDelay($$0);
    }

    @Override // net.minecraft.world.entity.ai.goal.Goal
    public void tick() {
        this.goal.tick();
    }

    @Override // net.minecraft.world.entity.ai.goal.Goal
    public void setFlags(EnumSet<Goal.Flag> $$0) {
        this.goal.setFlags($$0);
    }

    @Override // net.minecraft.world.entity.ai.goal.Goal
    public EnumSet<Goal.Flag> getFlags() {
        return this.goal.getFlags();
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public int getPriority() {
        return this.priority;
    }

    public Goal getGoal() {
        return this.goal;
    }

    public boolean equals(Object $$0) {
        if (this == $$0) {
            return true;
        }
        if ($$0 == null || getClass() != $$0.getClass()) {
            return false;
        }
        return this.goal.equals(((WrappedGoal) $$0).goal);
    }

    public int hashCode() {
        return this.goal.hashCode();
    }
}
