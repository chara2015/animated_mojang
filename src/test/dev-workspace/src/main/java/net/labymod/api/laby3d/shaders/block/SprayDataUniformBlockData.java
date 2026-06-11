package net.labymod.api.laby3d.shaders.block;

import net.labymod.laby3d.api.shaders.block.UniformBlockData;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/shaders/block/SprayDataUniformBlockData.class */
public class SprayDataUniformBlockData implements UniformBlockData<SprayDataUniformBlock> {
    private float wear;

    public float wear() {
        return this.wear;
    }

    public void setWear(float wear) {
        this.wear = wear;
    }

    public void update(SprayDataUniformBlock block) {
        block.wear().set(Float.valueOf(this.wear));
    }
}
