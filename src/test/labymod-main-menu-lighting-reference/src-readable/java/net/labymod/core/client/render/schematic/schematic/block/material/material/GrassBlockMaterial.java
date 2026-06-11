package net.labymod.core.client.render.schematic.block.material.material;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.client.render.schematic.SchematicAccessor;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.block.Face;
import net.labymod.core.client.render.schematic.block.material.MaterialRegistry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/block/material/material/GrassBlockMaterial.class */
public class GrassBlockMaterial extends SolidMaterial {
    private final ResourceLocation resourceTop;
    private final ResourceLocation resourceBottom;
    private final ResourceLocation resourceSide;
    private final ResourceLocation resourceSideSnow;

    public GrassBlockMaterial() {
        super("grass_block");
        this.resourceTop = createResource("grass_block_top");
        this.resourceBottom = createResource("dirt");
        this.resourceSide = createResource("grass_block_side");
        this.resourceSideSnow = createResource("grass_block_snow");
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public ResourceLocation getSpriteResource(SchematicAccessor level, int x, int y, int z, Block block, Face face) {
        if (face == Face.TOP) {
            return this.resourceTop;
        }
        if (face == Face.BOTTOM) {
            return this.resourceBottom;
        }
        Block blockAtTop = level.getBlockAt(x, y, z, Face.TOP);
        if (blockAtTop.material() == MaterialRegistry.SNOW) {
            return this.resourceSideSnow;
        }
        return this.resourceSide;
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public int getColor(Block block, Face face) {
        if (face != Face.TOP) {
            return 16777215;
        }
        return 8174955;
    }
}
