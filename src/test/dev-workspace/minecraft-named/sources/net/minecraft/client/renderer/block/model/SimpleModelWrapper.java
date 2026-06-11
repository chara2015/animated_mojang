package net.minecraft.client.renderer.block.model;

import com.google.common.collect.HashMultimap;
import com.mojang.logging.LogUtils;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.QuadCollection;
import net.minecraft.client.resources.model.ResolvedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/SimpleModelWrapper.class */
public final class SimpleModelWrapper extends Record implements BlockModelPart {
    private final QuadCollection quads;
    private final boolean useAmbientOcclusion;
    private final TextureAtlasSprite particleIcon;
    private static final Logger LOGGER = LogUtils.getLogger();

    public SimpleModelWrapper(QuadCollection $$0, boolean $$1, TextureAtlasSprite $$2) {
        this.quads = $$0;
        this.useAmbientOcclusion = $$1;
        this.particleIcon = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, SimpleModelWrapper.class), SimpleModelWrapper.class, "quads;useAmbientOcclusion;particleIcon", "FIELD:Lnet/minecraft/client/renderer/block/model/SimpleModelWrapper;->quads:Lnet/minecraft/client/resources/model/QuadCollection;", "FIELD:Lnet/minecraft/client/renderer/block/model/SimpleModelWrapper;->useAmbientOcclusion:Z", "FIELD:Lnet/minecraft/client/renderer/block/model/SimpleModelWrapper;->particleIcon:Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, SimpleModelWrapper.class), SimpleModelWrapper.class, "quads;useAmbientOcclusion;particleIcon", "FIELD:Lnet/minecraft/client/renderer/block/model/SimpleModelWrapper;->quads:Lnet/minecraft/client/resources/model/QuadCollection;", "FIELD:Lnet/minecraft/client/renderer/block/model/SimpleModelWrapper;->useAmbientOcclusion:Z", "FIELD:Lnet/minecraft/client/renderer/block/model/SimpleModelWrapper;->particleIcon:Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, SimpleModelWrapper.class, Object.class), SimpleModelWrapper.class, "quads;useAmbientOcclusion;particleIcon", "FIELD:Lnet/minecraft/client/renderer/block/model/SimpleModelWrapper;->quads:Lnet/minecraft/client/resources/model/QuadCollection;", "FIELD:Lnet/minecraft/client/renderer/block/model/SimpleModelWrapper;->useAmbientOcclusion:Z", "FIELD:Lnet/minecraft/client/renderer/block/model/SimpleModelWrapper;->particleIcon:Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public QuadCollection quads() {
        return this.quads;
    }

    @Override // net.minecraft.client.renderer.block.model.BlockModelPart
    public boolean useAmbientOcclusion() {
        return this.useAmbientOcclusion;
    }

    @Override // net.minecraft.client.renderer.block.model.BlockModelPart
    public TextureAtlasSprite particleIcon() {
        return this.particleIcon;
    }

    public static BlockModelPart bake(ModelBaker $$0, Identifier $$1, ModelState $$2) {
        ResolvedModel $$3 = $$0.getModel($$1);
        TextureSlots $$4 = $$3.getTopTextureSlots();
        boolean $$5 = $$3.getTopAmbientOcclusion();
        TextureAtlasSprite $$6 = $$3.resolveParticleSprite($$4, $$0);
        QuadCollection $$7 = $$3.bakeTopGeometry($$4, $$0, $$2);
        HashMultimap hashMultimapCreate = null;
        for (BakedQuad $$9 : $$7.getAll()) {
            TextureAtlasSprite $$10 = $$9.sprite();
            if (!$$10.atlasLocation().equals(TextureAtlas.LOCATION_BLOCKS)) {
                if (hashMultimapCreate == null) {
                    hashMultimapCreate = HashMultimap.create();
                }
                hashMultimapCreate.put($$10.atlasLocation(), $$10.contents().name());
            }
        }
        if (hashMultimapCreate != null) {
            LOGGER.warn("Rejecting block model {}, since it contains sprites from outside of supported atlas: {}", $$1, hashMultimapCreate);
            return $$0.missingBlockModelPart();
        }
        return new SimpleModelWrapper($$7, $$5, $$6);
    }

    @Override // net.minecraft.client.renderer.block.model.BlockModelPart
    public List<BakedQuad> getQuads(Direction $$0) {
        return this.quads.getQuads($$0);
    }
}
