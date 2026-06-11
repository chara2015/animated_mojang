package net.minecraft.world.item.crafting;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import java.util.function.IntFunction;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/crafting/CookingBookCategory.class */
public enum CookingBookCategory implements StringRepresentable {
    FOOD(0, "food"),
    BLOCKS(1, StructureTemplate.BLOCKS_TAG),
    MISC(2, "misc");

    private static final IntFunction<CookingBookCategory> BY_ID = ByIdMap.continuous($$0 -> {
        return $$0.id;
    }, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
    public static final Codec<CookingBookCategory> CODEC = StringRepresentable.fromEnum(CookingBookCategory::values);
    public static final StreamCodec<ByteBuf, CookingBookCategory> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, $$0 -> {
        return $$0.id;
    });
    private final int id;
    private final String name;

    CookingBookCategory(int $$0, String $$1) {
        this.id = $$0;
        this.name = $$1;
    }

    @Override // net.minecraft.util.StringRepresentable
    public String getSerializedName() {
        return this.name;
    }
}
