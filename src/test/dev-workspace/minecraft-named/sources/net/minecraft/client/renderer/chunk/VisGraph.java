package net.minecraft.client.renderer.chunk;

import it.unimi.dsi.fastutil.ints.IntArrayFIFOQueue;
import java.util.BitSet;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Util;
import net.minecraft.world.level.levelgen.Density;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/chunk/VisGraph.class */
public class VisGraph {
    private static final int SIZE_IN_BITS = 4;
    private static final int LEN = 16;
    private static final int MASK = 15;
    private static final int SIZE = 4096;
    private static final int X_SHIFT = 0;
    private static final int Z_SHIFT = 4;
    private static final int Y_SHIFT = 8;
    private static final int INVALID_INDEX = -1;
    private final BitSet bitSet = new BitSet(4096);
    private int empty = 4096;
    private static final int DX = (int) Math.pow(16.0d, Density.SURFACE);
    private static final int DZ = (int) Math.pow(16.0d, 1.0d);
    private static final int DY = (int) Math.pow(16.0d, 2.0d);
    private static final Direction[] DIRECTIONS = Direction.values();
    private static final int[] INDEX_OF_EDGES = (int[]) Util.make(new int[1352], $$0 -> {
        int $$3 = 0;
        for (int $$4 = 0; $$4 < 16; $$4++) {
            for (int $$5 = 0; $$5 < 16; $$5++) {
                for (int $$6 = 0; $$6 < 16; $$6++) {
                    if ($$4 == 0 || $$4 == 15 || $$5 == 0 || $$5 == 15 || $$6 == 0 || $$6 == 15) {
                        int i = $$3;
                        $$3++;
                        $$0[i] = getIndex($$4, $$5, $$6);
                    }
                }
            }
        }
    });

    public void setOpaque(BlockPos $$0) {
        this.bitSet.set(getIndex($$0), true);
        this.empty--;
    }

    private static int getIndex(BlockPos $$0) {
        return getIndex($$0.getX() & 15, $$0.getY() & 15, $$0.getZ() & 15);
    }

    private static int getIndex(int $$0, int $$1, int $$2) {
        return ($$0 << 0) | ($$1 << 8) | ($$2 << 4);
    }

    public VisibilitySet resolve() {
        VisibilitySet $$0 = new VisibilitySet();
        if (4096 - this.empty < 256) {
            $$0.setAll(true);
        } else if (this.empty == 0) {
            $$0.setAll(false);
        } else {
            for (int $$1 : INDEX_OF_EDGES) {
                if (!this.bitSet.get($$1)) {
                    $$0.add(floodFill($$1));
                }
            }
        }
        return $$0;
    }

    private Set<Direction> floodFill(int $$0) {
        Set<Direction> $$1 = EnumSet.noneOf(Direction.class);
        IntArrayFIFOQueue intArrayFIFOQueue = new IntArrayFIFOQueue();
        intArrayFIFOQueue.enqueue($$0);
        this.bitSet.set($$0, true);
        while (!intArrayFIFOQueue.isEmpty()) {
            int $$3 = intArrayFIFOQueue.dequeueInt();
            addEdges($$3, $$1);
            for (Direction $$4 : DIRECTIONS) {
                int $$5 = getNeighborIndexAtFace($$3, $$4);
                if ($$5 >= 0 && !this.bitSet.get($$5)) {
                    this.bitSet.set($$5, true);
                    intArrayFIFOQueue.enqueue($$5);
                }
            }
        }
        return $$1;
    }

    private void addEdges(int $$0, Set<Direction> $$1) {
        int $$2 = ($$0 >> 0) & 15;
        if ($$2 == 0) {
            $$1.add(Direction.WEST);
        } else if ($$2 == 15) {
            $$1.add(Direction.EAST);
        }
        int $$3 = ($$0 >> 8) & 15;
        if ($$3 == 0) {
            $$1.add(Direction.DOWN);
        } else if ($$3 == 15) {
            $$1.add(Direction.UP);
        }
        int $$4 = ($$0 >> 4) & 15;
        if ($$4 == 0) {
            $$1.add(Direction.NORTH);
        } else if ($$4 == 15) {
            $$1.add(Direction.SOUTH);
        }
    }

    private int getNeighborIndexAtFace(int $$0, Direction $$1) {
        switch ($$1) {
            case DOWN:
                if ((($$0 >> 8) & 15) == 0) {
                    return -1;
                }
                return $$0 - DY;
            case UP:
                if ((($$0 >> 8) & 15) == 15) {
                    return -1;
                }
                return $$0 + DY;
            case NORTH:
                if ((($$0 >> 4) & 15) == 0) {
                    return -1;
                }
                return $$0 - DZ;
            case SOUTH:
                if ((($$0 >> 4) & 15) == 15) {
                    return -1;
                }
                return $$0 + DZ;
            case WEST:
                if ((($$0 >> 0) & 15) == 0) {
                    return -1;
                }
                return $$0 - DX;
            case EAST:
                if ((($$0 >> 0) & 15) == 15) {
                    return -1;
                }
                return $$0 + DX;
            default:
                return -1;
        }
    }
}
