package net.minecraft.client.resources.sounds;

import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.Weighted;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.SampledFloat;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/sounds/Sound.class */
public class Sound implements Weighted<Sound> {
    public static final FileToIdConverter SOUND_LISTER = new FileToIdConverter("sounds", ".ogg");
    private final Identifier location;
    private final SampledFloat volume;
    private final SampledFloat pitch;
    private final int weight;
    private final Type type;
    private final boolean stream;
    private final boolean preload;
    private final int attenuationDistance;

    public Sound(Identifier $$0, SampledFloat $$1, SampledFloat $$2, int $$3, Type $$4, boolean $$5, boolean $$6, int $$7) {
        this.location = $$0;
        this.volume = $$1;
        this.pitch = $$2;
        this.weight = $$3;
        this.type = $$4;
        this.stream = $$5;
        this.preload = $$6;
        this.attenuationDistance = $$7;
    }

    public Identifier getLocation() {
        return this.location;
    }

    public Identifier getPath() {
        return SOUND_LISTER.idToFile(this.location);
    }

    public SampledFloat getVolume() {
        return this.volume;
    }

    public SampledFloat getPitch() {
        return this.pitch;
    }

    @Override // net.minecraft.client.sounds.Weighted
    public int getWeight() {
        return this.weight;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.minecraft.client.sounds.Weighted
    public Sound getSound(RandomSource $$0) {
        return this;
    }

    @Override // net.minecraft.client.sounds.Weighted
    public void preloadIfRequired(SoundEngine $$0) {
        if (this.preload) {
            $$0.requestPreload(this);
        }
    }

    public Type getType() {
        return this.type;
    }

    public boolean shouldStream() {
        return this.stream;
    }

    public boolean shouldPreload() {
        return this.preload;
    }

    public int getAttenuationDistance() {
        return this.attenuationDistance;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/sounds/Sound$Type.class */
    public enum Type {
        FILE("file"),
        SOUND_EVENT("event");

        private final String name;

        Type(String $$0) {
            this.name = $$0;
        }

        public static Type getByName(String $$0) {
            for (Type $$1 : values()) {
                if ($$1.name.equals($$0)) {
                    return $$1;
                }
            }
            return null;
        }
    }

    public String toString() {
        return "Sound[" + String.valueOf(this.location) + "]";
    }
}
