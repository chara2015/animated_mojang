package net.labymod.api.event.client.resources;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/resources/ReleaseTextureEvent.class */
public final class ReleaseTextureEvent extends Record implements Event {
    private final ResourceLocation location;

    public ReleaseTextureEvent(ResourceLocation location) {
        this.location = location;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ReleaseTextureEvent.class), ReleaseTextureEvent.class, "location", "FIELD:Lnet/labymod/api/event/client/resources/ReleaseTextureEvent;->location:Lnet/labymod/api/client/resources/ResourceLocation;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ReleaseTextureEvent.class), ReleaseTextureEvent.class, "location", "FIELD:Lnet/labymod/api/event/client/resources/ReleaseTextureEvent;->location:Lnet/labymod/api/client/resources/ResourceLocation;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ReleaseTextureEvent.class, Object.class), ReleaseTextureEvent.class, "location", "FIELD:Lnet/labymod/api/event/client/resources/ReleaseTextureEvent;->location:Lnet/labymod/api/client/resources/ResourceLocation;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public ResourceLocation location() {
        return this.location;
    }
}
