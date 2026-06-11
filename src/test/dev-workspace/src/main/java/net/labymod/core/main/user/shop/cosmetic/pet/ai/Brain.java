package net.labymod.core.main.user.shop.cosmetic.pet.ai;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.labymod.core.main.user.shop.cosmetic.pet.ai.goal.Goal;
import net.labymod.core.main.user.shop.cosmetic.pet.ai.goal.GoalAction;
import net.labymod.core.main.user.shop.cosmetic.pet.ai.goal.NoGoal;
import net.labymod.core.main.user.shop.cosmetic.pet.ai.goal.PriorityGoal;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/pet/ai/Brain.class */
public class Brain {
    private static final PriorityGoal NO_GOAL = new PriorityGoal(Integer.MAX_VALUE, new NoGoal());
    private final Map<GoalAction, PriorityGoal> lockedActions = new HashMap();
    private final Set<GoalAction> disabledActions = new HashSet();
    private final Set<PriorityGoal> goals = new HashSet();

    public void addGoal(int priority, Goal goal) {
        this.goals.add(new PriorityGoal(priority, goal));
    }

    public void removeGoal(Goal goal) {
        for (PriorityGoal priorityGoal : this.goals) {
            if (priorityGoal.getGoal() == goal && priorityGoal.isRunning()) {
                priorityGoal.stop();
            }
        }
        this.goals.removeIf(priorityGoal2 -> {
            return priorityGoal2.getGoal() == goal;
        });
    }

    public void tick() {
        processGoals();
    }

    private void processGoals() {
        stopGoals();
        cleanUpLockedActions();
        startGoals();
        tickGoals();
    }

    private void cleanUpLockedActions() {
        this.lockedActions.entrySet().removeIf(entry -> {
            return !((PriorityGoal) entry.getValue()).isRunning();
        });
    }

    private void startGoals() {
        for (PriorityGoal goal : this.goals) {
            if (canStartGoal(goal)) {
                for (GoalAction action : goal.getActions()) {
                    PriorityGoal lockedActionGoal = this.lockedActions.getOrDefault(action, NO_GOAL);
                    lockedActionGoal.stop();
                    this.lockedActions.put(action, goal);
                }
                goal.start();
            }
        }
    }

    private void stopGoals() {
        for (PriorityGoal goal : this.goals) {
            if (shouldStopGoal(goal)) {
                goal.stop();
            }
        }
    }

    private boolean canStartGoal(@NotNull PriorityGoal goal) {
        return !goal.isRunning() && !hasDisabledAction(goal) && isActionCompatible(goal) && goal.canUse();
    }

    private boolean shouldStopGoal(@NotNull PriorityGoal goal) {
        return goal.isRunning() && (hasDisabledAction(goal) || !goal.canContinueToUse());
    }

    private void tickGoals() {
        for (PriorityGoal goal : this.goals) {
            if (goal.isRunning()) {
                goal.tick();
            }
        }
    }

    private boolean hasDisabledAction(@NotNull PriorityGoal goal) {
        for (GoalAction action : goal.getActions()) {
            if (this.disabledActions.contains(action)) {
                return true;
            }
        }
        return false;
    }

    private boolean isActionCompatible(@NotNull PriorityGoal goal) {
        for (GoalAction action : goal.getActions()) {
            if (!this.lockedActions.getOrDefault(action, NO_GOAL).canBeReplacedBy(goal)) {
                return false;
            }
        }
        return true;
    }
}
