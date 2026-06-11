package net.minecraft.world.phys.shapes;

import java.util.BitSet;
import net.minecraft.core.Direction;
import net.minecraft.world.level.lighting.ChunkSkyLightSources;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/phys/shapes/BitSetDiscreteVoxelShape.class */
public final class BitSetDiscreteVoxelShape extends DiscreteVoxelShape {
    private final BitSet storage;
    private int xMin;
    private int yMin;
    private int zMin;
    private int xMax;
    private int yMax;
    private int zMax;

    public BitSetDiscreteVoxelShape(int $$0, int $$1, int $$2) {
        super($$0, $$1, $$2);
        this.storage = new BitSet($$0 * $$1 * $$2);
        this.xMin = $$0;
        this.yMin = $$1;
        this.zMin = $$2;
    }

    public static BitSetDiscreteVoxelShape withFilledBounds(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, int $$8) {
        BitSetDiscreteVoxelShape $$9 = new BitSetDiscreteVoxelShape($$0, $$1, $$2);
        $$9.xMin = $$3;
        $$9.yMin = $$4;
        $$9.zMin = $$5;
        $$9.xMax = $$6;
        $$9.yMax = $$7;
        $$9.zMax = $$8;
        for (int $$10 = $$3; $$10 < $$6; $$10++) {
            for (int $$11 = $$4; $$11 < $$7; $$11++) {
                for (int $$12 = $$5; $$12 < $$8; $$12++) {
                    $$9.fillUpdateBounds($$10, $$11, $$12, false);
                }
            }
        }
        return $$9;
    }

    public BitSetDiscreteVoxelShape(DiscreteVoxelShape $$0) {
        super($$0.xSize, $$0.ySize, $$0.zSize);
        if ($$0 instanceof BitSetDiscreteVoxelShape) {
            this.storage = (BitSet) ((BitSetDiscreteVoxelShape) $$0).storage.clone();
        } else {
            this.storage = new BitSet(this.xSize * this.ySize * this.zSize);
            for (int $$1 = 0; $$1 < this.xSize; $$1++) {
                for (int $$2 = 0; $$2 < this.ySize; $$2++) {
                    for (int $$3 = 0; $$3 < this.zSize; $$3++) {
                        if ($$0.isFull($$1, $$2, $$3)) {
                            this.storage.set(getIndex($$1, $$2, $$3));
                        }
                    }
                }
            }
        }
        this.xMin = $$0.firstFull(Direction.Axis.X);
        this.yMin = $$0.firstFull(Direction.Axis.Y);
        this.zMin = $$0.firstFull(Direction.Axis.Z);
        this.xMax = $$0.lastFull(Direction.Axis.X);
        this.yMax = $$0.lastFull(Direction.Axis.Y);
        this.zMax = $$0.lastFull(Direction.Axis.Z);
    }

    protected int getIndex(int $$0, int $$1, int $$2) {
        return ((($$0 * this.ySize) + $$1) * this.zSize) + $$2;
    }

    @Override // net.minecraft.world.phys.shapes.DiscreteVoxelShape
    public boolean isFull(int $$0, int $$1, int $$2) {
        return this.storage.get(getIndex($$0, $$1, $$2));
    }

    private void fillUpdateBounds(int $$0, int $$1, int $$2, boolean $$3) {
        this.storage.set(getIndex($$0, $$1, $$2));
        if ($$3) {
            this.xMin = Math.min(this.xMin, $$0);
            this.yMin = Math.min(this.yMin, $$1);
            this.zMin = Math.min(this.zMin, $$2);
            this.xMax = Math.max(this.xMax, $$0 + 1);
            this.yMax = Math.max(this.yMax, $$1 + 1);
            this.zMax = Math.max(this.zMax, $$2 + 1);
        }
    }

    @Override // net.minecraft.world.phys.shapes.DiscreteVoxelShape
    public void fill(int $$0, int $$1, int $$2) {
        fillUpdateBounds($$0, $$1, $$2, true);
    }

    @Override // net.minecraft.world.phys.shapes.DiscreteVoxelShape
    public boolean isEmpty() {
        return this.storage.isEmpty();
    }

    @Override // net.minecraft.world.phys.shapes.DiscreteVoxelShape
    public int firstFull(Direction.Axis $$0) {
        return $$0.choose(this.xMin, this.yMin, this.zMin);
    }

    @Override // net.minecraft.world.phys.shapes.DiscreteVoxelShape
    public int lastFull(Direction.Axis $$0) {
        return $$0.choose(this.xMax, this.yMax, this.zMax);
    }

