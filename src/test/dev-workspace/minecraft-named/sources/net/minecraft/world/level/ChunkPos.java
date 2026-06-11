package net.minecraft.world.level;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.level.chunk.status.ChunkPyramid;
import net.minecraft.world.level.chunk.status.ChunkStatus;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/ChunkPos.class */
public class ChunkPos {
    private static final int SAFETY_MARGIN = 1056;
    private static final long COORD_BITS = 32;
    private static final long COORD_MASK = 4294967295L;
    private static final int REGION_BITS = 5;
    public static final int REGION_SIZE = 32;
    private static final int REGION_MASK = 31;
    public static final int REGION_MAX_INDEX = 31;
    public final int x;
    public final int z;
    private static final int HASH_A = 1664525;
    private static final int HASH_C = 1013904223;
    private static final int HASH_Z_XOR = -559038737;
    public static final Codec<ChunkPos> CODEC = Codec.INT_STREAM.comapFlatMap($$0 -> {
        return Util.fixedSize($$0, 2).map($$0 -> {
            return new ChunkPos($$0[0], $$0[1]);
        });
    }, $$02 -> {
        return IntStream.of($$02.x, $$02.z);
    }).stable();
    public static final StreamCodec<ByteBuf, ChunkPos> STREAM_CODEC = new StreamCodec<ByteBuf, ChunkPos>() { // from class: net.minecraft.world.level.ChunkPos.1
        @Override // net.minecraft.network.codec.StreamDecoder
        public ChunkPos decode(ByteBuf $$0) {
            return FriendlyByteBuf.readChunkPos($$0);
        }

        @Override // net.minecraft.network.codec.StreamEncoder
        public void encode(ByteBuf $$0, ChunkPos $$1) {
            FriendlyByteBuf.writeChunkPos($$0, $$1);
        }
    };
    public static final long INVALID_CHUNK_POS = asLong(1875066, 1875066);
    private static final int SAFETY_MARGIN_CHUNKS = ((32 + ChunkPyramid.GENERATION_PYRAMID.getStepTo(ChunkStatus.FULL).accumulatedDependencies().size()) + 1) * 2;
    public static final int MAX_COORDINATE_VALUE = SectionPos.blockToSectionCoord(BlockPos.MAX_HORIZONTAL_COORDINATE) - SAFETY_MARGIN_CHUNKS;
    public static final ChunkPos ZERO = new ChunkPos(0, 0);

    public ChunkPos(int $$0, int $$1) {
        this.x = $$0;
        this.z = $$1;
    }

    public ChunkPos(BlockPos $$0) {
        this.x = SectionPos.blockToSectionCoord($$0.getX());
        this.z = SectionPos.blockToSectionCoord($$0.getZ());
    }

    public ChunkPos(long $$0) {
        this.x = (int) $$0;
        this.z = (int) ($$0 >> COORD_BITS);
    }

    public static ChunkPos minFromRegion(int $$0, int $$1) {
        return new ChunkPos($$0 << 5, $$1 << 5);
    }

    public static ChunkPos maxFromRegion(int $$0, int $$1) {
        return new ChunkPos(($$0 << 5) + 31, ($$1 << 5) + 31);
    }

    public boolean isValid() {
        return isValid(this.x, this.z);
    }

    public static boolean isValid(int $$0, int $$1) {
        return Mth.absMax($$0, $$1) <= MAX_COORDINATE_VALUE;
    }

    public long toLong() {
        return asLong(this.x, this.z);
    }

    public static long asLong(int $$0, int $$1) {
        return (((long) $$0) & COORD_MASK) | ((((long) $$1) & COORD_MASK) << COORD_BITS);
    }

    public static long asLong(BlockPos $$0) {
        return asLong(SectionPos.blockToSectionCoord($$0.getX()), SectionPos.blockToSectionCoord($$0.getZ()));
    }

    public static int getX(long $$0) {
        return (int) ($$0 & COORD_MASK);
    }

    public static int getZ(long $$0) {
        return (int) (($$0 >>> COORD_BITS) & COORD_MASK);
    }

    public int hashCode() {
        return hash(this.x, this.z);
    }

    public static int hash(int $$0, int $$1) {
        int $$2 = (HASH_A * $$0) + HASH_C;
        int $$3 = (HASH_A * ($$1 ^ HASH_Z_XOR)) + HASH_C;
        return $$2 ^ $$3;
    }

