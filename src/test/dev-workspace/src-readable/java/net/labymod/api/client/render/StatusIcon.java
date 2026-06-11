package net.labymod.api.client.render;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.client.resources.ResourceLocation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/StatusIcon.class */
public final class StatusIcon extends Record {
    private final ResourceLocation location;
    private final int x;
    private final int y;

    public StatusIcon(ResourceLocation location, int x, int y) {
        this.location = location;
        this.x = x;
        this.y = y;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, StatusIcon.class), StatusIcon.class, "location;x;y", "FIELD:Lnet/labymod/api/client/render/StatusIcon;->location:Lnet/labymod/api/client/resources/ResourceLocation;", "FIELD:Lnet/labymod/api/client/render/StatusIcon;->x:I", "FIELD:Lnet/labymod/api/client/render/StatusIcon;->y:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, StatusIcon.class), StatusIcon.class, "location;x;y", "FIELD:Lnet/labymod/api/client/render/StatusIcon;->location:Lnet/labymod/api/client/resources/ResourceLocation;", "FIELD:Lnet/labymod/api/client/render/StatusIcon;->x:I", "FIELD:Lnet/labymod/api/client/render/StatusIcon;->y:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, StatusIcon.class, Object.class), StatusIcon.class, "location;x;y", "FIELD:Lnet/labymod/api/client/render/StatusIcon;->location:Lnet/labymod/api/client/resources/ResourceLocation;", "FIELD:Lnet/labymod/api/client/render/StatusIcon;->x:I", "FIELD:Lnet/labymod/api/client/render/StatusIcon;->y:I").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public ResourceLocation location() {
        return this.location;
    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }
}
