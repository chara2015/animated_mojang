package net.minecraft.world.item.equipment.trim;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Map;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/equipment/trim/MaterialAssetGroup.class */
public final class MaterialAssetGroup extends Record {
    private final AssetInfo base;
    private final Map<ResourceKey<EquipmentAsset>, AssetInfo> overrides;
    public static final String SEPARATOR = "_";
    public static final MapCodec<MaterialAssetGroup> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(AssetInfo.CODEC.fieldOf("asset_name").forGetter((v0) -> {
            return v0.base();
        }), Codec.unboundedMap(ResourceKey.codec(EquipmentAssets.ROOT_ID), AssetInfo.CODEC).optionalFieldOf("override_armor_assets", Map.of()).forGetter((v0) -> {
            return v0.overrides();
        })).apply($$0, MaterialAssetGroup::new);
    });
    public static final StreamCodec<ByteBuf, MaterialAssetGroup> STREAM_CODEC = StreamCodec.composite(AssetInfo.STREAM_CODEC, (v0) -> {
        return v0.base();
    }, ByteBufCodecs.map(Object2ObjectOpenHashMap::new, ResourceKey.streamCodec(EquipmentAssets.ROOT_ID), AssetInfo.STREAM_CODEC), (v0) -> {
        return v0.overrides();
    }, MaterialAssetGroup::new);
    public static final MaterialAssetGroup QUARTZ = create("quartz");
    public static final MaterialAssetGroup IRON = create("iron", Map.of(EquipmentAssets.IRON, "iron_darker"));
    public static final MaterialAssetGroup NETHERITE = create("netherite", Map.of(EquipmentAssets.NETHERITE, "netherite_darker"));
    public static final MaterialAssetGroup REDSTONE = create("redstone");
    public static final MaterialAssetGroup COPPER = create("copper", Map.of(EquipmentAssets.COPPER, "copper_darker"));
    public static final MaterialAssetGroup GOLD = create("gold", Map.of(EquipmentAssets.GOLD, "gold_darker"));
    public static final MaterialAssetGroup EMERALD = create("emerald");
    public static final MaterialAssetGroup DIAMOND = create("diamond", Map.of(EquipmentAssets.DIAMOND, "diamond_darker"));
    public static final MaterialAssetGroup LAPIS = create("lapis");
    public static final MaterialAssetGroup AMETHYST = create("amethyst");
    public static final MaterialAssetGroup RESIN = create("resin");

    public MaterialAssetGroup(AssetInfo $$0, Map<ResourceKey<EquipmentAsset>, AssetInfo> $$1) {
        this.base = $$0;
        this.overrides = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, MaterialAssetGroup.class), MaterialAssetGroup.class, "base;overrides", "FIELD:Lnet/minecraft/world/item/equipment/trim/MaterialAssetGroup;->base:Lnet/minecraft/world/item/equipment/trim/MaterialAssetGroup$AssetInfo;", "FIELD:Lnet/minecraft/world/item/equipment/trim/MaterialAssetGroup;->overrides:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, MaterialAssetGroup.class), MaterialAssetGroup.class, "base;overrides", "FIELD:Lnet/minecraft/world/item/equipment/trim/MaterialAssetGroup;->base:Lnet/minecraft/world/item/equipment/trim/MaterialAssetGroup$AssetInfo;", "FIELD:Lnet/minecraft/world/item/equipment/trim/MaterialAssetGroup;->overrides:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, MaterialAssetGroup.class, Object.class), MaterialAssetGroup.class, "base;overrides", "FIELD:Lnet/minecraft/world/item/equipment/trim/MaterialAssetGroup;->base:Lnet/minecraft/world/item/equipment/trim/MaterialAssetGroup$AssetInfo;", "FIELD:Lnet/minecraft/world/item/equipment/trim/MaterialAssetGroup;->overrides:Ljava/util/Map;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public AssetInfo base() {
        return this.base;
    }

    public Map<ResourceKey<EquipmentAsset>, AssetInfo> overrides() {
        return this.overrides;
    }

    public static MaterialAssetGroup create(String $$0) {
        return new MaterialAssetGroup(new AssetInfo($$0), Map.of());
    }

    public static MaterialAssetGroup create(String $$0, Map<ResourceKey<EquipmentAsset>, String> $$1) {
        return new MaterialAssetGroup(new AssetInfo($$0), Map.copyOf(Maps.transformValues($$1, AssetInfo::new)));
    }

    public AssetInfo assetId(ResourceKey<EquipmentAsset> $$0) {
        return this.overrides.getOrDefault($$0, this.base);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/equipment/trim/MaterialAssetGroup$AssetInfo.class */
    public static final class AssetInfo extends Record {
        private final String suffix;
        public static final Codec<AssetInfo> CODEC = ExtraCodecs.RESOURCE_PATH_CODEC.xmap(AssetInfo::new, (v0) -> {
            return v0.suffix();
        });
        public static final StreamCodec<ByteBuf, AssetInfo> STREAM_CODEC = ByteBufCodecs.STRING_UTF8.map(AssetInfo::new, (v0) -> {
            return v0.suffix();
        });

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, AssetInfo.class), AssetInfo.class, "suffix", "FIELD:Lnet/minecraft/world/item/equipment/trim/MaterialAssetGroup$AssetInfo;->suffix:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, AssetInfo.class), AssetInfo.class, "suffix", "FIELD:Lnet/minecraft/world/item/equipment/trim/MaterialAssetGroup$AssetInfo;->suffix:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, AssetInfo.class, Object.class), AssetInfo.class, "suffix", "FIELD:Lnet/minecraft/world/item/equipment/trim/MaterialAssetGroup$AssetInfo;->suffix:Ljava/lang/String;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public String suffix() {
            return this.suffix;
        }

        public AssetInfo(String $$0) {
            if (Identifier.isValidPath($$0)) {
                this.suffix = $$0;
                return;
            }
            throw new IllegalArgumentException("Invalid string to use as a resource path element: " + $$0);
        }
    }
}
