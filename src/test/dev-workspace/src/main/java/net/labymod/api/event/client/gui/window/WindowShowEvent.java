package net.labymod.api.event.client.gui.window;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/gui/window/WindowShowEvent.class */
public final class WindowShowEvent extends Record implements Event {
    private final long windowHandle;

    public WindowShowEvent(long windowHandle) {
        this.windowHandle = windowHandle;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, WindowShowEvent.class), WindowShowEvent.class, "windowHandle", "FIELD:Lnet/labymod/api/event/client/gui/window/WindowShowEvent;->windowHandle:J").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, WindowShowEvent.class), WindowShowEvent.class, "windowHandle", "FIELD:Lnet/labymod/api/event/client/gui/window/WindowShowEvent;->windowHandle:J").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, WindowShowEvent.class, Object.class), WindowShowEvent.class, "windowHandle", "FIELD:Lnet/labymod/api/event/client/gui/window/WindowShowEvent;->windowHandle:J").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public long windowHandle() {
        return this.windowHandle;
    }
}
