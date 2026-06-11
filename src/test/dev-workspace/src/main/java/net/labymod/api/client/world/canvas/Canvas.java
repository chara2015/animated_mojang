package net.labymod.api.client.world.canvas;

import net.labymod.api.Laby;
import net.labymod.api.client.world.signobject.SignObjectPosition;
import net.labymod.api.client.world.signobject.object.SignObject;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/canvas/Canvas.class */
public interface Canvas extends SignObject<CanvasMeta> {
    public static final float PIXEL_PER_BLOCK = 15.882353f;

    CanvasRenderer renderer();

    void setRenderer(CanvasRenderer canvasRenderer);

    default float getWidth() {
        CanvasMeta meta = meta();
        float width = meta.size().getWidth();
        if (width == 0.0f) {
            return meta.template().calculateWidth(meta.size().getHeight());
        }
        return width;
    }

    default float getHeight() {
        CanvasMeta meta = meta();
        float height = meta.size().getHeight();
        if (height == 0.0f) {
            return meta.template().calculateHeight(meta.size().getWidth());
        }
        return height;
    }

    static Canvas createCanvas(CanvasMeta meta, SignObjectPosition position) {
        return Laby.references().canvasFactory().createCanvas(meta, position);
    }
}
