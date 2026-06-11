package net.minecraft.world.entity;

import java.util.List;
import net.minecraft.world.level.levelgen.Density;
import net.minecraft.world.phys.Vec3;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/EntityAttachment.class */
public enum EntityAttachment {
    PASSENGER(Fallback.AT_HEIGHT),
    VEHICLE(Fallback.AT_FEET),
    NAME_TAG(Fallback.AT_HEIGHT),
    WARDEN_CHEST(Fallback.AT_CENTER);

    private final Fallback fallback;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/EntityAttachment$Fallback.class */
    public interface Fallback {
        public static final List<Vec3> ZERO = List.of(Vec3.ZERO);
        public static final Fallback AT_FEET = ($$0, $$1) -> {
            return ZERO;
        };
        public static final Fallback AT_HEIGHT = ($$0, $$1) -> {
            return List.of(new Vec3(Density.SURFACE, $$1, Density.SURFACE));
        };
        public static final Fallback AT_CENTER = ($$0, $$1) -> {
            return List.of(new Vec3(Density.SURFACE, ((double) $$1) / 2.0d, Density.SURFACE));
        };

        List<Vec3> create(float f, float f2);
    }

    EntityAttachment(Fallback $$0) {
        this.fallback = $$0;
    }

    public List<Vec3> createFallbackPoints(float $$0, float $$1) {
        return this.fallback.create($$0, $$1);
    }
}
