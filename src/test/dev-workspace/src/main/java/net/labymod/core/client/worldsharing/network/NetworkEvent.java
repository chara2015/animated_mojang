package net.labymod.core.client.worldsharing.network;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/worldsharing/network/NetworkEvent.class */
public interface NetworkEvent {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/worldsharing/network/NetworkEvent$Subject.class */
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Subject {
        String value();
    }

    void handle(NetEventHandler netEventHandler);
}
