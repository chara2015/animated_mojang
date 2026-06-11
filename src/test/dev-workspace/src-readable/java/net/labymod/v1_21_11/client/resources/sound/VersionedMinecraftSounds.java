package net.labymod.v1_21_11.client.resources.sound;

import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.sound.MinecraftSounds;
import net.labymod.api.client.sound.SoundService;
import net.labymod.api.client.sound.SoundType;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.math.vector.FloatVector3;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/resources/sound/VersionedMinecraftSounds.class */
@Singleton
@Implements(MinecraftSounds.class)
public class VersionedMinecraftSounds implements MinecraftSounds {
    private final SoundService soundService = Laby.references().soundService();

    public VersionedMinecraftSounds() {
        this.soundService.bindDefault(SoundType.BUTTON_CLICK, ResourceLocation.create("minecraft", "ui.button.click"));
    }

    public void playButtonPress() {
        this.soundService.play(SoundType.BUTTON_CLICK);
    }

    public void playChatFilterSound() {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.NOTE_BLOCK_HARP, 1.0f));
    }

    public void playSound(ResourceLocation location, float volume, float pitch) {
        playSound(SimpleSoundInstance.forUI(SoundEvent.createFixedRangeEvent((Identifier) location.getMinecraftLocation(), 16.0f), pitch, volume));
    }

    public void playSound(ResourceLocation location, float volume, float pitch, FloatVector3 position) {
        playSound(new SimpleSoundInstance((Identifier) location.getMinecraftLocation(), SoundSource.MASTER, volume, pitch, SoundInstance.createUnseededRandom(), false, 0, SoundInstance.Attenuation.NONE, position.getX(), position.getY(), position.getZ(), true));
    }

    public void playSound(ResourceLocation location, float volume, float pitch, DoubleVector3 position) {
        playSound(new SimpleSoundInstance((Identifier) location.getMinecraftLocation(), SoundSource.MASTER, volume, pitch, SoundInstance.createUnseededRandom(), false, 0, SoundInstance.Attenuation.NONE, position.getX(), position.getY(), position.getZ(), true));
    }

    private void playSound(SimpleSoundInstance sound) {
        sound.resolve(Minecraft.getInstance().getSoundManager());
        Minecraft.getInstance().getSoundManager().play(sound);
    }
}
