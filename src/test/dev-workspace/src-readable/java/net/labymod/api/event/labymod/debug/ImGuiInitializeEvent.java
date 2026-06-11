package net.labymod.api.event.labymod.debug;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.client.gfx.imgui.control.ControlEntryRegistry;
import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/debug/ImGuiInitializeEvent.class */
public final class ImGuiInitializeEvent extends Record implements Event {
    private final ControlEntryRegistry registry;

    public ImGuiInitializeEvent(ControlEntryRegistry registry) {
        this.registry = registry;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ImGuiInitializeEvent.class), ImGuiInitializeEvent.class, "registry", "FIELD:Lnet/labymod/api/event/labymod/debug/ImGuiInitializeEvent;->registry:Lnet/labymod/api/client/gfx/imgui/control/ControlEntryRegistry;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ImGuiInitializeEvent.class), ImGuiInitializeEvent.class, "registry", "FIELD:Lnet/labymod/api/event/labymod/debug/ImGuiInitializeEvent;->registry:Lnet/labymod/api/client/gfx/imgui/control/ControlEntryRegistry;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ImGuiInitializeEvent.class, Object.class), ImGuiInitializeEvent.class, "registry", "FIELD:Lnet/labymod/api/event/labymod/debug/ImGuiInitializeEvent;->registry:Lnet/labymod/api/client/gfx/imgui/control/ControlEntryRegistry;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public ControlEntryRegistry registry() {
        return this.registry;
    }
}
