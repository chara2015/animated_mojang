package net.labymod.core.laby3d.render.queue;

import net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer;
import net.labymod.api.client.gfx.pipeline.renderer.text.FormattedTextLayout;
import net.labymod.api.client.gfx.pipeline.renderer.text.state.TextState;
import net.labymod.api.laby3d.render.queue.AbstractSubmission;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/render/queue/TextSubmission.class */
public class TextSubmission extends AbstractSubmission {
    private final FontRenderer renderer;
    private final Matrix4f pose;
    private final FormattedTextLayout layout;
    private final float x;
    private final float y;
    private final int argb;
    private final int lightCoords;
    private final int backgroundArgb;
    private final int flags;
    private TextState textState;

    public TextSubmission(FontRenderer renderer, Matrix4f pose, FormattedTextLayout layout, float x, float y, int argb, int lightCoords, int backgroundArgb, int flags) {
        super(null);
        this.renderer = renderer;
        this.pose = pose;
        this.layout = layout;
        this.x = x;
        this.y = y;
        this.argb = argb;
        this.lightCoords = lightCoords;
        this.backgroundArgb = backgroundArgb;
        this.flags = flags;
    }

    public Matrix4f pose() {
        return this.pose;
    }

    public int lightCoords() {
        return this.lightCoords;
    }

    public TextState prepareText() {
        if (this.textState == null) {
            this.textState = this.renderer.prepareText(this.layout, this.x, this.y, this.argb, this.lightCoords, this.backgroundArgb, this.flags);
        }
        return this.textState;
    }

    public int flags() {
        return this.flags;
    }
}
