package net.minecraft.world.phys.shapes;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleLists;
import net.minecraft.world.level.levelgen.Density;
import net.minecraft.world.phys.shapes.IndexMerger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/phys/shapes/IndirectMerger.class */
public class IndirectMerger implements IndexMerger {
    private static final DoubleList EMPTY = DoubleLists.unmodifiable(DoubleArrayList.wrap(new double[]{Density.SURFACE}));
    private final double[] result;
    private final int[] firstIndices;
    private final int[] secondIndices;
    private final int resultLength;

    /* JADX WARN: Removed duplicated region for block: B:50:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00f5  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0130 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x010b A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public IndirectMerger(it.unimi.dsi.fastutil.doubles.DoubleList r8, it.unimi.dsi.fastutil.doubles.DoubleList r9, boolean r10, boolean r11) {
        /*
            Method dump skipped, instruction units count: 340
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: net.minecraft.world.phys.shapes.IndirectMerger.<init>(it.unimi.dsi.fastutil.doubles.DoubleList, it.unimi.dsi.fastutil.doubles.DoubleList, boolean, boolean):void");
    }

    @Override // net.minecraft.world.phys.shapes.IndexMerger
    public boolean forMergedIndexes(IndexMerger.IndexConsumer $$0) {
        int $$1 = this.resultLength - 1;
        for (int $$2 = 0; $$2 < $$1; $$2++) {
            if (!$$0.merge(this.firstIndices[$$2], this.secondIndices[$$2], $$2)) {
                return false;
            }
        }
        return true;
    }

    @Override // net.minecraft.world.phys.shapes.IndexMerger
    public int size() {
        return this.resultLength;
    }

    @Override // net.minecraft.world.phys.shapes.IndexMerger
    public DoubleList getList() {
        return this.resultLength <= 1 ? EMPTY : DoubleArrayList.wrap(this.result, this.resultLength);
    }
}
