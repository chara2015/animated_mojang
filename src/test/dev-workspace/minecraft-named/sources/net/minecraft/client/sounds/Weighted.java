package net.minecraft.client.sounds;

import net.minecraft.util.RandomSource;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/sounds/Weighted.class */
public interface Weighted<T> {
    int getWeight();

    T getSound(RandomSource randomSource);

    void preloadIfRequired(SoundEngine soundEngine);
}
