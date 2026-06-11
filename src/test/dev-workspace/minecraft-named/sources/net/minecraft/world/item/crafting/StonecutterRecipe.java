package net.minecraft.world.item.crafting;

import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.item.crafting.display.StonecutterRecipeDisplay;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/crafting/StonecutterRecipe.class */
public class StonecutterRecipe extends SingleItemRecipe {
    public StonecutterRecipe(String $$0, Ingredient $$1, ItemStack $$2) {
        super($$0, $$1, $$2);
    }

    @Override // net.minecraft.world.item.crafting.SingleItemRecipe, net.minecraft.world.item.crafting.Recipe
    public RecipeType<StonecutterRecipe> getType() {
        return RecipeType.STONECUTTING;
    }

    @Override // net.minecraft.world.item.crafting.SingleItemRecipe, net.minecraft.world.item.crafting.Recipe
    public RecipeSerializer<StonecutterRecipe> getSerializer() {
        return RecipeSerializer.STONECUTTER;
    }

    @Override // net.minecraft.world.item.crafting.Recipe
    public List<RecipeDisplay> display() {
        return List.of(new StonecutterRecipeDisplay(input().display(), resultDisplay(), new SlotDisplay.ItemSlotDisplay(Items.STONECUTTER)));
    }

    public SlotDisplay resultDisplay() {
        return new SlotDisplay.ItemStackSlotDisplay(result());
    }

    @Override // net.minecraft.world.item.crafting.Recipe
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.STONECUTTER;
    }
}
