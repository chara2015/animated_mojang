package net.labymod.core.client.render.schematic.block.material.material;

import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import net.labymod.core.client.gfx.pipeline.texture.atlas.DefaultTextureUV;
import net.labymod.core.client.render.schematic.SchematicAccessor;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.block.Face;
import net.labymod.core.client.render.schematic.block.ParameterType;
import net.labymod.core.client.render.schematic.block.material.BoundingBox;
import net.labymod.core.client.render.schematic.block.material.RenderLayer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/block/material/material/SlabMaterial.class */
public class SlabMaterial extends Material {
    public SlabMaterial(String id) {
        String str;
        super(id);
        if (this.id.equals("oak_slab")) {
            str = "oak_planks";
        } else {
            str = "cobblestone";
        }
        this.resourceLocation = createResource(str);
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public boolean isFullBlock() {
        return false;
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public boolean shouldRenderFace(SchematicAccessor level, int x, int y, int z, Block block, Face face) {
        String type = (String) block.getParameter(ParameterType.TYPE, "bottom");
        Block blockAtFace = level.getBlockAt(x, y, z, face);
        Material materialAtFace = blockAtFace.material();
        String typeAtFace = (String) blockAtFace.getParameter(ParameterType.TYPE, "bottom");
        if (materialAtFace != this || !type.equals(typeAtFace)) {
            if (materialAtFace.isFullBlock() && !materialAtFace.isTranslucent()) {
                return false;
            }
            return super.shouldRenderFace(level, x, y, z, block, face);
        }
        return false;
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public BoundingBox getBoundingBox(SchematicAccessor level, int x, int y, int z, Block block) {
        String type = (String) block.getParameter(ParameterType.TYPE, "bottom");
        switch (type) {
            case "top":
                this.boundingBox.set(0.0f, 0.5f, 0.0f, 1.0f, 1.0f, 1.0f);
                break;
            case "double":
                this.boundingBox.set(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                break;
            default:
                this.boundingBox.set(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
                break;
        }
        return super.getBoundingBox(level, x, y, z, block);
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public TextureUV getUV(SchematicAccessor level, int x, int y, int z, TextureAtlas atlas, Block block, Face face) {
        TextureUV uv = super.getUV(level, x, y, z, atlas, block, face);
        if (uv == null) {
            return null;
        }
        String type = (String) block.getParameter(ParameterType.TYPE, "bottom");
        if (type.equals("double")) {
            return uv;
        }
        float height = uv.getMaxV() - uv.getMinV();
        return new DefaultTextureUV(uv.getMinU(), uv.getMinV(), uv.getMaxU(), uv.getMinV() + ((height / 16.0f) * 8.0f));
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public RenderLayer getRenderLayer() {
        return RenderLayer.CUBE;
    }
}
