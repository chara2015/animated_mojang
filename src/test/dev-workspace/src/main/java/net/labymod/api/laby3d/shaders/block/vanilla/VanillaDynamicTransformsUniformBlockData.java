package net.labymod.api.laby3d.shaders.block.vanilla;

import net.labymod.laby3d.api.shaders.block.UniformBlockData;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/shaders/block/vanilla/VanillaDynamicTransformsUniformBlockData.class */
public class VanillaDynamicTransformsUniformBlockData implements UniformBlockData<VanillaDynamicTransformsUniformBlock> {
    private final Matrix4f modelViewMatrix = new Matrix4f();
    private final Vector4f colorModulator = new Vector4f();
    private final Vector3f modelOffset = new Vector3f();
    private final Matrix4f textureMatrix = new Matrix4f();
    private float lineWidth = 1.0f;

    public Matrix4f modelViewMatrix() {
        return this.modelViewMatrix;
    }

    public Vector4f colorModulator() {
        return this.colorModulator;
    }

    public Vector3f modelOffset() {
        return this.modelOffset;
    }

    public Matrix4f textureMatrix() {
        return this.textureMatrix;
    }

    public float getLineWidth() {
        return this.lineWidth;
    }

    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
    }

    public void update(VanillaDynamicTransformsUniformBlock block) {
        block.modelViewMatrix().set(this.modelViewMatrix);
        block.colorModulator().set(this.colorModulator);
        block.modelOffset().set(this.modelOffset);
        block.textureMatrix().set(this.textureMatrix);
        block.lineWidth().set(Float.valueOf(this.lineWidth));
    }
}
