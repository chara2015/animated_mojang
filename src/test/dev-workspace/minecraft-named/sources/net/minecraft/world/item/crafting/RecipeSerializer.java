package net.minecraft.world.item.crafting;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import net.minecraft.world.item.crafting.SmithingTrimRecipe;
import net.minecraft.world.item.crafting.TransmuteRecipe;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/crafting/RecipeSerializer.class */
public interface RecipeSerializer<T extends Recipe<?>> {
    public static final RecipeSerializer<ShapedRecipe> SHAPED_RECIPE = register("crafting_shaped", new ShapedRecipe.Serializer());
    public static final RecipeSerializer<ShapelessRecipe> SHAPELESS_RECIPE = register("crafting_shapeless", new ShapelessRecipe.Serializer());
    public static final RecipeSerializer<ArmorDyeRecipe> ARMOR_DYE = register("crafting_special_armordye", new CustomRecipe.Serializer(ArmorDyeRecipe::new));
    public static final RecipeSerializer<BookCloningRecipe> BOOK_CLONING = register("crafting_special_bookcloning", new CustomRecipe.Serializer(BookCloningRecipe::new));
    public static final RecipeSerializer<MapCloningRecipe> MAP_CLONING = register("crafting_special_mapcloning", new CustomRecipe.Serializer(MapCloningRecipe::new));
    public static final RecipeSerializer<MapExtendingRecipe> MAP_EXTENDING = register("crafting_special_mapextending", new CustomRecipe.Serializer(MapExtendingRecipe::new));
    public static final RecipeSerializer<FireworkRocketRecipe> FIREWORK_ROCKET = register("crafting_special_firework_rocket", new CustomRecipe.Serializer(FireworkRocketRecipe::new));
    public static final RecipeSerializer<FireworkStarRecipe> FIREWORK_STAR = register("crafting_special_firework_star", new CustomRecipe.Serializer(FireworkStarRecipe::new));
    public static final RecipeSerializer<FireworkStarFadeRecipe> FIREWORK_STAR_FADE = register("crafting_special_firework_star_fade", new CustomRecipe.Serializer(FireworkStarFadeRecipe::new));
    public static final RecipeSerializer<TippedArrowRecipe> TIPPED_ARROW = register("crafting_special_tippedarrow", new CustomRecipe.Serializer(TippedArrowRecipe::new));
    public static final RecipeSerializer<BannerDuplicateRecipe> BANNER_DUPLICATE = register("crafting_special_bannerduplicate", new CustomRecipe.Serializer(BannerDuplicateRecipe::new));
    public static final RecipeSerializer<ShieldDecorationRecipe> SHIELD_DECORATION = register("crafting_special_shielddecoration", new CustomRecipe.Serializer(ShieldDecorationRecipe::new));
    public static final RecipeSerializer<TransmuteRecipe> TRANSMUTE = register("crafting_transmute", new TransmuteRecipe.Serializer());
    public static final RecipeSerializer<RepairItemRecipe> REPAIR_ITEM = register("crafting_special_repairitem", new CustomRecipe.Serializer(RepairItemRecipe::new));
    public static final RecipeSerializer<SmeltingRecipe> SMELTING_RECIPE = register("smelting", new AbstractCookingRecipe.Serializer(SmeltingRecipe::new, 200));
    public static final RecipeSerializer<BlastingRecipe> BLASTING_RECIPE = register("blasting", new AbstractCookingRecipe.Serializer(BlastingRecipe::new, 100));
    public static final RecipeSerializer<SmokingRecipe> SMOKING_RECIPE = register("smoking", new AbstractCookingRecipe.Serializer(SmokingRecipe::new, 100));
    public static final RecipeSerializer<CampfireCookingRecipe> CAMPFIRE_COOKING_RECIPE = register("campfire_cooking", new AbstractCookingRecipe.Serializer(CampfireCookingRecipe::new, 100));
    public static final RecipeSerializer<StonecutterRecipe> STONECUTTER = register("stonecutting", new SingleItemRecipe.Serializer(StonecutterRecipe::new));
    public static final RecipeSerializer<SmithingTransformRecipe> SMITHING_TRANSFORM = register("smithing_transform", new SmithingTransformRecipe.Serializer());
    public static final RecipeSerializer<SmithingTrimRecipe> SMITHING_TRIM = register("smithing_trim", new SmithingTrimRecipe.Serializer());
    public static final RecipeSerializer<DecoratedPotRecipe> DECORATED_POT_RECIPE = register("crafting_decorated_pot", new CustomRecipe.Serializer(DecoratedPotRecipe::new));

    MapCodec<T> codec();

    @Deprecated
    StreamCodec<RegistryFriendlyByteBuf, T> streamCodec();

    static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String $$0, S $$1) {
        return (S) Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, $$0, $$1);
    }
}
