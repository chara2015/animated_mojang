package net.labymod.core.client.render.schematic.block.material.material;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/block/material/material/PumpkinMaterial.class */
public class PumpkinMaterial extends FacingMaterial {
    public PumpkinMaterial(String id) {
        super(id);
        this.resourceTop = createResource("pumpkin_top");
        this.resourceBottom = createResource("pumpkin_top");
        this.resourceSide = createResource("pumpkin");
        this.resourceFront = this.resourceLocation;
    }
}
