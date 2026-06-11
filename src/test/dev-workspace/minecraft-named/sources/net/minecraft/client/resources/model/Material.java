package net.minecraft.client.resources.model;

import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/model/Material.class */
public class Material {
    public static final Comparator<Material> COMPARATOR = Comparator.comparing((v0) -> {
        return v0.atlasLocation();
    }).thenComparing((v0) -> {
        return v0.texture();
    });
    private final Identifier atlasLocation;
    private final Identifier texture;
    private RenderType renderType;

    public Material(Identifier $$0, Identifier $$1) {
        this.atlasLocation = $$0;
        this.texture = $$1;
    }

    public Identifier atlasLocation() {
        return this.atlasLocation;
    }

    public Identifier texture() {
        return this.texture;
    }

    public RenderType renderType(Function<Identifier, RenderType> $$0) {
        if (this.renderType == null) {
            this.renderType = $$0.apply(this.atlasLocation);
        }
        return this.renderType;
    }

    public VertexConsumer buffer(MaterialSet $$0, MultiBufferSource $$1, Function<Identifier, RenderType> $$2) {
        return $$0.get(this).wrap($$1.getBuffer(renderType($$2)));
    }

    public VertexConsumer buffer(MaterialSet $$0, MultiBufferSource $$1, Function<Identifier, RenderType> $$2, boolean $$3, boolean $$4) {
        return $$0.get(this).wrap(ItemRenderer.getFoilBuffer($$1, renderType($$2), $$3, $$4));
    }

    public boolean equals(Object $$0) {
        if (this == $$0) {
            return true;
        }
        if ($$0 == null || getClass() != $$0.getClass()) {
            return false;
        }
        Material $$1 = (Material) $$0;
        return this.atlasLocation.equals($$1.atlasLocation) && this.texture.equals($$1.texture);
    }

    public int hashCode() {
        return Objects.hash(this.atlasLocation, this.texture);
    }

    public String toString() {
        return "Material{atlasLocation=" + String.valueOf(this.atlasLocation) + ", texture=" + String.valueOf(this.texture) + "}";
    }
}
