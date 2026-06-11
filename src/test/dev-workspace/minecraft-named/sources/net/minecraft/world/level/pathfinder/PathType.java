package net.minecraft.world.level.pathfinder;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/pathfinder/PathType.class */
public enum PathType {
    BLOCKED(-1.0f),
    OPEN(0.0f),
    WALKABLE(0.0f),
    WALKABLE_DOOR(0.0f),
    TRAPDOOR(0.0f),
    POWDER_SNOW(-1.0f),
    DANGER_POWDER_SNOW(0.0f),
    FENCE(-1.0f),
    LAVA(-1.0f),
    WATER(8.0f),
    WATER_BORDER(8.0f),
    RAIL(0.0f),
    UNPASSABLE_RAIL(-1.0f),
    DANGER_FIRE(8.0f),
    DAMAGE_FIRE(16.0f),
    DANGER_OTHER(8.0f),
    DAMAGE_OTHER(-1.0f),
    DOOR_OPEN(0.0f),
    DOOR_WOOD_CLOSED(-1.0f),
    DOOR_IRON_CLOSED(-1.0f),
    BREACH(4.0f),
    LEAVES(-1.0f),
    STICKY_HONEY(8.0f),
    COCOA(0.0f),
    DAMAGE_CAUTIOUS(0.0f),
    DANGER_TRAPDOOR(0.0f);

    private final float malus;

    PathType(float $$0) {
        this.malus = $$0;
    }

    public float getMalus() {
        return this.malus;
    }
}
