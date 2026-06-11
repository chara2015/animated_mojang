package com.mojang.blaze3d.audio;

import com.mojang.blaze3d.opengl.GlConst;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.sound.sampled.AudioFormat;
import net.minecraft.client.sounds.AudioStream;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.openal.AL10;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/audio/Channel.class */
public class Channel {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final int QUEUED_BUFFER_COUNT = 4;
    public static final int BUFFER_DURATION_SECONDS = 1;
    private final int source;
    private final AtomicBoolean initialized = new AtomicBoolean(true);
    private int streamingBufferSize = GlConst.GL_COLOR_BUFFER_BIT;
    private AudioStream stream;

    static Channel create() {
        int[] $$0 = new int[1];
        AL10.alGenSources($$0);
        if (OpenAlUtil.checkALError("Allocate new source")) {
            return null;
        }
        return new Channel($$0[0]);
    }

    private Channel(int $$0) {
        this.source = $$0;
    }

    public void destroy() {
        if (this.initialized.compareAndSet(true, false)) {
            AL10.alSourceStop(this.source);
            OpenAlUtil.checkALError("Stop");
            if (this.stream != null) {
                try {
                    this.stream.close();
                } catch (IOException $$0) {
                    LOGGER.error("Failed to close audio stream", $$0);
                }
                removeProcessedBuffers();
                this.stream = null;
            }
            AL10.alDeleteSources(new int[]{this.source});
            OpenAlUtil.checkALError("Cleanup");
        }
    }

    public void play() {
        AL10.alSourcePlay(this.source);
    }

    private int getState() {
        if (!this.initialized.get()) {
            return 4116;
        }
        return AL10.alGetSourcei(this.source, 4112);
    }

    public void pause() {
        if (getState() == 4114) {
            AL10.alSourcePause(this.source);
        }
    }

    public void unpause() {
        if (getState() == 4115) {
            AL10.alSourcePlay(this.source);
        }
    }

    public void stop() {
        if (this.initialized.get()) {
            AL10.alSourceStop(this.source);
            OpenAlUtil.checkALError("Stop");
        }
    }

    public boolean playing() {
        return getState() == 4114;
    }

    public boolean stopped() {
        return getState() == 4116;
    }

    public void setSelfPosition(Vec3 $$0) {
        AL10.alSourcefv(this.source, 4100, new float[]{(float) $$0.x, (float) $$0.y, (float) $$0.z});
    }

    public void setPitch(float $$0) {
        AL10.alSourcef(this.source, 4099, $$0);
    }

    public void setLooping(boolean $$0) {
        AL10.alSourcei(this.source, 4103, $$0 ? 1 : 0);
    }

    public void setVolume(float $$0) {
        AL10.alSourcef(this.source, 4106, $$0);
    }

    public void disableAttenuation() {
        AL10.alSourcei(this.source, 53248, 0);
    }

    public void linearAttenuation(float $$0) {
        AL10.alSourcei(this.source, 53248, 53251);
        AL10.alSourcef(this.source, 4131, $$0);
        AL10.alSourcef(this.source, 4129, 1.0f);
        AL10.alSourcef(this.source, 4128, 0.0f);
    }

    public void setRelative(boolean $$0) {
        AL10.alSourcei(this.source, GlConst.GL_EQUAL, $$0 ? 1 : 0);
    }

    public void attachStaticBuffer(SoundBuffer $$0) {
        $$0.getAlBuffer().ifPresent($$02 -> {
            AL10.alSourcei(this.source, 4105, $$02);
        });
    }

    public void attachBufferStream(AudioStream $$0) {
        this.stream = $$0;
        AudioFormat $$1 = $$0.getFormat();
        this.streamingBufferSize = calculateBufferSize($$1, 1);
        pumpBuffers(4);
    }

    private static int calculateBufferSize(AudioFormat $$0, int $$1) {
        return (int) ((($$1 * $$0.getSampleSizeInBits()) / 8.0f) * $$0.getChannels() * $$0.getSampleRate());
    }

    private void pumpBuffers(int $$0) {
        if (this.stream != null) {
            for (int $$1 = 0; $$1 < $$0; $$1++) {
                try {
                    ByteBuffer $$2 = this.stream.read(this.streamingBufferSize);
                    if ($$2 != null) {
                        new SoundBuffer($$2, this.stream.getFormat()).releaseAlBuffer().ifPresent($$02 -> {
                            AL10.alSourceQueueBuffers(this.source, new int[]{$$02});
                        });
                    }
                } catch (IOException $$3) {
                    LOGGER.error("Failed to read from audio stream", $$3);
                    return;
                }
            }
        }
    }

    public void updateStream() {
        if (this.stream != null) {
            int $$0 = removeProcessedBuffers();
            pumpBuffers($$0);
        }
    }

    private int removeProcessedBuffers() {
        int $$0 = AL10.alGetSourcei(this.source, 4118);
        if ($$0 > 0) {
            int[] $$1 = new int[$$0];
            AL10.alSourceUnqueueBuffers(this.source, $$1);
            OpenAlUtil.checkALError("Unqueue buffers");
            AL10.alDeleteBuffers($$1);
            OpenAlUtil.checkALError("Remove processed buffers");
        }
        return $$0;
    }
}
