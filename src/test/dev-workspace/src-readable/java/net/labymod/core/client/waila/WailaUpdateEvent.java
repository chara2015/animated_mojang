package net.labymod.core.client.waila;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.client.waila.Waila;
import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/waila/WailaUpdateEvent.class */
public final class WailaUpdateEvent extends Record implements Event {
    private final Waila<?> oldWaila;
    private final Waila<?> newWaila;

    public WailaUpdateEvent(Waila<?> oldWaila, Waila<?> newWaila) {
        this.oldWaila = oldWaila;
        this.newWaila = newWaila;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, WailaUpdateEvent.class), WailaUpdateEvent.class, "oldWaila;newWaila", "FIELD:Lnet/labymod/core/client/waila/WailaUpdateEvent;->oldWaila:Lnet/labymod/api/client/waila/Waila;", "FIELD:Lnet/labymod/core/client/waila/WailaUpdateEvent;->newWaila:Lnet/labymod/api/client/waila/Waila;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, WailaUpdateEvent.class), WailaUpdateEvent.class, "oldWaila;newWaila", "FIELD:Lnet/labymod/core/client/waila/WailaUpdateEvent;->oldWaila:Lnet/labymod/api/client/waila/Waila;", "FIELD:Lnet/labymod/core/client/waila/WailaUpdateEvent;->newWaila:Lnet/labymod/api/client/waila/Waila;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, WailaUpdateEvent.class, Object.class), WailaUpdateEvent.class, "oldWaila;newWaila", "FIELD:Lnet/labymod/core/client/waila/WailaUpdateEvent;->oldWaila:Lnet/labymod/api/client/waila/Waila;", "FIELD:Lnet/labymod/core/client/waila/WailaUpdateEvent;->newWaila:Lnet/labymod/api/client/waila/Waila;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public Waila<?> oldWaila() {
        return this.oldWaila;
    }

    public Waila<?> newWaila() {
        return this.newWaila;
    }
}
