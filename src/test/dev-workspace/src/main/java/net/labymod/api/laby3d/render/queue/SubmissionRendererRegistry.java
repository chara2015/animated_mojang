package net.labymod.api.laby3d.render.queue;

import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/render/queue/SubmissionRendererRegistry.class */
@Referenceable
public interface SubmissionRendererRegistry {
    <T extends Submission> void register(SubmissionRenderer<T> submissionRenderer);

    void draw(SubmissionRendererContext submissionRendererContext, Submission submission);
}