    public boolean equals(Object $$0) {
        if (this == $$0) {
            return true;
        }
        if (!($$0 instanceof ChunkPos)) {
            return false;
        }
        ChunkPos $$1 = (ChunkPos) $$0;
        return this.x == $$1.x && this.z == $$1.z;
    }

    public int getMiddleBlockX() {
        return getBlockX(8);
    }

    public int getMiddleBlockZ() {
        return getBlockZ(8);
    }

    public int getMinBlockX() {
        return SectionPos.sectionToBlockCoord(this.x);
    }

    public int getMinBlockZ() {
        return SectionPos.sectionToBlockCoord(this.z);
    }

    public int getMaxBlockX() {
        return getBlockX(15);
    }

    public int getMaxBlockZ() {
        return getBlockZ(15);
    }

    public int getRegionX() {
        return this.x >> 5;
    }

    public int getRegionZ() {
        return this.z >> 5;
    }

    public int getRegionLocalX() {
        return this.x & 31;
    }

    public int getRegionLocalZ() {
        return this.z & 31;
    }

    public BlockPos getBlockAt(int $$0, int $$1, int $$2) {
        return new BlockPos(getBlockX($$0), $$1, getBlockZ($$2));
    }

    public int getBlockX(int $$0) {
        return SectionPos.sectionToBlockCoord(this.x, $$0);
    }

    public int getBlockZ(int $$0) {
        return SectionPos.sectionToBlockCoord(this.z, $$0);
    }

    public BlockPos getMiddleBlockPosition(int $$0) {
        return new BlockPos(getMiddleBlockX(), $$0, getMiddleBlockZ());
    }

    public boolean contains(BlockPos $$0) {
        return $$0.getX() >= getMinBlockX() && $$0.getZ() >= getMinBlockZ() && $$0.getX() <= getMaxBlockX() && $$0.getZ() <= getMaxBlockZ();
    }

    public String toString() {
        return "[" + this.x + ", " + this.z + "]";
    }

    public BlockPos getWorldPosition() {
        return new BlockPos(getMinBlockX(), 0, getMinBlockZ());
    }

    public int getChessboardDistance(ChunkPos $$0) {
        return getChessboardDistance($$0.x, $$0.z);
    }

    public int getChessboardDistance(int $$0, int $$1) {
        return Mth.chessboardDistance($$0, $$1, this.x, this.z);
    }

    public int distanceSquared(ChunkPos $$0) {
        return distanceSquared($$0.x, $$0.z);
    }

    public int distanceSquared(long $$0) {
        return distanceSquared(getX($$0), getZ($$0));
    }

    private int distanceSquared(int $$0, int $$1) {
        int $$2 = $$0 - this.x;
        int $$3 = $$1 - this.z;
        return ($$2 * $$2) + ($$3 * $$3);
    }

    public static Stream<ChunkPos> rangeClosed(ChunkPos $$0, int $$1) {
        return rangeClosed(new ChunkPos($$0.x - $$1, $$0.z - $$1), new ChunkPos($$0.x + $$1, $$0.z + $$1));
    }

    public static Stream<ChunkPos> rangeClosed(final ChunkPos $$0, final ChunkPos $$1) {
        int $$2 = Math.abs($$0.x - $$1.x) + 1;
        int $$3 = Math.abs($$0.z - $$1.z) + 1;
        final int $$4 = $$0.x < $$1.x ? 1 : -1;
        final int $$5 = $$0.z < $$1.z ? 1 : -1;
        return StreamSupport.stream(new Spliterators.AbstractSpliterator<ChunkPos>($$2 * $$3, 64) { // from class: net.minecraft.world.level.ChunkPos.2
            private ChunkPos pos;

            @Override // java.util.Spliterator
            public boolean tryAdvance(Consumer<? super ChunkPos> $$02) {
                if (this.pos == null) {
                    this.pos = $$0;
                } else {
                    int $$12 = this.pos.x;
                    int $$22 = this.pos.z;
                    if ($$12 == $$1.x) {
                        if ($$22 == $$1.z) {
                            return false;
                        }
                        this.pos = new ChunkPos($$0.x, $$22 + $$5);
                    } else {
                        this.pos = new ChunkPos($$12 + $$4, $$22);
                    }
                }
                $$02.accept(this.pos);
                return true;
            }
        }, false);
    }
}
