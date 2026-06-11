package net.labymod.v1_12_2.client.resources.sound;

import net.labymod.api.client.resources.ResourceLocation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/resources/sound/VersionedPositionedSound.class */
public class VersionedPositionedSound extends cgj {
    public VersionedPositionedSound(ResourceLocation resourceLocation, float volume, float pitch, a type, float x, float y, float z) {
        super((nf) resourceLocation.getMinecraftLocation(), qg.a);
        this.d = volume;
        this.e = pitch;
        this.f = x;
        this.g = y;
        this.h = z;
        this.i = false;
        this.j = 0;
        this.k = type;
    }
}
