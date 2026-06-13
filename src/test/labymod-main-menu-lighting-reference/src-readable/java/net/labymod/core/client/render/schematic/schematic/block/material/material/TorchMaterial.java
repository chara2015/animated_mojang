package net.labymod.core.client.render.schematic.block.material.material;

import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import net.labymod.core.client.render.schematic.SchematicAccessor;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.block.Face;
import net.labymod.core.client.render.schematic.block.ParameterType;
import net.labymod.core.client.render.schematic.block.material.BoundingBox;
import net.labymod.core.client.render.schematic.block.material.RenderLayer;
import net.labymod.core.client.render.schematic.lighting.LightSource;
import net.labymod.core.client.render.schematic.lighting.PointLightSource;
import net.labymod.core.client.render.schematic.particle.ParticleRenderer;
import net.labymod.core.client.render.schematic.particle.particle.FlameParticle;
import net.labymod.core.client.render.schematic.particle.particle.SmokeParticle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/block/material/material/TorchMaterial.class */
public class TorchMaterial extends Material {
    private final Type type;

    public TorchMaterial(String id, Type type) {
        super(id);
        this.type = type;
        this.resourceLocation = createResource(type.getBlockResourceName());
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public int getLight(Block block) {
        return 15;
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public int getLightColor(Block block) {
        return this.type.getColor();
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public boolean isFullBlock() {
        return false;
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public BoundingBox getBoundingBox(SchematicAccessor level, int x, int y, int z, Block block) {
        this.boundingBox.setCorner(4, 0.5f - 0.0625f, 0.625f, 0.5f - 0.0625f);
        this.boundingBox.setCorner(5, 0.5f - 0.0625f, 0.625f, 0.5f + 0.0625f);
        this.boundingBox.setCorner(6, 0.5f + 0.0625f, 0.625f, 0.5f - 0.0625f);
        this.boundingBox.setCorner(7, 0.5f + 0.0625f, 0.625f, 0.5f + 0.0625f);
        this.boundingBox.setCorner(0, 0.5f - 0.0625f, 0.0f, 0.5f - 0.0625f);
        this.boundingBox.setCorner(1, 0.5f - 0.0625f, 0.0f, 0.5f + 0.0625f);
        this.boundingBox.setCorner(2, 0.5f + 0.0625f, 0.0f, 0.5f - 0.0625f);
        this.boundingBox.setCorner(3, 0.5f + 0.0625f, 0.0f, 0.5f + 0.0625f);
        String facing = (String) block.getParameter(ParameterType.FACING, "none");
        boolean south = facing.equals(ParameterType.SOUTH);
        if (facing.equals(ParameterType.NORTH) || south) {
            this.boundingBox.rotateX(0.5f, 1.0f, 0.5f + (0.1f * (south ? -1 : 1)), 30 * (south ? 1 : -1));
        } else {
            boolean west = facing.equals(ParameterType.WEST);
            this.boundingBox.rotateZ(0.5f + (0.1f * (west ? 1 : -1)), 1.0f, 0.5f, 30 * (west ? 1 : -1));
        }
        return super.getBoundingBox(level, x, y, z, block);
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public LightSource createLightSource(int x, int y, int z, Block block) {
        float centerX = x + 0.5f;
        float centerY = y + 0.75f;
        float centerZ = z + 0.5f;
        int color = getLightColor(block);
        PointLightSource lightSource = new PointLightSource();
        lightSource.getColor().set(((color >> 16) & 255) / 255.0f, ((color >> 8) & 255) / 255.0f, (color & 255) / 255.0f);
        lightSource.getPosition().set(centerX, centerY, centerZ);
        lightSource.setConstant(0.12f);
        lightSource.setQuadratic(0.256f);
        lightSource.setLinear(0.2f);
        return lightSource;
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public TextureUV getUV(SchematicAccessor level, int x, int y, int z, TextureAtlas atlas, Block block, Face face) {
        return getUVCut(atlas, this.resourceLocation, 7, face == Face.BOTTOM ? 14 : 6, 2, face.isYAxis() ? 2 : 10);
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public void randomDisplayTick(SchematicAccessor level, ParticleRenderer particleRenderer, Block block, int x, int y, int z) {
        double centerX;
        double centerZ;
        double finalY;
        centerX = x + 0.5f;
        double centerY = y + 0.7f;
        centerZ = z + 0.5f;
        String facing = (String) block.getParameter(ParameterType.FACING, "none");
        finalY = centerY + 0.0d;
        switch (facing) {
            case "east":
                double eastX = centerX - 0.2d;
                particleRenderer.spawnParticle(new SmokeParticle(level, eastX, finalY, centerZ));
                particleRenderer.spawnParticle(new FlameParticle(level, eastX, finalY, centerZ, this.type));
                break;
            case "west":
                double westX = centerX + 0.2d;
                particleRenderer.spawnParticle(new SmokeParticle(level, westX, finalY, centerZ));
                particleRenderer.spawnParticle(new FlameParticle(level, westX, finalY, centerZ, this.type));
                break;
            case "south":
                double southZ = centerZ - 0.2d;
                particleRenderer.spawnParticle(new SmokeParticle(level, centerX, finalY, southZ));
                particleRenderer.spawnParticle(new FlameParticle(level, centerX, finalY, southZ, this.type));
                break;
            case "north":
                double northZ = centerZ + 0.2d;
                particleRenderer.spawnParticle(new SmokeParticle(level, centerX, finalY, northZ));
                particleRenderer.spawnParticle(new FlameParticle(level, centerX, finalY, northZ, this.type));
                break;
            case "none":
                particleRenderer.spawnParticle(new SmokeParticle(level, centerX, finalY, centerZ));
                particleRenderer.spawnParticle(new FlameParticle(level, centerX, finalY, centerZ, this.type));
                break;
        }
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public RenderLayer getRenderLayer() {
        return RenderLayer.CUBE;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/block/material/material/TorchMaterial$Type.class */
    public enum Type {
        DEFAULT("torch", "flame", 13395456),
        SOUL("soul_torch", "soul_fire_flame", 677541);

        private final String blockResourceName;
        private final String particleResourceName;
        private final int color;

        Type(String blockResourceName, String particleResourceName, int color) {
            this.blockResourceName = blockResourceName;
            this.particleResourceName = particleResourceName;
            this.color = color;
        }

        public String getBlockResourceName() {
            return this.blockResourceName;
        }

        public String getParticleResourceName() {
            return this.particleResourceName;
        }

        public int getColor() {
            return this.color;
        }
    }
}
