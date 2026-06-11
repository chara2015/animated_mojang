package net.minecraft.world.entity;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import java.util.function.IntFunction;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/Pose.class */
public enum Pose implements StringRepresentable {
    STANDING(0, "standing"),
    FALL_FLYING(1, "fall_flying"),
    SLEEPING(2, "sleeping"),
    SWIMMING(3, "swimming"),
    SPIN_ATTACK(4, "spin_attack"),
    CROUCHING(5, "crouching"),
    LONG_JUMPING(6, "long_jumping"),
    DYING(7, "dying"),
    CROAKING(8, "croaking"),
    USING_TONGUE(9, "using_tongue"),
    SITTING(10, "sitting"),
    ROARING(11, "roaring"),
    SNIFFING(12, "sniffing"),
    EMERGING(13, "emerging"),
    DIGGING(14, "digging"),
    SLIDING(15, "sliding"),
    SHOOTING(16, "shooting"),
    INHALING(17, "inhaling");

    public static final IntFunction<Pose> BY_ID = ByIdMap.continuous((v0) -> {
        return v0.id();
    }, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
    public static final Codec<Pose> CODEC = StringRepresentable.fromEnum(Pose::values);
    public static final StreamCodec<ByteBuf, Pose> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, (v0) -> {
        return v0.id();
    });
    private final int id;
    private final String name;

    Pose(int $$0, String $$1) {
        this.id = $$0;
        this.name = $$1;
    }

    public int id() {
        return this.id;
    }

    @Override // net.minecraft.util.StringRepresentable
    public String getSerializedName() {
        return this.name;
    }
}
