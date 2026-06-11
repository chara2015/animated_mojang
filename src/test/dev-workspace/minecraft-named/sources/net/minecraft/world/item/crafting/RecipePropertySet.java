package net.minecraft.world.item.crafting;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/crafting/RecipePropertySet.class */
public class RecipePropertySet {
    public static final ResourceKey<? extends Registry<RecipePropertySet>> TYPE_KEY = ResourceKey.createRegistryKey(Identifier.withDefaultNamespace("recipe_property_set"));
    public static final ResourceKey<RecipePropertySet> SMITHING_BASE = registerVanilla("smithing_base");
    public static final ResourceKey<RecipePropertySet> SMITHING_TEMPLATE = registerVanilla("smithing_template");
    public static final ResourceKey<RecipePropertySet> SMITHING_ADDITION = registerVanilla("smithing_addition");
    public static final ResourceKey<RecipePropertySet> FURNACE_INPUT = registerVanilla("furnace_input");
    public static final ResourceKey<RecipePropertySet> BLAST_FURNACE_INPUT = registerVanilla("blast_furnace_input");
    public static final ResourceKey<RecipePropertySet> SMOKER_INPUT = registerVanilla("smoker_input");
    public static final ResourceKey<RecipePropertySet> CAMPFIRE_INPUT = registerVanilla("campfire_input");
    public static final StreamCodec<RegistryFriendlyByteBuf, RecipePropertySet> STREAM_CODEC = Item.STREAM_CODEC.apply(ByteBufCodecs.list()).map($$0 -> {
        return new RecipePropertySet(Set.copyOf($$0));
    }, $$02 -> {
        return List.copyOf($$02.items);
    });
    public static final RecipePropertySet EMPTY = new RecipePropertySet(Set.of());
    private final Set<Holder<Item>> items;

    private RecipePropertySet(Set<Holder<Item>> $$0) {
        this.items = $$0;
    }

    private static ResourceKey<RecipePropertySet> registerVanilla(String $$0) {
        return ResourceKey.create(TYPE_KEY, Identifier.withDefaultNamespace($$0));
    }

    public boolean test(ItemStack $$0) {
        return this.items.contains($$0.getItemHolder());
    }

    static RecipePropertySet create(Collection<Ingredient> $$0) {
        Set<Holder<Item>> $$1 = (Set) $$0.stream().flatMap((v0) -> {
            return v0.items();
        }).collect(Collectors.toUnmodifiableSet());
        return new RecipePropertySet($$1);
    }
}
