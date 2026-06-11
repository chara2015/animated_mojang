package net.labymod.v1_17_1.client.resources.sound;

import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.Namespaces;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.sound.MinecraftSounds;
import net.labymod.api.client.sound.SoundService;
import net.labymod.api.client.sound.SoundType;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.client.sound.DefaultSoundService;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/client/resources/sound/VersionedMinecraftSounds.class */
@Singleton
@Implements(MinecraftSounds.class)
public class VersionedMinecraftSounds implements MinecraftSounds {
    private final SoundService soundService = Laby.references().soundService();

    public VersionedMinecraftSounds() {
        ((DefaultSoundService) this.soundService).bindDefault(SoundType.BUTTON_CLICK, ResourceLocation.create(Namespaces.MINECRAFT, "ui.button.click"));
    }

    @Override // net.labymod.api.client.resources.sound.MinecraftSounds
    public void playButtonPress() {
        this.soundService.play(SoundType.BUTTON_CLICK);
    }

    @Override // net.labymod.api.client.resources.sound.MinecraftSounds
    public void playChatFilterSound() {
        dvp.C().W().a(ezz.a(afe.mq, 1.0f));
    }

    @Override // net.labymod.api.client.resources.sound.MinecraftSounds
    public void playSound(ResourceLocation location, float volume, float pitch) {
        playSound(ezz.a(new afd((ww) location.getMinecraftLocation()), pitch, volume));
    }

    @Override // net.labymod.api.client.resources.sound.MinecraftSounds
    public void playSound(ResourceLocation location, float volume, float pitch, FloatVector3 position) {
        playSound(new ezz((ww) location.getMinecraftLocation(), aff.a, volume, pitch, false, 0, a.a, position.getX(), position.getY(), position.getZ(), true));
    }

    @Override // net.labymod.api.client.resources.sound.MinecraftSounds
    public void playSound(ResourceLocation location, float volume, float pitch, DoubleVector3 position) {
        playSound(new ezz((ww) location.getMinecraftLocation(), aff.a, volume, pitch, false, 0, a.a, position.getX(), position.getY(), position.getZ(), true));
    }

    private void playSound(ezz sound) {
        sound.a(dvp.C().W());
        dvp.C().W().a(sound);
    }
}
