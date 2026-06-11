package net.labymod.api.laby3d.render.queue;

import net.labymod.api.laby3d.render.buffer.RenderBufferSource;
import net.labymod.api.laby3d.render.queue.Submission;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/render/queue/VanillaSubmissionRenderer.class */
public interface VanillaSubmissionRenderer<T extends Submission> {
    void renderVanilla(RenderBufferSource renderBufferSource, T t);
}
