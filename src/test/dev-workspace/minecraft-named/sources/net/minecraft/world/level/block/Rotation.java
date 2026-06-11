package net.minecraft.world.level.block;

import com.mojang.math.OctahedralGroup;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import java.util.List;
import java.util.function.IntFunction;
import net.minecraft.core.Direction;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.Util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/Rotation.class */
public enum Rotation implements StringRepresentable {
    NONE(0, "none", OctahedralGroup.IDENTITY),
    CLOCKWISE_90(1, "clockwise_90", OctahedralGroup.ROT_90_Y_NEG),
    CLOCKWISE_180(2, "180", OctahedralGroup.ROT_180_FACE_XZ),
    COUNTERCLOCKWISE_90(3, "counterclockwise_90", OctahedralGroup.ROT_90_Y_POS);

    public static final IntFunction<Rotation> BY_ID = ByIdMap.continuous((v0) -> {
        return v0.getIndex();
    }, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
    public static final Codec<Rotation> CODEC = StringRepresentable.fromEnum(Rotation::values);
    public static final StreamCodec<ByteBuf, Rotation> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, (v0) -> {
        return v0.getIndex();
    });

    @Deprecated
    public static final Codec<Rotation> LEGACY_CODEC = ExtraCodecs.legacyEnum(Rotation::valueOf);
    private final int index;
    private final String id;
    private final OctahedralGroup rotation;

    Rotation(int $$0, String $$1, OctahedralGroup $$2) {
        this.index = $$0;
        this.id = $$1;
        this.rotation = $$2;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public Rotation getRotated(Rotation $$0) throws MatchException {
        switch ($$0.ordinal()) {
            case 1:
                switch (this) {
                    case NONE:
                        return CLOCKWISE_90;
                    case CLOCKWISE_90:
                        return CLOCKWISE_180;
                    case CLOCKWISE_180:
                        return COUNTERCLOCKWISE_90;
                    case COUNTERCLOCKWISE_90:
                        return NONE;
                    default:
                        throw new MatchException((String) null, (Throwable) null);
                }
            case 2:
                switch (this) {
                    case NONE:
                        return CLOCKWISE_180;
                    case CLOCKWISE_90:
                        return COUNTERCLOCKWISE_90;
                    case CLOCKWISE_180:
                        return NONE;
                    case COUNTERCLOCKWISE_90:
                        return CLOCKWISE_90;
                    default:
                        throw new MatchException((String) null, (Throwable) null);
                }
            case 3:
                switch (this) {
                    case NONE:
                        return COUNTERCLOCKWISE_90;
                    case CLOCKWISE_90:
                        return NONE;
                    case CLOCKWISE_180:
                        return CLOCKWISE_90;
                    case COUNTERCLOCKWISE_90:
                        return CLOCKWISE_180;
                    default:
                        throw new MatchException((String) null, (Throwable) null);
                }
            default:
                return this;
        }
    }

    public OctahedralGroup rotation() {
        return this.rotation;
    }

    public Direction rotate(Direction $$0) {
        if ($$0.getAxis() == Direction.Axis.Y) {
            return $$0;
        }
        switch (ordinal()) {
        }
        return $$0;
    }

    public int rotate(int $$0, int $$1) {
        switch (ordinal()) {
            case 1:
                return ($$0 + ($$1 / 4)) % $$1;
            case 2:
                return ($$0 + ($$1 / 2)) % $$1;
            case 3:
                return ($$0 + (($$1 * 3) / 4)) % $$1;
            default:
                return $$0;
        }
    }

    public static Rotation getRandom(RandomSource $$0) {
        return (Rotation) Util.getRandom(values(), $$0);
    }

    public static List<Rotation> getShuffled(RandomSource $$0) {
        return Util.shuffledCopy(values(), $$0);
    }

    @Override // net.minecraft.util.StringRepresentable
    public String getSerializedName() {
        return this.id;
    }

    private int getIndex() {
        return this.index;
    }
}
