package net.labymod.v1_12_2.client.resources.sound;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/resources/sound/VersionedMinecraftSounds.class */
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
        bib.z().U().a(cgp.a(create("block.note.harp"), 1.0f));
    }

    @Override // net.labymod.api.client.resources.sound.MinecraftSounds
    public void playSound(ResourceLocation location, float volume, float pitch) {
        bib.z().U().a(new VersionedPositionedSound(location, volume, pitch, a.a, 0.0f, 0.0f, 0.0f));
    }

    @Override // net.labymod.api.client.resources.sound.MinecraftSounds
    public void playSound(ResourceLocation location, float volume, float pitch, FloatVector3 position) {
        bib.z().U().a(new VersionedPositionedSound(location, volume, pitch, a.b, position.getX(), position.getY(), position.getZ()));
    }

    @Override // net.labymod.api.client.resources.sound.MinecraftSounds
    public void playSound(ResourceLocation location, float volume, float pitch, DoubleVector3 position) {
        bib.z().U().a(new VersionedPositionedSound(location, volume, pitch, a.b, (float) position.getX(), (float) position.getY(), (float) position.getZ()));
    }

    private qe create(String path) {
        return new qe(new nf(path));
    }
}
