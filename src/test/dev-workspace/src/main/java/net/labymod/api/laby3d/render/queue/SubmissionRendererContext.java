package net.labymod.api.laby3d.render.queue;

import net.labymod.api.laby3d.render.buffer.RenderBufferSource;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/render/queue/SubmissionRendererContext.class */
public final class SubmissionRendererContext {
    private final RenderBufferSource source;
    private final boolean forceVanillaPath;

    public SubmissionRendererContext(RenderBufferSource source, boolean forceVanillaPath) {
        this.source = source;
        this.forceVanillaPath = forceVanillaPath;
    }

    public RenderBufferSource source() {
        return this.source;
    }

    public boolean forceVanillaPath() {
        return this.forceVanillaPath;
    }
}
