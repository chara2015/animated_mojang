package net.labymod.api.client.render.font;

import java.util.function.Consumer;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.icon.IconBatch;
import net.labymod.api.client.render.matrix.Stack;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/font/FontIconRenderListeners.class */
public class FontIconRenderListeners {
    public static final FontIconRenderListener NORMAL_2D = new FontIconRenderListener() { // from class: net.labymod.api.client.render.font.FontIconRenderListeners.1
    };
    public static final FontIconRenderListener NORMAL_3D = new FontIconRenderListener() { // from class: net.labymod.api.client.render.font.FontIconRenderListeners.2
    };
    private static final IconBatch BATCH = new IconBatch();
    private static final FontIconRenderListener BATCH_LISTENER = new FontIconRenderListener() { // from class: net.labymod.api.client.render.font.FontIconRenderListeners.3
        @Override // net.labymod.api.client.render.font.FontIconRenderListener
        public void render(Stack stack, Icon icon, float x, float y, float width, float height, int rgb) {
            FontIconRenderListeners.BATCH.addIcon(stack, icon, x, y, width, height, rgb);
        }
    };

    public static void batch(Stack stack, Consumer<FontIconRenderListener> renderer) {
        batch(stack, null, renderer);
    }

    public static void batch3d(Stack stack, Consumer<FontIconRenderListener> renderer) {
        batch(stack, NORMAL_3D, renderer);
    }

    public static void batch(Stack stack, FontIconRenderListener batchCustomizer, Consumer<FontIconRenderListener> renderer) {
        renderer.accept(BATCH_LISTENER);
        if (batchCustomizer != null) {
            batchCustomizer.preRender();
        }
        BATCH.renderBatch(stack);
        if (batchCustomizer != null) {
            batchCustomizer.postRender();
        }
    }
}
