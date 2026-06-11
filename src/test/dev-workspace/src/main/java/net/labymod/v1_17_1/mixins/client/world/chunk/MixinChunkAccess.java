package net.labymod.v1_17_1.mixins.client.world.chunk;

import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.client.blockentity.BlockEntity;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.chunk.Chunk;
import net.labymod.api.client.world.chunk.Heightmap;
import net.labymod.api.client.world.chunk.HeightmapType;
import net.labymod.api.util.debug.Preconditions;
import net.labymod.core.client.render.schematic.block.ParameterType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/client/world/chunk/MixinChunkAccess.class */
@Mixin({cmm.class})
public interface MixinChunkAccess extends Chunk {
    @Shadow
    cpt a(a aVar);

    @Shadow
    bvv f();

    @Shadow
    cmy[] d();

    @Override // net.labymod.api.client.world.chunk.Chunk
    default int getChunkX() {
        return f().b;
    }

    @Override // net.labymod.api.client.world.chunk.Chunk
    default int getChunkZ() {
        return f().c;
    }

    @Override // net.labymod.api.client.world.chunk.Chunk
    default int getAbsoluteBlockX(int chunkBlockX) {
        return f().a(chunkBlockX);
    }

    @Override // net.labymod.api.client.world.chunk.Chunk
    default int getAbsoluteBlockZ(int chunkBlockZ) {
        return f().b(chunkBlockZ);
    }

    @Override // net.labymod.api.client.world.chunk.Chunk
    default Heightmap heightmap(HeightmapType type) {
        Preconditions.notNull(type, ParameterType.TYPE);
        return a(a.b);
    }

    @Override // net.labymod.api.client.world.chunk.Chunk
    default boolean isLoaded() {
        return !(this instanceof cms);
    }

    @Override // net.labymod.api.client.world.chunk.Chunk
    default BlockState getBlockState(int x, int y, int z) {
        int index = y >> 4;
        if (index < 0 || index >= d().length) {
            return bzq.a.n();
        }
        cmy section = d()[index];
        if (section == null) {
            return bzq.a.n();
        }
        int yPos = y & 15;
        BlockState blockState = section.a(x, yPos, z);
        blockState.setCoordinates(getAbsoluteBlockX(x), y, getAbsoluteBlockZ(z));
        return blockState;
    }

    @Override // net.labymod.api.client.world.chunk.Chunk
    default BlockEntity[] getBlockEntities() {
        if (this instanceof cmx) {
            cmx levelChunk = (cmx) this;
            Map<gg, ciq> map = levelChunk.z();
            BlockEntity[] blockEntities = new BlockEntity[map.size()];
            int index = 0;
            for (Map.Entry<gg, ciq> entry : map.entrySet()) {
                int i = index;
                index++;
                blockEntities[i] = (BlockEntity) entry.getValue();
            }
            return blockEntities;
        }
        return new BlockEntity[0];
    }

    @Override // net.labymod.api.client.world.chunk.Chunk
    default int getHeightBasedOnSection(int startY) {
        cmy[] sections = d();
        if (sections.length == 0) {
            return 0;
        }
        int bottomY = Laby.labyAPI().minecraft().clientWorld().getMinBuildHeight();
        int startSectionIndex = Math.min((startY - bottomY) >> 4, sections.length - 1);
        if (startSectionIndex < 0) {
            startSectionIndex = 0;
        }
        int result = 0;
        for (int index = startSectionIndex; index < sections.length; index++) {
            cmy section = sections[index];
            if (section != null && !section.c()) {
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
            cmy section2 = sections[index2];
            if (section2 != null && !section2.c()) {
                int result2 = bottomY + (index2 << 4) + 15;
                return result2;
            }
        }
        return result;
    }
}
