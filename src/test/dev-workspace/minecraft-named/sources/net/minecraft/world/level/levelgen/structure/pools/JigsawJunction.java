package net.minecraft.world.level.levelgen.structure.pools;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/pools/JigsawJunction.class */
public class JigsawJunction {
    private final int sourceX;
    private final int sourceGroundY;
    private final int sourceZ;
    private final int deltaY;
    private final StructureTemplatePool.Projection destProjection;

    public JigsawJunction(int $$0, int $$1, int $$2, int $$3, StructureTemplatePool.Projection $$4) {
        this.sourceX = $$0;
        this.sourceGroundY = $$1;
        this.sourceZ = $$2;
        this.deltaY = $$3;
        this.destProjection = $$4;
    }

    public int getSourceX() {
        return this.sourceX;
    }

    public int getSourceGroundY() {
        return this.sourceGroundY;
    }

    public int getSourceZ() {
        return this.sourceZ;
    }

    public int getDeltaY() {
        return this.deltaY;
    }

    public StructureTemplatePool.Projection getDestProjection() {
        return this.destProjection;
    }

    public <T> Dynamic<T> serialize(DynamicOps<T> $$0) {
        ImmutableMap.Builder<T, T> $$1 = ImmutableMap.builder();
        $$1.put($$0.createString("source_x"), $$0.createInt(this.sourceX)).put($$0.createString("source_ground_y"), $$0.createInt(this.sourceGroundY)).put($$0.createString("source_z"), $$0.createInt(this.sourceZ)).put($$0.createString("delta_y"), $$0.createInt(this.deltaY)).put($$0.createString("dest_proj"), $$0.createString(this.destProjection.getName()));
        return new Dynamic<>($$0, $$0.createMap($$1.build()));
    }

    public static <T> JigsawJunction deserialize(Dynamic<T> $$0) {
        return new JigsawJunction($$0.get("source_x").asInt(0), $$0.get("source_ground_y").asInt(0), $$0.get("source_z").asInt(0), $$0.get("delta_y").asInt(0), StructureTemplatePool.Projection.byName($$0.get("dest_proj").asString("")));
    }

    public boolean equals(Object $$0) {
        if (this == $$0) {
            return true;
        }
        if ($$0 == null || getClass() != $$0.getClass()) {
            return false;
        }
        JigsawJunction $$1 = (JigsawJunction) $$0;
        return this.sourceX == $$1.sourceX && this.sourceZ == $$1.sourceZ && this.deltaY == $$1.deltaY && this.destProjection == $$1.destProjection;
    }

    public int hashCode() {
        int $$0 = this.sourceX;
        return (31 * ((31 * ((31 * ((31 * $$0) + this.sourceGroundY)) + this.sourceZ)) + this.deltaY)) + this.destProjection.hashCode();
    }

    public String toString() {
        return "JigsawJunction{sourceX=" + this.sourceX + ", sourceGroundY=" + this.sourceGroundY + ", sourceZ=" + this.sourceZ + ", deltaY=" + this.deltaY + ", destProjection=" + String.valueOf(this.destProjection) + "}";
    }
}
