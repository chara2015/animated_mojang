package net.minecraft.world.item.crafting;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import java.util.function.IntFunction;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/crafting/CraftingBookCategory.class */
public enum CraftingBookCategory implements StringRepresentable {
    BUILDING("building", 0),
    REDSTONE("redstone", 1),
    EQUIPMENT(LivingEntity.TAG_EQUIPMENT, 2),
    MISC("misc", 3);

    public static final Codec<CraftingBookCategory> CODEC = StringRepresentable.fromEnum(CraftingBookCategory::values);
    public static final IntFunction<CraftingBookCategory> BY_ID = ByIdMap.continuous((v0) -> {
        return v0.id();
    }, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
    public static final StreamCodec<ByteBuf, CraftingBookCategory> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, (v0) -> {
        return v0.id();
    });
    private final String name;
    private final int id;

    CraftingBookCategory(String $$0, int $$1) {
        this.name = $$0;
        this.id = $$1;
    }

    @Override // net.minecraft.util.StringRepresentable
    public String getSerializedName() {
        return this.name;
    }

    private int id() {
        return this.id;
    }
}
