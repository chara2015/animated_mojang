package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/placement/CaveSurface.class */
public enum CaveSurface implements StringRepresentable {
    CEILING(Direction.UP, 1, "ceiling"),
    FLOOR(Direction.DOWN, -1, "floor");

    public static final Codec<CaveSurface> CODEC = StringRepresentable.fromEnum(CaveSurface::values);
    private final Direction direction;
    private final int y;
    private final String id;

    CaveSurface(Direction $$0, int $$1, String $$2) {
        this.direction = $$0;
        this.y = $$1;
        this.id = $$2;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public int getY() {
        return this.y;
    }

    @Override // net.minecraft.util.StringRepresentable
    public String getSerializedName() {
        return this.id;
    }
}
