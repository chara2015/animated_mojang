package net.labymod.api.client.gui.screen.widget.attributes;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/attributes/Border.class */
public class Border {
    private int leftColor;
    private int topColor;
    private int rightColor;
    private int bottomColor;
    private float leftWidth;
    private float topWidth;
    private float rightWidth;
    private float bottomWidth;

    public void setColor(int color) {
        this.leftColor = color;
        this.topColor = color;
        this.rightColor = color;
        this.bottomColor = color;
    }

    public void setWidth(float width) {
        this.leftWidth = width;
        this.topWidth = width;
        this.rightWidth = width;
        this.bottomWidth = width;
    }

    public void setWidth(float left, float top, float right, float bottom) {
        this.leftWidth = left;
        this.topWidth = top;
        this.rightWidth = right;
        this.bottomWidth = bottom;
    }

    public void setLeftColor(int leftColor) {
        this.leftColor = leftColor;
    }

    public void setTopColor(int topColor) {
        this.topColor = topColor;
    }

    public void setRightColor(int rightColor) {
        this.rightColor = rightColor;
    }

    public void setBottomColor(int bottomColor) {
        this.bottomColor = bottomColor;
    }

    public void setLeftWidth(float leftWidth) {
        this.leftWidth = leftWidth;
    }

    public void setTopWidth(float topWidth) {
        this.topWidth = topWidth;
    }

    public void setRightWidth(float rightWidth) {
        this.rightWidth = rightWidth;
    }

    public void setBottomWidth(float bottomWidth) {
        this.bottomWidth = bottomWidth;
    }

    public int getLeftColor() {
        return this.leftColor;
    }

    public int getTopColor() {
        return this.topColor;
    }

    public int getRightColor() {
        return this.rightColor;
    }

    public int getBottomColor() {
        return this.bottomColor;
    }

    public float getLeftWidth() {
        return this.leftWidth;
    }

    public float getTopWidth() {
        return this.topWidth;
    }

    public float getRightWidth() {
        return this.rightWidth;
    }

    public float getBottomWidth() {
        return this.bottomWidth;
    }

    public boolean isSet() {
        return (this.leftWidth == 0.0f && this.topWidth == 0.0f && this.rightWidth == 0.0f && this.bottomWidth == 0.0f) ? false : true;
    }
}
