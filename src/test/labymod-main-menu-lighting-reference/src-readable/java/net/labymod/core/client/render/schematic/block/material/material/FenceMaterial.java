package net.labymod.core.client.render.schematic.block.material.material;

import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import net.labymod.core.client.render.schematic.SchematicAccessor;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.block.Face;
import net.labymod.core.client.render.schematic.block.material.RenderLayer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/block/material/material/FenceMaterial.class */
public class FenceMaterial extends Material {
    public FenceMaterial(String id) {
        super(id);
        this.resourceLocation = createResource("oak_planks");
        this.boundingBox.set(0.5f - 0.125f, 0.0f, 0.5f - 0.125f, 0.5f + 0.125f, 1.0f, 0.5f + 0.125f);
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public boolean shouldRenderFace(SchematicAccessor level, int x, int y, int z, Block block, Face face) {
        if (face.isYAxis()) {
            Block blockAtFace = level.getBlockAt(x, y, z, face);
            Material materialAtFace = blockAtFace.material();
            if (materialAtFace != this) {
                if (materialAtFace.isFullBlock() && !materialAtFace.isTranslucent()) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return super.shouldRenderFace(level, x, y, z, block, face);
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public boolean isFullBlock() {
        return false;
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public TextureUV getUV(SchematicAccessor level, int x, int y, int z, TextureAtlas atlas, Block block, Face face) {
        return super.getUVForBoundingBox(atlas, this.resourceLocation, this.boundingBox, face);
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public RenderLayer getRenderLayer() {
        return RenderLayer.CUBE;
    }
}
