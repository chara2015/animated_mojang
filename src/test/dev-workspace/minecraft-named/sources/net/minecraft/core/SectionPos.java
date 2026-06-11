package net.minecraft.core;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.longs.LongConsumer;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.entity.EntityAccess;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/SectionPos.class */
public class SectionPos extends Vec3i {
    public static final int SECTION_BITS = 4;
    public static final int SECTION_SIZE = 16;
    public static final int SECTION_MASK = 15;
    public static final int SECTION_HALF_SIZE = 8;
    public static final int SECTION_MAX_INDEX = 15;
    private static final int PACKED_X_LENGTH = 22;
    private static final int PACKED_Y_LENGTH = 20;
    private static final int PACKED_Z_LENGTH = 22;
    private static final long PACKED_X_MASK = 4194303;
    private static final long PACKED_Y_MASK = 1048575;
    private static final long PACKED_Z_MASK = 4194303;
    private static final int Y_OFFSET = 0;
    private static final int Z_OFFSET = 20;
    private static final int X_OFFSET = 42;
    private static final int RELATIVE_X_SHIFT = 8;
    private static final int RELATIVE_Y_SHIFT = 0;
    private static final int RELATIVE_Z_SHIFT = 4;
    public static final StreamCodec<ByteBuf, SectionPos> STREAM_CODEC = ByteBufCodecs.LONG.map((v0) -> {
        return of(v0);
    }, (v0) -> {
        return v0.asLong();
    });

    SectionPos(int $$0, int $$1, int $$2) {
        super($$0, $$1, $$2);
    }

    public static SectionPos of(int $$0, int $$1, int $$2) {
        return new SectionPos($$0, $$1, $$2);
    }

    public static SectionPos of(BlockPos $$0) {
        return new SectionPos(blockToSectionCoord($$0.getX()), blockToSectionCoord($$0.getY()), blockToSectionCoord($$0.getZ()));
    }

    public static SectionPos of(ChunkPos $$0, int $$1) {
        return new SectionPos($$0.x, $$1, $$0.z);
    }

    public static SectionPos of(EntityAccess $$0) {
        return of($$0.blockPosition());
    }

    public static SectionPos of(Position $$0) {
        return new SectionPos(blockToSectionCoord($$0.x()), blockToSectionCoord($$0.y()), blockToSectionCoord($$0.z()));
    }

    public static SectionPos of(long $$0) {
        return new SectionPos(x($$0), y($$0), z($$0));
    }

    public static SectionPos bottomOf(ChunkAccess $$0) {
        return of($$0.getPos(), $$0.getMinSectionY());
    }

    public static long offset(long $$0, Direction $$1) {
        return offset($$0, $$1.getStepX(), $$1.getStepY(), $$1.getStepZ());
    }

    public static long offset(long $$0, int $$1, int $$2, int $$3) {
        return asLong(x($$0) + $$1, y($$0) + $$2, z($$0) + $$3);
    }

    public static int posToSectionCoord(double $$0) {
        return blockToSectionCoord(Mth.floor($$0));
    }

    public static int blockToSectionCoord(int $$0) {
        return $$0 >> 4;
    }

    public static int blockToSectionCoord(double $$0) {
        return Mth.floor($$0) >> 4;
    }

    public static int sectionRelative(int $$0) {
        return $$0 & 15;
    }

    public static short sectionRelativePos(BlockPos $$0) {
        int $$1 = sectionRelative($$0.getX());
        int $$2 = sectionRelative($$0.getY());
        int $$3 = sectionRelative($$0.getZ());
        return (short) (($$1 << 8) | ($$3 << 4) | ($$2 << 0));
    }

    public static int sectionRelativeX(short $$0) {
        return ($$0 >>> 8) & 15;
    }

    public static int sectionRelativeY(short $$0) {
        return ($$0 >>> 0) & 15;
    }

    public static int sectionRelativeZ(short $$0) {
        return ($$0 >>> 4) & 15;
    }

    public int relativeToBlockX(short $$0) {
        return minBlockX() + sectionRelativeX($$0);
    }

    public int relativeToBlockY(short $$0) {
        return minBlockY() + sectionRelativeY($$0);
    }

    public int relativeToBlockZ(short $$0) {
        return minBlockZ() + sectionRelativeZ($$0);
    }

    public BlockPos relativeToBlockPos(short $$0) {
        return new BlockPos(relativeToBlockX($$0), relativeToBlockY($$0), relativeToBlockZ($$0));
    }

    public static int sectionToBlockCoord(int $$0) {
        return $$0 << 4;
    }

    public static int sectionToBlockCoord(int $$0, int $$1) {
        return sectionToBlockCoord($$0) + $$1;
    }

    public static int x(long $$0) {
        return (int) (($$0 << 0) >> 42);
    }

    public static int y(long $$0) {
        return (int) (($$0 << 44) >> 44);
    }

    public static int z(long $$0) {
        return (int) (($$0 << 22) >> 42);
    }

    public int x() {
        return getX();
    }

    public int y() {
        return getY();
    }

    public int z() {
        return getZ();
    }

    public int minBlockX() {
        return sectionToBlockCoord(x());
    }

    public int minBlockY() {
        return sectionToBlockCoord(y());
    }

    public int minBlockZ() {
        return sectionToBlockCoord(z());
    }

