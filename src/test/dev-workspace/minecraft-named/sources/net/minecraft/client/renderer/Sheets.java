package net.minecraft.client.renderer;

import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.client.renderer.blockentity.state.ChestRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.state.properties.WoodType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/Sheets.class */
public class Sheets {
    public static final Identifier SHULKER_SHEET = Identifier.withDefaultNamespace("textures/atlas/shulker_boxes.png");
    public static final Identifier BED_SHEET = Identifier.withDefaultNamespace("textures/atlas/beds.png");
    public static final Identifier BANNER_SHEET = Identifier.withDefaultNamespace("textures/atlas/banner_patterns.png");
    public static final Identifier SHIELD_SHEET = Identifier.withDefaultNamespace("textures/atlas/shield_patterns.png");
    public static final Identifier SIGN_SHEET = Identifier.withDefaultNamespace("textures/atlas/signs.png");
    public static final Identifier CHEST_SHEET = Identifier.withDefaultNamespace("textures/atlas/chest.png");
    public static final Identifier ARMOR_TRIMS_SHEET = Identifier.withDefaultNamespace("textures/atlas/armor_trims.png");
    public static final Identifier DECORATED_POT_SHEET = Identifier.withDefaultNamespace("textures/atlas/decorated_pot.png");
    public static final Identifier GUI_SHEET = Identifier.withDefaultNamespace("textures/atlas/gui.png");
    public static final Identifier MAP_DECORATIONS_SHEET = Identifier.withDefaultNamespace("textures/atlas/map_decorations.png");
    public static final Identifier PAINTINGS_SHEET = Identifier.withDefaultNamespace("textures/atlas/paintings.png");
    public static final Identifier CELESTIAL_SHEET = Identifier.withDefaultNamespace("textures/atlas/celestials.png");
    private static final RenderType SHULKER_BOX_SHEET_TYPE = RenderTypes.entityCutoutNoCull(SHULKER_SHEET);
    private static final RenderType BED_SHEET_TYPE = RenderTypes.entitySolid(BED_SHEET);
    private static final RenderType BANNER_SHEET_TYPE = RenderTypes.entityNoOutline(BANNER_SHEET);
    private static final RenderType SHIELD_SHEET_TYPE = RenderTypes.entityNoOutline(SHIELD_SHEET);
    private static final RenderType SIGN_SHEET_TYPE = RenderTypes.entityCutoutNoCull(SIGN_SHEET);
    private static final RenderType CHEST_SHEET_TYPE = RenderTypes.entityCutout(CHEST_SHEET);
    private static final RenderType ARMOR_TRIMS_SHEET_TYPE = RenderTypes.armorCutoutNoCull(ARMOR_TRIMS_SHEET);
    private static final RenderType ARMOR_TRIMS_DECAL_SHEET_TYPE = RenderTypes.createArmorDecalCutoutNoCull(ARMOR_TRIMS_SHEET);
    private static final RenderType SOLID_BLOCK_SHEET = RenderTypes.entitySolid(TextureAtlas.LOCATION_BLOCKS);
    private static final RenderType CUTOUT_BLOCK_SHEET = RenderTypes.entityCutout(TextureAtlas.LOCATION_BLOCKS);
    private static final RenderType TRANSLUCENT_BLOCK_ITEM_SHEET = RenderTypes.itemEntityTranslucentCull(TextureAtlas.LOCATION_BLOCKS);
    private static final RenderType TRANSLUCENT_ITEM_SHEET = RenderTypes.itemEntityTranslucentCull(TextureAtlas.LOCATION_ITEMS);
    public static final MaterialMapper ITEMS_MAPPER = new MaterialMapper(TextureAtlas.LOCATION_ITEMS, DecoratedPotBlockEntity.TAG_ITEM);
    public static final MaterialMapper BLOCKS_MAPPER = new MaterialMapper(TextureAtlas.LOCATION_BLOCKS, "block");
    public static final MaterialMapper BLOCK_ENTITIES_MAPPER = new MaterialMapper(TextureAtlas.LOCATION_BLOCKS, SpawnData.ENTITY_TAG);
    public static final MaterialMapper BANNER_MAPPER = new MaterialMapper(BANNER_SHEET, "entity/banner");
    public static final MaterialMapper SHIELD_MAPPER = new MaterialMapper(SHIELD_SHEET, "entity/shield");
    public static final MaterialMapper CHEST_MAPPER = new MaterialMapper(CHEST_SHEET, "entity/chest");
    public static final MaterialMapper DECORATED_POT_MAPPER = new MaterialMapper(DECORATED_POT_SHEET, "entity/decorated_pot");
    public static final MaterialMapper BED_MAPPER = new MaterialMapper(BED_SHEET, "entity/bed");
    public static final MaterialMapper SHULKER_MAPPER = new MaterialMapper(SHULKER_SHEET, "entity/shulker");
    public static final MaterialMapper SIGN_MAPPER = new MaterialMapper(SIGN_SHEET, "entity/signs");
    public static final MaterialMapper HANGING_SIGN_MAPPER = new MaterialMapper(SIGN_SHEET, "entity/signs/hanging");
    public static final Material DEFAULT_SHULKER_TEXTURE_LOCATION = SHULKER_MAPPER.defaultNamespaceApply("shulker");
    public static final List<Material> SHULKER_TEXTURE_LOCATION = (List) Arrays.stream(DyeColor.values()).sorted(Comparator.comparingInt((v0) -> {
        return v0.getId();
    })).map(Sheets::createShulkerMaterial).collect(ImmutableList.toImmutableList());
    public static final Map<WoodType, Material> SIGN_MATERIALS = (Map) WoodType.values().collect(Collectors.toMap(Function.identity(), Sheets::createSignMaterial));
    public static final Map<WoodType, Material> HANGING_SIGN_MATERIALS = (Map) WoodType.values().collect(Collectors.toMap(Function.identity(), Sheets::createHangingSignMaterial));
    public static final Material BANNER_BASE = BANNER_MAPPER.defaultNamespaceApply("base");
    public static final Material SHIELD_BASE = SHIELD_MAPPER.defaultNamespaceApply("base");
    private static final Map<Identifier, Material> BANNER_MATERIALS = new HashMap();
    private static final Map<Identifier, Material> SHIELD_MATERIALS = new HashMap();
    public static final Map<ResourceKey<DecoratedPotPattern>, Material> DECORATED_POT_MATERIALS = (Map) BuiltInRegistries.DECORATED_POT_PATTERN.listElements().collect(Collectors.toMap((v0) -> {
        return v0.key();
    }, $$0 -> {
        return DECORATED_POT_MAPPER.apply(((DecoratedPotPattern) $$0.value()).assetId());
    }));
    public static final Material DECORATED_POT_BASE = DECORATED_POT_MAPPER.defaultNamespaceApply("decorated_pot_base");
    public static final Material DECORATED_POT_SIDE = DECORATED_POT_MAPPER.defaultNamespaceApply("decorated_pot_side");
    private static final Material[] BED_TEXTURES = (Material[]) Arrays.stream(DyeColor.values()).sorted(Comparator.comparingInt((v0) -> {
        return v0.getId();
    })).map(Sheets::createBedMaterial).toArray($$0 -> {
        return new Material[$$0];
    });
    public static final Material CHEST_TRAP_LOCATION = CHEST_MAPPER.defaultNamespaceApply("trapped");
    public static final Material CHEST_TRAP_LOCATION_LEFT = CHEST_MAPPER.defaultNamespaceApply("trapped_left");
    public static final Material CHEST_TRAP_LOCATION_RIGHT = CHEST_MAPPER.defaultNamespaceApply("trapped_right");
    public static final Material CHEST_XMAS_LOCATION = CHEST_MAPPER.defaultNamespaceApply("christmas");
    public static final Material CHEST_XMAS_LOCATION_LEFT = CHEST_MAPPER.defaultNamespaceApply("christmas_left");
    public static final Material CHEST_XMAS_LOCATION_RIGHT = CHEST_MAPPER.defaultNamespaceApply("christmas_right");
    public static final Material CHEST_LOCATION = CHEST_MAPPER.defaultNamespaceApply("normal");
    public static final Material CHEST_LOCATION_LEFT = CHEST_MAPPER.defaultNamespaceApply("normal_left");
    public static final Material CHEST_LOCATION_RIGHT = CHEST_MAPPER.defaultNamespaceApply("normal_right");
    public static final Material ENDER_CHEST_LOCATION = CHEST_MAPPER.defaultNamespaceApply("ender");
    public static final Material COPPER_CHEST_LOCATION = CHEST_MAPPER.defaultNamespaceApply("copper");
    public static final Material COPPER_CHEST_LOCATION_LEFT = CHEST_MAPPER.defaultNamespaceApply("copper_left");
    public static final Material COPPER_CHEST_LOCATION_RIGHT = CHEST_MAPPER.defaultNamespaceApply("copper_right");
    public static final Material EXPOSED_COPPER_CHEST_LOCATION = CHEST_MAPPER.defaultNamespaceApply("copper_exposed");
    public static final Material EXPOSED_COPPER_CHEST_LOCATION_LEFT = CHEST_MAPPER.defaultNamespaceApply("copper_exposed_left");
    public static final Material EXPOSED_COPPER_CHEST_LOCATION_RIGHT = CHEST_MAPPER.defaultNamespaceApply("copper_exposed_right");
    public static final Material WEATHERED_COPPER_CHEST_LOCATION = CHEST_MAPPER.defaultNamespaceApply("copper_weathered");
    public static final Material WEATHERED_COPPER_CHEST_LOCATION_LEFT = CHEST_MAPPER.defaultNamespaceApply("copper_weathered_left");
    public static final Material WEATHERED_COPPER_CHEST_LOCATION_RIGHT = CHEST_MAPPER.defaultNamespaceApply("copper_weathered_right");
    public static final Material OXIDIZED_COPPER_CHEST_LOCATION = CHEST_MAPPER.defaultNamespaceApply("copper_oxidized");
    public static final Material OXIDIZED_COPPER_CHEST_LOCATION_LEFT = CHEST_MAPPER.defaultNamespaceApply("copper_oxidized_left");
    public static final Material OXIDIZED_COPPER_CHEST_LOCATION_RIGHT = CHEST_MAPPER.defaultNamespaceApply("copper_oxidized_right");

