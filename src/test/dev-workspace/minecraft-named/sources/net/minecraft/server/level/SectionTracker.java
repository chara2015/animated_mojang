package net.minecraft.server.level;

import net.minecraft.core.SectionPos;
import net.minecraft.world.level.lighting.DynamicGraphMinFixedPoint;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/level/SectionTracker.class */
public abstract class SectionTracker extends DynamicGraphMinFixedPoint {
    protected abstract int getLevelFromSource(long j);

    protected SectionTracker(int $$0, int $$1, int $$2) {
        super($$0, $$1, $$2);
    }

    @Override // net.minecraft.world.level.lighting.DynamicGraphMinFixedPoint
    protected void checkNeighborsAfterUpdate(long $$0, int $$1, boolean $$2) {
        if ($$2 && $$1 >= this.levelCount - 2) {
            return;
        }
        for (int $$3 = -1; $$3 <= 1; $$3++) {
            for (int $$4 = -1; $$4 <= 1; $$4++) {
                for (int $$5 = -1; $$5 <= 1; $$5++) {
                    long $$6 = SectionPos.offset($$0, $$3, $$4, $$5);
                    if ($$6 != $$0) {
                        checkNeighbor($$0, $$6, $$1, $$2);
                    }
                }
            }
        }
    }

    @Override // net.minecraft.world.level.lighting.DynamicGraphMinFixedPoint
    protected int getComputedLevel(long $$0, long $$1, int $$2) {
        int $$3 = $$2;
        for (int $$4 = -1; $$4 <= 1; $$4++) {
            for (int $$5 = -1; $$5 <= 1; $$5++) {
                for (int $$6 = -1; $$6 <= 1; $$6++) {
                    long $$7 = SectionPos.offset($$0, $$4, $$5, $$6);
                    if ($$7 == $$0) {
                        $$7 = Long.MAX_VALUE;
                    }
                    if ($$7 != $$1) {
                        int $$8 = computeLevelFromNeighbor($$7, $$0, getLevel($$7));
                        if ($$3 > $$8) {
                            $$3 = $$8;
                        }
                        if ($$3 == 0) {
                            return $$3;
                        }
                    }
                }
            }
        }
        return $$3;
    }

    @Override // net.minecraft.world.level.lighting.DynamicGraphMinFixedPoint
    protected int computeLevelFromNeighbor(long $$0, long $$1, int $$2) {
        if (isSource($$0)) {
            return getLevelFromSource($$1);
        }
        return $$2 + 1;
    }

    public void update(long $$0, int $$1, boolean $$2) {
        checkEdge(DynamicGraphMinFixedPoint.SOURCE, $$0, $$1, $$2);
    }
}
