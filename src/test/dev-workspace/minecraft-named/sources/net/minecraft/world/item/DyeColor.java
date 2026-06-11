package net.minecraft.world.item;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ARGB;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Contract;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/DyeColor.class */
public enum DyeColor implements StringRepresentable {
    WHITE(0, "white", 16383998, MapColor.SNOW, 15790320, 16777215),
    ORANGE(1, "orange", 16351261, MapColor.COLOR_ORANGE, 15435844, 16738335),
    MAGENTA(2, "magenta", 13061821, MapColor.COLOR_MAGENTA, 12801229, 16711935),
    LIGHT_BLUE(3, "light_blue", 3847130, MapColor.COLOR_LIGHT_BLUE, 6719955, 10141901),
    YELLOW(4, "yellow", 16701501, MapColor.COLOR_YELLOW, 14602026, 16776960),
    LIME(5, "lime", 8439583, MapColor.COLOR_LIGHT_GREEN, 4312372, 12582656),
    PINK(6, "pink", 15961002, MapColor.COLOR_PINK, 14188952, 16738740),
    GRAY(7, "gray", 4673362, MapColor.COLOR_GRAY, 4408131, 8421504),
    LIGHT_GRAY(8, "light_gray", 10329495, MapColor.COLOR_LIGHT_GRAY, 11250603, 13882323),
    CYAN(9, "cyan", 1481884, MapColor.COLOR_CYAN, 2651799, 65535),
    PURPLE(10, "purple", 8991416, MapColor.COLOR_PURPLE, 8073150, 10494192),
    BLUE(11, "blue", 3949738, MapColor.COLOR_BLUE, 2437522, 255),
    BROWN(12, "brown", 8606770, MapColor.COLOR_BROWN, 5320730, 9127187),
    GREEN(13, "green", 6192150, MapColor.COLOR_GREEN, 3887386, 65280),
    RED(14, "red", 11546150, MapColor.COLOR_RED, 11743532, DustParticleOptions.REDSTONE_PARTICLE_COLOR),
    BLACK(15, "black", 1908001, MapColor.COLOR_BLACK, 1973019, 0);

    private static final IntFunction<DyeColor> BY_ID = ByIdMap.continuous((v0) -> {
        return v0.getId();
    }, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
    private static final Int2ObjectOpenHashMap<DyeColor> BY_FIREWORK_COLOR = new Int2ObjectOpenHashMap<>((Map) Arrays.stream(values()).collect(Collectors.toMap($$0 -> {
        return Integer.valueOf($$0.fireworkColor);
    }, $$02 -> {
        return $$02;
    })));
    public static final StringRepresentable.EnumCodec<DyeColor> CODEC = StringRepresentable.fromEnum(DyeColor::values);
    public static final StreamCodec<ByteBuf, DyeColor> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, (v0) -> {
        return v0.getId();
    });

    @Deprecated
    public static final Codec<DyeColor> LEGACY_ID_CODEC = Codec.BYTE.xmap((v0) -> {
        return byId(v0);
    }, $$0 -> {
        return Byte.valueOf((byte) $$0.id);
    });
    private final int id;
    private final String name;
    private final MapColor mapColor;
    private final int textureDiffuseColor;
    private final int fireworkColor;
    private final int textColor;

    DyeColor(int $$0, String $$1, int $$2, MapColor $$3, int $$4, int $$5) {
        this.id = $$0;
        this.name = $$1;
        this.mapColor = $$3;
        this.textColor = ARGB.opaque($$5);
        this.textureDiffuseColor = ARGB.opaque($$2);
        this.fireworkColor = $$4;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getTextureDiffuseColor() {
        return this.textureDiffuseColor;
    }

    public MapColor getMapColor() {
        return this.mapColor;
    }

    public int getFireworkColor() {
        return this.fireworkColor;
    }

    public int getTextColor() {
        return this.textColor;
    }

    public static DyeColor byId(int $$0) {
        return BY_ID.apply($$0);
    }

    @Contract("_,!null->!null;_,null->_")
    public static DyeColor byName(String $$0, DyeColor $$1) {
        DyeColor $$2 = (DyeColor) CODEC.byName($$0);
        return $$2 != null ? $$2 : $$1;
    }

    public static DyeColor byFireworkColor(int $$0) {
        return (DyeColor) BY_FIREWORK_COLOR.get($$0);
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.name;
    }

    @Override // net.minecraft.util.StringRepresentable
    public String getSerializedName() {
        return this.name;
    }

    public static DyeColor getMixedColor(ServerLevel $$0, DyeColor $$1, DyeColor $$2) {
        CraftingInput $$3 = makeCraftColorInput($$1, $$2);
        Optional map = $$0.recipeAccess().getRecipeFor(RecipeType.CRAFTING, $$3, $$0).map($$22 -> {
            return ((CraftingRecipe) $$22.value()).assemble($$3, $$0.registryAccess());
        }).map((v0) -> {
            return v0.getItem();
        });
        Class<DyeItem> cls = DyeItem.class;
        Objects.requireNonNull(DyeItem.class);
        Optional optionalFilter = map.filter((v1) -> {
            return r1.isInstance(v1);
        });
        Class<DyeItem> cls2 = DyeItem.class;
        Objects.requireNonNull(DyeItem.class);
        return (DyeColor) optionalFilter.map((v1) -> {
            return r1.cast(v1);
        }).map((v0) -> {
            return v0.getDyeColor();
        }).orElseGet(() -> {
            return $$0.random.nextBoolean() ? $$1 : $$2;
        });
    }

    private static CraftingInput makeCraftColorInput(DyeColor $$0, DyeColor $$1) {
        return CraftingInput.of(2, 1, List.of(new ItemStack(DyeItem.byColor($$0)), new ItemStack(DyeItem.byColor($$1))));
    }
}