    public static RenderType bannerSheet() {
        return BANNER_SHEET_TYPE;
    }

    public static RenderType shieldSheet() {
        return SHIELD_SHEET_TYPE;
    }

    public static RenderType bedSheet() {
        return BED_SHEET_TYPE;
    }

    public static RenderType shulkerBoxSheet() {
        return SHULKER_BOX_SHEET_TYPE;
    }

    public static RenderType signSheet() {
        return SIGN_SHEET_TYPE;
    }

    public static RenderType hangingSignSheet() {
        return SIGN_SHEET_TYPE;
    }

    public static RenderType chestSheet() {
        return CHEST_SHEET_TYPE;
    }

    public static RenderType armorTrimsSheet(boolean $$0) {
        return $$0 ? ARMOR_TRIMS_DECAL_SHEET_TYPE : ARMOR_TRIMS_SHEET_TYPE;
    }

    public static RenderType solidBlockSheet() {
        return SOLID_BLOCK_SHEET;
    }

    public static RenderType cutoutBlockSheet() {
        return CUTOUT_BLOCK_SHEET;
    }

    public static RenderType translucentItemSheet() {
        return TRANSLUCENT_ITEM_SHEET;
    }

    public static RenderType translucentBlockItemSheet() {
        return TRANSLUCENT_BLOCK_ITEM_SHEET;
    }

