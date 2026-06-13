package net.labymod.core.client.render.schematic.block.material.material;

import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.lighting.LightSource;
import net.labymod.core.client.render.schematic.lighting.PointLightSource;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/block/material/material/GlowstoneMaterial.class */
public class GlowstoneMaterial extends SolidMaterial {
    public GlowstoneMaterial() {
        super("glowstone");
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public int getLight(Block block) {
        return 15;
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public int getLightColor(Block block) {
        return ColorFormat.ARGB32.pack(1.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public LightSource createLightSource(int x, int y, int z, Block block) {
        PointLightSource lightSource = new PointLightSource();
        lightSource.getPosition().set(x + 0.5f, y + 0.5f, z + 0.5f);
        lightSource.getColor().set(1.0f, 1.0f, 1.0f);
        lightSource.setQuadratic(0.0075f);
        lightSource.setLinear(0.045f);
        lightSource.setConstant(1.0f);
        return lightSource;
    }
}
