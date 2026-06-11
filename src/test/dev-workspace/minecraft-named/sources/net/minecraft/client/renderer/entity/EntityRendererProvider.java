package net.minecraft.client.renderer.entity;

import net.minecraft.client.gui.Font;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MapRenderer;
import net.minecraft.client.renderer.PlayerSkinRenderCache;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.AtlasManager;
import net.minecraft.client.resources.model.EquipmentAssetManager;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.data.AtlasIds;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.Entity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/EntityRendererProvider.class */
@FunctionalInterface
public interface EntityRendererProvider<T extends Entity> {
    EntityRenderer<T, ?> create(Context context);

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/EntityRendererProvider$Context.class */
    public static class Context {
        private final EntityRenderDispatcher entityRenderDispatcher;
        private final ItemModelResolver itemModelResolver;
        private final MapRenderer mapRenderer;
        private final BlockRenderDispatcher blockRenderDispatcher;
        private final ResourceManager resourceManager;
        private final EntityModelSet modelSet;
        private final EquipmentAssetManager equipmentAssets;
        private final Font font;
        private final EquipmentLayerRenderer equipmentRenderer;
        private final AtlasManager atlasManager;
        private final PlayerSkinRenderCache playerSkinRenderCache;

        public Context(EntityRenderDispatcher $$0, ItemModelResolver $$1, MapRenderer $$2, BlockRenderDispatcher $$3, ResourceManager $$4, EntityModelSet $$5, EquipmentAssetManager $$6, AtlasManager $$7, Font $$8, PlayerSkinRenderCache $$9) {
            this.entityRenderDispatcher = $$0;
            this.itemModelResolver = $$1;
            this.mapRenderer = $$2;
            this.blockRenderDispatcher = $$3;
            this.resourceManager = $$4;
            this.modelSet = $$5;
            this.equipmentAssets = $$6;
            this.font = $$8;
            this.atlasManager = $$7;
            this.playerSkinRenderCache = $$9;
            this.equipmentRenderer = new EquipmentLayerRenderer($$6, $$7.getAtlasOrThrow(AtlasIds.ARMOR_TRIMS));
        }

        public EntityRenderDispatcher getEntityRenderDispatcher() {
            return this.entityRenderDispatcher;
        }

        public ItemModelResolver getItemModelResolver() {
            return this.itemModelResolver;
        }

        public MapRenderer getMapRenderer() {
            return this.mapRenderer;
        }

        public BlockRenderDispatcher getBlockRenderDispatcher() {
            return this.blockRenderDispatcher;
        }

        public ResourceManager getResourceManager() {
            return this.resourceManager;
        }

        public EntityModelSet getModelSet() {
            return this.modelSet;
        }

        public EquipmentAssetManager getEquipmentAssets() {
            return this.equipmentAssets;
        }

        public EquipmentLayerRenderer getEquipmentRenderer() {
            return this.equipmentRenderer;
        }

        public MaterialSet getMaterials() {
            return this.atlasManager;
        }

        public TextureAtlas getAtlas(Identifier $$0) {
            return this.atlasManager.getAtlasOrThrow($$0);
        }

        public ModelPart bakeLayer(ModelLayerLocation $$0) {
            return this.modelSet.bakeLayer($$0);
        }

        public Font getFont() {
            return this.font;
        }

        public PlayerSkinRenderCache getPlayerSkinRenderCache() {
            return this.playerSkinRenderCache;
        }
    }
}