    public static Material getBedMaterial(DyeColor $$0) {
        return BED_TEXTURES[$$0.getId()];
    }

    public static Identifier colorToResourceMaterial(DyeColor $$0) {
        return Identifier.withDefaultNamespace($$0.getName());
    }

    public static Material createBedMaterial(DyeColor $$0) {
        return BED_MAPPER.apply(colorToResourceMaterial($$0));
    }

    public static Material getShulkerBoxMaterial(DyeColor $$0) {
        return SHULKER_TEXTURE_LOCATION.get($$0.getId());
    }

    public static Identifier colorToShulkerMaterial(DyeColor $$0) {
        return Identifier.withDefaultNamespace("shulker_" + $$0.getName());
    }

    public static Material createShulkerMaterial(DyeColor $$0) {
        return SHULKER_MAPPER.apply(colorToShulkerMaterial($$0));
    }

    private static Material createSignMaterial(WoodType $$0) {
        return SIGN_MAPPER.defaultNamespaceApply($$0.name());
    }

    private static Material createHangingSignMaterial(WoodType $$0) {
        return HANGING_SIGN_MAPPER.defaultNamespaceApply($$0.name());
    }

    public static Material getSignMaterial(WoodType $$0) {
        return SIGN_MATERIALS.get($$0);
    }

