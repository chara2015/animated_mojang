package net.labymod.core.client.render.schematic.block.material.material;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.client.render.schematic.SchematicAccessor;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.block.ParameterType;
import net.labymod.core.client.render.schematic.block.material.material.TorchMaterial;
import net.labymod.core.client.render.schematic.lighting.LightSource;
import net.labymod.core.client.render.schematic.lighting.PointLightSource;
import net.labymod.core.client.render.schematic.particle.ParticleRenderer;
import net.labymod.core.client.render.schematic.particle.particle.FlameParticle;
import net.labymod.core.client.render.schematic.particle.particle.SmokeParticle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/block/material/material/FurnaceMaterial.class */
public class FurnaceMaterial extends FacingMaterial {
    private final ResourceLocation resourceFrontLit;

    public FurnaceMaterial() {
        super("furnace");
        this.resourceFrontLit = createResource("furnace_front_lit");
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.FacingMaterial
    protected ResourceLocation getFrontSpriteResource(Block block) {
        if (((Boolean) block.getParameter(ParameterType.LIT, false)).booleanValue()) {
            return this.resourceFrontLit;
        }
        return super.getFrontSpriteResource(block);
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public LightSource createLightSource(int x, int y, int z, Block block) {
        boolean lit = ((Boolean) block.getParameter(ParameterType.LIT, false)).booleanValue();
        if (!lit) {
            return null;
        }
        int color = getLightColor(block);
        PointLightSource lightSource = new PointLightSource();
        lightSource.getColor().set(((color >> 16) & 255) / 255.0f, ((color >> 8) & 255) / 255.0f, (color & 255) / 255.0f);
        lightSource.getPosition().set(x + 0.5f, y + 0.5f, z + 0.5f);
        lightSource.setConstant(0.12f);
        lightSource.setQuadratic(0.256f);
        lightSource.setLinear(0.2f);
        return lightSource;
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public void randomDisplayTick(SchematicAccessor level, ParticleRenderer particleRenderer, Block block, int x, int y, int z) {
        float posX;
        float posY;
        float posZ;
        float edgeOffset;
        boolean lit = ((Boolean) block.getParameter(ParameterType.LIT, false)).booleanValue();
        if (!lit) {
        }
        posX = x + 0.5f;
        posY = y + 0.0f + ((RANDOM.nextFloat() * 6.0f) / 16.0f);
        posZ = z + 0.5f;
        edgeOffset = (RANDOM.nextFloat() * 0.6f) - 0.3f;
        String facing = (String) block.getParameter(ParameterType.FACING, "none");
        switch (facing) {
            case "east":
                particleRenderer.spawnParticle(new SmokeParticle(level, posX + 0.52f, posY, posZ + edgeOffset));
                particleRenderer.spawnParticle(new FlameParticle(level, posX + 0.52f, posY, posZ + edgeOffset, TorchMaterial.Type.DEFAULT));
                break;
            case "west":
                particleRenderer.spawnParticle(new SmokeParticle(level, posX - 0.52f, posY, posZ + edgeOffset));
                particleRenderer.spawnParticle(new FlameParticle(level, posX - 0.52f, posY, posZ + edgeOffset, TorchMaterial.Type.DEFAULT));
                break;
            case "south":
                particleRenderer.spawnParticle(new SmokeParticle(level, posX + edgeOffset, posY, posZ + 0.52f));
                particleRenderer.spawnParticle(new FlameParticle(level, posX + edgeOffset, posY, posZ + 0.52f, TorchMaterial.Type.DEFAULT));
                break;
            case "north":
                particleRenderer.spawnParticle(new SmokeParticle(level, posX + edgeOffset, posY, posZ - 0.52f));
                particleRenderer.spawnParticle(new FlameParticle(level, posX + edgeOffset, posY, posZ - 0.52f, TorchMaterial.Type.DEFAULT));
                break;
        }
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public int getLight(Block block) {
        return ((Boolean) block.getParameter(ParameterType.LIT, false)).booleanValue() ? 13 : 0;
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public int getLightColor(Block block) {
        return 13395456;
    }
}
