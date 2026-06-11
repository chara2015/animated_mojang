package net.labymod.core.client.render.schematic.block.material.material;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.client.render.schematic.SchematicAccessor;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.block.Face;
import net.labymod.core.client.render.schematic.block.ParameterType;
import net.labymod.core.client.render.schematic.block.material.BoundingBox;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/block/material/material/RailMaterial.class */
public class RailMaterial extends FloorPlaneMaterial {
    private final ResourceLocation resourceStraight;
    private final ResourceLocation resourceCorner;

    public RailMaterial(String id) {
        super(id);
        this.resourceStraight = createResource("rail");
        this.resourceCorner = createResource("rail_corner");
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public ResourceLocation getSpriteResource(SchematicAccessor level, int x, int y, int z, Block block, Face face) {
        String shape = (String) block.getParameter(ParameterType.SHAPE, ParameterType.NORTH);
        if (shape.equals("north_west") || shape.equals("north_east") || shape.equals("south_west") || shape.equals("south_east")) {
            return this.resourceCorner;
        }
        return this.resourceStraight;
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.FloorPlaneMaterial, net.labymod.core.client.render.schematic.block.material.material.Material
    public BoundingBox getBoundingBox(SchematicAccessor level, int x, int y, int z, Block block) {
        String shape = (String) block.getParameter(ParameterType.SHAPE, "north_south");
        this.boundingBox.set(0.0f, 0.0f, 0.0f, 1.0f, 0.01f, 1.0f);
        switch (shape) {
            case "east_west":
            case "north_west":
                this.boundingBox.rotateY(0.5f, 0.0f, 0.5f, 90.0f);
                break;
            case "south_west":
                this.boundingBox.rotateY(0.5f, 0.0f, 0.5f, 180.0f);
                break;
        }
        return this.boundingBox;
    }
}
