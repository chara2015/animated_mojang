package net.labymod.api.event.client.render.world;

import net.labymod.api.event.DefaultCancellable;
import net.labymod.api.event.Event;
import net.labymod.api.util.Color;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/world/RenderBlockSelectionBoxEvent.class */
public class RenderBlockSelectionBoxEvent extends DefaultCancellable implements Event {
    private static final Color DEFAULT_LINE_COLOR = Color.ofRGB(0, 0, 0, 102);
    private Color lineColor;
    private Color overlayColor;
    private final int x;
    private final int y;
    private final int z;

    public RenderBlockSelectionBoxEvent(Color lineColor, Color overlayColor, int x, int y, int z) {
        this.lineColor = lineColor;
        this.overlayColor = overlayColor;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public RenderBlockSelectionBoxEvent(Color lineColor, int x, int y, int z) {
        this(lineColor, null, x, y, z);
    }

    public RenderBlockSelectionBoxEvent(int x, int y, int z) {
        this(DEFAULT_LINE_COLOR, x, y, z);
    }

    public Color getLineColor() {
        return this.lineColor;
    }

    public Color getOverlayColor() {
        return this.overlayColor;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    @Deprecated(forRemoval = true, since = "4.2.51")
    public void setLineColor(java.awt.Color color) {
        this.lineColor = Color.of(color);
    }

    public void setOverlayColor(Color overlayColor) {
        this.overlayColor = overlayColor;
    }

    @Deprecated(forRemoval = true, since = "4.2.52")
    public void setOverlayColor(java.awt.Color color) {
        this.overlayColor = Color.of(color);
    }
}
