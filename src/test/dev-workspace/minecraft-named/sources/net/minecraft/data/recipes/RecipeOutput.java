package net.minecraft.data.recipes;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/data/recipes/RecipeOutput.class */
public interface RecipeOutput {
    void accept(ResourceKey<Recipe<?>> resourceKey, Recipe<?> recipe, AdvancementHolder advancementHolder);

    Advancement.Builder advancement();

    void includeRootAdvancement();
}
