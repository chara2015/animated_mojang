package net.minecraft.world.item.crafting;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.SelectableRecipe;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/crafting/RecipeAccess.class */
public interface RecipeAccess {
    RecipePropertySet propertySet(ResourceKey<RecipePropertySet> resourceKey);

    SelectableRecipe.SingleInputSet<StonecutterRecipe> stonecutterRecipes();
}
