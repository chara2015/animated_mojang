package net.labymod.core.client.render.schematic.block.material.material;

import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import net.labymod.core.client.render.schematic.SchematicAccessor;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.block.Face;
import net.labymod.core.client.render.schematic.block.ParameterType;
import net.labymod.core.client.render.schematic.block.material.BoundingBox;
import net.labymod.core.client.render.schematic.block.material.RenderLayer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/block/material/material/SnowMaterial.class */
public class SnowMaterial extends Material {
    public SnowMaterial() {
        super("snow");
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public boolean isFullBlock() {
        return false;
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public boolean shouldRenderFace(SchematicAccessor level, int x, int y, int z, Block block, Face face) {
        Material materialAtFace = level.getBlockAt(x, y, z, face).material();
        if (face == Face.BOTTOM || materialAtFace == this) {
            return false;
        }
        if (materialAtFace.isFullBlock() && !materialAtFace.isTranslucent()) {
            return false;
        }
        return super.shouldRenderFace(level, x, y, z, block, face);
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public BoundingBox getBoundingBox(SchematicAccessor level, int x, int y, int z, Block block) {
        int layers = ((Integer) block.getParameter(ParameterType.LAYERS, 1)).intValue();
        this.boundingBox.set(0.0f, 0.0f, 0.0f, 1.0f, layers / 8.0f, 1.0f);
        return super.getBoundingBox(level, x, y, z, block);
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public TextureUV getUV(SchematicAccessor level, int x, int y, int z, TextureAtlas atlas, Block block, Face face) {
        return super.getUV(level, x, y, z, atlas, block, face);
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public RenderLayer getRenderLayer() {
        return RenderLayer.CUBE;
    }
}
