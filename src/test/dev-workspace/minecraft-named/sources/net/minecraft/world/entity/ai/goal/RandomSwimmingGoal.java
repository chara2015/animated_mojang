package net.minecraft.world.entity.ai.goal;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.phys.Vec3;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/ai/goal/RandomSwimmingGoal.class */
public class RandomSwimmingGoal extends RandomStrollGoal {
    public RandomSwimmingGoal(PathfinderMob $$0, double $$1, int $$2) {
        super($$0, $$1, $$2);
    }

    @Override // net.minecraft.world.entity.ai.goal.RandomStrollGoal
    protected Vec3 getPosition() {
        return BehaviorUtils.getRandomSwimmablePos(this.mob, 10, 7);
    }
}
