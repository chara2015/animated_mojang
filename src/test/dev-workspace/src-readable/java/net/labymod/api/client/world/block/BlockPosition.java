package net.labymod.api.client.world.block;

import net.labymod.api.util.math.vector.IntVector3;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/block/BlockPosition.class */
public interface BlockPosition {
    int getX();

    int getY();

    int getZ();

    default IntVector3 toIntVector3() {
        return new IntVector3(getX(), getY(), getZ());
    }
}
