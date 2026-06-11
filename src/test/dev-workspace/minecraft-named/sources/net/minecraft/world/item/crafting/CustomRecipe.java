package net.minecraft.world.item.crafting;

import com.mojang.datafixers.Products;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import java.util.Objects;
import java.util.function.Function;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/crafting/CustomRecipe.class */
public abstract class CustomRecipe implements CraftingRecipe {
    private final CraftingBookCategory category;

    @Override // net.minecraft.world.item.crafting.CraftingRecipe, net.minecraft.world.item.crafting.Recipe
    public abstract RecipeSerializer<? extends CustomRecipe> getSerializer();

    public CustomRecipe(CraftingBookCategory $$0) {
        this.category = $$0;
    }

    @Override // net.minecraft.world.item.crafting.Recipe
    public boolean isSpecial() {
        return true;
    }

    @Override // net.minecraft.world.item.crafting.CraftingRecipe
    public CraftingBookCategory category() {
        return this.category;
    }

    @Override // net.minecraft.world.item.crafting.Recipe
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/crafting/CustomRecipe$Serializer.class */
    public static class Serializer<T extends CraftingRecipe> implements RecipeSerializer<T> {
        private final MapCodec<T> codec;
        private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

        /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/crafting/CustomRecipe$Serializer$Factory.class */
        @FunctionalInterface
        public interface Factory<T extends CraftingRecipe> {
            T create(CraftingBookCategory craftingBookCategory);
        }

        public Serializer(Factory<T> $$0) {
            this.codec = RecordCodecBuilder.mapCodec($$1 -> {
                Products.P1 p1Group = $$1.group(CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter((v0) -> {
                    return v0.category();
                }));
                Objects.requireNonNull($$0);
                return p1Group.apply($$1, $$0::create);
            });
            StreamCodec<ByteBuf, CraftingBookCategory> streamCodec = CraftingBookCategory.STREAM_CODEC;
            Function function = (v0) -> {
                return v0.category();
            };
            Objects.requireNonNull($$0);
            this.streamCodec = StreamCodec.composite(streamCodec, function, $$0::create);
        }

        @Override // net.minecraft.world.item.crafting.RecipeSerializer
        public MapCodec<T> codec() {
            return this.codec;
        }

        @Override // net.minecraft.world.item.crafting.RecipeSerializer
        public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
            return this.streamCodec;
        }
    }
}
