package net.minecraft.client.gui.navigation;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/navigation/ScreenPosition.class */
public final class ScreenPosition extends Record {
    private final int x;
    private final int y;

    public ScreenPosition(int $$0, int $$1) {
        this.x = $$0;
        this.y = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ScreenPosition.class), ScreenPosition.class, "x;y", "FIELD:Lnet/minecraft/client/gui/navigation/ScreenPosition;->x:I", "FIELD:Lnet/minecraft/client/gui/navigation/ScreenPosition;->y:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ScreenPosition.class), ScreenPosition.class, "x;y", "FIELD:Lnet/minecraft/client/gui/navigation/ScreenPosition;->x:I", "FIELD:Lnet/minecraft/client/gui/navigation/ScreenPosition;->y:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ScreenPosition.class, Object.class), ScreenPosition.class, "x;y", "FIELD:Lnet/minecraft/client/gui/navigation/ScreenPosition;->x:I", "FIELD:Lnet/minecraft/client/gui/navigation/ScreenPosition;->y:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static ScreenPosition of(ScreenAxis $$0, int $$1, int $$2) throws MatchException {
        switch ($$0) {
            case HORIZONTAL:
                return new ScreenPosition($$1, $$2);
            case VERTICAL:
                return new ScreenPosition($$2, $$1);
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public ScreenPosition step(ScreenDirection $$0) throws MatchException {
        switch ($$0) {
            case DOWN:
                return new ScreenPosition(this.x, this.y + 1);
            case UP:
                return new ScreenPosition(this.x, this.y - 1);
            case LEFT:
                return new ScreenPosition(this.x - 1, this.y);
            case RIGHT:
                return new ScreenPosition(this.x + 1, this.y);
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public int getCoordinate(ScreenAxis $$0) throws MatchException {
        switch ($$0) {
            case HORIZONTAL:
                return this.x;
            case VERTICAL:
                return this.y;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }
}
