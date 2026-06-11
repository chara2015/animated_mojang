package net.labymod.core.client.render.schematic.block.material.material;

import net.labymod.core.client.render.schematic.block.material.RenderLayer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/block/material/material/EmptyMaterial.class */
public class EmptyMaterial extends Material {
    public EmptyMaterial(String id) {
        super(id);
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public RenderLayer getRenderLayer() {
        return RenderLayer.NONE;
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public float getTransparency() {
        return 1.0f;
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public boolean isFullBlock() {
        return false;
    }
}