    static BitSetDiscreteVoxelShape join(DiscreteVoxelShape $$0, DiscreteVoxelShape $$1, IndexMerger $$2, IndexMerger $$3, IndexMerger $$4, BooleanOp $$5) {
        BitSetDiscreteVoxelShape $$6 = new BitSetDiscreteVoxelShape($$2.size() - 1, $$3.size() - 1, $$4.size() - 1);
        int[] $$7 = {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, ChunkSkyLightSources.NEGATIVE_INFINITY, ChunkSkyLightSources.NEGATIVE_INFINITY, ChunkSkyLightSources.NEGATIVE_INFINITY};
        $$2.forMergedIndexes(($$72, $$8, $$9) -> {
            boolean[] $$10 = {false};
            $$3.forMergedIndexes(($$102, $$11, $$12) -> {
                boolean[] $$13 = {false};
                $$4.forMergedIndexes(($$12, $$132, $$14) -> {
                    if ($$5.apply($$0.isFullWide($$72, $$102, $$12), $$1.isFullWide($$8, $$11, $$132))) {
                        $$6.storage.set($$6.getIndex($$9, $$12, $$14));
                        $$7[2] = Math.min($$7[2], $$14);
                        $$7[5] = Math.max($$7[5], $$14);
                        $$13[0] = true;
                        return true;
                    }
                    return true;
                });
                if ($$13[0]) {
                    $$7[1] = Math.min($$7[1], $$12);
                    $$7[4] = Math.max($$7[4], $$12);
                    $$10[0] = true;
                    return true;
                }
                return true;
            });
            if ($$10[0]) {
                $$7[0] = Math.min($$7[0], $$9);
                $$7[3] = Math.max($$7[3], $$9);
                return true;
            }
            return true;
        });
        $$6.xMin = $$7[0];
        $$6.yMin = $$7[1];
        $$6.zMin = $$7[2];
        $$6.xMax = $$7[3] + 1;
        $$6.yMax = $$7[4] + 1;
        $$6.zMax = $$7[5] + 1;
        return $$6;
    }

    protected static void forAllBoxes(DiscreteVoxelShape $$0, DiscreteVoxelShape.IntLineConsumer $$1, boolean $$2) {
        BitSetDiscreteVoxelShape $$3 = new BitSetDiscreteVoxelShape($$0);
        for (int $$4 = 0; $$4 < $$3.ySize; $$4++) {
            for (int $$5 = 0; $$5 < $$3.xSize; $$5++) {
                int $$6 = -1;
                for (int $$7 = 0; $$7 <= $$3.zSize; $$7++) {
                    if ($$3.isFullWide($$5, $$4, $$7)) {
                        if ($$2) {
                            if ($$6 == -1) {
                                $$6 = $$7;
                            }
                        } else {
                            $$1.consume($$5, $$4, $$7, $$5 + 1, $$4 + 1, $$7 + 1);
                        }
                    } else if ($$6 != -1) {
                        int $$8 = $$5;
                        int $$9 = $$4;
                        $$3.clearZStrip($$6, $$7, $$5, $$4);
                        while ($$3.isZStripFull($$6, $$7, $$8 + 1, $$4)) {
                            $$3.clearZStrip($$6, $$7, $$8 + 1, $$4);
                            $$8++;
                        }
                        while ($$3.isXZRectangleFull($$5, $$8 + 1, $$6, $$7, $$9 + 1)) {
                            for (int $$10 = $$5; $$10 <= $$8; $$10++) {
                                $$3.clearZStrip($$6, $$7, $$10, $$9 + 1);
                            }
                            $$9++;
                        }
                        $$1.consume($$5, $$4, $$6, $$8 + 1, $$9 + 1, $$7);
                        $$6 = -1;
                    }
                }
            }
        }
    }

    private boolean isZStripFull(int $$0, int $$1, int $$2, int $$3) {
        return $$2 < this.xSize && $$3 < this.ySize && this.storage.nextClearBit(getIndex($$2, $$3, $$0)) >= getIndex($$2, $$3, $$1);
    }

    private boolean isXZRectangleFull(int $$0, int $$1, int $$2, int $$3, int $$4) {
        for (int $$5 = $$0; $$5 < $$1; $$5++) {
            if (!isZStripFull($$2, $$3, $$5, $$4)) {
                return false;
            }
        }
        return true;
    }

    private void clearZStrip(int $$0, int $$1, int $$2, int $$3) {
        this.storage.clear(getIndex($$2, $$3, $$0), getIndex($$2, $$3, $$1));
    }

    public boolean isInterior(int $$0, int $$1, int $$2) {
        boolean $$3 = $$0 > 0 && $$0 < this.xSize - 1 && $$1 > 0 && $$1 < this.ySize - 1 && $$2 > 0 && $$2 < this.zSize - 1;
        return $$3 && isFull($$0, $$1, $$2) && isFull($$0 - 1, $$1, $$2) && isFull($$0 + 1, $$1, $$2) && isFull($$0, $$1 - 1, $$2) && isFull($$0, $$1 + 1, $$2) && isFull($$0, $$1, $$2 - 1) && isFull($$0, $$1, $$2 + 1);
    }
}
