package net.labymod.core.client.render.schematic.block.material.material;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.client.render.schematic.SchematicAccessor;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.block.Face;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/block/material/material/CraftingTableMaterial.class */
public class CraftingTableMaterial extends SolidMaterial {
    private final ResourceLocation resourceTop;
    private final ResourceLocation resourceBottom;
    private final ResourceLocation resourceSide;

    public CraftingTableMaterial() {
        super("crafting_table");
        this.resourceTop = createResource("crafting_table_top");
        this.resourceBottom = createResource("crafting_table_bottom");
        this.resourceSide = createResource("crafting_table_side");
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public ResourceLocation getSpriteResource(SchematicAccessor level, int x, int y, int z, Block block, Face face) {
        if (face == Face.TOP) {
            return this.resourceTop;
        }
        if (face == Face.BOTTOM) {
            return this.resourceBottom;
        }
        return this.resourceSide;
    }
}
