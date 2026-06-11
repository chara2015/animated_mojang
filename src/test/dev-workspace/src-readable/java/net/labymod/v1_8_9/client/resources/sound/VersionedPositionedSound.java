package net.labymod.v1_8_9.client.resources.sound;

import net.labymod.api.client.resources.ResourceLocation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/resources/sound/VersionedPositionedSound.class */
public class VersionedPositionedSound extends bpa {
    public VersionedPositionedSound(ResourceLocation resourceLocation, float volume, float pitch, a type, float x, float y, float z) {
        super((jy) resourceLocation.getMinecraftLocation());
        this.b = volume;
        this.c = pitch;
        this.d = x;
        this.e = y;
        this.f = z;
        this.g = false;
        this.h = 0;
        this.i = type;
    }
}
