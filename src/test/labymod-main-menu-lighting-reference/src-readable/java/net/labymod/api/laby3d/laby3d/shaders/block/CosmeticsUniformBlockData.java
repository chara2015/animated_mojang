package net.labymod.api.laby3d.shaders.block;

import net.labymod.laby3d.api.shaders.block.UniformBlockData;
import net.labymod.laby3d.api.shaders.block.property.ArrayUniformProperty;
import net.labymod.laby3d.api.util.Util;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector2i;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/shaders/block/CosmeticsUniformBlockData.class */
public class CosmeticsUniformBlockData implements UniformBlockData<CosmeticsUniformBlock> {
    private final Matrix4f modelMatrix = new Matrix4f();
    private final Vector2i lightCoords = new Vector2i();
    private final Matrix4f[] bones = new Matrix4f[240];

    public CosmeticsUniformBlockData() {
        Util.fillArray(this.bones, Matrix4f::new);
    }

    public Matrix4f modelMatrix() {
        return this.modelMatrix;
    }

    public Vector2i lightCoords() {
        return this.lightCoords;
    }

    public void setBone(int boneIndex, Matrix4fc bone) {
        this.bones[boneIndex].set(bone);
    }

    public void update(CosmeticsUniformBlock block) {
        ArrayUniformProperty<Matrix4f> bones = block.bones();
        block.modelMatrix().set(modelMatrix());
        block.lightCoords().set(lightCoords());
        for (int index = 0; index < this.bones.length; index++) {
            bones.set(index, this.bones[index]);
        }
    }
}
