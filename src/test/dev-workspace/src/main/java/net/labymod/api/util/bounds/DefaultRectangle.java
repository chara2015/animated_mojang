package net.labymod.api.util.bounds;

import java.util.Locale;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/bounds/DefaultRectangle.class */
public class DefaultRectangle implements MutableRectangle {
    protected float left;
    protected float top;
    protected float right;
    protected float bottom;

    public DefaultRectangle() {
    }

    public DefaultRectangle(Rectangle rectangle) {
        set(rectangle);
    }

    protected DefaultRectangle(float left, float top, float right, float bottom) {
        setBounds(left, top, right, bottom);
    }

    @Override // net.labymod.api.util.bounds.MutableRectangle
    public void setLeft(float left) {
        this.left = left;
    }

    @Override // net.labymod.api.util.bounds.MutableRectangle
    public void setTop(float top) {
        this.top = top;
    }

    @Override // net.labymod.api.util.bounds.MutableRectangle
    public void setRight(float right) {
        this.right = right;
    }

    @Override // net.labymod.api.util.bounds.MutableRectangle
    public void setBottom(float bottom) {
        this.bottom = bottom;
    }

    @Override // net.labymod.api.util.bounds.Rectangle
    public float getLeft() {
        return this.left;
    }

    @Override // net.labymod.api.util.bounds.Rectangle
    public float getTop() {
        return this.top;
    }

    @Override // net.labymod.api.util.bounds.Rectangle
    public float getRight() {
        return this.right;
    }

    @Override // net.labymod.api.util.bounds.Rectangle
    public float getBottom() {
        return this.bottom;
    }

    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DefaultRectangle that = (DefaultRectangle) o;
        return Float.compare(this.left, that.left) == 0 && Float.compare(this.top, that.top) == 0 && Float.compare(this.right, that.right) == 0 && Float.compare(this.bottom, that.bottom) == 0;
    }

    public int hashCode() {
        int result = Float.hashCode(this.left);
        return (31 * ((31 * ((31 * result) + Float.hashCode(this.top))) + Float.hashCode(this.right))) + Float.hashCode(this.bottom);
    }

    public String toString() {
        return String.format(Locale.ROOT, "%s,%s (%sx%s)", Float.valueOf(this.left), Float.valueOf(this.top), Float.valueOf(getWidth()), Float.valueOf(getHeight()));
    }
}
