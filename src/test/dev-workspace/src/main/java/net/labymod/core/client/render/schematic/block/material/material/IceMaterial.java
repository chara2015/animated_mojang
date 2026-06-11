package net.labymod.core.client.render.schematic.block.material.material;

import net.labymod.core.client.render.schematic.SchematicAccessor;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.block.Face;
import net.labymod.core.client.render.schematic.block.material.RenderLayer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/block/material/material/IceMaterial.class */
public class IceMaterial extends Material {
    public IceMaterial(String id) {
        super(id);
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public RenderLayer getRenderLayer() {
        return RenderLayer.CUT_OUT;
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public float getTransparency() {
        return 0.75f;
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public boolean shouldRenderFace(SchematicAccessor level, int x, int y, int z, Block block, Face face) {
        if (level.getBlockAt(x, y, z, face).material() == this) {
            return false;
        }
        return super.shouldRenderFace(level, x, y, z, block, face);
    }
}
