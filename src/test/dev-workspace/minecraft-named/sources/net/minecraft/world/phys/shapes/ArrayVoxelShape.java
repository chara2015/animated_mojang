package net.minecraft.world.phys.shapes;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.Arrays;
import net.minecraft.core.Direction;
import net.minecraft.util.Util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/phys/shapes/ArrayVoxelShape.class */
public class ArrayVoxelShape extends VoxelShape {
    private final DoubleList xs;
    private final DoubleList ys;
    private final DoubleList zs;

    protected ArrayVoxelShape(DiscreteVoxelShape $$0, double[] $$1, double[] $$2, double[] $$3) {
        this($$0, (DoubleList) DoubleArrayList.wrap(Arrays.copyOf($$1, $$0.getXSize() + 1)), (DoubleList) DoubleArrayList.wrap(Arrays.copyOf($$2, $$0.getYSize() + 1)), (DoubleList) DoubleArrayList.wrap(Arrays.copyOf($$3, $$0.getZSize() + 1)));
    }

    ArrayVoxelShape(DiscreteVoxelShape $$0, DoubleList $$1, DoubleList $$2, DoubleList $$3) {
        super($$0);
        int $$4 = $$0.getXSize() + 1;
        int $$5 = $$0.getYSize() + 1;
        int $$6 = $$0.getZSize() + 1;
        if ($$4 != $$1.size() || $$5 != $$2.size() || $$6 != $$3.size()) {
            throw ((IllegalArgumentException) Util.pauseInIde(new IllegalArgumentException("Lengths of point arrays must be consistent with the size of the VoxelShape.")));
        }
        this.xs = $$1;
        this.ys = $$2;
        this.zs = $$3;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.minecraft.world.phys.shapes.VoxelShape
    public DoubleList getCoords(Direction.Axis $$0) throws MatchException {
        switch ($$0) {
            case X:
                return this.xs;
            case Y:
                return this.ys;
            case Z:
                return this.zs;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }
}
