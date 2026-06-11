package net.labymod.core.main.user.shop.cosmetic.pet.ai.goal;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.main.user.shop.cosmetic.pet.ai.PetBehavior;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/pet/ai/goal/Goal.class */
public abstract class Goal {
    protected static final Random RANDOM = new Random();
    private final Set<GoalAction> actions = new HashSet();
    private final PetBehavior behavior;

    public abstract boolean canUse();

    protected Goal(PetBehavior behavior) {
        this.behavior = behavior;
    }

    public void start() {
    }

    public void stop() {
    }

    public void tick() {
    }

    public boolean canContinueToUse() {
        return canUse();
    }

    public PetBehavior behavior() {
        return this.behavior;
    }

    protected int adjustedTickDelay(int value) {
        return MathHelper.positiveCeilDiv(value, 2);
    }

    public Set<GoalAction> getActions() {
        return this.actions;
    }

    public void setActions(GoalAction... actions) {
        setActions(Set.of((Object[]) actions));
    }

    public void setActions(Set<GoalAction> actions) {
        this.actions.clear();
        this.actions.addAll(actions);
    }
}
