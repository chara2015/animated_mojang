package net.labymod.api.server;

import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/server/IntegratedServer.class */
@Referenceable
public interface IntegratedServer {
    boolean isLanWorldOpened();

    @Nullable
    LocalWorld getLocalWorld();
}
