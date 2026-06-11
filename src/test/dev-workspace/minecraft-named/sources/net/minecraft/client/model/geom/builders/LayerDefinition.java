package net.minecraft.client.model.geom.builders;

import net.minecraft.client.model.geom.ModelPart;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/geom/builders/LayerDefinition.class */
public class LayerDefinition {
    private final MeshDefinition mesh;
    private final MaterialDefinition material;

    private LayerDefinition(MeshDefinition $$0, MaterialDefinition $$1) {
        this.mesh = $$0;
        this.material = $$1;
    }

    public LayerDefinition apply(MeshTransformer $$0) {
        return new LayerDefinition($$0.apply(this.mesh), this.material);
    }

    public ModelPart bakeRoot() {
        return this.mesh.getRoot().bake(this.material.xTexSize, this.material.yTexSize);
    }

    public static LayerDefinition create(MeshDefinition $$0, int $$1, int $$2) {
        return new LayerDefinition($$0, new MaterialDefinition($$1, $$2));
    }
}
