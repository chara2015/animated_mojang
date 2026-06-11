package net.labymod.api.client.resources.sound;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.math.vector.FloatVector3;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/sound/MinecraftSounds.class */
@Referenceable
public interface MinecraftSounds {
    void playButtonPress();

    void playChatFilterSound();

    void playSound(ResourceLocation resourceLocation, float f, float f2);

    void playSound(ResourceLocation resourceLocation, float f, float f2, FloatVector3 floatVector3);

    void playSound(ResourceLocation resourceLocation, float f, float f2, DoubleVector3 doubleVector3);
}
