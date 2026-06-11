package net.minecraft.client.resources.sounds;

import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/sounds/AbstractSoundInstance.class */
public abstract class AbstractSoundInstance implements SoundInstance {
    protected Sound sound;
    protected final SoundSource source;
    protected final Identifier identifier;
    protected float volume;
    protected float pitch;
    protected double x;
    protected double y;
    protected double z;
    protected boolean looping;
    protected int delay;
    protected SoundInstance.Attenuation attenuation;
    protected boolean relative;
    protected RandomSource random;

    protected AbstractSoundInstance(SoundEvent $$0, SoundSource $$1, RandomSource $$2) {
        this($$0.location(), $$1, $$2);
    }

    protected AbstractSoundInstance(Identifier $$0, SoundSource $$1, RandomSource $$2) {
        this.volume = 1.0f;
        this.pitch = 1.0f;
        this.attenuation = SoundInstance.Attenuation.LINEAR;
        this.identifier = $$0;
        this.source = $$1;
        this.random = $$2;
    }

    @Override // net.minecraft.client.resources.sounds.SoundInstance
    public Identifier getIdentifier() {
        return this.identifier;
    }

    @Override // net.minecraft.client.resources.sounds.SoundInstance
    public WeighedSoundEvents resolve(SoundManager $$0) {
        if (this.identifier.equals(SoundManager.INTENTIONALLY_EMPTY_SOUND_LOCATION)) {
            this.sound = SoundManager.INTENTIONALLY_EMPTY_SOUND;
            return SoundManager.INTENTIONALLY_EMPTY_SOUND_EVENT;
        }
        WeighedSoundEvents $$1 = $$0.getSoundEvent(this.identifier);
        if ($$1 == null) {
            this.sound = SoundManager.EMPTY_SOUND;
        } else {
            this.sound = $$1.getSound(this.random);
        }
        return $$1;
    }

    @Override // net.minecraft.client.resources.sounds.SoundInstance
    public Sound getSound() {
        return this.sound;
    }

    @Override // net.minecraft.client.resources.sounds.SoundInstance
    public SoundSource getSource() {
        return this.source;
    }

    @Override // net.minecraft.client.resources.sounds.SoundInstance
    public boolean isLooping() {
        return this.looping;
    }

    @Override // net.minecraft.client.resources.sounds.SoundInstance
    public int getDelay() {
        return this.delay;
    }

    @Override // net.minecraft.client.resources.sounds.SoundInstance
    public float getVolume() {
        return this.volume * this.sound.getVolume().sample(this.random);
    }

    @Override // net.minecraft.client.resources.sounds.SoundInstance
    public float getPitch() {
        return this.pitch * this.sound.getPitch().sample(this.random);
    }

    @Override // net.minecraft.client.resources.sounds.SoundInstance
    public double getX() {
        return this.x;
    }

    @Override // net.minecraft.client.resources.sounds.SoundInstance
    public double getY() {
        return this.y;
    }

    @Override // net.minecraft.client.resources.sounds.SoundInstance
    public double getZ() {
        return this.z;
    }

    @Override // net.minecraft.client.resources.sounds.SoundInstance
    public SoundInstance.Attenuation getAttenuation() {
        return this.attenuation;
    }

    @Override // net.minecraft.client.resources.sounds.SoundInstance
    public boolean isRelative() {
        return this.relative;
    }

    public String toString() {
        return "SoundInstance[" + String.valueOf(this.identifier) + "]";
    }
}
