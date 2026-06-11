package net.labymod.core.client.render.schematic.block;

import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.client.gui.background.DynamicBackgroundController;
import net.labymod.core.client.render.schematic.SchematicAccessor;
import net.labymod.core.client.render.schematic.block.material.BoundingBox;
import net.labymod.core.client.render.schematic.block.material.material.Material;
import net.labymod.core.client.render.schematic.lighting.LightSource;
import net.labymod.core.client.render.schematic.lighting.LightSourceRegistry;
import net.labymod.core.client.render.schematic.lighting.legacy.LegacyLightEngine;
import net.labymod.laby3d.api.vertex.VertexConsumer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/block/BlockRenderer.class */
public class BlockRenderer {
    private final SchematicAccessor schematic;

    public BlockRenderer(SchematicAccessor level) {
        this.schematic = level;
    }

    public void renderCube(TextureAtlas atlas, VertexConsumer consumer, Block block, int x, int y, int z) {
        Material material = block.material();
        BoundingBox boundingBox = material.getBoundingBox(this.schematic, x, y, z, block);
        LightSource lightSource = material.createLightSource(x, y, z, block);
        if (lightSource != null) {
            LightSourceRegistry.getInstance().addLightSource(lightSource);
            block.setLightSource(lightSource);
        }
        material.render(this, atlas, consumer, block, this.schematic, x, y, z, boundingBox);
    }

    public void renderFace(TextureAtlas atlas, VertexConsumer consumer, Block block, Face face, int x, int y, int z, BoundingBox bb) {
        Material material = block.material();
        TextureUV uv = material.getUV(this.schematic, x, y, z, atlas, block, face);
        if (uv == null) {
            return;
        }
        renderFace(consumer, block, face, x, y, z, bb, uv);
    }

    public void renderFace(VertexConsumer consumer, Block block, Face face, int x, int y, int z, BoundingBox bb, TextureUV uv) {
        Material material = block.material();
        float minU = uv.getMinU();
        float minV = uv.getMinV();
        float maxU = uv.getMaxU();
        float maxV = uv.getMaxV();
        int color = material.getColor(block, face);
        float r = ((color >> 16) & 255) / 255.0f;
        float g = ((color >> 8) & 255) / 255.0f;
        float b = (color & 255) / 255.0f;
        int rot = material.getTextureRotation();
        switch (face) {
            case TOP:
                renderCorner(consumer, face, x, y, z, bb.getCorner(4, rot), minU, maxV, r, g, b);
                renderCorner(consumer, face, x, y, z, bb.getCorner(5, rot), minU, minV, r, g, b);
                renderCorner(consumer, face, x, y, z, bb.getCorner(7, rot), maxU, minV, r, g, b);
                renderCorner(consumer, face, x, y, z, bb.getCorner(6, rot), maxU, maxV, r, g, b);
                break;
            case BOTTOM:
                renderCorner(consumer, face, x, y, z, bb.getCorner(1, rot), maxU, maxV, r, g, b);
                renderCorner(consumer, face, x, y, z, bb.getCorner(0, rot), maxU, minV, r, g, b);
                renderCorner(consumer, face, x, y, z, bb.getCorner(2, rot), minU, minV, r, g, b);
                renderCorner(consumer, face, x, y, z, bb.getCorner(3, rot), minU, maxV, r, g, b);
                break;
            case NORTH:
                renderCorner(consumer, face, x, y, z, bb.getCorner(4), minU, minV, r, g, b);
                renderCorner(consumer, face, x, y, z, bb.getCorner(6), maxU, minV, r, g, b);
                renderCorner(consumer, face, x, y, z, bb.getCorner(2), maxU, maxV, r, g, b);
                renderCorner(consumer, face, x, y, z, bb.getCorner(0), minU, maxV, r, g, b);
                break;
            case EAST:
                renderCorner(consumer, face, x, y, z, bb.getCorner(6), minU, minV, r, g, b);
                renderCorner(consumer, face, x, y, z, bb.getCorner(7), maxU, minV, r, g, b);
                renderCorner(consumer, face, x, y, z, bb.getCorner(3), maxU, maxV, r, g, b);
                renderCorner(consumer, face, x, y, z, bb.getCorner(2), minU, maxV, r, g, b);
                break;
            case SOUTH:
                renderCorner(consumer, face, x, y, z, bb.getCorner(1), maxU, maxV, r, g, b);
                renderCorner(consumer, face, x, y, z, bb.getCorner(3), minU, maxV, r, g, b);
                renderCorner(consumer, face, x, y, z, bb.getCorner(7), minU, minV, r, g, b);
                renderCorner(consumer, face, x, y, z, bb.getCorner(5), maxU, minV, r, g, b);
                break;
            case WEST:
                renderCorner(consumer, face, x, y, z, bb.getCorner(0), maxU, maxV, r, g, b);
                renderCorner(consumer, face, x, y, z, bb.getCorner(1), minU, maxV, r, g, b);
                renderCorner(consumer, face, x, y, z, bb.getCorner(5), minU, minV, r, g, b);
                renderCorner(consumer, face, x, y, z, bb.getCorner(4), maxU, minV, r, g, b);
                break;
        }
    }

    private void renderCorner(VertexConsumer consumer, Face face, int x, int y, int z, FloatVector3 offset, float u, float v, float red, float green, float blue) {
        float posX = x + offset.getX();
        float posY = y + offset.getY();
        float posZ = z + offset.getZ();
        consumer.addVertex(posX, posY, posZ).setUv(u, v);
        int blockX = MathHelper.floor(posX);
        int blockY = MathHelper.floor(posY);
        int blockZ = MathHelper.floor(posZ);
        LegacyLightEngine engine = this.schematic.legacyLightEngine();
        consumer.setColor(red * engine.getRedStrengthAt(blockX, blockY, blockZ), green * engine.getGreenStrengthAt(blockX, blockY, blockZ), blue * engine.getBlueStrengthAt(blockX, blockY, blockZ), 1.0f).setFloat(DynamicBackgroundController.LIGHTING ? 2.0f : 0.0f).setNormal(face.getX(), face.getY(), face.getZ());
    }
}
