package net.labymod.core.client.render.schematic.block.material.material;

import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.block.Face;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/block/material/material/GrassMaterial.class */
public class GrassMaterial extends CrossPlanesMaterial {
    public GrassMaterial() {
        super("grass");
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public int getColor(Block block, Face face) {
        return 8174955;
    }
}
