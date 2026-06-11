package net.labymod.v1_20_4.mixins.client.world.chunk;

import java.util.Map;
import net.labymod.api.client.blockentity.BlockEntity;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.chunk.Chunk;
import net.labymod.api.client.world.chunk.Heightmap;
import net.labymod.api.client.world.chunk.HeightmapType;
import net.labymod.api.util.debug.Preconditions;
import net.labymod.core.client.render.schematic.block.ParameterType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/client/world/chunk/MixinChunkAccess.class */
@Mixin({dld.class})
public abstract class MixinChunkAccess implements Chunk {

    @Shadow
    @Final
    protected dlp[] m;

    @Shadow
    @Final
    protected csw d;

    @Shadow
    @Final
    protected Map<hx, dgv> k;

    @Shadow
    public abstract doq a(a aVar);

    @Shadow
    public abstract dlp b(int i);

    @Shadow
    public abstract dlp[] d();

    @Shadow
    public abstract int J_();

    @Override // net.labymod.api.client.world.chunk.Chunk
    public int getChunkX() {
        return this.d.e;
    }

    @Override // net.labymod.api.client.world.chunk.Chunk
    public int getChunkZ() {
        return this.d.f;
    }

    @Override // net.labymod.api.client.world.chunk.Chunk
    public int getAbsoluteBlockX(int chunkBlockX) {
        return this.d.a(chunkBlockX);
    }

    @Override // net.labymod.api.client.world.chunk.Chunk
    public int getAbsoluteBlockZ(int chunkBlockZ) {
        return this.d.b(chunkBlockZ);
    }

    @Override // net.labymod.api.client.world.chunk.Chunk
    public Heightmap heightmap(HeightmapType type) {
        Preconditions.notNull(type, ParameterType.TYPE);
        return a(a.b);
    }

    @Override // net.labymod.api.client.world.chunk.Chunk
    public boolean isLoaded() {
        return !(this instanceof dlk);
    }

    @Override // net.labymod.api.client.world.chunk.Chunk
    public BlockState getBlockState(int x, int y, int z) {
        int index = ((dld) this).e(y);
        if (index < 0 || index >= this.m.length) {
            return cws.a.o();
        }
        dlp section = this.m[index];
        if (section == null) {
            return cws.a.o();
        }
        int yPos = y & 15;
        BlockState blockState = section.a(x, yPos, z);
        blockState.setCoordinates(getAbsoluteBlockX(x), y, getAbsoluteBlockZ(z));
        return blockState;
    }

    @Override // net.labymod.api.client.world.chunk.Chunk
    public BlockEntity[] getBlockEntities() {
        BlockEntity[] blockEntities = new BlockEntity[this.k.size()];
        int index = 0;
        for (Map.Entry<hx, dgv> entry : this.k.entrySet()) {
            int i = index;
            index++;
            blockEntities[i] = (BlockEntity) entry.getValue();
        }
        return blockEntities;
    }

    @Override // net.labymod.api.client.world.chunk.Chunk
    public int getHeightBasedOnSection(int startY) {
        dlp[] sections = d();
        if (sections.length == 0) {
            return 0;
        }
        int bottomY = J_();
        int startSectionIndex = Math.min((startY - bottomY) >> 4, sections.length - 1);
        if (startSectionIndex < 0) {
            startSectionIndex = 0;
        }
        int result = 0;
        for (int index = startSectionIndex; index < sections.length; index++) {
            dlp section = sections[index];
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
            dlp section2 = sections[index2];
            if (section2 != null && !section2.c()) {
                int result2 = bottomY + (index2 << 4) + 15;
                return result2;
            }
        }
        return result;
    }
}
