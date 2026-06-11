package net.labymod.core.main.user.shop.cosmetic.pet.ai.goal;

import java.util.Set;
import net.labymod.core.main.user.shop.cosmetic.pet.ai.PetBehavior;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/pet/ai/goal/PriorityGoal.class */
public class PriorityGoal extends Goal {
    private final int priority;
    private final Goal delegate;
    private boolean running;

    public PriorityGoal(int priority, Goal delegate) {
        super(null);
        this.priority = priority;
        this.delegate = delegate;
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.goal.Goal
    public boolean canUse() {
        return this.delegate.canUse();
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.goal.Goal
    public boolean canContinueToUse() {
        return this.delegate.canContinueToUse();
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.goal.Goal
    public int adjustedTickDelay(int value) {
        return this.delegate.adjustedTickDelay(value);
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.goal.Goal
    public Set<GoalAction> getActions() {
        return this.delegate.getActions();
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.goal.Goal
    public void setActions(Set<GoalAction> actions) {
        this.delegate.setActions(actions);
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.goal.Goal
    public void start() {
        if (!this.running) {
            this.running = true;
            this.delegate.start();
        }
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.goal.Goal
    public void stop() {
        if (this.running) {
            this.running = false;
            this.delegate.stop();
        }
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.goal.Goal
    public void tick() {
        this.delegate.tick();
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.goal.Goal
    public PetBehavior behavior() {
        return this.delegate.behavior();
    }

    public Goal getGoal() {
        return this.delegate;
    }

    public boolean isRunning() {
        return this.running;
    }

    public boolean canBeReplacedBy(PriorityGoal other) {
        return other.getPriority() < getPriority();
    }

    public int getPriority() {
        return this.priority;
    }
}
