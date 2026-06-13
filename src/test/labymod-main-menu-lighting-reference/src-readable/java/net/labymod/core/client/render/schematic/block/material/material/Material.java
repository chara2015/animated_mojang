package net.labymod.core.client.render.schematic.block.material.material;

import java.util.Random;
import net.labymod.api.client.gfx.pipeline.texture.atlas.AnimatedTextureSprite;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.client.gfx.pipeline.texture.atlas.DefaultTextureUV;
import net.labymod.core.client.render.schematic.SchematicAccessor;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.block.BlockRenderer;
import net.labymod.core.client.render.schematic.block.Face;
import net.labymod.core.client.render.schematic.block.material.BoundingBox;
import net.labymod.core.client.render.schematic.block.material.MaterialRegistry;
import net.labymod.core.client.render.schematic.block.material.RenderLayer;
import net.labymod.core.client.render.schematic.lighting.LightSource;
import net.labymod.core.client.render.schematic.particle.ParticleRenderer;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/block/material/material/Material.class */
public abstract class Material {
    protected static final Random RANDOM = new Random();
    protected String id;
    protected ResourceLocation resourceLocation;
    protected final BoundingBox boundingBox = new BoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);

    public abstract RenderLayer getRenderLayer();

    public Material(String id) {
        this.id = id;
        this.resourceLocation = createResource(id);
        MaterialRegistry.register(this);
    }

    protected ResourceLocation createResource(String id) {
        return ResourceLocation.create("labymod", "block/" + id);
    }

    public ResourceLocation getSpriteResource(SchematicAccessor level, int x, int y, int z, Block block, Face face) {
        return this.resourceLocation;
    }

    public int getColor(Block block, Face face) {
        return 16777215;
    }

    public BoundingBox getBoundingBox(SchematicAccessor level, int x, int y, int z, Block block) {
        return this.boundingBox;
    }

    public boolean shouldRenderFace(SchematicAccessor level, int x, int y, int z, Block block, Face face) {
        Block facingBlock = level.getBlockAt(x, y, z, face);
        return (isFullBlock() && facingBlock.material().isFullBlock() && !facingBlock.material().isTranslucent()) ? false : true;
    }

    public TextureUV getUV(SchematicAccessor level, int x, int y, int z, TextureAtlas atlas, Block block, Face face) {
        TextureSprite sprite = getSprite(level, x, y, z, atlas, block, face);
        if (sprite == null) {
            return null;
        }
        return sprite.uv();
    }

    protected TextureUV getUV(SchematicAccessor level, int x, int y, int z, TextureAtlas atlas, Block block, Face face, long time) {
        TextureSprite sprite = getSprite(level, x, y, z, atlas, block, face);
        if (!(sprite instanceof AnimatedTextureSprite)) {
            if (sprite == null) {
                return null;
            }
            return sprite.uv();
        }
        AnimatedTextureSprite animatedTextureSprite = (AnimatedTextureSprite) sprite;
        return animatedTextureSprite.uv((int) (time % ((long) animatedTextureSprite.getFrames())));
    }

    @Nullable
    public TextureSprite getSprite(SchematicAccessor level, int x, int y, int z, TextureAtlas atlas, Block block, Face face) {
        ResourceLocation spriteResource = block.material().getSpriteResource(level, x, y, z, block, face);
        if (spriteResource == null) {
            return null;
        }
        return atlas.findSprite(spriteResource);
    }

    protected TextureUV getUVForBoundingBox(TextureAtlas atlas, ResourceLocation resourceLocation, BoundingBox boundingBox, Face face) {
        TextureUV uv;
        TextureSprite sprite = atlas.findSprite(resourceLocation);
        if (sprite == null || (uv = sprite.uv()) == null) {
            return null;
        }
        FloatVector3 bottom0 = boundingBox.getCorner(0);
        FloatVector3 top0 = boundingBox.getCorner(4);
        FloatVector3 top1 = boundingBox.getCorner(5);
        FloatVector3 top2 = boundingBox.getCorner(6);
        float minU = uv.getMinU();
        float minV = uv.getMinV();
        float maxU = uv.getMaxU();
        float maxV = uv.getMaxV();
        float ratioU = (maxU - minU) / 16.0f;
        float ratioV = (maxV - minV) / 16.0f;
        float x1 = 0.0f;
        float z1 = 0.0f;
        float x2 = 0.0f;
        float z2 = 0.0f;
        if (face == Face.TOP || face == Face.BOTTOM) {
            x1 = top0.getX();
            z1 = top0.getZ();
            x2 = top2.getX();
            z2 = top1.getZ();
        }
        if (face == Face.NORTH || face == Face.SOUTH) {
            x1 = top0.getX();
            z1 = bottom0.getY();
            x2 = top2.getX();
            z2 = top0.getY();
        }
        if (face == Face.EAST || face == Face.WEST) {
            x1 = top0.getZ();
            z1 = bottom0.getY();
            x2 = top1.getZ();
            z2 = top0.getY();
        }
        float minX = Math.min(x1, x2) * 16.0f;
        float minY = Math.min(z1, z2) * 16.0f;
        float maxX = Math.max(x1, x2) * 16.0f;
        float maxY = Math.max(z1, z2) * 16.0f;
        float width = maxX - minX;
        float height = maxY - minY;
        float minU2 = minU + (ratioU * minX);
        float minV2 = minV + (ratioV * minY);
        float maxU2 = minU2 + (ratioU * width);
        float maxV2 = minV2 + (ratioV * height);
        return new DefaultTextureUV(minU2, minV2, maxU2, maxV2);
    }

    protected TextureUV getUVCut(TextureAtlas atlas, ResourceLocation resourceLocation, int x, int y, int width, int height) {
        TextureUV uv;
        TextureSprite sprite = atlas.findSprite(resourceLocation);
        if (sprite == null || (uv = sprite.uv()) == null) {
            return null;
        }
        float minU = uv.getMinU();
        float minV = uv.getMinV();
        float maxU = uv.getMaxU();
        float maxV = uv.getMaxV();
        float ratioU = (maxU - minU) / 16.0f;
        float ratioV = (maxV - minV) / 16.0f;
        float minU2 = minU + (ratioU * x);
        float minV2 = minV + (ratioV * y);
        float maxU2 = minU2 + (ratioU * width);
        float maxV2 = minV2 + (ratioV * height);
        return new DefaultTextureUV(minU2, minV2, maxU2, maxV2);
    }

    public int getTextureRotation() {
        return 0;
    }

    public LightSource createLightSource(int x, int y, int z, Block block) {
        return null;
    }

    public void render(BlockRenderer renderer, TextureAtlas atlas, VertexConsumer consumer, Block block, SchematicAccessor level, int x, int y, int z, BoundingBox boundingBox) {
        for (Face face : Face.VALUES) {
            if (shouldRenderFace(level, x, y, z, block, face)) {
                renderer.renderFace(atlas, consumer, block, face, x, y, z, boundingBox);
            }
        }
    }

    public float getTransparency() {
        return 0.0f;
    }

    public boolean isTranslucent() {
        return getTransparency() > 0.0f || !isFullBlock();
    }

    public boolean isFullBlock() {
        return true;
    }

    public int getLight(Block block) {
        return 0;
    }

    public int getLightColor(Block block) {
        return 0;
    }

    public Boolean isLightSource(Block block) {
        return Boolean.valueOf(getLight(block) > 0);
    }

    public void randomDisplayTick(SchematicAccessor level, ParticleRenderer particleRenderer, Block block, int x, int y, int z) {
    }

    public int getVertexType() {
        return 0;
    }

    public String getId() {
        return this.id;
    }

    public String toString() {
        return getId();
    }
}
