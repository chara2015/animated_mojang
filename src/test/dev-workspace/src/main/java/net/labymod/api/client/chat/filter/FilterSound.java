package net.labymod.api.client.chat.filter;

import net.labymod.api.client.resources.ResourceLocation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/chat/filter/FilterSound.class */
public class FilterSound {
    private boolean shouldPlaySound;
    private ResourceLocation soundLocation;
    private float volume;
    private float pitch;

    public FilterSound(boolean shouldPlaySound, ResourceLocation soundLocation) {
        this(shouldPlaySound, soundLocation, 1.0f, 1.0f);
    }

    public FilterSound(boolean shouldPlaySound, ResourceLocation soundLocation, float volume, float pitch) {
        this.shouldPlaySound = shouldPlaySound;
        this.soundLocation = soundLocation;
        this.volume = volume;
        this.pitch = pitch;
    }

    public boolean shouldPlaySound() {
        return this.shouldPlaySound;
    }

    public void setShouldPlaySound(boolean shouldPlaySound) {
        this.shouldPlaySound = shouldPlaySound;
    }

    public ResourceLocation getSoundLocation() {
        return this.soundLocation;
    }

    public void setSoundLocation(ResourceLocation soundLocation) {
        this.soundLocation = soundLocation;
    }

    public float getVolume() {
        return this.volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
}
