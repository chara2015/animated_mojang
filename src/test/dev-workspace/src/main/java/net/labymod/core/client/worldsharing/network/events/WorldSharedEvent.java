package net.labymod.core.client.worldsharing.network.events;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.core.client.worldsharing.network.NetEventHandler;
import net.labymod.core.client.worldsharing.network.NetworkEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/worldsharing/network/events/WorldSharedEvent.class */
@NetworkEvent.Subject("world.start")
public final class WorldSharedEvent extends Record implements NetworkEvent {
    private final String worldName;
    private final String version;
    private final int mcVersion;
    private final int slots;
    private final byte privacy;

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, WorldSharedEvent.class), WorldSharedEvent.class, "worldName;version;mcVersion;slots;privacy", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/WorldSharedEvent;->worldName:Ljava/lang/String;", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/WorldSharedEvent;->version:Ljava/lang/String;", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/WorldSharedEvent;->mcVersion:I", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/WorldSharedEvent;->slots:I", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/WorldSharedEvent;->privacy:B").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, WorldSharedEvent.class), WorldSharedEvent.class, "worldName;version;mcVersion;slots;privacy", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/WorldSharedEvent;->worldName:Ljava/lang/String;", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/WorldSharedEvent;->version:Ljava/lang/String;", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/WorldSharedEvent;->mcVersion:I", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/WorldSharedEvent;->slots:I", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/WorldSharedEvent;->privacy:B").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, WorldSharedEvent.class, Object.class), WorldSharedEvent.class, "worldName;version;mcVersion;slots;privacy", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/WorldSharedEvent;->worldName:Ljava/lang/String;", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/WorldSharedEvent;->version:Ljava/lang/String;", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/WorldSharedEvent;->mcVersion:I", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/WorldSharedEvent;->slots:I", "FIELD:Lnet/labymod/core/client/worldsharing/network/events/WorldSharedEvent;->privacy:B").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public String worldName() {
        return this.worldName;
    }

    public String version() {
        return this.version;
    }

    public int mcVersion() {
        return this.mcVersion;
    }

    public int slots() {
        return this.slots;
    }

    public byte privacy() {
        return this.privacy;
    }

    public WorldSharedEvent(String worldName, String version, int mcVersion, int slots, byte privacy) {
        this.worldName = worldName;
        this.version = version;
        this.mcVersion = mcVersion;
        this.slots = slots;
        this.privacy = privacy;
    }

    @Override // net.labymod.core.client.worldsharing.network.NetworkEvent
    public void handle(NetEventHandler handler) {
    }
}
