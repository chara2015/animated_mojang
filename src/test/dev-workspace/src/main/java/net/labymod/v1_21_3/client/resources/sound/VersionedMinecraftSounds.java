package net.labymod.v1_21_3.client.resources.sound;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/client/resources/sound/VersionedMinecraftSounds.class */
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
        fmg.Q().ak().a(hfd.a(axf.sq, 1.0f));
    }

    @Override // net.labymod.api.client.resources.sound.MinecraftSounds
    public void playSound(ResourceLocation location, float volume, float pitch) {
        playSound(hfd.a(axe.a((alz) location.getMinecraftLocation(), 16.0f), pitch, volume));
    }

    @Override // net.labymod.api.client.resources.sound.MinecraftSounds
    public void playSound(ResourceLocation location, float volume, float pitch, FloatVector3 position) {
        playSound(new hfd((alz) location.getMinecraftLocation(), axg.a, volume, pitch, hfi.t(), false, 0, a.a, position.getX(), position.getY(), position.getZ(), true));
    }

    @Override // net.labymod.api.client.resources.sound.MinecraftSounds
    public void playSound(ResourceLocation location, float volume, float pitch, DoubleVector3 position) {
        playSound(new hfd((alz) location.getMinecraftLocation(), axg.a, volume, pitch, hfi.t(), false, 0, a.a, position.getX(), position.getY(), position.getZ(), true));
    }

    private void playSound(hfd sound) {
        sound.a(fmg.Q().ak());
        fmg.Q().ak().a(sound);
    }
}
