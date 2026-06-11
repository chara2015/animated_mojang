package net.labymod.v1_12_2.client.world;

import net.labymod.api.client.world.chunk.Heightmap;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/world/VersionedHeightmap.class */
public class VersionedHeightmap implements Heightmap {
    private final axw chunk;

    public VersionedHeightmap(axw chunk) {
        this.chunk = chunk;
    }

    @Override // net.labymod.api.client.world.chunk.Heightmap
    public int getHeight(int x, int z) {
        return this.chunk.b(x, z);
    }
}
