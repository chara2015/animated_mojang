package net.minecraft.world.phys.shapes;

import com.google.common.collect.Lists;
import com.google.common.math.DoubleMath;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.core.AxisCycle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.level.levelgen.Density;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import org.apache.commons.lang3.mutable.MutableObject;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/phys/shapes/VoxelShape.class */
public abstract class VoxelShape {
    protected final DiscreteVoxelShape shape;
    private VoxelShape[] faces;

    public abstract DoubleList getCoords(Direction.Axis axis);

    protected VoxelShape(DiscreteVoxelShape $$0) {
        this.shape = $$0;
    }

    public double min(Direction.Axis $$0) {
        int $$1 = this.shape.firstFull($$0);
        if ($$1 >= this.shape.getSize($$0)) {
            return Double.POSITIVE_INFINITY;
        }
        return get($$0, $$1);
    }

    public double max(Direction.Axis $$0) {
        int $$1 = this.shape.lastFull($$0);
        if ($$1 <= 0) {
            return Double.NEGATIVE_INFINITY;
        }
        return get($$0, $$1);
    }

    public AABB bounds() {
        if (isEmpty()) {
            throw ((UnsupportedOperationException) Util.pauseInIde(new UnsupportedOperationException("No bounds for empty shape.")));
        }
        return new AABB(min(Direction.Axis.X), min(Direction.Axis.Y), min(Direction.Axis.Z), max(Direction.Axis.X), max(Direction.Axis.Y), max(Direction.Axis.Z));
    }

    public VoxelShape singleEncompassing() {
        if (isEmpty()) {
            return Shapes.empty();
        }
        return Shapes.box(min(Direction.Axis.X), min(Direction.Axis.Y), min(Direction.Axis.Z), max(Direction.Axis.X), max(Direction.Axis.Y), max(Direction.Axis.Z));
    }

    protected double get(Direction.Axis $$0, int $$1) {
        return getCoords($$0).getDouble($$1);
    }

    public boolean isEmpty() {
        return this.shape.isEmpty();
    }

    public VoxelShape move(Vec3 $$0) {
        return move($$0.x, $$0.y, $$0.z);
    }

    public VoxelShape move(Vec3i $$0) {
        return move($$0.getX(), $$0.getY(), $$0.getZ());
    }

    public VoxelShape move(double $$0, double $$1, double $$2) {
        if (isEmpty()) {
            return Shapes.empty();
        }
        return new ArrayVoxelShape(this.shape, (DoubleList) new OffsetDoubleList(getCoords(Direction.Axis.X), $$0), (DoubleList) new OffsetDoubleList(getCoords(Direction.Axis.Y), $$1), (DoubleList) new OffsetDoubleList(getCoords(Direction.Axis.Z), $$2));
    }

    public VoxelShape optimize() {
        VoxelShape[] $$0 = {Shapes.empty()};
        forAllBoxes(($$1, $$2, $$3, $$4, $$5, $$6) -> {
            $$0[0] = Shapes.joinUnoptimized($$0[0], Shapes.box($$1, $$2, $$3, $$4, $$5, $$6), BooleanOp.OR);
        });
        return $$0[0];
    }

    public void forAllEdges(Shapes.DoubleLineConsumer $$0) {
        this.shape.forAllEdges(($$1, $$2, $$3, $$4, $$5, $$6) -> {
            $$0.consume(get(Direction.Axis.X, $$1), get(Direction.Axis.Y, $$2), get(Direction.Axis.Z, $$3), get(Direction.Axis.X, $$4), get(Direction.Axis.Y, $$5), get(Direction.Axis.Z, $$6));
        }, true);
    }

    public void forAllBoxes(Shapes.DoubleLineConsumer $$0) {
        DoubleList $$1 = getCoords(Direction.Axis.X);
        DoubleList $$2 = getCoords(Direction.Axis.Y);
        DoubleList $$3 = getCoords(Direction.Axis.Z);
        this.shape.forAllBoxes(($$4, $$5, $$6, $$7, $$8, $$9) -> {
            $$0.consume($$1.getDouble($$4), $$2.getDouble($$5), $$3.getDouble($$6), $$1.getDouble($$7), $$2.getDouble($$8), $$3.getDouble($$9));
        }, true);
    }

