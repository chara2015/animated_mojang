package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/ai/goal/Goal.class */
public abstract class Goal {
    private final EnumSet<Flag> flags = EnumSet.noneOf(Flag.class);

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/ai/goal/Goal$Flag.class */
    public enum Flag {
        MOVE,
        LOOK,
        JUMP,
        TARGET
    }

    public abstract boolean canUse();

    public boolean canContinueToUse() {
        return canUse();
    }

    public boolean isInterruptable() {
        return true;
    }

    public void start() {
    }

    public void stop() {
    }

    public boolean requiresUpdateEveryTick() {
        return false;
    }

    public void tick() {
    }

    public void setFlags(EnumSet<Flag> $$0) {
        this.flags.clear();
        this.flags.addAll($$0);
    }

    public String toString() {
        return getClass().getSimpleName();
    }

    public EnumSet<Flag> getFlags() {
        return this.flags;
    }

    protected int adjustedTickDelay(int $$0) {
        return requiresUpdateEveryTick() ? $$0 : reducedTickDelay($$0);
    }

    protected static int reducedTickDelay(int $$0) {
        return Mth.positiveCeilDiv($$0, 2);
    }

    protected static ServerLevel getServerLevel(Entity $$0) {
        return (ServerLevel) $$0.level();
    }

    protected static ServerLevel getServerLevel(Level $$0) {
        return (ServerLevel) $$0;
    }
}
