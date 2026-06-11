package net.minecraft.client.renderer.rendertype;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.AddressMode;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuSampler;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.blockentity.AbstractEndPortalRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.TextureTransform;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Leashable;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/rendertype/RenderTypes.class */
public class RenderTypes {
    static final BiFunction<Identifier, Boolean, RenderType> OUTLINE = Util.memoize(($$0, $$1) -> {
        return RenderType.create("outline", RenderSetup.builder($$1.booleanValue() ? RenderPipelines.OUTLINE_CULL : RenderPipelines.OUTLINE_NO_CULL).withTexture("Sampler0", $$0).setOutputTarget(OutputTarget.OUTLINE_TARGET).setOutline(RenderSetup.OutlineProperty.IS_OUTLINE).createRenderSetup());
    });
    public static final Supplier<GpuSampler> MOVING_BLOCK_SAMPLER = () -> {
        return RenderSystem.getSamplerCache().getSampler(AddressMode.CLAMP_TO_EDGE, AddressMode.CLAMP_TO_EDGE, FilterMode.LINEAR, FilterMode.NEAREST, true);
    };
    private static final RenderType SOLID_MOVING_BLOCK = RenderType.create("solid_moving_block", RenderSetup.builder(RenderPipelines.SOLID_BLOCK).useLightmap().withTexture("Sampler0", TextureAtlas.LOCATION_BLOCKS, MOVING_BLOCK_SAMPLER).affectsCrumbling().setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE).createRenderSetup());
    private static final RenderType CUTOUT_MOVING_BLOCK = RenderType.create("cutout_moving_block", RenderSetup.builder(RenderPipelines.CUTOUT_BLOCK).useLightmap().withTexture("Sampler0", TextureAtlas.LOCATION_BLOCKS, MOVING_BLOCK_SAMPLER).affectsCrumbling().setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE).createRenderSetup());
    private static final RenderType TRANSLUCENT_MOVING_BLOCK = RenderType.create("translucent_moving_block", RenderSetup.builder(RenderPipelines.TRANSLUCENT_MOVING_BLOCK).useLightmap().withTexture("Sampler0", TextureAtlas.LOCATION_BLOCKS, MOVING_BLOCK_SAMPLER).setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET).sortOnUpload().bufferSize(RenderType.SMALL_BUFFER_SIZE).setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE).createRenderSetup());
    private static final Function<Identifier, RenderType> ARMOR_CUTOUT_NO_CULL = Util.memoize($$0 -> {
        RenderSetup $$1 = RenderSetup.builder(RenderPipelines.ARMOR_CUTOUT_NO_CULL).withTexture("Sampler0", $$0).useLightmap().useOverlay().setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING).affectsCrumbling().setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE).createRenderSetup();
        return RenderType.create("armor_cutout_no_cull", $$1);
    });
    private static final Function<Identifier, RenderType> ARMOR_TRANSLUCENT = Util.memoize($$0 -> {
        RenderSetup $$1 = RenderSetup.builder(RenderPipelines.ARMOR_TRANSLUCENT).withTexture("Sampler0", $$0).useLightmap().useOverlay().setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING).affectsCrumbling().sortOnUpload().setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE).createRenderSetup();
        return RenderType.create("armor_translucent", $$1);
    });
    private static final Function<Identifier, RenderType> ENTITY_SOLID = Util.memoize($$0 -> {
        RenderSetup $$1 = RenderSetup.builder(RenderPipelines.ENTITY_SOLID).withTexture("Sampler0", $$0).useLightmap().useOverlay().affectsCrumbling().setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE).createRenderSetup();
        return RenderType.create("entity_solid", $$1);
    });
    private static final Function<Identifier, RenderType> ENTITY_SOLID_Z_OFFSET_FORWARD = Util.memoize($$0 -> {
        RenderSetup $$1 = RenderSetup.builder(RenderPipelines.ENTITY_SOLID_Z_OFFSET_FORWARD).withTexture("Sampler0", $$0).useLightmap().useOverlay().setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING_FORWARD).affectsCrumbling().setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE).createRenderSetup();
        return RenderType.create("entity_solid_z_offset_forward", $$1);
    });
    private static final Function<Identifier, RenderType> ENTITY_CUTOUT = Util.memoize($$0 -> {
        RenderSetup $$1 = RenderSetup.builder(RenderPipelines.ENTITY_CUTOUT).withTexture("Sampler0", $$0).useLightmap().useOverlay().affectsCrumbling().setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE).createRenderSetup();
        return RenderType.create("entity_cutout", $$1);
    });
    private static final BiFunction<Identifier, Boolean, RenderType> ENTITY_CUTOUT_NO_CULL = Util.memoize(($$0, $$1) -> {
        RenderSetup $$2 = RenderSetup.builder(RenderPipelines.ENTITY_CUTOUT_NO_CULL).withTexture("Sampler0", $$0).useLightmap().useOverlay().affectsCrumbling().setOutline($$1.booleanValue() ? RenderSetup.OutlineProperty.AFFECTS_OUTLINE : RenderSetup.OutlineProperty.NONE).createRenderSetup();
        return RenderType.create("entity_cutout_no_cull", $$2);
    });
    private static final BiFunction<Identifier, Boolean, RenderType> ENTITY_CUTOUT_NO_CULL_Z_OFFSET = Util.memoize(($$0, $$1) -> {
        RenderSetup $$2 = RenderSetup.builder(RenderPipelines.ENTITY_CUTOUT_NO_CULL_Z_OFFSET).withTexture("Sampler0", $$0).useLightmap().useOverlay().setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING).affectsCrumbling().setOutline($$1.booleanValue() ? RenderSetup.OutlineProperty.AFFECTS_OUTLINE : RenderSetup.OutlineProperty.NONE).createRenderSetup();
        return RenderType.create("entity_cutout_no_cull_z_offset", $$2);
    });
    private static final Function<Identifier, RenderType> ITEM_ENTITY_TRANSLUCENT_CULL = Util.memoize($$0 -> {
        RenderSetup $$1 = RenderSetup.builder(RenderPipelines.ITEM_ENTITY_TRANSLUCENT_CULL).withTexture("Sampler0", $$0).setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET).useLightmap().useOverlay().affectsCrumbling().sortOnUpload().setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE).createRenderSetup();
        return RenderType.create("item_entity_translucent_cull", $$1);
    });
    private static final BiFunction<Identifier, Boolean, RenderType> ENTITY_TRANSLUCENT = Util.memoize(($$0, $$1) -> {
        RenderSetup $$2 = RenderSetup.builder(RenderPipelines.ENTITY_TRANSLUCENT).withTexture("Sampler0", $$0).useLightmap().useOverlay().affectsCrumbling().sortOnUpload().setOutline($$1.booleanValue() ? RenderSetup.OutlineProperty.AFFECTS_OUTLINE : RenderSetup.OutlineProperty.NONE).createRenderSetup();
        return RenderType.create("entity_translucent", $$2);
    });
    private static final BiFunction<Identifier, Boolean, RenderType> ENTITY_TRANSLUCENT_EMISSIVE = Util.memoize(($$0, $$1) -> {
        RenderSetup $$2 = RenderSetup.builder(RenderPipelines.ENTITY_TRANSLUCENT_EMISSIVE).withTexture("Sampler0", $$0).useOverlay().affectsCrumbling().sortOnUpload().setOutline($$1.booleanValue() ? RenderSetup.OutlineProperty.AFFECTS_OUTLINE : RenderSetup.OutlineProperty.NONE).createRenderSetup();
        return RenderType.create("entity_translucent_emissive", $$2);
    });
    private static final Function<Identifier, RenderType> ENTITY_SMOOTH_CUTOUT = Util.memoize($$0 -> {
        RenderSetup $$1 = RenderSetup.builder(RenderPipelines.ENTITY_SMOOTH_CUTOUT).withTexture("Sampler0", $$0).useLightmap().useOverlay().setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE).createRenderSetup();
        return RenderType.create("entity_smooth_cutout", $$1);
    });
    private static final BiFunction<Identifier, Boolean, RenderType> BEACON_BEAM = Util.memoize(($$0, $$1) -> {
        RenderSetup $$2 = RenderSetup.builder($$1.booleanValue() ? RenderPipelines.BEACON_BEAM_TRANSLUCENT : RenderPipelines.BEACON_BEAM_OPAQUE).withTexture("Sampler0", $$0).sortOnUpload().createRenderSetup();
        return RenderType.create("beacon_beam", $$2);
    });
    private static final Function<Identifier, RenderType> ENTITY_DECAL = Util.memoize($$0 -> {
        RenderSetup $$1 = RenderSetup.builder(RenderPipelines.ENTITY_DECAL).withTexture("Sampler0", $$0).useLightmap().useOverlay().createRenderSetup();
        return RenderType.create("entity_decal", $$1);
    });
    private static final Function<Identifier, RenderType> ENTITY_NO_OUTLINE = Util.memoize($$0 -> {
        RenderSetup $$1 = RenderSetup.builder(RenderPipelines.ENTITY_NO_OUTLINE).withTexture("Sampler0", $$0).useLightmap().useOverlay().sortOnUpload().createRenderSetup();
        return RenderType.create("entity_no_outline", $$1);
    });
    private static final Function<Identifier, RenderType> ENTITY_SHADOW = Util.memoize($$0 -> {
        RenderSetup $$1 = RenderSetup.builder(RenderPipelines.ENTITY_SHADOW).withTexture("Sampler0", $$0).useLightmap().useOverlay().setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING).createRenderSetup();
        return RenderType.create("entity_shadow", $$1);
    });
    private static final Function<Identifier, RenderType> DRAGON_EXPLOSION_ALPHA = Util.memoize($$0 -> {
        RenderSetup $$1 = RenderSetup.builder(RenderPipelines.DRAGON_EXPLOSION_ALPHA).withTexture("Sampler0", $$0).setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE).createRenderSetup();
        return RenderType.create("entity_alpha", $$1);
    });
    private static final Function<Identifier, RenderType> EYES = Util.memoize($$0 -> {
        return RenderType.create(PartNames.EYES, RenderSetup.builder(RenderPipelines.EYES).withTexture("Sampler0", $$0).sortOnUpload().createRenderSetup());
    });
    private static final RenderType LEASH = RenderType.create(Leashable.LEASH_TAG, RenderSetup.builder(RenderPipelines.LEASH).useLightmap().createRenderSetup());
    private static final RenderType WATER_MASK = RenderType.create("water_mask", RenderSetup.builder(RenderPipelines.WATER_MASK).createRenderSetup());
    private static final RenderType ARMOR_ENTITY_GLINT = RenderType.create("armor_entity_glint", RenderSetup.builder(RenderPipelines.GLINT).withTexture("Sampler0", ItemRenderer.ENCHANTED_GLINT_ARMOR).setTextureTransform(TextureTransform.ARMOR_ENTITY_GLINT_TEXTURING).setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING).createRenderSetup());
    private static final RenderType GLINT_TRANSLUCENT = RenderType.create("glint_translucent", RenderSetup.builder(RenderPipelines.GLINT).withTexture("Sampler0", ItemRenderer.ENCHANTED_GLINT_ITEM).setTextureTransform(TextureTransform.GLINT_TEXTURING).setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET).createRenderSetup());
    private static final RenderType GLINT = RenderType.create("glint", RenderSetup.builder(RenderPipelines.GLINT).withTexture("Sampler0", ItemRenderer.ENCHANTED_GLINT_ITEM).setTextureTransform(TextureTransform.GLINT_TEXTURING).createRenderSetup());
    private static final RenderType ENTITY_GLINT = RenderType.create("entity_glint", RenderSetup.builder(RenderPipelines.GLINT).withTexture("Sampler0", ItemRenderer.ENCHANTED_GLINT_ITEM).setTextureTransform(TextureTransform.ENTITY_GLINT_TEXTURING).createRenderSetup());
    private static final Function<Identifier, RenderType> CRUMBLING = Util.memoize($$0 -> {
        return RenderType.create("crumbling", RenderSetup.builder(RenderPipelines.CRUMBLING).withTexture("Sampler0", $$0).sortOnUpload().createRenderSetup());
    });
    private static final Function<Identifier, RenderType> TEXT = Util.memoize($$0 -> {
        return RenderType.create(Display.TextDisplay.TAG_TEXT, RenderSetup.builder(RenderPipelines.TEXT).withTexture("Sampler0", $$0).useLightmap().bufferSize(RenderType.SMALL_BUFFER_SIZE).createRenderSetup());
    });
    private static final RenderType TEXT_BACKGROUND = RenderType.create("text_background", RenderSetup.builder(RenderPipelines.TEXT_BACKGROUND).useLightmap().sortOnUpload().createRenderSetup());
    private static final Function<Identifier, RenderType> TEXT_INTENSITY = Util.memoize($$0 -> {
        return RenderType.create("text_intensity", RenderSetup.builder(RenderPipelines.TEXT_INTENSITY).withTexture("Sampler0", $$0).useLightmap().bufferSize(RenderType.SMALL_BUFFER_SIZE).createRenderSetup());
    });
    private static final Function<Identifier, RenderType> TEXT_POLYGON_OFFSET = Util.memoize($$0 -> {
        return RenderType.create("text_polygon_offset", RenderSetup.builder(RenderPipelines.TEXT_POLYGON_OFFSET).withTexture("Sampler0", $$0).useLightmap().sortOnUpload().createRenderSetup());
    });
    private static final Function<Identifier, RenderType> TEXT_INTENSITY_POLYGON_OFFSET = Util.memoize($$0 -> {
        return RenderType.create("text_intensity_polygon_offset", RenderSetup.builder(RenderPipelines.TEXT_INTENSITY).withTexture("Sampler0", $$0).useLightmap().sortOnUpload().createRenderSetup());
    });
    private static final Function<Identifier, RenderType> TEXT_SEE_THROUGH = Util.memoize($$0 -> {
        return RenderType.create("text_see_through", RenderSetup.builder(RenderPipelines.TEXT_SEE_THROUGH).withTexture("Sampler0", $$0).useLightmap().createRenderSetup());
    });
    private static final RenderType TEXT_BACKGROUND_SEE_THROUGH = RenderType.create("text_background_see_through", RenderSetup.builder(RenderPipelines.TEXT_BACKGROUND_SEE_THROUGH).useLightmap().sortOnUpload().createRenderSetup());
    private static final Function<Identifier, RenderType> TEXT_INTENSITY_SEE_THROUGH = Util.memoize($$0 -> {
        return RenderType.create("text_intensity_see_through", RenderSetup.builder(RenderPipelines.TEXT_INTENSITY_SEE_THROUGH).withTexture("Sampler0", $$0).useLightmap().sortOnUpload().createRenderSetup());
    });
    private static final RenderType LIGHTNING = RenderType.create("lightning", RenderSetup.builder(RenderPipelines.LIGHTNING).setOutputTarget(OutputTarget.WEATHER_TARGET).sortOnUpload().createRenderSetup());
    private static final RenderType DRAGON_RAYS = RenderType.create("dragon_rays", RenderSetup.builder(RenderPipelines.DRAGON_RAYS).createRenderSetup());
    private static final RenderType DRAGON_RAYS_DEPTH = RenderType.create("dragon_rays_depth", RenderSetup.builder(RenderPipelines.DRAGON_RAYS_DEPTH).createRenderSetup());
    private static final RenderType TRIPWIRE_MOVING_BLOCk = RenderType.create("tripwire_moving_block", RenderSetup.builder(RenderPipelines.TRIPWIRE_BLOCK).useLightmap().withTexture("Sampler0", TextureAtlas.LOCATION_BLOCKS, MOVING_BLOCK_SAMPLER).setOutputTarget(OutputTarget.WEATHER_TARGET).affectsCrumbling().sortOnUpload().setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE).createRenderSetup());
    private static final RenderType END_PORTAL = RenderType.create("end_portal", RenderSetup.builder(RenderPipelines.END_PORTAL).withTexture("Sampler0", AbstractEndPortalRenderer.END_SKY_LOCATION).withTexture("Sampler1", AbstractEndPortalRenderer.END_PORTAL_LOCATION).createRenderSetup());
    private static final RenderType END_GATEWAY = RenderType.create("end_gateway", RenderSetup.builder(RenderPipelines.END_GATEWAY).withTexture("Sampler0", AbstractEndPortalRenderer.END_SKY_LOCATION).withTexture("Sampler1", AbstractEndPortalRenderer.END_PORTAL_LOCATION).createRenderSetup());
    public static final RenderType LINES = RenderType.create("lines", RenderSetup.builder(RenderPipelines.LINES).setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING).setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET).createRenderSetup());
    public static final RenderType LINES_TRANSLUCENT = RenderType.create("lines_translucent", RenderSetup.builder(RenderPipelines.LINES_TRANSLUCENT).setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING).setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET).createRenderSetup());
    public static final RenderType SECONDARY_BLOCK_OUTLINE = RenderType.create("secondary_block_outline", RenderSetup.builder(RenderPipelines.SECONDARY_BLOCK_OUTLINE).setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING).setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET).createRenderSetup());
    private static final RenderType DEBUG_FILLED_BOX = RenderType.create("debug_filled_box", RenderSetup.builder(RenderPipelines.DEBUG_FILLED_BOX).sortOnUpload().setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING).createRenderSetup());
    private static final RenderType DEBUG_POINT = RenderType.create("debug_point", RenderSetup.builder(RenderPipelines.DEBUG_POINTS).createRenderSetup());
    private static final RenderType DEBUG_QUADS = RenderType.create("debug_quads", RenderSetup.builder(RenderPipelines.DEBUG_QUADS).sortOnUpload().createRenderSetup());
    private static final RenderType DEBUG_TRIANGLE_FAN = RenderType.create("debug_triangle_fan", RenderSetup.builder(RenderPipelines.DEBUG_TRIANGLE_FAN).sortOnUpload().createRenderSetup());
    private static final Function<Identifier, RenderType> WEATHER_DEPTH_WRITE = createWeather(RenderPipelines.WEATHER_DEPTH_WRITE);
    private static final Function<Identifier, RenderType> WEATHER_NO_DEPTH_WRITE = createWeather(RenderPipelines.WEATHER_NO_DEPTH_WRITE);
    private static final Function<Identifier, RenderType> BLOCK_SCREEN_EFFECT = Util.memoize($$0 -> {
        return RenderType.create("block_screen_effect", RenderSetup.builder(RenderPipelines.BLOCK_SCREEN_EFFECT).withTexture("Sampler0", $$0).createRenderSetup());
    });
    private static final Function<Identifier, RenderType> FIRE_SCREEN_EFFECT = Util.memoize($$0 -> {
        return RenderType.create("fire_screen_effect", RenderSetup.builder(RenderPipelines.FIRE_SCREEN_EFFECT).withTexture("Sampler0", $$0).createRenderSetup());
    });

    public static RenderType solidMovingBlock() {
        return SOLID_MOVING_BLOCK;
    }

    public static RenderType cutoutMovingBlock() {
        return CUTOUT_MOVING_BLOCK;
    }

    public static RenderType translucentMovingBlock() {
        return TRANSLUCENT_MOVING_BLOCK;
    }

    public static RenderType armorCutoutNoCull(Identifier $$0) {
        return ARMOR_CUTOUT_NO_CULL.apply($$0);
    }

    public static RenderType createArmorDecalCutoutNoCull(Identifier $$0) {
        RenderSetup $$1 = RenderSetup.builder(RenderPipelines.ARMOR_DECAL_CUTOUT_NO_CULL).withTexture("Sampler0", $$0).useLightmap().useOverlay().setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING).affectsCrumbling().setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE).createRenderSetup();
        return RenderType.create("armor_decal_cutout_no_cull", $$1);
    }

    public static RenderType armorTranslucent(Identifier $$0) {
        return ARMOR_TRANSLUCENT.apply($$0);
    }

    public static RenderType entitySolid(Identifier $$0) {
        return ENTITY_SOLID.apply($$0);
    }

    public static RenderType entitySolidZOffsetForward(Identifier $$0) {
        return ENTITY_SOLID_Z_OFFSET_FORWARD.apply($$0);
    }

    public static RenderType entityCutout(Identifier $$0) {
        return ENTITY_CUTOUT.apply($$0);
    }

    public static RenderType entityCutoutNoCull(Identifier $$0, boolean $$1) {
        return ENTITY_CUTOUT_NO_CULL.apply($$0, Boolean.valueOf($$1));
    }

    public static RenderType entityCutoutNoCull(Identifier $$0) {
        return entityCutoutNoCull($$0, true);
    }

    public static RenderType entityCutoutNoCullZOffset(Identifier $$0, boolean $$1) {
        return ENTITY_CUTOUT_NO_CULL_Z_OFFSET.apply($$0, Boolean.valueOf($$1));
    }

    public static RenderType entityCutoutNoCullZOffset(Identifier $$0) {
        return entityCutoutNoCullZOffset($$0, true);
    }

    public static RenderType itemEntityTranslucentCull(Identifier $$0) {
        return ITEM_ENTITY_TRANSLUCENT_CULL.apply($$0);
    }

    public static RenderType entityTranslucent(Identifier $$0, boolean $$1) {
        return ENTITY_TRANSLUCENT.apply($$0, Boolean.valueOf($$1));
    }

    public static RenderType entityTranslucent(Identifier $$0) {
        return entityTranslucent($$0, true);
    }

    public static RenderType entityTranslucentEmissive(Identifier $$0, boolean $$1) {
        return ENTITY_TRANSLUCENT_EMISSIVE.apply($$0, Boolean.valueOf($$1));
    }

    public static RenderType entityTranslucentEmissive(Identifier $$0) {
        return entityTranslucentEmissive($$0, true);
    }

    public static RenderType entitySmoothCutout(Identifier $$0) {
        return ENTITY_SMOOTH_CUTOUT.apply($$0);
    }

    public static RenderType beaconBeam(Identifier $$0, boolean $$1) {
        return BEACON_BEAM.apply($$0, Boolean.valueOf($$1));
    }

    public static RenderType entityDecal(Identifier $$0) {
        return ENTITY_DECAL.apply($$0);
    }

    public static RenderType entityNoOutline(Identifier $$0) {
        return ENTITY_NO_OUTLINE.apply($$0);
    }

    public static RenderType entityShadow(Identifier $$0) {
        return ENTITY_SHADOW.apply($$0);
    }

    public static RenderType dragonExplosionAlpha(Identifier $$0) {
        return DRAGON_EXPLOSION_ALPHA.apply($$0);
    }

    public static RenderType eyes(Identifier $$0) {
        return EYES.apply($$0);
    }

    public static RenderType breezeEyes(Identifier $$0) {
        return ENTITY_TRANSLUCENT_EMISSIVE.apply($$0, false);
    }

    public static RenderType breezeWind(Identifier $$0, float $$1, float $$2) {
        return RenderType.create("breeze_wind", RenderSetup.builder(RenderPipelines.BREEZE_WIND).withTexture("Sampler0", $$0).setTextureTransform(new TextureTransform.OffsetTextureTransform($$1, $$2)).useLightmap().sortOnUpload().createRenderSetup());
    }

    public static RenderType energySwirl(Identifier $$0, float $$1, float $$2) {
        return RenderType.create("energy_swirl", RenderSetup.builder(RenderPipelines.ENERGY_SWIRL).withTexture("Sampler0", $$0).setTextureTransform(new TextureTransform.OffsetTextureTransform($$1, $$2)).useLightmap().useOverlay().sortOnUpload().createRenderSetup());
    }

    public static RenderType leash() {
        return LEASH;
    }

    public static RenderType waterMask() {
        return WATER_MASK;
    }

    public static RenderType outline(Identifier $$0) {
        return OUTLINE.apply($$0, false);
    }

    public static RenderType armorEntityGlint() {
        return ARMOR_ENTITY_GLINT;
    }

    public static RenderType glintTranslucent() {
        return GLINT_TRANSLUCENT;
    }

    public static RenderType glint() {
        return GLINT;
    }

    public static RenderType entityGlint() {
        return ENTITY_GLINT;
    }

    public static RenderType crumbling(Identifier $$0) {
        return CRUMBLING.apply($$0);
    }

    public static RenderType text(Identifier $$0) {
        return TEXT.apply($$0);
    }

    public static RenderType textBackground() {
        return TEXT_BACKGROUND;
    }

    public static RenderType textIntensity(Identifier $$0) {
        return TEXT_INTENSITY.apply($$0);
    }

    public static RenderType textPolygonOffset(Identifier $$0) {
        return TEXT_POLYGON_OFFSET.apply($$0);
    }

    public static RenderType textIntensityPolygonOffset(Identifier $$0) {
        return TEXT_INTENSITY_POLYGON_OFFSET.apply($$0);
    }

    public static RenderType textSeeThrough(Identifier $$0) {
        return TEXT_SEE_THROUGH.apply($$0);
    }

    public static RenderType textBackgroundSeeThrough() {
        return TEXT_BACKGROUND_SEE_THROUGH;
    }

    public static RenderType textIntensitySeeThrough(Identifier $$0) {
        return TEXT_INTENSITY_SEE_THROUGH.apply($$0);
    }

    public static RenderType lightning() {
        return LIGHTNING;
    }

    public static RenderType dragonRays() {
        return DRAGON_RAYS;
    }

    public static RenderType dragonRaysDepth() {
        return DRAGON_RAYS_DEPTH;
    }

    public static RenderType tripwireMovingBlock() {
        return TRIPWIRE_MOVING_BLOCk;
    }

    public static RenderType endPortal() {
        return END_PORTAL;
    }

    public static RenderType endGateway() {
        return END_GATEWAY;
    }

    public static RenderType lines() {
        return LINES;
    }

    public static RenderType linesTranslucent() {
        return LINES_TRANSLUCENT;
    }

    public static RenderType secondaryBlockOutline() {
        return SECONDARY_BLOCK_OUTLINE;
    }

    public static RenderType debugFilledBox() {
        return DEBUG_FILLED_BOX;
    }

    public static RenderType debugPoint() {
        return DEBUG_POINT;
    }

    public static RenderType debugQuads() {
        return DEBUG_QUADS;
    }

    public static RenderType debugTriangleFan() {
        return DEBUG_TRIANGLE_FAN;
    }

    private static Function<Identifier, RenderType> createWeather(RenderPipeline $$0) {
        return Util.memoize($$1 -> {
            return RenderType.create("weather", RenderSetup.builder($$0).withTexture("Sampler0", $$1).setOutputTarget(OutputTarget.WEATHER_TARGET).useLightmap().createRenderSetup());
        });
    }

    public static RenderType weather(Identifier $$0, boolean $$1) {
        return ($$1 ? WEATHER_DEPTH_WRITE : WEATHER_NO_DEPTH_WRITE).apply($$0);
    }

    public static RenderType blockScreenEffect(Identifier $$0) {
        return BLOCK_SCREEN_EFFECT.apply($$0);
    }

    public static RenderType fireScreenEffect(Identifier $$0) {
        return FIRE_SCREEN_EFFECT.apply($$0);
    }
}
