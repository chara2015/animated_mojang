package net.minecraft.client.renderer.item;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.block.model.TextureSlots;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ResolvedModel;
import net.minecraft.world.item.ItemDisplayContext;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/item/ModelRenderProperties.class */
public final class ModelRenderProperties extends Record {
    private final boolean usesBlockLight;
    private final TextureAtlasSprite particleIcon;
    private final ItemTransforms transforms;

    public ModelRenderProperties(boolean $$0, TextureAtlasSprite $$1, ItemTransforms $$2) {
        this.usesBlockLight = $$0;
        this.particleIcon = $$1;
        this.transforms = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ModelRenderProperties.class), ModelRenderProperties.class, "usesBlockLight;particleIcon;transforms", "FIELD:Lnet/minecraft/client/renderer/item/ModelRenderProperties;->usesBlockLight:Z", "FIELD:Lnet/minecraft/client/renderer/item/ModelRenderProperties;->particleIcon:Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "FIELD:Lnet/minecraft/client/renderer/item/ModelRenderProperties;->transforms:Lnet/minecraft/client/renderer/block/model/ItemTransforms;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ModelRenderProperties.class), ModelRenderProperties.class, "usesBlockLight;particleIcon;transforms", "FIELD:Lnet/minecraft/client/renderer/item/ModelRenderProperties;->usesBlockLight:Z", "FIELD:Lnet/minecraft/client/renderer/item/ModelRenderProperties;->particleIcon:Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "FIELD:Lnet/minecraft/client/renderer/item/ModelRenderProperties;->transforms:Lnet/minecraft/client/renderer/block/model/ItemTransforms;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ModelRenderProperties.class, Object.class), ModelRenderProperties.class, "usesBlockLight;particleIcon;transforms", "FIELD:Lnet/minecraft/client/renderer/item/ModelRenderProperties;->usesBlockLight:Z", "FIELD:Lnet/minecraft/client/renderer/item/ModelRenderProperties;->particleIcon:Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "FIELD:Lnet/minecraft/client/renderer/item/ModelRenderProperties;->transforms:Lnet/minecraft/client/renderer/block/model/ItemTransforms;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public boolean usesBlockLight() {
        return this.usesBlockLight;
    }

    public TextureAtlasSprite particleIcon() {
        return this.particleIcon;
    }

    public ItemTransforms transforms() {
        return this.transforms;
    }

    public static ModelRenderProperties fromResolvedModel(ModelBaker $$0, ResolvedModel $$1, TextureSlots $$2) {
        TextureAtlasSprite $$3 = $$1.resolveParticleSprite($$2, $$0);
        return new ModelRenderProperties($$1.getTopGuiLight().lightLikeBlock(), $$3, $$1.getTopTransforms());
    }

    public void applyToLayer(ItemStackRenderState.LayerRenderState $$0, ItemDisplayContext $$1) {
        $$0.setUsesBlockLight(this.usesBlockLight);
        $$0.setParticleIcon(this.particleIcon);
        $$0.setTransform(this.transforms.getTransform($$1));
    }
}
