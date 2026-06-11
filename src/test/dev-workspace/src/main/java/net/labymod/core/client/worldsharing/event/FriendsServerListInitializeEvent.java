package net.labymod.core.client.worldsharing.event;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.event.Event;
import net.labymod.api.event.ReplayableEvent;
import net.labymod.core.client.worldsharing.model.FriendServerListener;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/worldsharing/event/FriendsServerListInitializeEvent.class */
@ReplayableEvent
public final class FriendsServerListInitializeEvent extends Record implements Event {
    private final FriendServerListener friendServerListener;

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, FriendsServerListInitializeEvent.class), FriendsServerListInitializeEvent.class, "friendServerListener", "FIELD:Lnet/labymod/core/client/worldsharing/event/FriendsServerListInitializeEvent;->friendServerListener:Lnet/labymod/core/client/worldsharing/model/FriendServerListener;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, FriendsServerListInitializeEvent.class), FriendsServerListInitializeEvent.class, "friendServerListener", "FIELD:Lnet/labymod/core/client/worldsharing/event/FriendsServerListInitializeEvent;->friendServerListener:Lnet/labymod/core/client/worldsharing/model/FriendServerListener;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, FriendsServerListInitializeEvent.class, Object.class), FriendsServerListInitializeEvent.class, "friendServerListener", "FIELD:Lnet/labymod/core/client/worldsharing/event/FriendsServerListInitializeEvent;->friendServerListener:Lnet/labymod/core/client/worldsharing/model/FriendServerListener;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public FriendServerListener friendServerListener() {
        return this.friendServerListener;
    }

    public FriendsServerListInitializeEvent(FriendServerListener friendServerListener) {
        this.friendServerListener = friendServerListener;
    }
}
