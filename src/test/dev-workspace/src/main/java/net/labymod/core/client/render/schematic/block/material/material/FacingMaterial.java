package net.labymod.core.client.render.schematic.block.material.material;

import java.util.Locale;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.client.render.schematic.SchematicAccessor;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.block.Face;
import net.labymod.core.client.render.schematic.block.ParameterType;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/block/material/material/FacingMaterial.class */
public class FacingMaterial extends SolidMaterial {
    protected ResourceLocation resourceTop;
    protected ResourceLocation resourceBottom;
    protected ResourceLocation resourceFront;
    protected ResourceLocation resourceSide;

    public FacingMaterial(String id) {
        super(id);
        this.resourceTop = createResource(id + "_top");
        this.resourceBottom = createResource(id + "_bottom");
        this.resourceFront = createResource(id + "_front");
        this.resourceSide = createResource(id + "_side");
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public ResourceLocation getSpriteResource(SchematicAccessor level, int x, int y, int z, Block block, Face face) {
        if (face == Face.TOP) {
            return this.resourceTop;
        }
        if (face == Face.BOTTOM) {
            return this.resourceBottom;
        }
        String facing = (String) block.getParameter(ParameterType.FACING, ParameterType.NORTH);
        if (Face.valueOf(facing.toUpperCase(Locale.ENGLISH)) == face) {
            return getFrontSpriteResource(block);
        }
        return this.resourceSide;
    }

    protected ResourceLocation getFrontSpriteResource(Block block) {
        return this.resourceFront;
    }
}
