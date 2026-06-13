package net.labymod.core.client.render.schematic.block.material.material;

import net.labymod.core.client.render.schematic.SchematicAccessor;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.block.Face;
import net.labymod.core.client.render.schematic.block.material.BoundingBox;
import net.labymod.core.client.render.schematic.block.material.RenderLayer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/block/material/material/FloorPlaneMaterial.class */
public class FloorPlaneMaterial extends Material {
    public FloorPlaneMaterial(String id) {
        super(id);
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public boolean isFullBlock() {
        return false;
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public boolean shouldRenderFace(SchematicAccessor level, int x, int y, int z, Block block, Face face) {
        return face == Face.TOP;
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public BoundingBox getBoundingBox(SchematicAccessor level, int x, int y, int z, Block block) {
        this.boundingBox.set(0.0f, 0.0f, 0.0f, 1.0f, 0.01f, 1.0f);
        return super.getBoundingBox(level, x, y, z, block);
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public RenderLayer getRenderLayer() {
        return RenderLayer.CUT_OUT;
    }
}
