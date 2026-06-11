package net.labymod.v26_1_1.client.gfx.pipeline;

import com.mojang.blaze3d.systems.CommandEncoder;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/client/gfx/pipeline/CommandEncoderPipelineExt.class */
public interface CommandEncoderPipelineExt {
    void invalidatePipeline();

    static void invalidate(CommandEncoder encoder) {
        if (encoder instanceof CommandEncoderPipelineExt) {
            CommandEncoderPipelineExt pipelineExt = (CommandEncoderPipelineExt) encoder;
            pipelineExt.invalidatePipeline();
        }
    }
}
