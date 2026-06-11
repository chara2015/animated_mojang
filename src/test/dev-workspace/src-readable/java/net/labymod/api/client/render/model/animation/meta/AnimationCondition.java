package net.labymod.api.client.render.model.animation.meta;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.util.function.Functional;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/animation/meta/AnimationCondition.class */
public enum AnimationCondition {
    NO_MOTION(entity -> {
        return Boolean.valueOf(entity.getForwardMotion() >= 0.0f);
    }),
    MOTION_FORWARD(entity2 -> {
        return Boolean.valueOf(entity2.getForwardMotion() != 0.0f);
    }),
    MOTION_BACKWARDS(entity3 -> {
        return Boolean.valueOf(entity3.getForwardMotion() <= 0.0f);
    }),
    SNEAKING(entity4 -> {
        return Boolean.valueOf(!entity4.isCrouching());
    }),
    NOT_SNEAKING((v0) -> {
        return v0.isCrouching();
    }),
    IN_WATER(entity5 -> {
        return Boolean.valueOf(!entity5.isInWater());
    }),
    IN_NOT_WATER((v0) -> {
        return v0.isInWater();
    }),
    ON_WATER(entity6 -> {
        return Boolean.valueOf(!entity6.isOnGround());
    }),
    IN_AIR((v0) -> {
        return v0.isOnGround();
    });

    private static final Map<String, AnimationCondition> VALUES = (Map) Functional.of(new HashMap(), map -> {
        AnimationCondition[] arr$ = values();
        for (AnimationCondition value : arr$) {
            map.put(value.name(), value);
        }
    });
    private final Function<Entity, Boolean> condition;

    AnimationCondition(Function function) {
        this.condition = function;
    }

    public boolean apply(Entity entity) {
        return this.condition.apply(entity).booleanValue();
    }

    public static AnimationCondition findCondition(String name) {
        AnimationCondition animationCondition = VALUES.get(name);
        if (animationCondition == null) {
            throw new IllegalArgumentException("No enum constant " + AnimationCondition.class.getCanonicalName() + "." + name);
        }
        return animationCondition;
    }
}
