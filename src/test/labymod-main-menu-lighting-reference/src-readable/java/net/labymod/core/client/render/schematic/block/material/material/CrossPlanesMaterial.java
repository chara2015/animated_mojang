package net.labymod.core.client.render.schematic.block.material.material;

import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.client.render.schematic.SchematicAccessor;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.block.Face;
import net.labymod.core.client.render.schematic.block.material.BoundingBox;
import net.labymod.core.client.render.schematic.block.material.RenderLayer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/block/material/material/CrossPlanesMaterial.class */
public class CrossPlanesMaterial extends Material {
    public CrossPlanesMaterial(String id) {
        super(id);
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public boolean isFullBlock() {
        return false;
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public boolean shouldRenderFace(SchematicAccessor level, int x, int y, int z, Block block, Face face) {
        return face == Face.NORTH || face == Face.SOUTH;
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public BoundingBox getBoundingBox(SchematicAccessor level, int x, int y, int z, Block block) {
        this.boundingBox.set(0.5f - 0.38f, 0.0f, 0.5f - 0.38f, 0.5f + 0.38f, 1.0f, 0.5f + 0.38f);
        FloatVector3 corner1 = this.boundingBox.getCorner(0).copy();
        FloatVector3 corner2 = this.boundingBox.getCorner(1).copy();
        FloatVector3 corner3 = this.boundingBox.getCorner(4).copy();
        FloatVector3 corner4 = this.boundingBox.getCorner(5).copy();
        this.boundingBox.setCorner(1, corner1.getX(), corner1.getY(), corner1.getZ());
        this.boundingBox.setCorner(0, corner2.getX(), corner2.getY(), corner2.getZ());
        this.boundingBox.setCorner(5, corner3.getX(), corner3.getY(), corner3.getZ());
        this.boundingBox.setCorner(4, corner4.getX(), corner4.getY(), corner4.getZ());
        return this.boundingBox;
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public RenderLayer getRenderLayer() {
        return RenderLayer.CUT_OUT;
    }
}
