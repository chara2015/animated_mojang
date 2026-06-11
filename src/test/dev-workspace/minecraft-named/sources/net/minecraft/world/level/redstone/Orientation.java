package net.minecraft.world.level.redstone;

import com.google.common.annotations.VisibleForTesting;
import io.netty.buffer.ByteBuf;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/redstone/Orientation.class */
public class Orientation {
    public static final StreamCodec<ByteBuf, Orientation> STREAM_CODEC = ByteBufCodecs.idMapper(Orientation::fromIndex, (v0) -> {
        return v0.getIndex();
    });
    private static final Orientation[] ORIENTATIONS = (Orientation[]) Util.make(() -> {
        Orientation[] $$0 = new Orientation[48];
        generateContext(new Orientation(Direction.UP, Direction.NORTH, SideBias.LEFT), $$0);
        return $$0;
    });
    private final Direction up;
    private final Direction front;
    private final Direction side;
    private final SideBias sideBias;
    private final int index;
    private final List<Direction> neighbors;
    private final List<Direction> horizontalNeighbors;
    private final List<Direction> verticalNeighbors;
    private final Map<Direction, Orientation> withFront = new EnumMap(Direction.class);
    private final Map<Direction, Orientation> withUp = new EnumMap(Direction.class);
    private final Map<SideBias, Orientation> withSideBias = new EnumMap(SideBias.class);

    private Orientation(Direction $$0, Direction $$1, SideBias $$2) {
        this.up = $$0;
        this.front = $$1;
        this.sideBias = $$2;
        this.index = generateIndex($$0, $$1, $$2);
        Vec3i $$3 = $$1.getUnitVec3i().cross($$0.getUnitVec3i());
        Direction $$4 = Direction.getNearest($$3, null);
        Objects.requireNonNull($$4);
        if (this.sideBias == SideBias.RIGHT) {
            this.side = $$4;
        } else {
            this.side = $$4.getOpposite();
        }
        this.neighbors = List.of(this.front.getOpposite(), this.front, this.side, this.side.getOpposite(), this.up.getOpposite(), this.up);
        this.horizontalNeighbors = this.neighbors.stream().filter($$02 -> {
            return $$02.getAxis() != this.up.getAxis();
        }).toList();
        this.verticalNeighbors = this.neighbors.stream().filter($$03 -> {
            return $$03.getAxis() == this.up.getAxis();
        }).toList();
    }

    public static Orientation of(Direction $$0, Direction $$1, SideBias $$2) {
        return ORIENTATIONS[generateIndex($$0, $$1, $$2)];
    }

    public Orientation withUp(Direction $$0) {
        return this.withUp.get($$0);
    }

    public Orientation withFront(Direction $$0) {
        return this.withFront.get($$0);
    }

    public Orientation withFrontPreserveUp(Direction $$0) {
        if ($$0.getAxis() == this.up.getAxis()) {
            return this;
        }
        return this.withFront.get($$0);
    }

    public Orientation withFrontAdjustSideBias(Direction $$0) {
        Orientation $$1 = withFront($$0);
        if (this.front == $$1.side) {
            return $$1.withMirror();
        }
        return $$1;
    }

    public Orientation withSideBias(SideBias $$0) {
        return this.withSideBias.get($$0);
    }

    public Orientation withMirror() {
        return withSideBias(this.sideBias.getOpposite());
    }

    public Direction getFront() {
        return this.front;
    }

    public Direction getUp() {
        return this.up;
    }

    public Direction getSide() {
        return this.side;
    }

    public SideBias getSideBias() {
        return this.sideBias;
    }

    public List<Direction> getDirections() {
        return this.neighbors;
    }

    public List<Direction> getHorizontalDirections() {
        return this.horizontalNeighbors;
    }

    public List<Direction> getVerticalDirections() {
        return this.verticalNeighbors;
    }

    public String toString() {
        return "[up=" + String.valueOf(this.up) + ",front=" + String.valueOf(this.front) + ",sideBias=" + String.valueOf(this.sideBias) + "]";
    }

    public int getIndex() {
        return this.index;
    }

    public static Orientation fromIndex(int $$0) {
        return ORIENTATIONS[$$0];
    }

    public static Orientation random(RandomSource $$0) {
        return (Orientation) Util.getRandom(ORIENTATIONS, $$0);
    }

    private static Orientation generateContext(Orientation $$0, Orientation[] $$1) {
        if ($$1[$$0.getIndex()] != null) {
            return $$1[$$0.getIndex()];
        }
        $$1[$$0.getIndex()] = $$0;
        for (SideBias $$2 : SideBias.values()) {
            $$0.withSideBias.put($$2, generateContext(new Orientation($$0.up, $$0.front, $$2), $$1));
        }
        for (Direction $$3 : Direction.values()) {
            Direction $$4 = $$0.up;
            if ($$3 == $$0.up) {
                $$4 = $$0.front.getOpposite();
            }
            if ($$3 == $$0.up.getOpposite()) {
                $$4 = $$0.front;
            }
            $$0.withFront.put($$3, generateContext(new Orientation($$4, $$3, $$0.sideBias), $$1));
        }
        for (Direction $$5 : Direction.values()) {
            Direction $$6 = $$0.front;
            if ($$5 == $$0.front) {
                $$6 = $$0.up.getOpposite();
            }
            if ($$5 == $$0.front.getOpposite()) {
                $$6 = $$0.up;
            }
            $$0.withUp.put($$5, generateContext(new Orientation($$5, $$6, $$0.sideBias), $$1));
        }
        return $$0;
    }

    @VisibleForTesting
    protected static int generateIndex(Direction $$0, Direction $$1, SideBias $$2) {
        int $$4;
        if ($$0.getAxis() == $$1.getAxis()) {
            throw new IllegalStateException("Up-vector and front-vector can not be on the same axis");
        }
        if ($$0.getAxis() == Direction.Axis.Y) {
            $$4 = $$1.getAxis() == Direction.Axis.X ? 1 : 0;
        } else {
            $$4 = $$1.getAxis() == Direction.Axis.Y ? 1 : 0;
        }
        int $$5 = ($$4 << 1) | $$1.getAxisDirection().ordinal();
        return ((($$0.ordinal() << 2) + $$5) << 1) + $$2.ordinal();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/redstone/Orientation$SideBias.class */
    public enum SideBias {
        LEFT("left"),
        RIGHT("right");

        private final String name;

        SideBias(String $$0) {
            this.name = $$0;
        }

        public SideBias getOpposite() {
            return this == LEFT ? RIGHT : LEFT;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.name;
        }
    }
}
