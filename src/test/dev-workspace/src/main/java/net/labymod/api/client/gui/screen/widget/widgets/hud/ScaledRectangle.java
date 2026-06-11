package net.labymod.api.client.gui.screen.widget.widgets.hud;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.bounds.Rectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/hud/ScaledRectangle.class */
public class ScaledRectangle implements Rectangle {
    private final Widget widget;
    private final Bounds bounds;

    public ScaledRectangle(Widget widget) {
        this.widget = widget;
        this.bounds = widget.bounds();
    }

    @Override // net.labymod.api.util.bounds.Rectangle
    public float getLeft() {
        return this.bounds.getLeft();
    }

    @Override // net.labymod.api.util.bounds.Rectangle
    public float getTop() {
        return this.bounds.getTop();
    }

    @Override // net.labymod.api.util.bounds.Rectangle
    public float getRight() {
        return this.bounds.getLeft() + (this.bounds.getWidth() * this.widget.getScaleX());
    }

    @Override // net.labymod.api.util.bounds.Rectangle
    public float getBottom() {
        return this.bounds.getTop() + (this.bounds.getHeight() * this.widget.getScaleY());
    }

    public void setPosition(float x, float y, ModifyReason reason) {
        this.bounds.setPosition(x, y, reason);
    }

    public void setX(float x, ModifyReason reason) {
        this.bounds.setX(x, reason);
    }

    public void setY(float y, ModifyReason reason) {
        this.bounds.setY(y, reason);
    }

    public void setSize(float scaledWidth, float scaledHeight, ModifyReason reason) {
        this.bounds.setSize(scaledWidth / this.widget.getScaleX(), scaledHeight / this.widget.getScaleY(), reason);
    }

    public void checkForChanges() {
        this.bounds.checkForChanges();
    }
}
