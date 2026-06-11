package net.labymod.core.client.render.schematic.block.material.material;

import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.client.render.schematic.SchematicAccessor;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.block.BlockRenderer;
import net.labymod.core.client.render.schematic.block.Face;
import net.labymod.core.client.render.schematic.block.ParameterType;
import net.labymod.core.client.render.schematic.block.material.BoundingBox;
import net.labymod.core.client.render.schematic.block.material.RenderLayer;
import net.labymod.laby3d.api.vertex.VertexConsumer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/block/material/material/LeverMaterial.class */
public class LeverMaterial extends Material {
    private final ResourceLocation lever;

    public LeverMaterial() {
        super("lever");
        this.resourceLocation = createResource("cobblestone");
        this.lever = createResource("lever");
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public RenderLayer getRenderLayer() {
        return RenderLayer.CUT_OUT;
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public boolean isFullBlock() {
        return false;
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public BoundingBox getBoundingBox(SchematicAccessor level, int x, int y, int z, Block block) {
        String facing = (String) block.getParameter(ParameterType.FACING, ParameterType.NORTH);
        String face = (String) block.getParameter(ParameterType.FACE, "wall");
        float baseWidth = 0.0625f * 6.0f;
        float baseHeight = 0.0625f * 3.0f;
        float baseDepth = 0.0625f * 8.0f;
        if (face.equals("floor")) {
            float posX = (0.0625f * 8.0f) - (baseWidth / 2.0f);
            float posZ = (0.0625f * 8.0f) - (baseDepth / 2.0f);
            this.boundingBox.set(posX, 0.0f, posZ, posX + baseWidth, 0.0f + baseHeight, posZ + baseDepth);
        }
        if (face.equals("ceiling")) {
            float posX2 = (0.0625f * 8.0f) - (baseWidth / 2.0f);
            float posY = (0.0625f * 16.0f) - baseHeight;
            float posZ2 = (0.0625f * 8.0f) - (baseDepth / 2.0f);
            this.boundingBox.set(posX2, posY, posZ2, posX2 + baseWidth, posY + baseHeight, posZ2 + baseDepth);
        }
        if (face.equals("wall")) {
            if (facing.equals(ParameterType.NORTH) || facing.equals(ParameterType.SOUTH)) {
                float posX3 = (0.0625f * 8.0f) - (baseWidth / 2.0f);
                float posY2 = (0.0625f * 8.0f) - (baseDepth / 2.0f);
                float posZ3 = facing.equals(ParameterType.NORTH) ? (0.0625f * 16.0f) - baseHeight : 0.0f;
                this.boundingBox.set(posX3, posY2, posZ3, posX3 + baseWidth, posY2 + baseDepth, posZ3 + baseHeight);
            }
            if (facing.equals(ParameterType.EAST) || facing.equals(ParameterType.WEST)) {
                float posX4 = facing.equals(ParameterType.WEST) ? (0.0625f * 16.0f) - baseHeight : 0.0f;
                float posY3 = (0.0625f * 8.0f) - (baseDepth / 2.0f);
                float posZ4 = (0.0625f * 8.0f) - (baseWidth / 2.0f);
                this.boundingBox.set(posX4, posY3, posZ4, posX4 + baseHeight, posY3 + baseDepth, posZ4 + baseWidth);
            }
        }
        return super.getBoundingBox(level, x, y, z, block);
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public TextureUV getUV(SchematicAccessor level, int x, int y, int z, TextureAtlas atlas, Block block, Face face) {
        return getUVForBoundingBox(atlas, this.resourceLocation, this.boundingBox, face);
    }

    @Override // net.labymod.core.client.render.schematic.block.material.material.Material
    public void render(BlockRenderer renderer, TextureAtlas atlas, VertexConsumer consumer, Block block, SchematicAccessor level, int x, int y, int z, BoundingBox boundingBox) {
        super.render(renderer, atlas, consumer, block, level, x, y, z, boundingBox);
        renderLever(renderer, atlas, consumer, block, x, y, z);
    }

    private void renderLever(BlockRenderer renderer, TextureAtlas atlas, VertexConsumer consumer, Block block, int x, int y, int z) {
        String faceType = (String) block.getParameter(ParameterType.FACE, "wall");
        boolean powered = ((Boolean) block.getParameter(ParameterType.POWERED, false)).booleanValue();
        float size = 2.0f / 16.0f;
        float height = 10.0f / 16.0f;
        BoundingBox bb = null;
        if (faceType.equals("floor")) {
            float posX = 0.5f - (size / 2.0f);
            float posY = 1.0f / 16.0f;
            float posZ = 0.5f - (size / 2.0f);
            bb = new BoundingBox(posX, posY, posZ, posX + size, posY + height, posZ + size);
            bb.rotateX(0.5f, 1.0f / 16.0f, 0.5f, 50.0f * (powered ? -1 : 1));
        }
        if (faceType.equals("ceiling")) {
            float posX2 = 0.5f - (size / 2.0f);
            float posY2 = (1.0f - height) - (1.0f / 16.0f);
            float posZ2 = 0.5f - (size / 2.0f);
            bb = new BoundingBox(posX2, posY2 + height, posZ2, posX2 + size, posY2, posZ2 + size);
            bb.rotateX(0.5f, 1.0f - (1.0f / 16.0f), 0.5f, 50.0f * (powered ? -1 : 1));
        }
        if (faceType.equals("wall")) {
            String facing = (String) block.getParameter(ParameterType.FACING, ParameterType.NORTH);
            boolean south = facing.equals(ParameterType.SOUTH);
            if (facing.equals(ParameterType.NORTH) || south) {
                float posX3 = 0.5f - (size / 2.0f);
                float posY3 = 1.0f / 16.0f;
                float posZ3 = 0.5f - (size / 2.0f);
                bb = new BoundingBox(posX3, posY3, posZ3, posX3 + size, posY3 + height, posZ3 + size);
                bb.rotateX(0.5f, 0.5f, 0.5f, 90 * (south ? 1 : -1));
                bb.rotateX(0.5f, 0.5f, south ? 1.0f / 16.0f : 1.0f - (1.0f / 16.0f), 50.0f * (powered ^ south ? -1 : 1));
            }
            if (facing.equals(ParameterType.EAST) || facing.equals(ParameterType.WEST)) {
                boolean west = facing.equals(ParameterType.WEST);
                float posX4 = 0.5f - (size / 2.0f);
                float posY4 = 1.0f / 16.0f;
                float posZ4 = 0.5f - (size / 2.0f);
                bb = new BoundingBox(posX4, posY4, posZ4, posX4 + size, posY4 + height, posZ4 + size);
                bb.rotateZ(0.5f, 0.5f, 0.5f, (-90) * (west ? -1 : 1));
                bb.rotateZ(west ? 1.0f - (1.0f / 16.0f) : 1.0f / 16.0f, 0.5f, 0.0f, 50.0f * (powered ^ west ? 1 : -1));
            }
        }
        if (bb == null) {
            return;
        }
        Face[] faceArr = Face.VALUES;
        int length = faceArr.length;
        for (int i = 0; i < length; i++) {
            Face face = faceArr[i];
            TextureUV uv = getUVCut(atlas, this.lever, 7, face == Face.BOTTOM ? 14 : 6, 2, face.isYAxis() ? 2 : 10);
            renderer.renderFace(consumer, block, face, x, y, z, bb, uv);
        }
    }
}
