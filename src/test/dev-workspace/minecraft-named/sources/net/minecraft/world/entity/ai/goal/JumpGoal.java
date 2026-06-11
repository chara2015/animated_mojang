package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.Goal;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/ai/goal/JumpGoal.class */
public abstract class JumpGoal extends Goal {
    public JumpGoal() {
        setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
    }
}
