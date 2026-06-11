package net.labymod.v1_8_9.mixins.client.world.chunk;

import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.client.blockentity.BlockEntity;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.chunk.Chunk;
import net.labymod.api.client.world.chunk.Heightmap;
import net.labymod.api.client.world.chunk.HeightmapType;
import net.labymod.api.util.debug.Preconditions;
import net.labymod.core.client.render.schematic.block.ParameterType;
import net.labymod.v1_8_9.client.util.MinecraftUtil;
import net.labymod.v1_8_9.client.world.VersionedHeightmap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/world/chunk/MixinChunk.class */
@Mixin({amy.class})
@Implements({@Interface(iface = Chunk.class, prefix = "chunk$", remap = Interface.Remap.NONE)})
public abstract class MixinChunk implements Chunk {

    @Shadow
    @Final
    public int a;

    @Shadow
    @Final
    public int b;

    @Shadow
    private boolean h;

    @Shadow
    @Final
    private amz[] d;
    private final Heightmap labyMod$heightmap = new VersionedHeightmap((amy) this);

    @Shadow
    public abstract amz[] h();

    @Intrinsic
    public int chunk$getChunkX() {
        return this.a;
    }

    @Intrinsic
    public int chunk$getChunkZ() {
        return this.b;
    }

    @Override // net.labymod.api.client.world.chunk.Chunk
    public int getAbsoluteBlockX(int chunkBlockX) {
        return (this.a << 4) + chunkBlockX;
    }

    @Override // net.labymod.api.client.world.chunk.Chunk
    public int getAbsoluteBlockZ(int chunkBlockZ) {
        return (this.b << 4) + chunkBlockZ;
    }

    @Override // net.labymod.api.client.world.chunk.Chunk
    public Heightmap heightmap(HeightmapType type) {
        Preconditions.notNull(type, ParameterType.TYPE);
        return this.labyMod$heightmap;
    }

    @Intrinsic
    public boolean chunk$isLoaded() {
        return this.h;
    }

    @Override // net.labymod.api.client.world.chunk.Chunk
    public BlockState getBlockState(int x, int y, int z) {
        int section = y >> 4;
        int absoluteBlockX = getAbsoluteBlockX(x);
        int absoluteBlockZ = getAbsoluteBlockZ(z);
        if (section < 0 || section >= this.d.length) {
            return MinecraftUtil.fromMinecraft(afi.a.Q(), absoluteBlockX, y, absoluteBlockZ);
        }
        amz storageArray = this.d[section];
        if (storageArray == null) {
            return MinecraftUtil.fromMinecraft(afi.a.Q(), absoluteBlockX, y, absoluteBlockZ);
        }
        alz state = storageArray.a(x, y & 15, z);
        return MinecraftUtil.fromMinecraft(state, absoluteBlockX, y, absoluteBlockZ);
    }

    @Override // net.labymod.api.client.world.chunk.Chunk
    public BlockEntity[] getBlockEntities() {
        Map<cj, akw> map = ((amy) this).r();
        BlockEntity[] blockEntities = new BlockEntity[map.size()];
        int index = 0;
        for (Map.Entry<cj, akw> entry : map.entrySet()) {
            int i = index;
            index++;
            blockEntities[i] = (BlockEntity) entry.getValue();
        }
        return blockEntities;
    }

    @Override // net.labymod.api.client.world.chunk.Chunk
    public int getHeightBasedOnSection(int startY) {
        amz[] sections = h();
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
            amz section = sections[index];
            if (section != null && !section.a()) {
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
            amz section2 = sections[index2];
            if (section2 != null && !section2.a()) {
                int result2 = bottomY + (index2 << 4) + 15;
                return result2;
            }
        }
        return result;
    }
}
