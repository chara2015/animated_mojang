package net.labymod.api.laby3d.shaders.block;

import net.labymod.laby3d.api.shaders.block.UniformBlockData;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/shaders/block/LightingUniformBlockData.class */
public class LightingUniformBlockData implements UniformBlockData<LightingUniformBlock> {
    private final Vector3f light0Direction;
    private final Vector3f light1Direction;

    public LightingUniformBlockData() {
        this(new Vector3f(), new Vector3f());
    }

    public LightingUniformBlockData(Vector3f light0Direction, Vector3f light1Direction) {
        this.light0Direction = light0Direction;
        this.light1Direction = light1Direction;
    }

    public Vector3f light0Direction() {
        return this.light0Direction;
    }

    public Vector3f light1Direction() {
        return this.light1Direction;
    }

    public void update(LightingUniformBlock block) {
        block.light0Direction().set(light0Direction());
        block.light1Direction().set(light1Direction());
    }
}