    public List<AABB> toAabbs() {
        List<AABB> $$0 = Lists.newArrayList();
        forAllBoxes(($$1, $$2, $$3, $$4, $$5, $$6) -> {
            $$0.add(new AABB($$1, $$2, $$3, $$4, $$5, $$6));
        });
        return $$0;
    }

    public double min(Direction.Axis $$0, double $$1, double $$2) {
        Direction.Axis $$3 = AxisCycle.FORWARD.cycle($$0);
        Direction.Axis $$4 = AxisCycle.BACKWARD.cycle($$0);
        int $$5 = findIndex($$3, $$1);
        int $$6 = findIndex($$4, $$2);
        int $$7 = this.shape.firstFull($$0, $$5, $$6);
        if ($$7 >= this.shape.getSize($$0)) {
            return Double.POSITIVE_INFINITY;
        }
        return get($$0, $$7);
    }

    public double max(Direction.Axis $$0, double $$1, double $$2) {
        Direction.Axis $$3 = AxisCycle.FORWARD.cycle($$0);
        Direction.Axis $$4 = AxisCycle.BACKWARD.cycle($$0);
        int $$5 = findIndex($$3, $$1);
        int $$6 = findIndex($$4, $$2);
        int $$7 = this.shape.lastFull($$0, $$5, $$6);
        if ($$7 <= 0) {
            return Double.NEGATIVE_INFINITY;
        }
        return get($$0, $$7);
    }

    protected int findIndex(Direction.Axis $$0, double $$1) {
        return Mth.binarySearch(0, this.shape.getSize($$0) + 1, $$2 -> {
            return $$1 < get($$0, $$2);
        }) - 1;
    }

    public BlockHitResult clip(Vec3 $$0, Vec3 $$1, BlockPos $$2) {
        if (isEmpty()) {
            return null;
        }
        Vec3 $$3 = $$1.subtract($$0);
        if ($$3.lengthSqr() < 1.0E-7d) {
            return null;
        }
        Vec3 $$4 = $$0.add($$3.scale(0.001d));
        if (this.shape.isFullWide(findIndex(Direction.Axis.X, $$4.x - ((double) $$2.getX())), findIndex(Direction.Axis.Y, $$4.y - ((double) $$2.getY())), findIndex(Direction.Axis.Z, $$4.z - ((double) $$2.getZ())))) {
            return new BlockHitResult($$4, Direction.getApproximateNearest($$3.x, $$3.y, $$3.z).getOpposite(), $$2, true);
        }
        return AABB.clip(toAabbs(), $$0, $$1, $$2);
    }

    public Optional<Vec3> closestPointTo(Vec3 $$0) {
        if (isEmpty()) {
            return Optional.empty();
        }
        MutableObject<Vec3> $$1 = new MutableObject<>();
        forAllBoxes(($$2, $$3, $$4, $$5, $$6, $$7) -> {
            double $$8 = Mth.clamp($$0.x(), $$2, $$5);
            double $$9 = Mth.clamp($$0.y(), $$3, $$6);
            double $$10 = Mth.clamp($$0.z(), $$4, $$7);
            Vec3 $$11 = (Vec3) $$1.get();
            if ($$11 == null || $$0.distanceToSqr($$8, $$9, $$10) < $$0.distanceToSqr($$11)) {
                $$1.setValue(new Vec3($$8, $$9, $$10));
            }
        });
        return Optional.of((Vec3) Objects.requireNonNull((Vec3) $$1.get()));
    }

    public VoxelShape getFaceShape(Direction $$0) {
        if (isEmpty() || this == Shapes.block()) {
            return this;
        }
        if (this.faces != null) {
            VoxelShape $$1 = this.faces[$$0.ordinal()];
            if ($$1 != null) {
                return $$1;
            }
        } else {
            this.faces = new VoxelShape[6];
        }
        VoxelShape $$2 = calculateFace($$0);
        this.faces[$$0.ordinal()] = $$2;
        return $$2;
    }

