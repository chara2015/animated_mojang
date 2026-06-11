package net.labymod.api.client.gui.mouse;

import net.labymod.api.util.bounds.Point;
import net.labymod.api.util.bounds.Rectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/mouse/Mouse.class */
public class Mouse implements Point {
    public static final Mouse OUT_OF_BOUNDS = new Mouse(-1.0d, -1.0d);
    protected double x;
    protected double y;
    protected boolean grabbed;

    public Mouse() {
    }

    public Mouse(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override // net.labymod.api.util.bounds.Point
    public int getX() {
        return (int) this.x;
    }

    @Override // net.labymod.api.util.bounds.Point
    public int getY() {
        return (int) this.y;
    }

    public double getXDouble() {
        return this.x;
    }

    public double getYDouble() {
        return this.y;
    }

    public MutableMouse mutable() {
        return new MutableMouse(this);
    }

    public Mouse copy() {
        return new Mouse(this.x, this.y);
    }

    public boolean isInside(double x, double y, double width, double height) {
        return this.x >= x && this.x <= x + width && this.y >= y && this.y <= y + height;
    }

    public boolean isInside(Rectangle rectangle) {
        return isInside(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
    }

    public boolean isOutOfBounds() {
        return this.x < 0.0d && this.y < 0.0d;
    }

    public boolean isGrabbed() {
        return this.grabbed;
    }

    public void setGrabbed(boolean grabbed) {
        this.grabbed = grabbed;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Mouse mouse = (Mouse) o;
        return Double.compare(mouse.x, this.x) == 0 && Double.compare(mouse.y, this.y) == 0;
    }

    public int hashCode() {
        long temp = Double.doubleToLongBits(this.x);
        int result = (int) (temp ^ (temp >>> 32));
        long temp2 = Double.doubleToLongBits(this.y);
        return (31 * result) + ((int) (temp2 ^ (temp2 >>> 32)));
    }

    public String toString() {
        double d = this.x;
        double d2 = this.y;
        return "Mouse{x=" + d + ",y=" + d + "}";
    }
}
