package net.labymod.v26_1.mixins.client.world.chunk;

import java.util.Map;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.chunk.Chunk;
import net.labymod.api.client.world.chunk.HeightmapType;
import net.labymod.api.util.debug.Preconditions;
import net.labymod.core.client.render.schematic.block.ParameterType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.EmptyLevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.Heightmap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/client/world/chunk/MixinChunkAccess.class */
@Mixin({ChunkAccess.class})
public abstract class MixinChunkAccess implements Chunk {

    @Shadow
    @Final
    protected LevelChunkSection[] sections;

    @Shadow
    @Final
    protected ChunkPos chunkPos;

    @Shadow
    @Final
    protected Map<BlockPos, BlockEntity> blockEntities;

    @Shadow
    public abstract Heightmap getOrCreateHeightmapUnprimed(Heightmap.Types types);

    @Shadow
    public abstract LevelChunkSection getSection(int i);

    @Shadow
    public abstract LevelChunkSection[] getSections();

    @Shadow
    public abstract int getMinY();

    @Override // net.labymod.api.client.world.chunk.Chunk
    public int getChunkX() {
        return this.chunkPos.x();
    }

    @Override // net.labymod.api.client.world.chunk.Chunk
    public int getChunkZ() {
        return this.chunkPos.z();
    }

    @Override // net.labymod.api.client.world.chunk.Chunk
    public int getAbsoluteBlockX(int chunkBlockX) {
        return this.chunkPos.getBlockX(chunkBlockX);
    }

    @Override // net.labymod.api.client.world.chunk.Chunk
    public int getAbsoluteBlockZ(int chunkBlockZ) {
        return this.chunkPos.getBlockZ(chunkBlockZ);
    }

    @Override // net.labymod.api.client.world.chunk.Chunk
    public net.labymod.api.client.world.chunk.Heightmap heightmap(HeightmapType type) {
        Preconditions.notNull(type, ParameterType.TYPE);
        return getOrCreateHeightmapUnprimed(Heightmap.Types.WORLD_SURFACE);
    }

    @Override // net.labymod.api.client.world.chunk.Chunk
    public boolean isLoaded() {
        return !(this instanceof EmptyLevelChunk);
    }

    @Override // net.labymod.api.client.world.chunk.Chunk
    public BlockState getBlockState(int x, int y, int z) {
        int index = ((ChunkAccess) this).getSectionIndex(y);
        if (index < 0 || index >= this.sections.length) {
            return Blocks.AIR.defaultBlockState();
        }
        LevelChunkSection section = this.sections[index];
        if (section == null) {
            return Blocks.AIR.defaultBlockState();
        }
        int yPos = y & 15;
        BlockState blockState = section.getBlockState(x, yPos, z);
        blockState.setCoordinates(getAbsoluteBlockX(x), y, getAbsoluteBlockZ(z));
        return blockState;
    }

    @Override // net.labymod.api.client.world.chunk.Chunk
    public net.labymod.api.client.blockentity.BlockEntity[] getBlockEntities() {
        net.labymod.api.client.blockentity.BlockEntity[] blockEntities = new net.labymod.api.client.blockentity.BlockEntity[this.blockEntities.size()];
        int index = 0;
        for (Map.Entry<BlockPos, BlockEntity> entry : this.blockEntities.entrySet()) {
            int i = index;
            index++;
            blockEntities[i] = (net.labymod.api.client.blockentity.BlockEntity) entry.getValue();
        }
        return blockEntities;
    }

    @Override // net.labymod.api.client.world.chunk.Chunk
    public int getHeightBasedOnSection(int startY) {
        LevelChunkSection[] sections = getSections();
        if (sections.length == 0) {
            return 0;
        }
        int bottomY = getMinY();
        int startSectionIndex = Math.min((startY - bottomY) >> 4, sections.length - 1);
        if (startSectionIndex < 0) {
            startSectionIndex = 0;
        }
        int result = 0;
        for (int index = startSectionIndex; index < sections.length; index++) {
            LevelChunkSection section = sections[index];
            if (section != null && !section.hasOnlyAir()) {
                result = bottomY + (index << 4) + 15;
            }
        }
        if (result != 0) {
            return result;
        }
        if (startSectionIndex == 0) {
            return 0;
        }
        for (int index2 = startSectionIndex - 1; index2 >= 0; index2--) {
            LevelChunkSection section2 = sections[index2];
            if (section2 != null && !section2.hasOnlyAir()) {
                int result2 = bottomY + (index2 << 4) + 15;
                return result2;
            }
        }
        return result;
    }
}
