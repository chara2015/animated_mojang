package net.minecraft.world.item.crafting.display;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.display.RecipeDisplay;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/crafting/display/SmithingRecipeDisplay.class */
public final class SmithingRecipeDisplay extends Record implements RecipeDisplay {
    private final SlotDisplay template;
    private final SlotDisplay base;
    private final SlotDisplay addition;
    private final SlotDisplay result;
    private final SlotDisplay craftingStation;
    public static final MapCodec<SmithingRecipeDisplay> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(SlotDisplay.CODEC.fieldOf("template").forGetter((v0) -> {
            return v0.template();
        }), SlotDisplay.CODEC.fieldOf("base").forGetter((v0) -> {
            return v0.base();
        }), SlotDisplay.CODEC.fieldOf("addition").forGetter((v0) -> {
            return v0.addition();
        }), SlotDisplay.CODEC.fieldOf("result").forGetter((v0) -> {
            return v0.result();
        }), SlotDisplay.CODEC.fieldOf("crafting_station").forGetter((v0) -> {
            return v0.craftingStation();
        })).apply($$0, SmithingRecipeDisplay::new);
    });
    public static final StreamCodec<RegistryFriendlyByteBuf, SmithingRecipeDisplay> STREAM_CODEC = StreamCodec.composite(SlotDisplay.STREAM_CODEC, (v0) -> {
        return v0.template();
    }, SlotDisplay.STREAM_CODEC, (v0) -> {
        return v0.base();
    }, SlotDisplay.STREAM_CODEC, (v0) -> {
        return v0.addition();
    }, SlotDisplay.STREAM_CODEC, (v0) -> {
        return v0.result();
    }, SlotDisplay.STREAM_CODEC, (v0) -> {
        return v0.craftingStation();
    }, SmithingRecipeDisplay::new);
    public static final RecipeDisplay.Type<SmithingRecipeDisplay> TYPE = new RecipeDisplay.Type<>(MAP_CODEC, STREAM_CODEC);

    public SmithingRecipeDisplay(SlotDisplay $$0, SlotDisplay $$1, SlotDisplay $$2, SlotDisplay $$3, SlotDisplay $$4) {
        this.template = $$0;
        this.base = $$1;
        this.addition = $$2;
        this.result = $$3;
        this.craftingStation = $$4;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, SmithingRecipeDisplay.class), SmithingRecipeDisplay.class, "template;base;addition;result;craftingStation", "FIELD:Lnet/minecraft/world/item/crafting/display/SmithingRecipeDisplay;->template:Lnet/minecraft/world/item/crafting/display/SlotDisplay;", "FIELD:Lnet/minecraft/world/item/crafting/display/SmithingRecipeDisplay;->base:Lnet/minecraft/world/item/crafting/display/SlotDisplay;", "FIELD:Lnet/minecraft/world/item/crafting/display/SmithingRecipeDisplay;->addition:Lnet/minecraft/world/item/crafting/display/SlotDisplay;", "FIELD:Lnet/minecraft/world/item/crafting/display/SmithingRecipeDisplay;->result:Lnet/minecraft/world/item/crafting/display/SlotDisplay;", "FIELD:Lnet/minecraft/world/item/crafting/display/SmithingRecipeDisplay;->craftingStation:Lnet/minecraft/world/item/crafting/display/SlotDisplay;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, SmithingRecipeDisplay.class), SmithingRecipeDisplay.class, "template;base;addition;result;craftingStation", "FIELD:Lnet/minecraft/world/item/crafting/display/SmithingRecipeDisplay;->template:Lnet/minecraft/world/item/crafting/display/SlotDisplay;", "FIELD:Lnet/minecraft/world/item/crafting/display/SmithingRecipeDisplay;->base:Lnet/minecraft/world/item/crafting/display/SlotDisplay;", "FIELD:Lnet/minecraft/world/item/crafting/display/SmithingRecipeDisplay;->addition:Lnet/minecraft/world/item/crafting/display/SlotDisplay;", "FIELD:Lnet/minecraft/world/item/crafting/display/SmithingRecipeDisplay;->result:Lnet/minecraft/world/item/crafting/display/SlotDisplay;", "FIELD:Lnet/minecraft/world/item/crafting/display/SmithingRecipeDisplay;->craftingStation:Lnet/minecraft/world/item/crafting/display/SlotDisplay;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, SmithingRecipeDisplay.class, Object.class), SmithingRecipeDisplay.class, "template;base;addition;result;craftingStation", "FIELD:Lnet/minecraft/world/item/crafting/display/SmithingRecipeDisplay;->template:Lnet/minecraft/world/item/crafting/display/SlotDisplay;", "FIELD:Lnet/minecraft/world/item/crafting/display/SmithingRecipeDisplay;->base:Lnet/minecraft/world/item/crafting/display/SlotDisplay;", "FIELD:Lnet/minecraft/world/item/crafting/display/SmithingRecipeDisplay;->addition:Lnet/minecraft/world/item/crafting/display/SlotDisplay;", "FIELD:Lnet/minecraft/world/item/crafting/display/SmithingRecipeDisplay;->result:Lnet/minecraft/world/item/crafting/display/SlotDisplay;", "FIELD:Lnet/minecraft/world/item/crafting/display/SmithingRecipeDisplay;->craftingStation:Lnet/minecraft/world/item/crafting/display/SlotDisplay;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public SlotDisplay template() {
        return this.template;
    }

    public SlotDisplay base() {
        return this.base;
    }

    public SlotDisplay addition() {
        return this.addition;
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
    public RecipeDisplay.Type<SmithingRecipeDisplay> type() {
        return TYPE;
    }
}
