package net.minecraft.world.entity.ai.goal;

import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.ai.goal.Goal;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/ai/goal/GoalSelector.class */
public class GoalSelector {
    private static final WrappedGoal NO_GOAL = new WrappedGoal(Integer.MAX_VALUE, new Goal() { // from class: net.minecraft.world.entity.ai.goal.GoalSelector.1
        @Override // net.minecraft.world.entity.ai.goal.Goal
        public boolean canUse() {
            return false;
        }
    }) { // from class: net.minecraft.world.entity.ai.goal.GoalSelector.2
        @Override // net.minecraft.world.entity.ai.goal.WrappedGoal
        public boolean isRunning() {
            return false;
        }
    };
    private final Map<Goal.Flag, WrappedGoal> lockedFlags = new EnumMap(Goal.Flag.class);
    private final Set<WrappedGoal> availableGoals = new ObjectLinkedOpenHashSet();
    private final EnumSet<Goal.Flag> disabledFlags = EnumSet.noneOf(Goal.Flag.class);

    public void addGoal(int $$0, Goal $$1) {
        this.availableGoals.add(new WrappedGoal($$0, $$1));
    }

    public void removeAllGoals(Predicate<Goal> $$0) {
        this.availableGoals.removeIf($$1 -> {
            return $$0.test($$1.getGoal());
        });
    }

    public void removeGoal(Goal $$0) {
        for (WrappedGoal $$1 : this.availableGoals) {
            if ($$1.getGoal() == $$0 && $$1.isRunning()) {
                $$1.stop();
            }
        }
        this.availableGoals.removeIf($$12 -> {
            return $$12.getGoal() == $$0;
        });
    }

    private static boolean goalContainsAnyFlags(WrappedGoal $$0, EnumSet<Goal.Flag> $$1) {
        for (Goal.Flag $$2 : $$0.getFlags()) {
            if ($$1.contains($$2)) {
                return true;
            }
        }
        return false;
    }

    private static boolean goalCanBeReplacedForAllFlags(WrappedGoal $$0, Map<Goal.Flag, WrappedGoal> $$1) {
        for (Goal.Flag $$2 : $$0.getFlags()) {
            if (!$$1.getOrDefault($$2, NO_GOAL).canBeReplacedBy($$0)) {
                return false;
            }
        }
        return true;
    }

    public void tick() {
        ProfilerFiller $$0 = Profiler.get();
        $$0.push("goalCleanup");
        for (WrappedGoal $$1 : this.availableGoals) {
            if ($$1.isRunning() && (goalContainsAnyFlags($$1, this.disabledFlags) || !$$1.canContinueToUse())) {
                $$1.stop();
            }
        }
        this.lockedFlags.entrySet().removeIf($$02 -> {
            return !((WrappedGoal) $$02.getValue()).isRunning();
        });
        $$0.pop();
        $$0.push("goalUpdate");
        for (WrappedGoal $$2 : this.availableGoals) {
            if (!$$2.isRunning() && !goalContainsAnyFlags($$2, this.disabledFlags) && goalCanBeReplacedForAllFlags($$2, this.lockedFlags) && $$2.canUse()) {
                for (Goal.Flag $$3 : $$2.getFlags()) {
                    WrappedGoal $$4 = this.lockedFlags.getOrDefault($$3, NO_GOAL);
                    $$4.stop();
                    this.lockedFlags.put($$3, $$2);
                }
                $$2.start();
            }
        }
        $$0.pop();
        tickRunningGoals(true);
    }

    public void tickRunningGoals(boolean $$0) {
        ProfilerFiller $$1 = Profiler.get();
        $$1.push("goalTick");
        for (WrappedGoal $$2 : this.availableGoals) {
            if ($$2.isRunning() && ($$0 || $$2.requiresUpdateEveryTick())) {
                $$2.tick();
            }
        }
        $$1.pop();
    }

    public Set<WrappedGoal> getAvailableGoals() {
        return this.availableGoals;
    }

    public void disableControlFlag(Goal.Flag $$0) {
        this.disabledFlags.add($$0);
    }

    public void enableControlFlag(Goal.Flag $$0) {
        this.disabledFlags.remove($$0);
    }

    public void setControlFlag(Goal.Flag $$0, boolean $$1) {
        if ($$1) {
            enableControlFlag($$0);
        } else {
            disableControlFlag($$0);
        }
    }
}
