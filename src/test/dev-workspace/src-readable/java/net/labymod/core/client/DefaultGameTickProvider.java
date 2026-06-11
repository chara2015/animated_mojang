package net.labymod.core.client;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.GameTickProvider;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.models.Implements;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/DefaultGameTickProvider.class */
@Singleton
@Implements(GameTickProvider.class)
public class DefaultGameTickProvider implements GameTickProvider {
    private final LabyAPI labyAPI;
    private int tickCount;
    private int pausedTickCount;

    @Inject
    public DefaultGameTickProvider(LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }

    @Subscribe
    public void onGameTick(GameTickEvent event) {
        if (event.phase() != Phase.PRE) {
            return;
        }
        this.tickCount++;
        if (this.labyAPI.minecraft().isPaused()) {
            return;
        }
        this.pausedTickCount++;
    }

    @Override // net.labymod.api.client.GameTickProvider
    public float get() {
        return this.tickCount + this.labyAPI.minecraft().getPartialTicks();
    }

    @Override // net.labymod.api.client.GameTickProvider
    public float getPaused() {
        return this.pausedTickCount + this.labyAPI.minecraft().getPartialTicks();
    }

    @Override // net.labymod.api.client.GameTickProvider
    public int tickCount() {
        return this.tickCount;
    }

    @Override // net.labymod.api.client.GameTickProvider
    public int pausedTickCount() {
        return this.pausedTickCount;
    }
}
