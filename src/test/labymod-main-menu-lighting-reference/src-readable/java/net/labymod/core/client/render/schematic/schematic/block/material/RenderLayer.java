package net.labymod.core.client.render.schematic.block.material;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/block/material/RenderLayer.class */
public enum RenderLayer {
    NONE("none"),
    LIQUID("liquid"),
    CUBE("solid"),
    CUT_OUT("cutout");

    private final String name;

    RenderLayer(String name) {
        this.name = name;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.name;
    }
}
