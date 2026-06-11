package net.minecraft.world.item.crafting;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.Optional;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.display.SlotDisplay;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/crafting/SelectableRecipe.class */
public final class SelectableRecipe<T extends Recipe<?>> extends Record {
    private final SlotDisplay optionDisplay;
    private final Optional<RecipeHolder<T>> recipe;

    public SelectableRecipe(SlotDisplay $$0, Optional<RecipeHolder<T>> $$1) {
        this.optionDisplay = $$0;
        this.recipe = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, SelectableRecipe.class), SelectableRecipe.class, "optionDisplay;recipe", "FIELD:Lnet/minecraft/world/item/crafting/SelectableRecipe;->optionDisplay:Lnet/minecraft/world/item/crafting/display/SlotDisplay;", "FIELD:Lnet/minecraft/world/item/crafting/SelectableRecipe;->recipe:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, SelectableRecipe.class), SelectableRecipe.class, "optionDisplay;recipe", "FIELD:Lnet/minecraft/world/item/crafting/SelectableRecipe;->optionDisplay:Lnet/minecraft/world/item/crafting/display/SlotDisplay;", "FIELD:Lnet/minecraft/world/item/crafting/SelectableRecipe;->recipe:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, SelectableRecipe.class, Object.class), SelectableRecipe.class, "optionDisplay;recipe", "FIELD:Lnet/minecraft/world/item/crafting/SelectableRecipe;->optionDisplay:Lnet/minecraft/world/item/crafting/display/SlotDisplay;", "FIELD:Lnet/minecraft/world/item/crafting/SelectableRecipe;->recipe:Ljava/util/Optional;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public SlotDisplay optionDisplay() {
        return this.optionDisplay;
    }

    public Optional<RecipeHolder<T>> recipe() {
        return this.recipe;
    }

    public static <T extends Recipe<?>> StreamCodec<RegistryFriendlyByteBuf, SelectableRecipe<T>> noRecipeCodec() {
        return StreamCodec.composite(SlotDisplay.STREAM_CODEC, (v0) -> {
            return v0.optionDisplay();
        }, $$0 -> {
            return new SelectableRecipe($$0, Optional.empty());
        });
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/crafting/SelectableRecipe$SingleInputEntry.class */
    public static final class SingleInputEntry<T extends Recipe<?>> extends Record {
        private final Ingredient input;
        private final SelectableRecipe<T> recipe;

        public SingleInputEntry(Ingredient $$0, SelectableRecipe<T> $$1) {
            this.input = $$0;
            this.recipe = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, SingleInputEntry.class), SingleInputEntry.class, "input;recipe", "FIELD:Lnet/minecraft/world/item/crafting/SelectableRecipe$SingleInputEntry;->input:Lnet/minecraft/world/item/crafting/Ingredient;", "FIELD:Lnet/minecraft/world/item/crafting/SelectableRecipe$SingleInputEntry;->recipe:Lnet/minecraft/world/item/crafting/SelectableRecipe;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, SingleInputEntry.class), SingleInputEntry.class, "input;recipe", "FIELD:Lnet/minecraft/world/item/crafting/SelectableRecipe$SingleInputEntry;->input:Lnet/minecraft/world/item/crafting/Ingredient;", "FIELD:Lnet/minecraft/world/item/crafting/SelectableRecipe$SingleInputEntry;->recipe:Lnet/minecraft/world/item/crafting/SelectableRecipe;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, SingleInputEntry.class, Object.class), SingleInputEntry.class, "input;recipe", "FIELD:Lnet/minecraft/world/item/crafting/SelectableRecipe$SingleInputEntry;->input:Lnet/minecraft/world/item/crafting/Ingredient;", "FIELD:Lnet/minecraft/world/item/crafting/SelectableRecipe$SingleInputEntry;->recipe:Lnet/minecraft/world/item/crafting/SelectableRecipe;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Ingredient input() {
            return this.input;
        }

        public SelectableRecipe<T> recipe() {
            return this.recipe;
        }

        public static <T extends Recipe<?>> StreamCodec<RegistryFriendlyByteBuf, SingleInputEntry<T>> noRecipeCodec() {
            return StreamCodec.composite(Ingredient.CONTENTS_STREAM_CODEC, (v0) -> {
                return v0.input();
            }, SelectableRecipe.noRecipeCodec(), (v0) -> {
                return v0.recipe();
            }, SingleInputEntry::new);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/crafting/SelectableRecipe$SingleInputSet.class */
    public static final class SingleInputSet<T extends Recipe<?>> extends Record {
        private final List<SingleInputEntry<T>> entries;

        public SingleInputSet(List<SingleInputEntry<T>> $$0) {
            this.entries = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, SingleInputSet.class), SingleInputSet.class, "entries", "FIELD:Lnet/minecraft/world/item/crafting/SelectableRecipe$SingleInputSet;->entries:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, SingleInputSet.class), SingleInputSet.class, "entries", "FIELD:Lnet/minecraft/world/item/crafting/SelectableRecipe$SingleInputSet;->entries:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, SingleInputSet.class, Object.class), SingleInputSet.class, "entries", "FIELD:Lnet/minecraft/world/item/crafting/SelectableRecipe$SingleInputSet;->entries:Ljava/util/List;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public List<SingleInputEntry<T>> entries() {
            return this.entries;
        }

        public static <T extends Recipe<?>> SingleInputSet<T> empty() {
            return new SingleInputSet<>(List.of());
        }

        public static <T extends Recipe<?>> StreamCodec<RegistryFriendlyByteBuf, SingleInputSet<T>> noRecipeCodec() {
            return StreamCodec.composite(SingleInputEntry.noRecipeCodec().apply(ByteBufCodecs.list()), (v0) -> {
                return v0.entries();
            }, SingleInputSet::new);
        }

        public boolean acceptsInput(ItemStack $$0) {
            return this.entries.stream().anyMatch($$1 -> {
                return $$1.input.test($$0);
            });
        }

        public SingleInputSet<T> selectByInput(ItemStack $$0) {
            return new SingleInputSet<>(this.entries.stream().filter($$1 -> {
                return $$1.input.test($$0);
            }).toList());
        }

        public boolean isEmpty() {
            return this.entries.isEmpty();
        }

        public int size() {
            return this.entries.size();
        }
    }
}
