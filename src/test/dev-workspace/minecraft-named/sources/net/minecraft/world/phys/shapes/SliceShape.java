package net.minecraft.world.phys.shapes;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.core.Direction;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/phys/shapes/SliceShape.class */
public class SliceShape extends VoxelShape {
    private final VoxelShape delegate;
    private final Direction.Axis axis;
    private static final DoubleList SLICE_COORDS = new CubePointRange(1);

    public SliceShape(VoxelShape $$0, Direction.Axis $$1, int $$2) {
        super(makeSlice($$0.shape, $$1, $$2));
        this.delegate = $$0;
        this.axis = $$1;
    }

    private static DiscreteVoxelShape makeSlice(DiscreteVoxelShape $$0, Direction.Axis $$1, int $$2) {
        return new SubShape($$0, $$1.choose($$2, 0, 0), $$1.choose(0, $$2, 0), $$1.choose(0, 0, $$2), $$1.choose($$2 + 1, $$0.xSize, $$0.xSize), $$1.choose($$0.ySize, $$2 + 1, $$0.ySize), $$1.choose($$0.zSize, $$0.zSize, $$2 + 1));
    }

    @Override // net.minecraft.world.phys.shapes.VoxelShape
    public DoubleList getCoords(Direction.Axis $$0) {
        if ($$0 == this.axis) {
            return SLICE_COORDS;
        }
        return this.delegate.getCoords($$0);
    }
}
