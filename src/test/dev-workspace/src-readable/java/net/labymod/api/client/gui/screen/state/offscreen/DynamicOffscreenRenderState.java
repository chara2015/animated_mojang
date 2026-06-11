package net.labymod.api.client.gui.screen.state.offscreen;

import net.labymod.api.client.gui.screen.state.RoundedData;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.bounds.Rectangle;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/offscreen/DynamicOffscreenRenderState.class */
@ApiStatus.Internal
public class DynamicOffscreenRenderState extends AbstractOffscreenRenderState {
    private final DynamicRenderer renderer;

    public DynamicOffscreenRenderState(Matrix4f pose, float left, float top, float right, float bottom, float scale, @Nullable RoundedData roundedData, DynamicRenderer renderer) {
        super(pose, left, top, right, bottom, scale, roundedData);
        this.renderer = renderer;
    }

    public DynamicOffscreenRenderState(Matrix4f pose, Rectangle bounds, float scale, @Nullable RoundedData roundedData, DynamicRenderer renderer) {
        super(pose, bounds, scale, roundedData);
        this.renderer = renderer;
    }

    public void render(OffscreenContext context, Stack stack) {
        this.renderer.render(this, context, stack);
    }

    public float getTranslateY(int height, int guiScale) {
        return this.renderer.getTranslateY(height, guiScale);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/offscreen/DynamicOffscreenRenderState$DynamicRenderer.class */
    @FunctionalInterface
    public interface DynamicRenderer {
        void render(DynamicOffscreenRenderState dynamicOffscreenRenderState, OffscreenContext offscreenContext, Stack stack);

        default float getTranslateY(int height, int guiScale) {
            return height;
        }
    }
}
