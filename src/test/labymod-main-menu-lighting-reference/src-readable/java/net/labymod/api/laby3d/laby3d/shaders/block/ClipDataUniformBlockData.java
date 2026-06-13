package net.labymod.api.laby3d.shaders.block;

import net.labymod.laby3d.api.shaders.block.UniformBlockData;
import org.joml.Vector4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/shaders/block/ClipDataUniformBlockData.class */
public class ClipDataUniformBlockData implements UniformBlockData<ClipDataUniformBlock> {
    private final Vector4f clipBounds = new Vector4f();
    private final Vector4f clipCornerRadius = new Vector4f();
    private float clipEnabled;

    public Vector4f clipBounds() {
        return this.clipBounds;
    }

    public Vector4f clipCornerRadius() {
        return this.clipCornerRadius;
    }

    public float clipEnabled() {
        return this.clipEnabled;
    }

    public void setClipEnabled(float clipEnabled) {
        this.clipEnabled = clipEnabled;
    }

    public void update(ClipDataUniformBlock block) {
        block.clipBounds().set(this.clipBounds);
        block.clipCornerRadius().set(this.clipCornerRadius);
        block.clipEnabled().set(Float.valueOf(this.clipEnabled));
    }
}