    public static Material getHangingSignMaterial(WoodType $$0) {
        return HANGING_SIGN_MATERIALS.get($$0);
    }

    public static Material getBannerMaterial(Holder<BannerPattern> $$0) {
        Map<Identifier, Material> map = BANNER_MATERIALS;
        Identifier identifierAssetId = $$0.value().assetId();
        MaterialMapper materialMapper = BANNER_MAPPER;
        Objects.requireNonNull(materialMapper);
        return map.computeIfAbsent(identifierAssetId, materialMapper::apply);
    }

    public static Material getShieldMaterial(Holder<BannerPattern> $$0) {
        Map<Identifier, Material> map = SHIELD_MATERIALS;
        Identifier identifierAssetId = $$0.value().assetId();
        MaterialMapper materialMapper = SHIELD_MAPPER;
        Objects.requireNonNull(materialMapper);
        return map.computeIfAbsent(identifierAssetId, materialMapper::apply);
    }

    public static Material getDecoratedPotMaterial(ResourceKey<DecoratedPotPattern> $$0) {
        if ($$0 == null) {
            return null;
        }
        return DECORATED_POT_MATERIALS.get($$0);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static Material chooseMaterial(ChestRenderState.ChestMaterialType $$0, ChestType $$1) throws MatchException {
        switch ($$0) {
            case ENDER_CHEST:
                return ENDER_CHEST_LOCATION;
            case CHRISTMAS:
                return chooseMaterial($$1, CHEST_XMAS_LOCATION, CHEST_XMAS_LOCATION_LEFT, CHEST_XMAS_LOCATION_RIGHT);
            case TRAPPED:
                return chooseMaterial($$1, CHEST_TRAP_LOCATION, CHEST_TRAP_LOCATION_LEFT, CHEST_TRAP_LOCATION_RIGHT);
            case COPPER_UNAFFECTED:
                return chooseMaterial($$1, COPPER_CHEST_LOCATION, COPPER_CHEST_LOCATION_LEFT, COPPER_CHEST_LOCATION_RIGHT);
            case COPPER_EXPOSED:
                return chooseMaterial($$1, EXPOSED_COPPER_CHEST_LOCATION, EXPOSED_COPPER_CHEST_LOCATION_LEFT, EXPOSED_COPPER_CHEST_LOCATION_RIGHT);
            case COPPER_WEATHERED:
                return chooseMaterial($$1, WEATHERED_COPPER_CHEST_LOCATION, WEATHERED_COPPER_CHEST_LOCATION_LEFT, WEATHERED_COPPER_CHEST_LOCATION_RIGHT);
            case COPPER_OXIDIZED:
                return chooseMaterial($$1, OXIDIZED_COPPER_CHEST_LOCATION, OXIDIZED_COPPER_CHEST_LOCATION_LEFT, OXIDIZED_COPPER_CHEST_LOCATION_RIGHT);
            case REGULAR:
                return chooseMaterial($$1, CHEST_LOCATION, CHEST_LOCATION_LEFT, CHEST_LOCATION_RIGHT);
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    private static Material chooseMaterial(ChestType $$0, Material $$1, Material $$2, Material $$3) {
        switch ($$0) {
            case LEFT:
                return $$2;
            case RIGHT:
                return $$3;
            case SINGLE:
            default:
                return $$1;
        }
    }
}
