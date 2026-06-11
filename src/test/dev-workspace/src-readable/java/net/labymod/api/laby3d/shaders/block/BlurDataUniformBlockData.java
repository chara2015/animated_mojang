package net.labymod.api.laby3d.shaders.block;

import net.labymod.laby3d.api.shaders.block.UniformBlockData;
import org.joml.Vector2f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/shaders/block/BlurDataUniformBlockData.class */
public class BlurDataUniformBlockData implements UniformBlockData<BlurDataUniformBlock> {
    private final Vector2f offset = new Vector2f();

    public Vector2f offset() {
        return this.offset;
    }

    public void update(BlurDataUniformBlock block) {
        block.offset().set(this.offset);
    }
}
