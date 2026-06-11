package net.minecraft.client.data.models;

import com.mojang.serialization.Codec;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/data/models/EquipmentAssetProvider.class */
public class EquipmentAssetProvider implements DataProvider {
    private final PackOutput.PathProvider pathProvider;

    public EquipmentAssetProvider(PackOutput $$0) {
        this.pathProvider = $$0.createPathProvider(PackOutput.Target.RESOURCE_PACK, LivingEntity.TAG_EQUIPMENT);
    }

    private static void bootstrap(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> $$0) {
        $$0.accept(EquipmentAssets.LEATHER, EquipmentClientInfo.builder().addHumanoidLayers(Identifier.withDefaultNamespace("leather"), true).addHumanoidLayers(Identifier.withDefaultNamespace("leather_overlay"), false).addLayers(EquipmentClientInfo.LayerType.HORSE_BODY, EquipmentClientInfo.Layer.leatherDyeable(Identifier.withDefaultNamespace("leather"), true), EquipmentClientInfo.Layer.leatherDyeable(Identifier.withDefaultNamespace("leather_overlay"), false)).build());
        $$0.accept(EquipmentAssets.CHAINMAIL, onlyHumanoid("chainmail"));
        $$0.accept(EquipmentAssets.COPPER, humanoidAndMountArmor("copper"));
        $$0.accept(EquipmentAssets.IRON, humanoidAndMountArmor("iron"));
        $$0.accept(EquipmentAssets.GOLD, humanoidAndMountArmor("gold"));
        $$0.accept(EquipmentAssets.DIAMOND, humanoidAndMountArmor("diamond"));
        $$0.accept(EquipmentAssets.TURTLE_SCUTE, EquipmentClientInfo.builder().addMainHumanoidLayer(Identifier.withDefaultNamespace("turtle_scute"), false).build());
        $$0.accept(EquipmentAssets.NETHERITE, humanoidAndMountArmor("netherite"));
        $$0.accept(EquipmentAssets.ARMADILLO_SCUTE, EquipmentClientInfo.builder().addLayers(EquipmentClientInfo.LayerType.WOLF_BODY, EquipmentClientInfo.Layer.onlyIfDyed(Identifier.withDefaultNamespace("armadillo_scute"), false)).addLayers(EquipmentClientInfo.LayerType.WOLF_BODY, EquipmentClientInfo.Layer.onlyIfDyed(Identifier.withDefaultNamespace("armadillo_scute_overlay"), true)).build());
        $$0.accept(EquipmentAssets.ELYTRA, EquipmentClientInfo.builder().addLayers(EquipmentClientInfo.LayerType.WINGS, new EquipmentClientInfo.Layer(Identifier.withDefaultNamespace("elytra"), Optional.empty(), true)).build());
        EquipmentClientInfo.Layer $$1 = new EquipmentClientInfo.Layer(Identifier.withDefaultNamespace("saddle"));
        $$0.accept(EquipmentAssets.SADDLE, EquipmentClientInfo.builder().addLayers(EquipmentClientInfo.LayerType.PIG_SADDLE, $$1).addLayers(EquipmentClientInfo.LayerType.STRIDER_SADDLE, $$1).addLayers(EquipmentClientInfo.LayerType.CAMEL_SADDLE, $$1).addLayers(EquipmentClientInfo.LayerType.CAMEL_HUSK_SADDLE, $$1).addLayers(EquipmentClientInfo.LayerType.HORSE_SADDLE, $$1).addLayers(EquipmentClientInfo.LayerType.DONKEY_SADDLE, $$1).addLayers(EquipmentClientInfo.LayerType.MULE_SADDLE, $$1).addLayers(EquipmentClientInfo.LayerType.SKELETON_HORSE_SADDLE, $$1).addLayers(EquipmentClientInfo.LayerType.ZOMBIE_HORSE_SADDLE, $$1).addLayers(EquipmentClientInfo.LayerType.NAUTILUS_SADDLE, $$1).build());
        for (Map.Entry<DyeColor, ResourceKey<EquipmentAsset>> $$2 : EquipmentAssets.HARNESSES.entrySet()) {
            DyeColor $$3 = $$2.getKey();
            ResourceKey<EquipmentAsset> $$4 = $$2.getValue();
            $$0.accept($$4, EquipmentClientInfo.builder().addLayers(EquipmentClientInfo.LayerType.HAPPY_GHAST_BODY, EquipmentClientInfo.Layer.onlyIfDyed(Identifier.withDefaultNamespace($$3.getSerializedName() + "_harness"), false)).build());
        }
        for (Map.Entry<DyeColor, ResourceKey<EquipmentAsset>> $$5 : EquipmentAssets.CARPETS.entrySet()) {
            DyeColor $$6 = $$5.getKey();
            ResourceKey<EquipmentAsset> $$7 = $$5.getValue();
            $$0.accept($$7, EquipmentClientInfo.builder().addLayers(EquipmentClientInfo.LayerType.LLAMA_BODY, new EquipmentClientInfo.Layer(Identifier.withDefaultNamespace($$6.getSerializedName()))).build());
        }
        $$0.accept(EquipmentAssets.TRADER_LLAMA, EquipmentClientInfo.builder().addLayers(EquipmentClientInfo.LayerType.LLAMA_BODY, new EquipmentClientInfo.Layer(Identifier.withDefaultNamespace("trader_llama"))).build());
    }

    private static EquipmentClientInfo onlyHumanoid(String $$0) {
        return EquipmentClientInfo.builder().addHumanoidLayers(Identifier.withDefaultNamespace($$0)).build();
    }

    private static EquipmentClientInfo humanoidAndMountArmor(String $$0) {
        return EquipmentClientInfo.builder().addHumanoidLayers(Identifier.withDefaultNamespace($$0)).addLayers(EquipmentClientInfo.LayerType.HORSE_BODY, EquipmentClientInfo.Layer.leatherDyeable(Identifier.withDefaultNamespace($$0), false)).addLayers(EquipmentClientInfo.LayerType.NAUTILUS_BODY, EquipmentClientInfo.Layer.leatherDyeable(Identifier.withDefaultNamespace($$0), false)).build();
    }

    @Override // net.minecraft.data.DataProvider
    public CompletableFuture<?> run(CachedOutput $$0) {
        Map<ResourceKey<EquipmentAsset>, EquipmentClientInfo> $$1 = new HashMap<>();
        bootstrap(($$12, $$2) -> {
            if ($$1.putIfAbsent($$12, $$2) != null) {
                throw new IllegalStateException("Tried to register equipment asset twice for id: " + String.valueOf($$12));
            }
        });
        Codec<EquipmentClientInfo> codec = EquipmentClientInfo.CODEC;
        PackOutput.PathProvider pathProvider = this.pathProvider;
        Objects.requireNonNull(pathProvider);
        return DataProvider.saveAll($$0, codec, pathProvider::json, $$1);
    }

    @Override // net.minecraft.data.DataProvider
    public String getName() {
        return "Equipment Asset Definitions";
    }
}
