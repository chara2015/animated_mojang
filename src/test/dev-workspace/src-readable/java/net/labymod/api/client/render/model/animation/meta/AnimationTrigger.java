package net.labymod.api.client.render.model.animation.meta;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/animation/meta/AnimationTrigger.class */
public class AnimationTrigger {
    private static final Map<String, AnimationTrigger> TRIGGERS = new HashMap();
    public static final AnimationTrigger NONE = create("none");
    public static final AnimationTrigger MOVING = create("moving", (v0) -> {
        v0.moving();
    });
    public static final AnimationTrigger IDLE = create("idle", (v0) -> {
        v0.idle();
    });
    public static final AnimationTrigger START_MOVING = create("start_moving", (v0) -> {
        v0.moving();
    });
    public static final AnimationTrigger STOP_MOVING = create("stop_moving", (v0) -> {
        v0.moving();
    });
    public static final AnimationTrigger START_SNEAKING = create("start_sneaking", (v0) -> {
        v0.sneaking();
    });
    public static final AnimationTrigger STOP_SNEAKING = create("stop_sneaking", (v0) -> {
        v0.sneaking();
    });
    public static final AnimationTrigger SNEAK_MOVING = create("sneak_moving", builder -> {
        builder.sneaking().moving();
    });
    public static final AnimationTrigger SNEAK_IDLE = create("sneak_idle", builder -> {
        builder.sneaking().idle();
    });
    public static final AnimationTrigger START_FIRST = create("start_first", (v0) -> {
        v0.firstPerson();
    });
    public static final AnimationTrigger STOP_FIRST = create("stop_first", (v0) -> {
        v0.firstPerson();
    });
    public static final AnimationTrigger FIRST_MOVING = create("first_moving", builder -> {
        builder.firstPerson().moving();
    });
    public static final AnimationTrigger FIRST_IDLE = create("first_idle", builder -> {
        builder.firstPerson().idle();
    });
    public static final AnimationTrigger FIRST_START_MOVING = create("first_start_moving", builder -> {
        builder.firstPerson().moving();
    });
    public static final AnimationTrigger FIRST_STOP_MOVING = create("first_stop_moving", builder -> {
        builder.firstPerson().moving();
    });
    public static final AnimationTrigger FIRST_START_SNEAKING = create("first_start_sneaking", builder -> {
        builder.firstPerson().sneaking();
    });
    public static final AnimationTrigger FIRST_STOP_SNEAKING = create("first_stop_sneaking", builder -> {
        builder.firstPerson().sneaking();
    });
    public static final AnimationTrigger FIRST_SNEAK_MOVING = create("first_sneak_moving", builder -> {
        builder.firstPerson().sneaking().moving();
    });
    public static final AnimationTrigger FIRST_SNEAK_IDLE = create("first_sneak_idle", builder -> {
        builder.firstPerson().sneaking().idle();
    });
    public static final AnimationTrigger BLOCKING = create("blocking");
    public static final AnimationTrigger BLOCK_ATTACK = create("block_attack");
    private final String name;
    private final int mask;

    private AnimationTrigger(String name, int mask) {
        this.name = name;
        this.mask = mask;
    }

    private static AnimationTrigger create(String name) {
        return create(name, (v0) -> {
            v0.build();
        });
    }

    private static AnimationTrigger create(String name, Consumer<Builder> builderConsumer) {
        return TRIGGERS.computeIfAbsent(name, triggerName -> {
            Builder builder = new Builder(triggerName);
            builderConsumer.accept(builder);
            return builder.build();
        });
    }

    @Nullable
    public static AnimationTrigger getByName(String name) {
        for (AnimationTrigger trigger : TRIGGERS.values()) {
            if (trigger.getName().equalsIgnoreCase(name)) {
                return trigger;
            }
        }
        return null;
    }

    @Contract(pure = true)
    @NotNull
    public static Collection<AnimationTrigger> values() {
        return TRIGGERS.values();
    }

