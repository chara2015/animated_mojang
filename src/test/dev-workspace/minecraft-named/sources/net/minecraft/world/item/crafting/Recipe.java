package net.minecraft.world.item.crafting;

import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.level.Level;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/crafting/Recipe.class */
public interface Recipe<T extends RecipeInput> {
    public static final Codec<Recipe<?>> CODEC = BuiltInRegistries.RECIPE_SERIALIZER.byNameCodec().dispatch((v0) -> {
        return v0.getSerializer();
    }, (v0) -> {
        return v0.codec();
    });
    public static final Codec<ResourceKey<Recipe<?>>> KEY_CODEC = ResourceKey.codec(Registries.RECIPE);
    public static final StreamCodec<RegistryFriendlyByteBuf, Recipe<?>> STREAM_CODEC = ByteBufCodecs.registry(Registries.RECIPE_SERIALIZER).dispatch((v0) -> {
        return v0.getSerializer();
    }, (v0) -> {
        return v0.streamCodec();
    });

    boolean matches(T t, Level level);

    ItemStack assemble(T t, HolderLookup.Provider provider);

    RecipeSerializer<? extends Recipe<T>> getSerializer();

    RecipeType<? extends Recipe<T>> getType();

    PlacementInfo placementInfo();

    RecipeBookCategory recipeBookCategory();

    default boolean isSpecial() {
        return false;
    }

    default boolean showNotification() {
        return true;
    }

    default String group() {
        return "";
    }

    default List<RecipeDisplay> display() {
        return List.of();
    }
}
