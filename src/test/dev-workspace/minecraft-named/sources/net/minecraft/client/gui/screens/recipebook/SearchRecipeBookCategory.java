package net.minecraft.client.gui.screens.recipebook;

import java.util.List;
import net.minecraft.world.item.crafting.ExtendedRecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeBookCategory;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/screens/recipebook/SearchRecipeBookCategory.class */
public enum SearchRecipeBookCategory implements ExtendedRecipeBookCategory {
    CRAFTING(RecipeBookCategories.CRAFTING_EQUIPMENT, RecipeBookCategories.CRAFTING_BUILDING_BLOCKS, RecipeBookCategories.CRAFTING_MISC, RecipeBookCategories.CRAFTING_REDSTONE),
    FURNACE(RecipeBookCategories.FURNACE_FOOD, RecipeBookCategories.FURNACE_BLOCKS, RecipeBookCategories.FURNACE_MISC),
    BLAST_FURNACE(RecipeBookCategories.BLAST_FURNACE_BLOCKS, RecipeBookCategories.BLAST_FURNACE_MISC),
    SMOKER(RecipeBookCategories.SMOKER_FOOD);

    private final List<RecipeBookCategory> includedCategories;

    SearchRecipeBookCategory(RecipeBookCategory... $$0) {
        this.includedCategories = List.of((Object[]) $$0);
    }

    public List<RecipeBookCategory> includedCategories() {
        return this.includedCategories;
    }
}
