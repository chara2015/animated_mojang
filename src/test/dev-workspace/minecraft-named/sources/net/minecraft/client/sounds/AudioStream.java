package net.minecraft.client.sounds;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.sound.sampled.AudioFormat;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/sounds/AudioStream.class */
public interface AudioStream extends Closeable {
    AudioFormat getFormat();

    ByteBuffer read(int i) throws IOException;
}
