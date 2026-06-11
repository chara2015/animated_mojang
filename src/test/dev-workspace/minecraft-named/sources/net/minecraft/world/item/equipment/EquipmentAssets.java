package net.minecraft.world.item.equipment;

import java.util.Map;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Util;
import net.minecraft.world.item.DyeColor;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/equipment/EquipmentAssets.class */
public interface EquipmentAssets {
    public static final ResourceKey<? extends Registry<EquipmentAsset>> ROOT_ID = ResourceKey.createRegistryKey(Identifier.withDefaultNamespace("equipment_asset"));
    public static final ResourceKey<EquipmentAsset> LEATHER = createId("leather");
    public static final ResourceKey<EquipmentAsset> COPPER = createId("copper");
    public static final ResourceKey<EquipmentAsset> CHAINMAIL = createId("chainmail");
    public static final ResourceKey<EquipmentAsset> IRON = createId("iron");
    public static final ResourceKey<EquipmentAsset> GOLD = createId("gold");
    public static final ResourceKey<EquipmentAsset> DIAMOND = createId("diamond");
    public static final ResourceKey<EquipmentAsset> TURTLE_SCUTE = createId("turtle_scute");
    public static final ResourceKey<EquipmentAsset> NETHERITE = createId("netherite");
    public static final ResourceKey<EquipmentAsset> ARMADILLO_SCUTE = createId("armadillo_scute");
    public static final ResourceKey<EquipmentAsset> ELYTRA = createId("elytra");
    public static final ResourceKey<EquipmentAsset> SADDLE = createId("saddle");
    public static final Map<DyeColor, ResourceKey<EquipmentAsset>> CARPETS = Util.makeEnumMap(DyeColor.class, $$0 -> {
        return createId($$0.getSerializedName() + "_carpet");
    });
    public static final ResourceKey<EquipmentAsset> TRADER_LLAMA = createId("trader_llama");
    public static final Map<DyeColor, ResourceKey<EquipmentAsset>> HARNESSES = Util.makeEnumMap(DyeColor.class, $$0 -> {
        return createId($$0.getSerializedName() + "_harness");
    });

    static ResourceKey<EquipmentAsset> createId(String $$0) {
        return ResourceKey.create(ROOT_ID, Identifier.withDefaultNamespace($$0));
    }
}
