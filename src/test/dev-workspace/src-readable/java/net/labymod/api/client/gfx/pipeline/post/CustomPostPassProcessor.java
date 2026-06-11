package net.labymod.api.client.gfx.pipeline.post;

import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/post/CustomPostPassProcessor.class */
public interface CustomPostPassProcessor {
    void process(PostPassData postPassData, CommandBuffer commandBuffer, float f);
}
