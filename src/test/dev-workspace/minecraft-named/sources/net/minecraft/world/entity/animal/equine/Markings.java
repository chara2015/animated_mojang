package net.minecraft.world.entity.animal.equine;

import java.util.function.IntFunction;
import net.minecraft.util.ByIdMap;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/animal/equine/Markings.class */
public enum Markings {
    NONE(0),
    WHITE(1),
    WHITE_FIELD(2),
    WHITE_DOTS(3),
    BLACK_DOTS(4);

    private static final IntFunction<Markings> BY_ID = ByIdMap.continuous((v0) -> {
        return v0.getId();
    }, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
    private final int id;

    Markings(int $$0) {
        this.id = $$0;
    }

    public int getId() {
        return this.id;
    }

    public static Markings byId(int $$0) {
        return BY_ID.apply($$0);
    }
}
