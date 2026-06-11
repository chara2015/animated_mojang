package net.labymod.core.client.render.schematic.block.material.material;

import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.render.schematic.SchematicAccessor;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.block.Face;
import net.labymod.core.client.render.schematic.block.ParameterType;
import net.labymod.core.client.render.schematic.block.material.BoundingBox;
import net.labymod.core.client.render.schematic.block.material.MaterialRegistry;
import net.labymod.core.client.render.schematic.block.material.RenderLayer;
import net.labymod.core.client.render.schematic.lighting.LightSource;
import net.labymod.core.client.render.schematic.lighting.PointLightSource;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/block/material/material/LiquidMaterial.class */
public class LiquidMaterial extends Material {
    private int rotation;

    public LiquidMaterial(String id) {
        super(id);
        this.resourceLocation = createResource("lava_flow");
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public boolean isFullBlock() {
        return false;
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public int getLight(Block block) {
        return 15;
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public int getLightColor(Block block) {
        return ColorFormat.ARGB32.pack(1.0f, 0.44f, 0.05f, 1.0f);
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public boolean shouldRenderFace(SchematicAccessor level, int x, int y, int z, Block block, Face face) {
        Block blockAtFace = level.getBlockAt(x, y, z, face);
        Material materialAtFace = blockAtFace.material();
        return materialAtFace == this ? (face == Face.TOP || face == Face.BOTTOM) ? false : true : !materialAtFace.isFullBlock();
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public BoundingBox getBoundingBox(SchematicAccessor level, int x, int y, int z, Block block) {
        int iIntValue;
        int iIntValue2;
        int iIntValue3;
        int iIntValue4;
        int length = ((Integer) block.getParameter(ParameterType.LEVEL, 15)).intValue();
        if (level.getBlockAt(x, y + 1, z).material() == this) {
            length = 0;
        }
        this.boundingBox.setCorner(0, 0.0f, 0.0f, 0.0f);
        this.boundingBox.setCorner(1, 0.0f, 0.0f, 1.0f);
        this.boundingBox.setCorner(2, 1.0f, 0.0f, 0.0f);
        this.boundingBox.setCorner(3, 1.0f, 0.0f, 1.0f);
        this.rotation = -1;
        int edge1 = length;
        int edge2 = length;
        int edge3 = length;
        int edge4 = length;
        Block otherBlock = level.getBlockAt(x - 1, y, z);
        if (otherBlock.material() == this) {
            if (level.getBlockAt(x - 1, y + 1, z).material() == this) {
                iIntValue4 = 0;
            } else {
                iIntValue4 = ((Integer) otherBlock.getParameter(ParameterType.LEVEL, 15)).intValue();
            }
            int otherLength = iIntValue4;
            edge1 = Math.min(edge1, otherLength);
            edge2 = Math.min(edge2, otherLength);
            if (otherLength < length) {
                this.rotation = 1;
            }
        } else if (otherBlock.material() == MaterialRegistry.AIR && level.getBlockAt(x - 1, y - 1, z).material() == this && level.getBlockAt(x, y + 1, z).material() != this) {
            edge1 = 6;
            edge2 = 6;
        }
        Block otherBlock2 = level.getBlockAt(x + 1, y, z);
        if (otherBlock2.material() == this) {
            if (level.getBlockAt(x + 1, y + 1, z).material() == this) {
                iIntValue3 = 0;
            } else {
                iIntValue3 = ((Integer) otherBlock2.getParameter(ParameterType.LEVEL, 15)).intValue();
            }
            int otherLength2 = iIntValue3;
            edge3 = Math.min(edge3, otherLength2);
            edge4 = Math.min(edge4, otherLength2);
            if (otherLength2 < length) {
                this.rotation = 3;
            }
        } else if (otherBlock2.material() == MaterialRegistry.AIR && level.getBlockAt(x + 1, y - 1, z).material() == this && level.getBlockAt(x, y + 1, z).material() != this) {
            edge3 = 6;
            edge4 = 6;
        }
        Block otherBlock3 = level.getBlockAt(x, y, z + 1);
        if (otherBlock3.material() == this) {
            if (level.getBlockAt(x, y + 1, z + 1).material() == this) {
                iIntValue2 = 0;
            } else {
                iIntValue2 = ((Integer) otherBlock3.getParameter(ParameterType.LEVEL, 15)).intValue();
            }
            int otherLength3 = iIntValue2;
            edge2 = Math.min(edge2, otherLength3);
            edge4 = Math.min(edge4, otherLength3);
            if (otherLength3 < length) {
                this.rotation = 0;
            }
        } else if (otherBlock3.material() == MaterialRegistry.AIR && level.getBlockAt(x, y - 1, z + 1).material() == this && level.getBlockAt(x, y + 1, z).material() != this) {
            edge2 = 6;
            edge4 = 6;
        }
        Block otherBlock4 = level.getBlockAt(x, y, z - 1);
        if (otherBlock4.material() == this) {
            if (level.getBlockAt(x, y + 1, z - 1).material() == this) {
                iIntValue = 0;
            } else {
                iIntValue = ((Integer) otherBlock4.getParameter(ParameterType.LEVEL, 15)).intValue();
            }
            int otherLength4 = iIntValue;
            edge1 = Math.min(edge1, otherLength4);
            edge3 = Math.min(edge3, otherLength4);
            if (otherLength4 < length) {
                this.rotation = 2;
            }
        } else if (otherBlock4.material() == MaterialRegistry.AIR && level.getBlockAt(x, y - 1, z - 1).material() == this && level.getBlockAt(x, y + 1, z).material() != this) {
            edge1 = 6;
            edge3 = 6;
        }
        this.boundingBox.setCorner(4, 0.0f, 1.0f - (edge1 / 8.0f), 0.0f);
        this.boundingBox.setCorner(5, 0.0f, 1.0f - (edge2 / 8.0f), 1.0f);
        this.boundingBox.setCorner(6, 1.0f, 1.0f - (edge3 / 8.0f), 0.0f);
        this.boundingBox.setCorner(7, 1.0f, 1.0f - (edge4 / 8.0f), 1.0f);
        return super.getBoundingBox(level, x, y, z, block);
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public LightSource createLightSource(int x, int y, int z, Block block) {
        if (x % 4 != 0 && y % 4 != 0 && z % 4 != 0) {
            return null;
        }
        int color = getLightColor(block);
        PointLightSource lightSource = new PointLightSource();
        lightSource.getPosition().set(x + 0.5f, y + 0.5f, z + 0.5f);
        ColorFormat colorFormat = ColorFormat.ARGB32;
        lightSource.getColor().set(colorFormat.normalizedRed(color), colorFormat.normalizedGreen(color), colorFormat.normalizedBlue(color));
        lightSource.setQuadratic(0.44f);
        lightSource.setLinear(0.35f);
        lightSource.setConstant(1.0f);
        return lightSource;
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public TextureUV getUV(SchematicAccessor level, int x, int y, int z, TextureAtlas atlas, Block block, Face face) {
        long time = (this.rotation == -1 && face == Face.TOP) ? 0L : TimeUtil.getMillis() / 300;
        return super.getUV(level, x, y, z, atlas, block, face, time);
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public int getTextureRotation() {
        if (this.rotation == -1) {
            return 0;
        }
        return this.rotation;
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public RenderLayer getRenderLayer() {
        return RenderLayer.LIQUID;
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public int getVertexType() {
        return 2;
    }
}
