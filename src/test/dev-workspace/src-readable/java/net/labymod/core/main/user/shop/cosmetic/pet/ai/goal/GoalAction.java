package net.labymod.core.main.user.shop.cosmetic.pet.ai.goal;

import java.util.Objects;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/pet/ai/goal/GoalAction.class */
public final class GoalAction {
    public static final GoalAction MOVE = new GoalAction("move");
    public static final GoalAction LOOK = new GoalAction("look");
    private final String name;

    public GoalAction(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GoalAction that = (GoalAction) o;
        return Objects.equals(this.name, that.name);
    }

    public int hashCode() {
        return Objects.hashCode(this.name);
    }
}
