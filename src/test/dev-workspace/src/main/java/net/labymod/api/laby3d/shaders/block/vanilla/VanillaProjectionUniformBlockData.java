package net.labymod.api.laby3d.shaders.block.vanilla;

import net.labymod.laby3d.api.shaders.block.UniformBlockData;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/shaders/block/vanilla/VanillaProjectionUniformBlockData.class */
public class VanillaProjectionUniformBlockData implements UniformBlockData<VanillaProjectionUniformBlock> {
    private final Matrix4f projectionMatrix;

    public VanillaProjectionUniformBlockData(Matrix4f projectionMatrix) {
        this.projectionMatrix = new Matrix4f(projectionMatrix);
    }

    public void update(VanillaProjectionUniformBlock block) {
        block.projectionMatrix().set(this.projectionMatrix);
    }
}