    public int maxBlockX() {
        return sectionToBlockCoord(x(), 15);
    }

    public int maxBlockY() {
        return sectionToBlockCoord(y(), 15);
    }

    public int maxBlockZ() {
        return sectionToBlockCoord(z(), 15);
    }

    public static long blockToSection(long $$0) {
        return asLong(blockToSectionCoord(BlockPos.getX($$0)), blockToSectionCoord(BlockPos.getY($$0)), blockToSectionCoord(BlockPos.getZ($$0)));
    }

    public static long getZeroNode(int $$0, int $$1) {
        return getZeroNode(asLong($$0, 0, $$1));
    }

    public static long getZeroNode(long $$0) {
        return $$0 & (-1048576);
    }

    public static long sectionToChunk(long $$0) {
        return ChunkPos.asLong(x($$0), z($$0));
    }

    public BlockPos origin() {
        return new BlockPos(sectionToBlockCoord(x()), sectionToBlockCoord(y()), sectionToBlockCoord(z()));
    }

    public BlockPos center() {
        return origin().offset(8, 8, 8);
    }

    public ChunkPos chunk() {
        return new ChunkPos(x(), z());
    }

    public static long asLong(BlockPos $$0) {
        return asLong(blockToSectionCoord($$0.getX()), blockToSectionCoord($$0.getY()), blockToSectionCoord($$0.getZ()));
    }

    public static long asLong(int $$0, int $$1, int $$2) {
        long $$3 = 0 | ((((long) $$0) & 4194303) << 42);
        return $$3 | ((((long) $$1) & PACKED_Y_MASK) << 0) | ((((long) $$2) & 4194303) << 20);
    }

    public long asLong() {
        return asLong(x(), y(), z());
    }

    @Override // net.minecraft.core.Vec3i
    public SectionPos offset(int $$0, int $$1, int $$2) {
        if ($$0 == 0 && $$1 == 0 && $$2 == 0) {
            return this;
        }
        return new SectionPos(x() + $$0, y() + $$1, z() + $$2);
    }

    public Stream<BlockPos> blocksInside() {
        return BlockPos.betweenClosedStream(minBlockX(), minBlockY(), minBlockZ(), maxBlockX(), maxBlockY(), maxBlockZ());
    }

    public static Stream<SectionPos> cube(SectionPos $$0, int $$1) {
        int $$2 = $$0.x();
        int $$3 = $$0.y();
        int $$4 = $$0.z();
        return betweenClosedStream($$2 - $$1, $$3 - $$1, $$4 - $$1, $$2 + $$1, $$3 + $$1, $$4 + $$1);
    }

    public static Stream<SectionPos> aroundChunk(ChunkPos $$0, int $$1, int $$2, int $$3) {
        int $$4 = $$0.x;
        int $$5 = $$0.z;
        return betweenClosedStream($$4 - $$1, $$2, $$5 - $$1, $$4 + $$1, $$3, $$5 + $$1);
    }

    public static Stream<SectionPos> betweenClosedStream(final int $$0, final int $$1, final int $$2, final int $$3, final int $$4, final int $$5) {
        return StreamSupport.stream(new Spliterators.AbstractSpliterator<SectionPos>((($$3 - $$0) + 1) * (($$4 - $$1) + 1) * (($$5 - $$2) + 1), 64) { // from class: net.minecraft.core.SectionPos.1
            final Cursor3D cursor;

            {
                this.cursor = new Cursor3D($$0, $$1, $$2, $$3, $$4, $$5);
            }

            @Override // java.util.Spliterator
            public boolean tryAdvance(Consumer<? super SectionPos> $$02) {
                if (this.cursor.advance()) {
                    $$02.accept(new SectionPos(this.cursor.nextX(), this.cursor.nextY(), this.cursor.nextZ()));
                    return true;
                }
                return false;
            }
        }, false);
    }

    public static void aroundAndAtBlockPos(BlockPos $$0, LongConsumer $$1) {
        aroundAndAtBlockPos($$0.getX(), $$0.getY(), $$0.getZ(), $$1);
    }

    public static void aroundAndAtBlockPos(long $$0, LongConsumer $$1) {
        aroundAndAtBlockPos(BlockPos.getX($$0), BlockPos.getY($$0), BlockPos.getZ($$0), $$1);
    }

    public static void aroundAndAtBlockPos(int $$0, int $$1, int $$2, LongConsumer $$3) {
        int $$4 = blockToSectionCoord($$0 - 1);
        int $$5 = blockToSectionCoord($$0 + 1);
        int $$6 = blockToSectionCoord($$1 - 1);
        int $$7 = blockToSectionCoord($$1 + 1);
        int $$8 = blockToSectionCoord($$2 - 1);
        int $$9 = blockToSectionCoord($$2 + 1);
        if ($$4 == $$5 && $$6 == $$7 && $$8 == $$9) {
            $$3.accept(asLong($$4, $$6, $$8));
            return;
        }
        for (int $$10 = $$4; $$10 <= $$5; $$10++) {
            for (int $$11 = $$6; $$11 <= $$7; $$11++) {
                for (int $$12 = $$8; $$12 <= $$9; $$12++) {
                    $$3.accept(asLong($$10, $$11, $$12));
                }
            }
        }
    }
}
