package net.labymod.api.client;

import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/GameTickProvider.class */
@Referenceable
public interface GameTickProvider {
    float get();

    float getPaused();

    int tickCount();

    int pausedTickCount();
}
