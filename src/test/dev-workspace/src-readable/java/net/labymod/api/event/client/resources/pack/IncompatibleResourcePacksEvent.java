package net.labymod.api.event.client.resources.pack;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Collection;
import net.labymod.api.client.resources.pack.IncompatibleResourcePack;
import net.labymod.api.event.Event;
import net.labymod.api.event.LabyEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/resources/pack/IncompatibleResourcePacksEvent.class */
@LabyEvent(background = true)
public final class IncompatibleResourcePacksEvent extends Record implements Event {
    private final Collection<IncompatibleResourcePack> incompatibleResourcePacks;

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, IncompatibleResourcePacksEvent.class), IncompatibleResourcePacksEvent.class, "incompatibleResourcePacks", "FIELD:Lnet/labymod/api/event/client/resources/pack/IncompatibleResourcePacksEvent;->incompatibleResourcePacks:Ljava/util/Collection;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, IncompatibleResourcePacksEvent.class), IncompatibleResourcePacksEvent.class, "incompatibleResourcePacks", "FIELD:Lnet/labymod/api/event/client/resources/pack/IncompatibleResourcePacksEvent;->incompatibleResourcePacks:Ljava/util/Collection;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, IncompatibleResourcePacksEvent.class, Object.class), IncompatibleResourcePacksEvent.class, "incompatibleResourcePacks", "FIELD:Lnet/labymod/api/event/client/resources/pack/IncompatibleResourcePacksEvent;->incompatibleResourcePacks:Ljava/util/Collection;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public Collection<IncompatibleResourcePack> incompatibleResourcePacks() {
        return this.incompatibleResourcePacks;
    }

    public IncompatibleResourcePacksEvent(Collection<IncompatibleResourcePack> incompatibleResourcePacks) {
        this.incompatibleResourcePacks = incompatibleResourcePacks;
    }
}
