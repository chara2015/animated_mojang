package net.labymod.core.client.render.model.box;

import java.util.Set;
import net.labymod.api.client.render.model.box.ModelBox;
import net.labymod.api.client.render.model.box.ModelBoxQuad;
import net.labymod.api.client.render.model.box.ModelBoxVertex;
import net.labymod.api.util.math.Direction;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/model/box/DefaultModelBox.class */
public class DefaultModelBox implements ModelBox {
    private static final Set<Direction> ALL_FACES = Set.of((Object[]) Direction.VALUES);
    private final ModelBoxQuad[] quads;
    private final float minX;
    private final float minY;
    private final float minZ;
    private final float maxX;
    private final float maxY;
    private final float maxZ;
    private final boolean mirror;

    public DefaultModelBox(int texU, int texV, float x, float y, float z, float sizeX, float sizeY, float sizeZ, float growX, float growY, float growZ, boolean mirror, float texWidth, float texHeight) {
        this(texU, texV, x, y, z, sizeX, sizeY, sizeZ, growX, growY, growZ, mirror, texWidth, texHeight, ALL_FACES);
    }

    public DefaultModelBox(int texU, int texV, float x, float y, float z, float sizeX, float sizeY, float sizeZ, float growX, float growY, float growZ, boolean mirror, float texWidth, float texHeight, Set<Direction> visibleFaces) {
        this.quads = new ModelBoxQuad[6];
        this.minX = x - growX;
        this.minY = y - growY;
        this.minZ = z - growZ;
        this.maxX = x + sizeX + growX;
        this.maxY = y + sizeY + growY;
        this.maxZ = z + sizeZ + growZ;
        this.mirror = mirror;
        float minX = this.minX;
        float minY = this.minY;
        float minZ = this.minZ;
        float maxX = this.maxX;
        float maxY = this.maxY;
        float maxZ = this.maxZ;
        if (mirror) {
            maxX = minX;
            minX = maxX;
        }
        ModelBoxVertex v000 = new DefaultModelBoxVertex(minX, minY, minZ, 0.0f, 0.0f);
        ModelBoxVertex v100 = new DefaultModelBoxVertex(maxX, minY, minZ, 0.0f, 8.0f);
        ModelBoxVertex v110 = new DefaultModelBoxVertex(maxX, maxY, minZ, 8.0f, 8.0f);
        ModelBoxVertex v010 = new DefaultModelBoxVertex(minX, maxY, minZ, 8.0f, 0.0f);
        ModelBoxVertex v001 = new DefaultModelBoxVertex(minX, minY, maxZ, 0.0f, 0.0f);
        ModelBoxVertex v101 = new DefaultModelBoxVertex(maxX, minY, maxZ, 0.0f, 8.0f);
        ModelBoxVertex v111 = new DefaultModelBoxVertex(maxX, maxY, maxZ, 8.0f, 8.0f);
        ModelBoxVertex v011 = new DefaultModelBoxVertex(minX, maxY, maxZ, 8.0f, 0.0f);
        float u0 = texU;
        float u1 = texU + sizeZ;
        float u2 = texU + sizeZ + sizeX;
        float u3 = texU + sizeZ + sizeX + sizeX;
        float u4 = texU + sizeZ + sizeX + sizeZ;
        float u5 = texU + sizeZ + sizeX + sizeZ + sizeX;
        float v0 = texV;
        float v1 = texV + sizeZ;
        float v2 = texV + sizeZ + sizeY;
        int faceIndex = 0;
        if (visibleFaces.contains(Direction.DOWN)) {
            faceIndex = 0 + 1;
            this.quads[0] = new DefaultModelBoxQuad(new ModelBoxVertex[]{v101, v001, v000, v100}, u1, v0, u2, v1, texWidth, texHeight, mirror, Direction.DOWN);
        }
        if (visibleFaces.contains(Direction.UP)) {
            int i = faceIndex;
            faceIndex++;
            this.quads[i] = new DefaultModelBoxQuad(new ModelBoxVertex[]{v110, v010, v011, v111}, u2, v1, u3, v0, texWidth, texHeight, mirror, Direction.UP);
        }
        if (visibleFaces.contains(Direction.WEST)) {
            int i2 = faceIndex;
            faceIndex++;
            this.quads[i2] = new DefaultModelBoxQuad(new ModelBoxVertex[]{v000, v001, v011, v010}, u0, v1, u1, v2, texWidth, texHeight, mirror, Direction.WEST);
        }
        if (visibleFaces.contains(Direction.NORTH)) {
            int i3 = faceIndex;
            faceIndex++;
            this.quads[i3] = new DefaultModelBoxQuad(new ModelBoxVertex[]{v100, v000, v010, v110}, u1, v1, u2, v2, texWidth, texHeight, mirror, Direction.NORTH);
        }
        if (visibleFaces.contains(Direction.EAST)) {
            int i4 = faceIndex;
            faceIndex++;
            this.quads[i4] = new DefaultModelBoxQuad(new ModelBoxVertex[]{v101, v100, v110, v111}, u2, v1, u4, v2, texWidth, texHeight, mirror, Direction.EAST);
        }
        if (visibleFaces.contains(Direction.SOUTH)) {
            this.quads[faceIndex] = new DefaultModelBoxQuad(new ModelBoxVertex[]{v001, v101, v111, v011}, u4, v1, u5, v2, texWidth, texHeight, mirror, Direction.SOUTH);
        }
    }

    @Override // net.labymod.api.client.render.model.box.ModelBox
    public float getMinX() {
        return this.minX;
    }

    @Override // net.labymod.api.client.render.model.box.ModelBox
    public float getMinY() {
        return this.minY;
    }

    @Override // net.labymod.api.client.render.model.box.ModelBox
    public float getMinZ() {
        return this.minZ;
    }

    @Override // net.labymod.api.client.render.model.box.ModelBox
    public float getMaxX() {
        return this.maxX;
    }

    @Override // net.labymod.api.client.render.model.box.ModelBox
    public float getMaxY() {
        return this.maxY;
    }

    @Override // net.labymod.api.client.render.model.box.ModelBox
    public float getMaxZ() {
        return this.maxZ;
    }

    @Override // net.labymod.api.client.render.model.box.ModelBox
    public float getWidth() {
        return Math.abs(this.maxX - this.minX);
    }

    @Override // net.labymod.api.client.render.model.box.ModelBox
    public float getHeight() {
        return Math.abs(this.maxY - this.minY);
    }

    @Override // net.labymod.api.client.render.model.box.ModelBox
    public float getDepth() {
        return Math.abs(this.maxZ - this.minZ);
    }

    @Override // net.labymod.api.client.render.model.box.ModelBox
    public boolean isMirror() {
        return this.mirror;
    }

    @Override // net.labymod.api.client.render.model.box.ModelBox
    public ModelBoxQuad[] getQuads() {
        return this.quads;
    }
}
