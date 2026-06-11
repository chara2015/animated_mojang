package net.minecraft.world.item.crafting.display;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.flag.FeatureFlagSet;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/crafting/display/RecipeDisplay.class */
public interface RecipeDisplay {
    public static final Codec<RecipeDisplay> CODEC = BuiltInRegistries.RECIPE_DISPLAY.byNameCodec().dispatch((v0) -> {
        return v0.type();
    }, (v0) -> {
        return v0.codec();
    });
    public static final StreamCodec<RegistryFriendlyByteBuf, RecipeDisplay> STREAM_CODEC = ByteBufCodecs.registry(Registries.RECIPE_DISPLAY).dispatch((v0) -> {
        return v0.type();
    }, (v0) -> {
        return v0.streamCodec();
    });

    SlotDisplay result();

    SlotDisplay craftingStation();

    Type<? extends RecipeDisplay> type();

    default boolean isEnabled(FeatureFlagSet $$0) {
        return result().isEnabled($$0) && craftingStation().isEnabled($$0);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/crafting/display/RecipeDisplay$Type.class */
    public static final class Type<T extends RecipeDisplay> extends Record {
        private final MapCodec<T> codec;
        private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

        public Type(MapCodec<T> $$0, StreamCodec<RegistryFriendlyByteBuf, T> $$1) {
            this.codec = $$0;
            this.streamCodec = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Type.class), Type.class, "codec;streamCodec", "FIELD:Lnet/minecraft/world/item/crafting/display/RecipeDisplay$Type;->codec:Lcom/mojang/serialization/MapCodec;", "FIELD:Lnet/minecraft/world/item/crafting/display/RecipeDisplay$Type;->streamCodec:Lnet/minecraft/network/codec/StreamCodec;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Type.class), Type.class, "codec;streamCodec", "FIELD:Lnet/minecraft/world/item/crafting/display/RecipeDisplay$Type;->codec:Lcom/mojang/serialization/MapCodec;", "FIELD:Lnet/minecraft/world/item/crafting/display/RecipeDisplay$Type;->streamCodec:Lnet/minecraft/network/codec/StreamCodec;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Type.class, Object.class), Type.class, "codec;streamCodec", "FIELD:Lnet/minecraft/world/item/crafting/display/RecipeDisplay$Type;->codec:Lcom/mojang/serialization/MapCodec;", "FIELD:Lnet/minecraft/world/item/crafting/display/RecipeDisplay$Type;->streamCodec:Lnet/minecraft/network/codec/StreamCodec;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public MapCodec<T> codec() {
            return this.codec;
        }

        public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
            return this.streamCodec;
        }
    }
}
