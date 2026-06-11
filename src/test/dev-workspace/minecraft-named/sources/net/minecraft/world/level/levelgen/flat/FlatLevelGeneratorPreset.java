package net.minecraft.world.level.levelgen.flat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.item.Item;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/flat/FlatLevelGeneratorPreset.class */
public final class FlatLevelGeneratorPreset extends Record {
    private final Holder<Item> displayItem;
    private final FlatLevelGeneratorSettings settings;
    public static final Codec<FlatLevelGeneratorPreset> DIRECT_CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(Item.CODEC.fieldOf("display").forGetter($$0 -> {
            return $$0.displayItem;
        }), FlatLevelGeneratorSettings.CODEC.fieldOf("settings").forGetter($$02 -> {
            return $$02.settings;
        })).apply($$0, FlatLevelGeneratorPreset::new);
    });
    public static final Codec<Holder<FlatLevelGeneratorPreset>> CODEC = RegistryFileCodec.create(Registries.FLAT_LEVEL_GENERATOR_PRESET, DIRECT_CODEC);

    public FlatLevelGeneratorPreset(Holder<Item> $$0, FlatLevelGeneratorSettings $$1) {
        this.displayItem = $$0;
        this.settings = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, FlatLevelGeneratorPreset.class), FlatLevelGeneratorPreset.class, "displayItem;settings", "FIELD:Lnet/minecraft/world/level/levelgen/flat/FlatLevelGeneratorPreset;->displayItem:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/world/level/levelgen/flat/FlatLevelGeneratorPreset;->settings:Lnet/minecraft/world/level/levelgen/flat/FlatLevelGeneratorSettings;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, FlatLevelGeneratorPreset.class), FlatLevelGeneratorPreset.class, "displayItem;settings", "FIELD:Lnet/minecraft/world/level/levelgen/flat/FlatLevelGeneratorPreset;->displayItem:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/world/level/levelgen/flat/FlatLevelGeneratorPreset;->settings:Lnet/minecraft/world/level/levelgen/flat/FlatLevelGeneratorSettings;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, FlatLevelGeneratorPreset.class, Object.class), FlatLevelGeneratorPreset.class, "displayItem;settings", "FIELD:Lnet/minecraft/world/level/levelgen/flat/FlatLevelGeneratorPreset;->displayItem:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/world/level/levelgen/flat/FlatLevelGeneratorPreset;->settings:Lnet/minecraft/world/level/levelgen/flat/FlatLevelGeneratorSettings;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Holder<Item> displayItem() {
        return this.displayItem;
    }

    public FlatLevelGeneratorSettings settings() {
        return this.settings;
    }
}
