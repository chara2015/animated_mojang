package net.labymod.core.main.user.shop.item.geometry;

import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.core.client.render.model.animation.DefaultAnimationController;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/geometry/AnimationStorage.class */
public class AnimationStorage {
    private AnimationController controller;
    private boolean lastSneaking;
    private boolean lastMoving;
    private boolean lastFirstPerson;
    private long lastTriggerMillis;

    private AnimationStorage() {
        this.controller = new DefaultAnimationController(DefaultAnimationController.SHARED_MODEL_ANIMATION);
    }

    private AnimationStorage(AnimationStorage other) {
        this.controller = other.controller;
        this.lastSneaking = other.lastSneaking;
        this.lastMoving = other.lastMoving;
        this.lastTriggerMillis = other.lastTriggerMillis;
    }

    @NotNull
    public static AnimationStorage create() {
        return new AnimationStorage();
    }

    @NotNull
    public static AnimationStorage copyOf(AnimationStorage other) {
        return new AnimationStorage(other);
    }

    public AnimationController getController() {
        return this.controller;
    }

    public void setController(AnimationController controller) {
        this.controller = controller;
    }

    public boolean isLastSneaking() {
        return this.lastSneaking;
    }

    public void setLastSneaking(boolean lastSneaking) {
        this.lastSneaking = lastSneaking;
    }

    public boolean isLastMoving() {
        return this.lastMoving;
    }

    public void setLastMoving(boolean lastMoving) {
        this.lastMoving = lastMoving;
    }

    public boolean isLastFirstPerson() {
        return this.lastFirstPerson;
    }

    public void setLastFirstPerson(boolean lastFirstPerson) {
        this.lastFirstPerson = lastFirstPerson;
    }

    public void setLastTriggerMillis(long lastTriggerMillis) {
        this.lastTriggerMillis = lastTriggerMillis;
    }

    public long getLastTriggerMillis() {
        return this.lastTriggerMillis;
    }
}
