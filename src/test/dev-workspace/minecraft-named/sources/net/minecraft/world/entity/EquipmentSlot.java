package net.minecraft.world.entity;

import io.netty.buffer.ByteBuf;
import java.util.List;
import java.util.function.IntFunction;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/EquipmentSlot.class */
public enum EquipmentSlot implements StringRepresentable {
    MAINHAND(Type.HAND, 0, 0, "mainhand"),
    OFFHAND(Type.HAND, 1, 5, "offhand"),
    FEET(Type.HUMANOID_ARMOR, 0, 1, 1, PartNames.FEET),
    LEGS(Type.HUMANOID_ARMOR, 1, 1, 2, "legs"),
    CHEST(Type.HUMANOID_ARMOR, 2, 1, 3, "chest"),
    HEAD(Type.HUMANOID_ARMOR, 3, 1, 4, PartNames.HEAD),
    BODY(Type.ANIMAL_ARMOR, 0, 1, 6, PartNames.BODY),
    SADDLE(Type.SADDLE, 0, 1, 7, "saddle");

    public static final int NO_COUNT_LIMIT = 0;
    public static final List<EquipmentSlot> VALUES = List.of((Object[]) values());
    public static final IntFunction<EquipmentSlot> BY_ID = ByIdMap.continuous($$0 -> {
        return $$0.id;
    }, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
    public static final StringRepresentable.EnumCodec<EquipmentSlot> CODEC = StringRepresentable.fromEnum(EquipmentSlot::values);
    public static final StreamCodec<ByteBuf, EquipmentSlot> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, $$0 -> {
        return $$0.id;
    });
    private final Type type;
    private final int index;
    private final int countLimit;
    private final int id;
    private final String name;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/EquipmentSlot$Type.class */
    public enum Type {
        HAND,
        HUMANOID_ARMOR,
        ANIMAL_ARMOR,
        SADDLE
    }

    EquipmentSlot(Type $$0, int $$1, int $$2, int $$3, String $$4) {
        this.type = $$0;
        this.index = $$1;
        this.countLimit = $$2;
        this.id = $$3;
        this.name = $$4;
    }

    EquipmentSlot(Type $$0, int $$1, int $$2, String $$3) {
        this($$0, $$1, 0, $$2, $$3);
    }

    public Type getType() {
        return this.type;
    }

    public int getIndex() {
        return this.index;
    }

    public int getIndex(int $$0) {
        return $$0 + this.index;
    }

    public ItemStack limit(ItemStack $$0) {
        return this.countLimit > 0 ? $$0.split(this.countLimit) : $$0;
    }

    public int getId() {
        return this.id;
    }

    public int getFilterBit(int $$0) {
        return this.id + $$0;
    }

    public String getName() {
        return this.name;
    }

    public boolean isArmor() {
        return this.type == Type.HUMANOID_ARMOR || this.type == Type.ANIMAL_ARMOR;
    }

    @Override // net.minecraft.util.StringRepresentable
    public String getSerializedName() {
        return this.name;
    }

    public boolean canIncreaseExperience() {
        return this.type != Type.SADDLE;
    }

    public static EquipmentSlot byName(String $$0) {
        EquipmentSlot $$1 = (EquipmentSlot) CODEC.byName($$0);
        if ($$1 != null) {
            return $$1;
        }
        throw new IllegalArgumentException("Invalid slot '" + $$0 + "'");
    }
}