    private VoxelShape calculateFace(Direction $$0) {
        Direction.Axis $$1 = $$0.getAxis();
        if (isCubeLikeAlong($$1)) {
            return this;
        }
        Direction.AxisDirection $$2 = $$0.getAxisDirection();
        int $$3 = findIndex($$1, $$2 == Direction.AxisDirection.POSITIVE ? 0.9999999d : 1.0E-7d);
        SliceShape $$4 = new SliceShape(this, $$1, $$3);
        if ($$4.isEmpty()) {
            return Shapes.empty();
        }
        if ($$4.isCubeLike()) {
            return Shapes.block();
        }
        return $$4;
    }

    protected boolean isCubeLike() {
        for (Direction.Axis $$0 : Direction.Axis.VALUES) {
            if (!isCubeLikeAlong($$0)) {
                return false;
            }
        }
        return true;
    }

    private boolean isCubeLikeAlong(Direction.Axis $$0) {
        DoubleList $$1 = getCoords($$0);
        return $$1.size() == 2 && DoubleMath.fuzzyEquals($$1.getDouble(0), Density.SURFACE, 1.0E-7d) && DoubleMath.fuzzyEquals($$1.getDouble(1), 1.0d, 1.0E-7d);
    }

    public double collide(Direction.Axis $$0, AABB $$1, double $$2) {
        return collideX(AxisCycle.between($$0, Direction.Axis.X), $$1, $$2);
    }

    protected double collideX(AxisCycle $$0, AABB $$1, double $$2) {
        if (isEmpty()) {
            return $$2;
        }
        if (Math.abs($$2) < 1.0E-7d) {
            return Density.SURFACE;
        }
        AxisCycle $$3 = $$0.inverse();
        Direction.Axis $$4 = $$3.cycle(Direction.Axis.X);
        Direction.Axis $$5 = $$3.cycle(Direction.Axis.Y);
        Direction.Axis $$6 = $$3.cycle(Direction.Axis.Z);
        double $$7 = $$1.max($$4);
        double $$8 = $$1.min($$4);
        int $$9 = findIndex($$4, $$8 + 1.0E-7d);
        int $$10 = findIndex($$4, $$7 - 1.0E-7d);
        int $$11 = Math.max(0, findIndex($$5, $$1.min($$5) + 1.0E-7d));
        int $$12 = Math.min(this.shape.getSize($$5), findIndex($$5, $$1.max($$5) - 1.0E-7d) + 1);
        int $$13 = Math.max(0, findIndex($$6, $$1.min($$6) + 1.0E-7d));
        int $$14 = Math.min(this.shape.getSize($$6), findIndex($$6, $$1.max($$6) - 1.0E-7d) + 1);
        int $$15 = this.shape.getSize($$4);
        if ($$2 > Density.SURFACE) {
            for (int $$16 = $$10 + 1; $$16 < $$15; $$16++) {
                for (int $$17 = $$11; $$17 < $$12; $$17++) {
                    for (int $$18 = $$13; $$18 < $$14; $$18++) {
                        if (this.shape.isFullWide($$3, $$16, $$17, $$18)) {
                            double $$19 = get($$4, $$16) - $$7;
                            if ($$19 >= -1.0E-7d) {
                                $$2 = Math.min($$2, $$19);
                            }
                            return $$2;
                        }
                    }
                }
            }
        } else if ($$2 < Density.SURFACE) {
            for (int $$20 = $$9 - 1; $$20 >= 0; $$20--) {
                for (int $$21 = $$11; $$21 < $$12; $$21++) {
                    for (int $$22 = $$13; $$22 < $$14; $$22++) {
                        if (this.shape.isFullWide($$3, $$20, $$21, $$22)) {
                            double $$23 = get($$4, $$20 + 1) - $$8;
                            if ($$23 <= 1.0E-7d) {
                                $$2 = Math.max($$2, $$23);
                            }
                            return $$2;
                        }
                    }
                }
            }
        }
        return $$2;
    }

    public boolean equals(Object $$0) {
        return super.equals($$0);
    }

    public String toString() {
        return isEmpty() ? "EMPTY" : "VoxelShape[" + String.valueOf(bounds()) + "]";
    }
}
