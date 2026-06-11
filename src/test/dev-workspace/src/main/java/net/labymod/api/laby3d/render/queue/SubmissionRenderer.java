package net.labymod.api.laby3d.render.queue;

import net.labymod.api.laby3d.render.buffer.RenderBufferSource;
import net.labymod.api.laby3d.render.queue.Submission;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/render/queue/SubmissionRenderer.class */
public interface SubmissionRenderer<T extends Submission> {
    void render(RenderBufferSource renderBufferSource, T t);

    Class<T> type();
}
