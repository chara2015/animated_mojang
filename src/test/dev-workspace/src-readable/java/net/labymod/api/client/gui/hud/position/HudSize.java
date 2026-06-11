package net.labymod.api.client.gui.hud.position;

import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.util.bounds.Rectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/hud/position/HudSize.class */
public class HudSize {
    private final HudWidgetConfig config;
    private float width;
    private float height;

    public HudSize(HudWidgetConfig config) {
        this(0.0f, 0.0f, config);
    }

    @Deprecated
    public HudSize(int width, int height, HudWidgetConfig config) {
        this(width, height, config);
    }

    public HudSize(float width, float height, HudWidgetConfig config) {
        this.width = width;
        this.height = height;
        this.config = config;
    }

    public float getScaledWidth() {
        return this.width * getScale();
    }

    public float getScaledHeight() {
        return this.height * getScale();
    }

    public float getActualWidth() {
        return this.width;
    }

    public float getActualHeight() {
        return this.height;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getScale() {
        return this.config.scale().get().floatValue();
    }

    public void set(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public HudSize copy() {
        return new HudSize(this.width, this.height, this.config);
    }

    public Rectangle toRectangle() {
        return Rectangle.relative(0.0f, 0.0f, this.width, this.height);
    }

    @Deprecated
    public int getWidth() {
        return (int) this.width;
    }

    @Deprecated
    public int getHeight() {
        return (int) this.height;
    }

    @Deprecated
    public void setWidth(int width) {
        this.width = width;
    }

    @Deprecated
    public void setHeight(int height) {
        this.height = height;
    }

    @Deprecated
    public void set(int width, int height) {
        this.width = width;
        this.height = height;
    }
}
