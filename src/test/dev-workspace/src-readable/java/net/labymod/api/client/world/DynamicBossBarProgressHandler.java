package net.labymod.api.client.world;

import it.unimi.dsi.fastutil.floats.FloatConsumer;
import net.labymod.api.util.function.FloatSupplier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/DynamicBossBarProgressHandler.class */
public class DynamicBossBarProgressHandler implements BossBarProgressHandler {
    private final FloatConsumer progressConsumer;
    private final FloatSupplier progressSupplier;

    public DynamicBossBarProgressHandler(FloatConsumer progressConsumer, FloatSupplier progressSupplier) {
        this.progressConsumer = progressConsumer;
        this.progressSupplier = progressSupplier;
    }

    @Override // net.labymod.api.client.world.BossBarProgressHandler
    public float getProgress() {
        return this.progressSupplier.get();
    }

    @Override // net.labymod.api.client.world.BossBarProgressHandler
    public void setProgress(float progress) {
        this.progressConsumer.accept(progress);
    }
}
