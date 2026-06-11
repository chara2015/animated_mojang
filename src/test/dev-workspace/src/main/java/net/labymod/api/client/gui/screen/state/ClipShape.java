package net.labymod.api.client.gui.screen.state;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.client.gui.screen.widget.attributes.BorderRadius;
import net.labymod.api.util.bounds.Rectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/ClipShape.class */
public final class ClipShape extends Record {
    private final float x;
    private final float y;
    private final float width;
    private final float height;
    private final float topLeftRadius;
    private final float topRightRadius;
    private final float bottomRightRadius;
    private final float bottomLeftRadius;

    public ClipShape(float x, float y, float width, float height, float topLeftRadius, float topRightRadius, float bottomRightRadius, float bottomLeftRadius) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.topLeftRadius = topLeftRadius;
        this.topRightRadius = topRightRadius;
        this.bottomRightRadius = bottomRightRadius;
        this.bottomLeftRadius = bottomLeftRadius;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ClipShape.class), ClipShape.class, "x;y;width;height;topLeftRadius;topRightRadius;bottomRightRadius;bottomLeftRadius", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClipShape;->x:F", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClipShape;->y:F", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClipShape;->width:F", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClipShape;->height:F", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClipShape;->topLeftRadius:F", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClipShape;->topRightRadius:F", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClipShape;->bottomRightRadius:F", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClipShape;->bottomLeftRadius:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ClipShape.class), ClipShape.class, "x;y;width;height;topLeftRadius;topRightRadius;bottomRightRadius;bottomLeftRadius", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClipShape;->x:F", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClipShape;->y:F", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClipShape;->width:F", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClipShape;->height:F", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClipShape;->topLeftRadius:F", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClipShape;->topRightRadius:F", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClipShape;->bottomRightRadius:F", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClipShape;->bottomLeftRadius:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ClipShape.class, Object.class), ClipShape.class, "x;y;width;height;topLeftRadius;topRightRadius;bottomRightRadius;bottomLeftRadius", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClipShape;->x:F", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClipShape;->y:F", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClipShape;->width:F", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClipShape;->height:F", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClipShape;->topLeftRadius:F", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClipShape;->topRightRadius:F", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClipShape;->bottomRightRadius:F", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClipShape;->bottomLeftRadius:F").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public float x() {
        return this.x;
    }

    public float y() {
        return this.y;
    }

    public float width() {
        return this.width;
    }

    public float height() {
        return this.height;
    }

    public float topLeftRadius() {
        return this.topLeftRadius;
    }

    public float topRightRadius() {
        return this.topRightRadius;
    }

    public float bottomRightRadius() {
        return this.bottomRightRadius;
    }

    public float bottomLeftRadius() {
        return this.bottomLeftRadius;
    }

    public static ClipShape circle(float centerX, float centerY, float radius) {
        return new ClipShape(centerX - radius, centerY - radius, radius * 2.0f, radius * 2.0f, radius, radius, radius, radius);
    }

    public static ClipShape roundedRect(float x, float y, float width, float height, BorderRadius borderRadius) {
        return new ClipShape(x, y, width, height, borderRadius.getLeftTop(), borderRadius.getRightTop(), borderRadius.getRightBottom(), borderRadius.getLeftBottom());
    }

    public static ClipShape roundedRect(Rectangle bounds, BorderRadius borderRadius) {
        return roundedRect(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight(), borderRadius);
    }

    public static ClipShape rect(float x, float y, float width, float height) {
        return new ClipShape(x, y, width, height, 0.0f, 0.0f, 0.0f, 0.0f);
    }

    public float left() {
        return this.x;
    }

    public float top() {
        return this.y;
    }

    public float right() {
        return this.x + this.width;
    }

    public float bottom() {
        return this.y + this.height;
    }
}
