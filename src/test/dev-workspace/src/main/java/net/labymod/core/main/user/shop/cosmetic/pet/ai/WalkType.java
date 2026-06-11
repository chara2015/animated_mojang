package net.labymod.core.main.user.shop.cosmetic.pet.ai;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/pet/ai/WalkType.class */
public final class WalkType {
    private static final Map<String, WalkType> WALK_TYPES = new HashMap();
    public static final WalkType CROUCHING = register("crouch", 0.25f);
    public static final WalkType WALK = register("walk", 1.0f);
    public static final WalkType SPRINT = register("sprint", 1.2f);
    public static final WalkType FLYING = register("flying", 1.55f);
    public static final WalkType RUN = register("run", 1.7f);
    private final String name;
    private final float multiplier;

    private WalkType(String name, float multiplier) {
        this.name = name;
        this.multiplier = multiplier;
    }

    public static WalkType register(String name, float multiplier) {
        return WALK_TYPES.computeIfAbsent(name, n -> {
            return new WalkType(n, multiplier);
        });
    }

    public static Map<String, WalkType> getWalkTypes() {
        return Collections.unmodifiableMap(WALK_TYPES);
    }

    public String getName() {
        return this.name;
    }

    public float getMultiplier() {
        return this.multiplier;
    }
}
