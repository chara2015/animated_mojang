package net.minecraft.world.item.crafting;

import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.HolderSetCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.ItemLike;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/crafting/Ingredient.class */
public final class Ingredient implements StackedContents.IngredientInfo<Holder<Item>>, Predicate<ItemStack> {
    public static final StreamCodec<RegistryFriendlyByteBuf, Ingredient> CONTENTS_STREAM_CODEC = ByteBufCodecs.holderSet(Registries.ITEM).map(Ingredient::new, $$0 -> {
        return $$0.values;
    });
    public static final StreamCodec<RegistryFriendlyByteBuf, Optional<Ingredient>> OPTIONAL_CONTENTS_STREAM_CODEC = ByteBufCodecs.holderSet(Registries.ITEM).map($$0 -> {
        return $$0.size() == 0 ? Optional.empty() : Optional.of(new Ingredient($$0));
    }, $$02 -> {
        return (HolderSet) $$02.map($$02 -> {
            return $$02.values;
        }).orElse(HolderSet.direct(new Holder[0]));
    });
    public static final Codec<HolderSet<Item>> NON_AIR_HOLDER_SET_CODEC = HolderSetCodec.create(Registries.ITEM, Item.CODEC, false);
    public static final Codec<Ingredient> CODEC = ExtraCodecs.nonEmptyHolderSet(NON_AIR_HOLDER_SET_CODEC).xmap(Ingredient::new, $$0 -> {
        return $$0.values;
    });
    private final HolderSet<Item> values;

    private Ingredient(HolderSet<Item> $$0) {
        $$0.unwrap().ifRight($$02 -> {
            if ($$02.isEmpty()) {
                throw new UnsupportedOperationException("Ingredients can't be empty");
            }
            if ($$02.contains(Items.AIR.builtInRegistryHolder())) {
                throw new UnsupportedOperationException("Ingredient can't contain air");
            }
        });
        this.values = $$0;
    }

    public static boolean testOptionalIngredient(Optional<Ingredient> $$0, ItemStack $$1) {
        Optional<U> map = $$0.map($$12 -> {
            return Boolean.valueOf($$12.test($$1));
        });
        Objects.requireNonNull($$1);
        return ((Boolean) map.orElseGet($$1::isEmpty)).booleanValue();
    }

    @Deprecated
    public Stream<Holder<Item>> items() {
        return this.values.stream();
    }

    public boolean isEmpty() {
        return this.values.size() == 0;
    }

    @Override // java.util.function.Predicate
    public boolean test(ItemStack $$0) {
        return $$0.is(this.values);
    }

    @Override // net.minecraft.world.entity.player.StackedContents.IngredientInfo
    public boolean acceptsItem(Holder<Item> $$0) {
        return this.values.contains($$0);
    }

    public boolean equals(Object $$0) {
        if ($$0 instanceof Ingredient) {
            Ingredient $$1 = (Ingredient) $$0;
            return Objects.equals(this.values, $$1.values);
        }
        return false;
    }

    public static Ingredient of(ItemLike $$0) {
        return new Ingredient(HolderSet.direct($$0.asItem().builtInRegistryHolder()));
    }

    public static Ingredient of(ItemLike... $$0) {
        return of((Stream<? extends ItemLike>) Arrays.stream($$0));
    }

    public static Ingredient of(Stream<? extends ItemLike> $$0) {
        return new Ingredient(HolderSet.direct($$0.map($$02 -> {
            return $$02.asItem().builtInRegistryHolder();
        }).toList()));
    }

    public static Ingredient of(HolderSet<Item> $$0) {
        return new Ingredient($$0);
    }

    public SlotDisplay display() {
        return (SlotDisplay) this.values.unwrap().map(SlotDisplay.TagSlotDisplay::new, $$0 -> {
            return new SlotDisplay.Composite($$0.stream().map(Ingredient::displayForSingleItem).toList());
        });
    }

    public static SlotDisplay optionalIngredientToDisplay(Optional<Ingredient> $$0) {
        return (SlotDisplay) $$0.map((v0) -> {
            return v0.display();
        }).orElse(SlotDisplay.Empty.INSTANCE);
    }

    private static SlotDisplay displayForSingleItem(Holder<Item> $$0) {
        SlotDisplay $$1 = new SlotDisplay.ItemSlotDisplay($$0);
        ItemStack $$2 = $$0.value().getCraftingRemainder();
        if (!$$2.isEmpty()) {
            SlotDisplay $$3 = new SlotDisplay.ItemStackSlotDisplay($$2);
            return new SlotDisplay.WithRemainder($$1, $$3);
        }
        return $$1;
    }
}
