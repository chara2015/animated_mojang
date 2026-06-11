package net.labymod.api.client.gui.mouse;

import java.util.function.BooleanSupplier;
import net.labymod.api.util.bounds.Rectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/mouse/MutableMouse.class */
public class MutableMouse extends Mouse {
    public MutableMouse() {
    }

    public MutableMouse(Mouse mouse) {
        super(mouse.x, mouse.y);
    }

    public MutableMouse(double x, double y) {
        super(x, y);
    }

    public void transform(Rectangle rectangle, Runnable runnable) {
        float dx = rectangle.getX();
        float dy = rectangle.getY();
        try {
            this.x -= (double) dx;
            this.y -= (double) dy;
            runnable.run();
            this.x += (double) dx;
            this.y += (double) dy;
        } catch (Throwable th) {
            this.x += (double) dx;
            this.y += (double) dy;
            throw th;
        }
    }

    public void set(double x, double y, Runnable runnable) {
        double px = this.x;
        double py = this.y;
        try {
            set(x, y);
            runnable.run();
            set(px, py);
        } catch (Throwable th) {
            set(px, py);
            throw th;
        }
    }

    public boolean set(double x, double y, BooleanSupplier transformHandler) {
        double px = this.x;
        double py = this.y;
        try {
            set(x, y);
            boolean asBoolean = transformHandler.getAsBoolean();
            set(px, py);
            return asBoolean;
        } catch (Throwable th) {
            set(px, py);
            throw th;
        }
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override // net.labymod.api.client.gui.mouse.Mouse
    public String toString() {
        double d = this.x;
        double d2 = this.y;
        return "Mouse{x=" + d + ",y=" + d + ",mutable}";
    }
}