    public static AnimationTrigger getMovingOrIdle(boolean moving, boolean crouching) {
        return getMovingOrIdle(moving, crouching, false);
    }

    public static AnimationTrigger getMovingOrIdle(boolean moving, boolean crouching, boolean firstPerson) {
        if (moving) {
            return getMoving(firstPerson, crouching);
        }
        return getIdle(firstPerson, crouching);
    }

    public static AnimationTrigger getMoving(boolean firstPerson, boolean crouching) {
        return firstPerson ? crouching ? FIRST_SNEAK_MOVING : FIRST_MOVING : crouching ? SNEAK_MOVING : MOVING;
    }

    public static AnimationTrigger getIdle(boolean firstPerson, boolean crouching) {
        return firstPerson ? crouching ? FIRST_SNEAK_IDLE : FIRST_IDLE : crouching ? SNEAK_IDLE : IDLE;
    }

    public static AnimationTrigger getSneakingToggle(boolean firstPerson, boolean start) {
        return firstPerson ? start ? FIRST_START_SNEAKING : FIRST_STOP_SNEAKING : start ? START_SNEAKING : STOP_SNEAKING;
    }

    public static AnimationTrigger getMovingToggle(boolean firstPerson, boolean start) {
        return firstPerson ? start ? FIRST_START_MOVING : FIRST_STOP_MOVING : start ? START_MOVING : STOP_MOVING;
    }

    public static AnimationTrigger getAlternateTrigger(AnimationTrigger trigger) {
        String triggerName = trigger.getName();
        if (trigger.isFirstPerson()) {
            triggerName = triggerName.substring(6);
        }
        AnimationTrigger alternateTrigger = getByName(triggerName);
        return alternateTrigger == null ? trigger : alternateTrigger;
    }

    public boolean isIdle() {
        return hasFlag(1);
    }

    public boolean isSneaking() {
        return hasFlag(2);
    }

    public boolean isMoving() {
        return hasFlag(4);
    }

    public boolean isFirstPerson() {
        return hasFlag(8);
    }

    public String getName() {
        return this.name;
    }

    private boolean hasFlag(int flag) {
        return Mask.hasFlag(this.mask, flag);
    }

    public String toString() {
        StringBuilder bobTheBuilder = new StringBuilder();
        bobTheBuilder.append("AnimationTrigger[");
        bobTheBuilder.append("name=").append(getName());
        if (!hasFlag(0)) {
            bobTheBuilder.append(", types=");
            if (isIdle()) {
                bobTheBuilder.append("idle ");
            }
            if (isSneaking()) {
                bobTheBuilder.append("sneaking ");
            }
            if (isMoving()) {
                bobTheBuilder.append("moving ");
            }
            if (isFirstPerson()) {
                bobTheBuilder.append("firstPerson ");
            }
        }
        bobTheBuilder.append("]");
        return bobTheBuilder.toString();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/animation/meta/AnimationTrigger$Mask.class */
    public static class Mask {
        public static final int NONE = 0;
        public static final int IDLE = 1;
        public static final int SNEAKING = 2;
        public static final int MOVING = 4;
        public static final int FIRST_PERSON = 8;

        public static boolean hasFlag(int mask, int flag) {
            return (mask & flag) != 0;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/animation/meta/AnimationTrigger$Builder.class */
    private static class Builder {
        private final String name;
        private int mask = 0;

        public Builder(String name) {
            this.name = name;
        }

        public Builder idle() {
            this.mask |= 1;
            return this;
        }

        public Builder sneaking() {
            this.mask |= 2;
            return this;
        }

        public Builder moving() {
            this.mask |= 4;
            return this;
        }

        public Builder firstPerson() {
            this.mask |= 8;
            return this;
        }

        public AnimationTrigger build() {
            Objects.requireNonNull(this.name, "name must not be null");
            return new AnimationTrigger(this.name, this.mask);
        }
    }
}
