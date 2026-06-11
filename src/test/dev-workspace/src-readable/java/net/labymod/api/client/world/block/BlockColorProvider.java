package net.labymod.api.client.world.block;

import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/block/BlockColorProvider.class */
@Referenceable
public interface BlockColorProvider {
    int getColor(BlockState blockState);

    int getColorMultiplier(BlockState blockState, int i, int i2, int i3, int i4);

    default int getColorMultiplier(BlockState state) {
        return getColorMultiplier(state, -1, -1, 1, 1);
    }
}
