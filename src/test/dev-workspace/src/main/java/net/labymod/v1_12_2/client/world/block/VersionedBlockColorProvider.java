package net.labymod.v1_12_2.client.world.block;

import javax.inject.Singleton;
import net.labymod.api.client.world.block.BlockColorProvider;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.vector.IntVector3;
import net.labymod.core.client.world.color.BlockColor;
import net.labymod.core.client.world.color.BlockColorCache;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/world/block/VersionedBlockColorProvider.class */
@Singleton
@Implements(BlockColorProvider.class)
public class VersionedBlockColorProvider implements BlockColorProvider {
    private final BlockColorCache blockColorCache = new BlockColorCache();

    @Override // net.labymod.api.client.world.block.BlockColorProvider
    public int getColor(BlockState state) {
        nf blockId = (nf) aow.h.b(state.block());
        BlockColor blockColor = this.blockColorCache.getBlockColor(blockId.toString());
        if (blockColor != null) {
            return blockColor.color();
        }
        return state.getTopColor();
    }

    @Override // net.labymod.api.client.world.block.BlockColorProvider
    public int getColorMultiplier(BlockState state) {
        bsb level = bib.z().f;
        if (level == null) {
            return 0;
        }
        bik blockColors = bib.z().al();
        return blockColors.a((awt) state, level, toBlockPos(state.position()));
    }

    @Override // net.labymod.api.client.world.block.BlockColorProvider
    public int getColorMultiplier(BlockState state, int minX, int minZ, int maxX, int maxZ) {
        return getColorMultiplier(state);
    }

    private et toBlockPos(IntVector3 vec) {
        return new et(vec.getX(), vec.getY(), vec.getZ());
    }
}
