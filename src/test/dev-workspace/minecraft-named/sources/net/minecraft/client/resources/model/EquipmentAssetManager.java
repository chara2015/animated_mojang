package net.minecraft.client.resources.model;

import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/model/EquipmentAssetManager.class */
public class EquipmentAssetManager extends SimpleJsonResourceReloadListener<EquipmentClientInfo> {
    public static final EquipmentClientInfo MISSING = new EquipmentClientInfo(Map.of());
    private static final FileToIdConverter ASSET_LISTER = FileToIdConverter.json(LivingEntity.TAG_EQUIPMENT);
    private Map<ResourceKey<EquipmentAsset>, EquipmentClientInfo> equipmentAssets;

    public EquipmentAssetManager() {
        super(EquipmentClientInfo.CODEC, ASSET_LISTER);
        this.equipmentAssets = Map.of();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.minecraft.server.packs.resources.SimplePreparableReloadListener
    public void apply(Map<Identifier, EquipmentClientInfo> $$0, ResourceManager $$1, ProfilerFiller $$2) {
        this.equipmentAssets = (Map) $$0.entrySet().stream().collect(Collectors.toUnmodifiableMap($$02 -> {
            return ResourceKey.create(EquipmentAssets.ROOT_ID, (Identifier) $$02.getKey());
        }, (v0) -> {
            return v0.getValue();
        }));
    }

    public EquipmentClientInfo get(ResourceKey<EquipmentAsset> $$0) {
        return this.equipmentAssets.getOrDefault($$0, MISSING);
    }
}
