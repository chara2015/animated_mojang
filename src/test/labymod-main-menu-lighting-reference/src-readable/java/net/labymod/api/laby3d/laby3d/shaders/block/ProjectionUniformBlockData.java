package net.labymod.api.laby3d.shaders.block;

import net.labymod.laby3d.api.shaders.block.UniformBlockData;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/shaders/block/ProjectionUniformBlockData.class */
public class ProjectionUniformBlockData implements UniformBlockData<ProjectionUniformBlock> {
    private final Matrix4f projectionMatrix;

    public ProjectionUniformBlockData() {
        this(new Matrix4f());
    }

    public ProjectionUniformBlockData(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }

    public ProjectionUniformBlockData(ProjectionUniformBlockData data) {
        this.projectionMatrix = new Matrix4f(data.projectionMatrix);
    }

    public Matrix4f projectionMatrix() {
        return this.projectionMatrix;
    }

    public void update(ProjectionUniformBlock block) {
        block.projectionMatrix().set(this.projectionMatrix);
    }
}
