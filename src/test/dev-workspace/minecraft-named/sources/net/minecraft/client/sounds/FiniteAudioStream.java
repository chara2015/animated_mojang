package net.minecraft.client.sounds;

import java.io.IOException;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/sounds/FiniteAudioStream.class */
public interface FiniteAudioStream extends AudioStream {
    ByteBuffer readAll() throws IOException;
}
