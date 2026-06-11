package net.minecraft.client.gui.navigation;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.util.Mth;
import org.joml.Matrix3x2fc;
import org.joml.Vector2f;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/navigation/ScreenRectangle.class */
public final class ScreenRectangle extends Record {
    private final ScreenPosition position;
    private final int width;
    private final int height;
    private static final ScreenRectangle EMPTY = new ScreenRectangle(0, 0, 0, 0);

    public ScreenRectangle(ScreenPosition $$0, int $$1, int $$2) {
        this.position = $$0;
        this.width = $$1;
        this.height = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ScreenRectangle.class), ScreenRectangle.class, "position;width;height", "FIELD:Lnet/minecraft/client/gui/navigation/ScreenRectangle;->position:Lnet/minecraft/client/gui/navigation/ScreenPosition;", "FIELD:Lnet/minecraft/client/gui/navigation/ScreenRectangle;->width:I", "FIELD:Lnet/minecraft/client/gui/navigation/ScreenRectangle;->height:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ScreenRectangle.class), ScreenRectangle.class, "position;width;height", "FIELD:Lnet/minecraft/client/gui/navigation/ScreenRectangle;->position:Lnet/minecraft/client/gui/navigation/ScreenPosition;", "FIELD:Lnet/minecraft/client/gui/navigation/ScreenRectangle;->width:I", "FIELD:Lnet/minecraft/client/gui/navigation/ScreenRectangle;->height:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ScreenRectangle.class, Object.class), ScreenRectangle.class, "position;width;height", "FIELD:Lnet/minecraft/client/gui/navigation/ScreenRectangle;->position:Lnet/minecraft/client/gui/navigation/ScreenPosition;", "FIELD:Lnet/minecraft/client/gui/navigation/ScreenRectangle;->width:I", "FIELD:Lnet/minecraft/client/gui/navigation/ScreenRectangle;->height:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public ScreenPosition position() {
        return this.position;
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    public ScreenRectangle(int $$0, int $$1, int $$2, int $$3) {
        this(new ScreenPosition($$0, $$1), $$2, $$3);
    }

    public static ScreenRectangle empty() {
        return EMPTY;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static ScreenRectangle of(ScreenAxis $$0, int $$1, int $$2, int $$3, int $$4) throws MatchException {
        switch ($$0) {
            case HORIZONTAL:
                return new ScreenRectangle($$1, $$2, $$3, $$4);
            case VERTICAL:
                return new ScreenRectangle($$2, $$1, $$4, $$3);
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    public ScreenRectangle step(ScreenDirection $$0) {
        return new ScreenRectangle(this.position.step($$0), this.width, this.height);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public int getLength(ScreenAxis $$0) throws MatchException {
        switch ($$0) {
            case HORIZONTAL:
                return this.width;
            case VERTICAL:
                return this.height;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public int getBoundInDirection(ScreenDirection $$0) throws MatchException {
        ScreenAxis $$1 = $$0.getAxis();
        if ($$0.isPositive()) {
            return (this.position.getCoordinate($$1) + getLength($$1)) - 1;
        }
        return this.position.getCoordinate($$1);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public ScreenRectangle getBorder(ScreenDirection $$0) throws MatchException {
        int $$1 = getBoundInDirection($$0);
        ScreenAxis $$2 = $$0.getAxis().orthogonal();
        int $$3 = getBoundInDirection($$2.getNegative());
        int $$4 = getLength($$2);
        return of($$0.getAxis(), $$1, $$3, 1, $$4).step($$0);
    }

    public boolean overlaps(ScreenRectangle $$0) {
        return overlapsInAxis($$0, ScreenAxis.HORIZONTAL) && overlapsInAxis($$0, ScreenAxis.VERTICAL);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public boolean overlapsInAxis(ScreenRectangle $$0, ScreenAxis $$1) throws MatchException {
        int $$2 = getBoundInDirection($$1.getNegative());
        int $$3 = $$0.getBoundInDirection($$1.getNegative());
        int $$4 = getBoundInDirection($$1.getPositive());
        int $$5 = $$0.getBoundInDirection($$1.getPositive());
        return Math.max($$2, $$3) <= Math.min($$4, $$5);
    }

    public int getCenterInAxis(ScreenAxis $$0) {
        return (getBoundInDirection($$0.getPositive()) + getBoundInDirection($$0.getNegative())) / 2;
    }

    public ScreenRectangle intersection(ScreenRectangle $$0) {
        int $$1 = Math.max(left(), $$0.left());
        int $$2 = Math.max(top(), $$0.top());
        int $$3 = Math.min(right(), $$0.right());
        int $$4 = Math.min(bottom(), $$0.bottom());
        if ($$1 >= $$3 || $$2 >= $$4) {
            return null;
        }
        return new ScreenRectangle($$1, $$2, $$3 - $$1, $$4 - $$2);
    }

    public boolean intersects(ScreenRectangle $$0) {
        return left() < $$0.right() && right() > $$0.left() && top() < $$0.bottom() && bottom() > $$0.top();
    }

    public boolean encompasses(ScreenRectangle $$0) {
        return $$0.left() >= left() && $$0.top() >= top() && $$0.right() <= right() && $$0.bottom() <= bottom();
    }

    public int top() {
        return this.position.y();
    }

    public int bottom() {
        return this.position.y() + this.height;
    }

    public int left() {
        return this.position.x();
    }

    public int right() {
        return this.position.x() + this.width;
    }

    public boolean containsPoint(int $$0, int $$1) {
        return $$0 >= left() && $$0 < right() && $$1 >= top() && $$1 < bottom();
    }

    public ScreenRectangle transformAxisAligned(Matrix3x2fc $$0) {
        Vector2f $$1 = $$0.transformPosition(left(), top(), new Vector2f());
        Vector2f $$2 = $$0.transformPosition(right(), bottom(), new Vector2f());
        return new ScreenRectangle(Mth.floor($$1.x), Mth.floor($$1.y), Mth.floor($$2.x - $$1.x), Mth.floor($$2.y - $$1.y));
    }

    public ScreenRectangle transformMaxBounds(Matrix3x2fc $$0) {
        Vector2f $$1 = $$0.transformPosition(left(), top(), new Vector2f());
        Vector2f $$2 = $$0.transformPosition(right(), top(), new Vector2f());
        Vector2f $$3 = $$0.transformPosition(left(), bottom(), new Vector2f());
        Vector2f $$4 = $$0.transformPosition(right(), bottom(), new Vector2f());
        float $$5 = Math.min(Math.min($$1.x(), $$3.x()), Math.min($$2.x(), $$4.x()));
        float $$6 = Math.max(Math.max($$1.x(), $$3.x()), Math.max($$2.x(), $$4.x()));
        float $$7 = Math.min(Math.min($$1.y(), $$3.y()), Math.min($$2.y(), $$4.y()));
        float $$8 = Math.max(Math.max($$1.y(), $$3.y()), Math.max($$2.y(), $$4.y()));
        return new ScreenRectangle(Mth.floor($$5), Mth.floor($$7), Mth.ceil($$6 - $$5), Mth.ceil($$8 - $$7));
    }
}
