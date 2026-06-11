package net.labymod.v1_21_11.client.world.block;

import javax.inject.Singleton;
import net.labymod.api.client.world.block.BlockColorProvider;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.vector.IntVector3;
import net.labymod.core.client.world.color.BlockColor;
import net.labymod.core.client.world.color.BlockColorCache;
import net.labymod.v1_21_11.client.world.LabyBiomeBlender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/world/block/VersionedBlockColorProvider.class */
@Singleton
@Implements(BlockColorProvider.class)
public class VersionedBlockColorProvider implements BlockColorProvider {
    private final BlockColorCache blockColorCache = new BlockColorCache();
    private final LabyBiomeBlender biomeBlender = new LabyBiomeBlender();

    public int getColor(BlockState state) {
        Identifier blockId = BuiltInRegistries.BLOCK.getKey(state.block());
        BlockColor blockColor = this.blockColorCache.getBlockColor(blockId.toString());
        if (blockColor != null) {
            return blockColor.color();
        }
        return state.getTopColor();
    }

    public int getColorMultiplier(BlockState state, int minX, int minZ, int maxX, int maxZ) {
        Level level = Minecraft.getInstance().level;
        if (level == null) {
            return 0;
        }
        BlockColors blockColors = Minecraft.getInstance().getBlockColors();
        return blockColors.getColor((net.minecraft.world.level.block.state.BlockState) state, this.biomeBlender.setLevel(level).setArea(minX, minZ, maxX, maxZ), toBlockPos(state.position()), 0);
    }

    private BlockPos toBlockPos(IntVector3 vec) {
        return new BlockPos(vec.getX(), vec.getY(), vec.getZ());
    }
}
