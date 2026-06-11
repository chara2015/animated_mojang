package net.minecraft.client.renderer.item;

import com.mojang.serialization.MapCodec;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.PlayerSkinRenderCache;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ResolvableModel;
import net.minecraft.util.RegistryContextSwapper;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/item/ItemModel.class */
public interface ItemModel {

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/item/ItemModel$Unbaked.class */
    public interface Unbaked extends ResolvableModel {
        MapCodec<? extends Unbaked> type();

        ItemModel bake(BakingContext bakingContext);
    }

    void update(ItemStackRenderState itemStackRenderState, ItemStack itemStack, ItemModelResolver itemModelResolver, ItemDisplayContext itemDisplayContext, ClientLevel clientLevel, ItemOwner itemOwner, int i);

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/item/ItemModel$BakingContext.class */
    public static final class BakingContext extends Record implements SpecialModelRenderer.BakingContext {
        private final ModelBaker blockModelBaker;
        private final EntityModelSet entityModelSet;
        private final MaterialSet materials;
        private final PlayerSkinRenderCache playerSkinRenderCache;
        private final ItemModel missingItemModel;
        private final RegistryContextSwapper contextSwapper;

        public BakingContext(ModelBaker $$0, EntityModelSet $$1, MaterialSet $$2, PlayerSkinRenderCache $$3, ItemModel $$4, RegistryContextSwapper $$5) {
            this.blockModelBaker = $$0;
            this.entityModelSet = $$1;
            this.materials = $$2;
            this.playerSkinRenderCache = $$3;
            this.missingItemModel = $$4;
            this.contextSwapper = $$5;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, BakingContext.class), BakingContext.class, "blockModelBaker;entityModelSet;materials;playerSkinRenderCache;missingItemModel;contextSwapper", "FIELD:Lnet/minecraft/client/renderer/item/ItemModel$BakingContext;->blockModelBaker:Lnet/minecraft/client/resources/model/ModelBaker;", "FIELD:Lnet/minecraft/client/renderer/item/ItemModel$BakingContext;->entityModelSet:Lnet/minecraft/client/model/geom/EntityModelSet;", "FIELD:Lnet/minecraft/client/renderer/item/ItemModel$BakingContext;->materials:Lnet/minecraft/client/resources/model/MaterialSet;", "FIELD:Lnet/minecraft/client/renderer/item/ItemModel$BakingContext;->playerSkinRenderCache:Lnet/minecraft/client/renderer/PlayerSkinRenderCache;", "FIELD:Lnet/minecraft/client/renderer/item/ItemModel$BakingContext;->missingItemModel:Lnet/minecraft/client/renderer/item/ItemModel;", "FIELD:Lnet/minecraft/client/renderer/item/ItemModel$BakingContext;->contextSwapper:Lnet/minecraft/util/RegistryContextSwapper;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, BakingContext.class), BakingContext.class, "blockModelBaker;entityModelSet;materials;playerSkinRenderCache;missingItemModel;contextSwapper", "FIELD:Lnet/minecraft/client/renderer/item/ItemModel$BakingContext;->blockModelBaker:Lnet/minecraft/client/resources/model/ModelBaker;", "FIELD:Lnet/minecraft/client/renderer/item/ItemModel$BakingContext;->entityModelSet:Lnet/minecraft/client/model/geom/EntityModelSet;", "FIELD:Lnet/minecraft/client/renderer/item/ItemModel$BakingContext;->materials:Lnet/minecraft/client/resources/model/MaterialSet;", "FIELD:Lnet/minecraft/client/renderer/item/ItemModel$BakingContext;->playerSkinRenderCache:Lnet/minecraft/client/renderer/PlayerSkinRenderCache;", "FIELD:Lnet/minecraft/client/renderer/item/ItemModel$BakingContext;->missingItemModel:Lnet/minecraft/client/renderer/item/ItemModel;", "FIELD:Lnet/minecraft/client/renderer/item/ItemModel$BakingContext;->contextSwapper:Lnet/minecraft/util/RegistryContextSwapper;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, BakingContext.class, Object.class), BakingContext.class, "blockModelBaker;entityModelSet;materials;playerSkinRenderCache;missingItemModel;contextSwapper", "FIELD:Lnet/minecraft/client/renderer/item/ItemModel$BakingContext;->blockModelBaker:Lnet/minecraft/client/resources/model/ModelBaker;", "FIELD:Lnet/minecraft/client/renderer/item/ItemModel$BakingContext;->entityModelSet:Lnet/minecraft/client/model/geom/EntityModelSet;", "FIELD:Lnet/minecraft/client/renderer/item/ItemModel$BakingContext;->materials:Lnet/minecraft/client/resources/model/MaterialSet;", "FIELD:Lnet/minecraft/client/renderer/item/ItemModel$BakingContext;->playerSkinRenderCache:Lnet/minecraft/client/renderer/PlayerSkinRenderCache;", "FIELD:Lnet/minecraft/client/renderer/item/ItemModel$BakingContext;->missingItemModel:Lnet/minecraft/client/renderer/item/ItemModel;", "FIELD:Lnet/minecraft/client/renderer/item/ItemModel$BakingContext;->contextSwapper:Lnet/minecraft/util/RegistryContextSwapper;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public ModelBaker blockModelBaker() {
            return this.blockModelBaker;
        }

        @Override // net.minecraft.client.renderer.special.SpecialModelRenderer.BakingContext
        public EntityModelSet entityModelSet() {
            return this.entityModelSet;
        }

        @Override // net.minecraft.client.renderer.special.SpecialModelRenderer.BakingContext
        public MaterialSet materials() {
            return this.materials;
        }

        @Override // net.minecraft.client.renderer.special.SpecialModelRenderer.BakingContext
        public PlayerSkinRenderCache playerSkinRenderCache() {
            return this.playerSkinRenderCache;
        }

        public ItemModel missingItemModel() {
            return this.missingItemModel;
        }

        public RegistryContextSwapper contextSwapper() {
            return this.contextSwapper;
        }
    }
}
