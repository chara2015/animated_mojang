package net.labymod.v1_20_1.client.resources.sound;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/client/resources/sound/VersionedMinecraftSounds.class */
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
        enn.N().ah().a(fxt.a(amh.qp, 1.0f));
    }

    @Override // net.labymod.api.client.resources.sound.MinecraftSounds
    public void playSound(ResourceLocation location, float volume, float pitch) {
        playSound(fxt.a(new amg((acq) location.getMinecraftLocation(), 16.0f, false), pitch, volume));
    }

    @Override // net.labymod.api.client.resources.sound.MinecraftSounds
    public void playSound(ResourceLocation location, float volume, float pitch, FloatVector3 position) {
        playSound(new fxt((acq) location.getMinecraftLocation(), ami.a, volume, pitch, fxy.t(), false, 0, a.a, position.getX(), position.getY(), position.getZ(), true));
    }

    @Override // net.labymod.api.client.resources.sound.MinecraftSounds
    public void playSound(ResourceLocation location, float volume, float pitch, DoubleVector3 position) {
        playSound(new fxt((acq) location.getMinecraftLocation(), ami.a, volume, pitch, fxy.t(), false, 0, a.a, position.getX(), position.getY(), position.getZ(), true));
    }

    private void playSound(fxt sound) {
        sound.a(enn.N().ah());
        enn.N().ah().a(sound);
    }
}
