package net.minecraft.world.item.crafting.display;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.display.RecipeDisplay;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/crafting/display/StonecutterRecipeDisplay.class */
public final class StonecutterRecipeDisplay extends Record implements RecipeDisplay {
    private final SlotDisplay input;
    private final SlotDisplay result;
    private final SlotDisplay craftingStation;
    public static final MapCodec<StonecutterRecipeDisplay> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(SlotDisplay.CODEC.fieldOf("input").forGetter((v0) -> {
            return v0.input();
        }), SlotDisplay.CODEC.fieldOf("result").forGetter((v0) -> {
            return v0.result();
        }), SlotDisplay.CODEC.fieldOf("crafting_station").forGetter((v0) -> {
            return v0.craftingStation();
        })).apply($$0, StonecutterRecipeDisplay::new);
    });
    public static final StreamCodec<RegistryFriendlyByteBuf, StonecutterRecipeDisplay> STREAM_CODEC = StreamCodec.composite(SlotDisplay.STREAM_CODEC, (v0) -> {
        return v0.input();
    }, SlotDisplay.STREAM_CODEC, (v0) -> {
        return v0.result();
    }, SlotDisplay.STREAM_CODEC, (v0) -> {
        return v0.craftingStation();
    }, StonecutterRecipeDisplay::new);
    public static final RecipeDisplay.Type<StonecutterRecipeDisplay> TYPE = new RecipeDisplay.Type<>(MAP_CODEC, STREAM_CODEC);

    public StonecutterRecipeDisplay(SlotDisplay $$0, SlotDisplay $$1, SlotDisplay $$2) {
        this.input = $$0;
        this.result = $$1;
        this.craftingStation = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, StonecutterRecipeDisplay.class), StonecutterRecipeDisplay.class, "input;result;craftingStation", "FIELD:Lnet/minecraft/world/item/crafting/display/StonecutterRecipeDisplay;->input:Lnet/minecraft/world/item/crafting/display/SlotDisplay;", "FIELD:Lnet/minecraft/world/item/crafting/display/StonecutterRecipeDisplay;->result:Lnet/minecraft/world/item/crafting/display/SlotDisplay;", "FIELD:Lnet/minecraft/world/item/crafting/display/StonecutterRecipeDisplay;->craftingStation:Lnet/minecraft/world/item/crafting/display/SlotDisplay;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, StonecutterRecipeDisplay.class), StonecutterRecipeDisplay.class, "input;result;craftingStation", "FIELD:Lnet/minecraft/world/item/crafting/display/StonecutterRecipeDisplay;->input:Lnet/minecraft/world/item/crafting/display/SlotDisplay;", "FIELD:Lnet/minecraft/world/item/crafting/display/StonecutterRecipeDisplay;->result:Lnet/minecraft/world/item/crafting/display/SlotDisplay;", "FIELD:Lnet/minecraft/world/item/crafting/display/StonecutterRecipeDisplay;->craftingStation:Lnet/minecraft/world/item/crafting/display/SlotDisplay;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, StonecutterRecipeDisplay.class, Object.class), StonecutterRecipeDisplay.class, "input;result;craftingStation", "FIELD:Lnet/minecraft/world/item/crafting/display/StonecutterRecipeDisplay;->input:Lnet/minecraft/world/item/crafting/display/SlotDisplay;", "FIELD:Lnet/minecraft/world/item/crafting/display/StonecutterRecipeDisplay;->result:Lnet/minecraft/world/item/crafting/display/SlotDisplay;", "FIELD:Lnet/minecraft/world/item/crafting/display/StonecutterRecipeDisplay;->craftingStation:Lnet/minecraft/world/item/crafting/display/SlotDisplay;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public SlotDisplay input() {
        return this.input;
    }

    @Override // net.minecraft.world.item.crafting.display.RecipeDisplay
    public SlotDisplay result() {
        return this.result;
    }

    @Override // net.minecraft.world.item.crafting.display.RecipeDisplay
    public SlotDisplay craftingStation() {
        return this.craftingStation;
    }

    @Override // net.minecraft.world.item.crafting.display.RecipeDisplay
    public RecipeDisplay.Type<StonecutterRecipeDisplay> type() {
        return TYPE;
    }
}
