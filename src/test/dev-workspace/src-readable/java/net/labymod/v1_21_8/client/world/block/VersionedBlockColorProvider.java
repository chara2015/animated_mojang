package net.labymod.v1_21_8.client.world.block;

import javax.inject.Singleton;
import net.labymod.api.client.world.block.BlockColorProvider;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.vector.IntVector3;
import net.labymod.core.client.world.color.BlockColor;
import net.labymod.core.client.world.color.BlockColorCache;
import net.labymod.v1_21_8.client.world.LabyBiomeBlender;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/client/world/block/VersionedBlockColorProvider.class */
@Singleton
@Implements(BlockColorProvider.class)
public class VersionedBlockColorProvider implements BlockColorProvider {
    private final BlockColorCache blockColorCache = new BlockColorCache();
    private final LabyBiomeBlender biomeBlender = new LabyBiomeBlender();

    @Override // net.labymod.api.client.world.block.BlockColorProvider
    public int getColor(BlockState state) {
        ame blockId = mm.e.b(state.block());
        BlockColor blockColor = this.blockColorCache.getBlockColor(blockId.toString());
        if (blockColor != null) {
            return blockColor.color();
        }
        return state.getTopColor();
    }

    @Override // net.labymod.api.client.world.block.BlockColorProvider
    public int getColorMultiplier(BlockState state, int minX, int minZ, int maxX, int maxZ) {
        dmu dmuVar = fue.R().s;
        if (dmuVar == null) {
            return 0;
        }
        fvi blockColors = fue.R().az();
        return blockColors.a((eeb) state, this.biomeBlender.setLevel(dmuVar).setArea(minX, minZ, maxX, maxZ), toBlockPos(state.position()), 0);
    }

    private jb toBlockPos(IntVector3 vec) {
        return new jb(vec.getX(), vec.getY(), vec.getZ());
    }
}
