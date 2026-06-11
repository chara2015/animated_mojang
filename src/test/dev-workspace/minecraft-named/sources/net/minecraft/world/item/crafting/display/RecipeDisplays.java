package net.minecraft.world.item.crafting.display;

import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.display.RecipeDisplay;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/crafting/display/RecipeDisplays.class */
public class RecipeDisplays {
    public static RecipeDisplay.Type<?> bootstrap(Registry<RecipeDisplay.Type<?>> $$0) {
        Registry.register($$0, "crafting_shapeless", ShapelessCraftingRecipeDisplay.TYPE);
        Registry.register($$0, "crafting_shaped", ShapedCraftingRecipeDisplay.TYPE);
        Registry.register($$0, "furnace", FurnaceRecipeDisplay.TYPE);
        Registry.register($$0, "stonecutter", StonecutterRecipeDisplay.TYPE);
        return (RecipeDisplay.Type) Registry.register($$0, "smithing", SmithingRecipeDisplay.TYPE);
    }
}
