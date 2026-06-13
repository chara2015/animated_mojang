package net.labymod.api.laby3d.shaders.block;

import net.labymod.laby3d.api.shaders.block.UniformBlockData;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/shaders/block/DynamicTransformsUniformBlockData.class */
public class DynamicTransformsUniformBlockData implements UniformBlockData<DynamicTransformsUniformBlock> {
    private final Matrix4f modelViewMatrix;
    private final Vector4f colorModulator;
    private final Vector4f modelOffset;
    private float lineWidth;

    public DynamicTransformsUniformBlockData() {
        this.modelViewMatrix = new Matrix4f();
        this.colorModulator = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.modelOffset = new Vector4f();
    }

    public DynamicTransformsUniformBlockData(@NotNull DynamicTransformsUniformBlockData data) {
        this();
        this.modelViewMatrix.set(data.modelViewMatrix);
        this.colorModulator.set(data.colorModulator);
        this.modelOffset.set(data.modelOffset);
        this.lineWidth = data.lineWidth;
    }

    public Matrix4f modelViewMatrix() {
        return this.modelViewMatrix;
    }

    public Vector4f colorModulator() {
        return this.colorModulator;
    }

    public Vector4f modelOffset() {
        return this.modelOffset;
    }

    public float lineWidth() {
        return this.lineWidth;
    }

    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
    }

    public void update(DynamicTransformsUniformBlock block) {
        block.modelViewMatrix().set(this.modelViewMatrix);
        block.colorModulator().set(this.colorModulator);
        block.modelOffset().set(this.modelOffset);
        block.lineWidth().set(Float.valueOf(this.lineWidth));
    }
}
