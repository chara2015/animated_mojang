package net.labymod.v1_8_9.client.world;

import net.labymod.api.client.world.chunk.Heightmap;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/world/VersionedHeightmap.class */
public class VersionedHeightmap implements Heightmap {
    private final amy chunk;

    public VersionedHeightmap(amy chunk) {
        this.chunk = chunk;
    }

    @Override // net.labymod.api.client.world.chunk.Heightmap
    public int getHeight(int x, int z) {
        return this.chunk.b(x, z);
    }
}
