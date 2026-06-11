package net.minecraft.client.resources.sounds;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/sounds/AbstractTickableSoundInstance.class */
public abstract class AbstractTickableSoundInstance extends AbstractSoundInstance implements TickableSoundInstance {
    private boolean stopped;

    protected AbstractTickableSoundInstance(SoundEvent $$0, SoundSource $$1, RandomSource $$2) {
        super($$0, $$1, $$2);
    }

    @Override // net.minecraft.client.resources.sounds.TickableSoundInstance
    public boolean isStopped() {
        return this.stopped;
    }

    protected final void stop() {
        this.stopped = true;
        this.looping = false;
    }
}
