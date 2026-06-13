package net.labymod.api.laby3d.render.queue.submissions;

import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.render.queue.Submission;
import net.labymod.laby3d.api.pipeline.RenderState;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/render/queue/submissions/IconSubmission.class */
public interface IconSubmission extends Submission {
    Icon icon();

    Matrix4f pose();

    float x();

    float y();

    float width();

    float height();

    int argb();

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/render/queue/submissions/IconSubmission$DisplayMode.class */
    public enum DisplayMode {
        NORMAL(RenderStates.NAMETAG_ICON),
        SEE_THROUGH(RenderStates.SEE_THROUGH_NAMETAG_ICON);

        private final RenderState renderState;

        DisplayMode(RenderState renderState) {
            this.renderState = renderState;
        }

        public RenderState renderState() {
            return this.renderState;
        }
    }
}
