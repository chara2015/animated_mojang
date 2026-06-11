package com.mojang.blaze3d.audio;

import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import java.nio.IntBuffer;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.OptionalLong;
import java.util.Set;
import net.minecraft.SharedConstants;
import net.minecraft.util.Mth;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALC11;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.openal.ALUtil;
import org.lwjgl.system.MemoryStack;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/audio/Library.class */
public class Library {
    private static final int NO_DEVICE = 0;
    private static final int DEFAULT_CHANNEL_COUNT = 30;
    private long currentDevice;
    private long context;
    private boolean supportsDisconnections;
    static final Logger LOGGER = LogUtils.getLogger();
    private static final ChannelPool EMPTY = new ChannelPool() { // from class: com.mojang.blaze3d.audio.Library.1
        @Override // com.mojang.blaze3d.audio.Library.ChannelPool
        public Channel acquire() {
            return null;
        }

        @Override // com.mojang.blaze3d.audio.Library.ChannelPool
        public boolean release(Channel $$0) {
            return false;
        }

        @Override // com.mojang.blaze3d.audio.Library.ChannelPool
        public void cleanup() {
        }

        @Override // com.mojang.blaze3d.audio.Library.ChannelPool
        public int getMaxCount() {
            return 0;
        }

        @Override // com.mojang.blaze3d.audio.Library.ChannelPool
        public int getUsedCount() {
            return 0;
        }
    };
    private ChannelPool staticChannels = EMPTY;
    private ChannelPool streamingChannels = EMPTY;
    private final Listener listener = new Listener();
    private String defaultDeviceName = getDefaultDeviceName();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/audio/Library$ChannelPool.class */
    interface ChannelPool {
        Channel acquire();

        boolean release(Channel channel);

        void cleanup();

        int getMaxCount();

        int getUsedCount();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/audio/Library$Pool.class */
    public enum Pool {
        STATIC,
        STREAMING
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/audio/Library$CountingChannelPool.class */
    static class CountingChannelPool implements ChannelPool {
        private final int limit;
        private final Set<Channel> activeChannels = Sets.newIdentityHashSet();

        public CountingChannelPool(int $$0) {
            this.limit = $$0;
        }

        @Override // com.mojang.blaze3d.audio.Library.ChannelPool
        public Channel acquire() {
            if (this.activeChannels.size() >= this.limit) {
                if (SharedConstants.IS_RUNNING_IN_IDE) {
                    Library.LOGGER.warn("Maximum sound pool size {} reached", Integer.valueOf(this.limit));
                    return null;
                }
                return null;
            }
            Channel $$0 = Channel.create();
            if ($$0 != null) {
                this.activeChannels.add($$0);
            }
            return $$0;
        }

        @Override // com.mojang.blaze3d.audio.Library.ChannelPool
        public boolean release(Channel $$0) {
            if (!this.activeChannels.remove($$0)) {
                return false;
            }
            $$0.destroy();
            return true;
        }

        @Override // com.mojang.blaze3d.audio.Library.ChannelPool
        public void cleanup() {
            this.activeChannels.forEach((v0) -> {
                v0.destroy();
            });
            this.activeChannels.clear();
        }

        @Override // com.mojang.blaze3d.audio.Library.ChannelPool
        public int getMaxCount() {
            return this.limit;
        }

        @Override // com.mojang.blaze3d.audio.Library.ChannelPool
        public int getUsedCount() {
            return this.activeChannels.size();
        }
    }

    public void init(String $$0, boolean $$1) {
        this.currentDevice = openDeviceOrFallback($$0);
        this.supportsDisconnections = false;
        ALCCapabilities $$2 = ALC.createCapabilities(this.currentDevice);
        if (OpenAlUtil.checkALCError(this.currentDevice, "Get capabilities")) {
            throw new IllegalStateException("Failed to get OpenAL capabilities");
        }
        if (!$$2.OpenALC11) {
            throw new IllegalStateException("OpenAL 1.1 not supported");
        }
        MemoryStack $$3 = MemoryStack.stackPush();
        try {
            IntBuffer $$4 = createAttributes($$3, $$2.ALC_SOFT_HRTF && $$1);
            this.context = ALC10.alcCreateContext(this.currentDevice, $$4);
            if ($$3 != null) {
                $$3.close();
            }
            if (OpenAlUtil.checkALCError(this.currentDevice, "Create context")) {
                throw new IllegalStateException("Unable to create OpenAL context");
            }
            ALC10.alcMakeContextCurrent(this.context);
            int $$5 = getChannelCount();
            int $$6 = Mth.clamp((int) Mth.sqrt($$5), 2, 8);
            int $$7 = Mth.clamp($$5 - $$6, 8, 255);
            this.staticChannels = new CountingChannelPool($$7);
            this.streamingChannels = new CountingChannelPool($$6);
            ALCapabilities $$8 = AL.createCapabilities($$2);
            OpenAlUtil.checkALError("Initialization");
            if (!$$8.AL_EXT_source_distance_model) {
                throw new IllegalStateException("AL_EXT_source_distance_model is not supported");
            }
            AL10.alEnable(512);
            if (!$$8.AL_EXT_LINEAR_DISTANCE) {
                throw new IllegalStateException("AL_EXT_LINEAR_DISTANCE is not supported");
            }
            OpenAlUtil.checkALError("Enable per-source distance models");
            LOGGER.info("OpenAL initialized on device {}", getCurrentDeviceName());
            this.supportsDisconnections = ALC10.alcIsExtensionPresent(this.currentDevice, "ALC_EXT_disconnect");
        } catch (Throwable th) {
            if ($$3 != null) {
                try {
                    $$3.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private IntBuffer createAttributes(MemoryStack $$0, boolean $$1) {
        IntBuffer $$3 = $$0.callocInt(11);
        int $$4 = ALC10.alcGetInteger(this.currentDevice, 6548);
        if ($$4 > 0) {
            $$3.put(6546).put($$1 ? 1 : 0);
            $$3.put(6550).put(0);
        }
        $$3.put(6554).put(1);
        return $$3.put(0).flip();
    }

    private int getChannelCount() {
        MemoryStack $$0 = MemoryStack.stackPush();
        try {
            int $$1 = ALC10.alcGetInteger(this.currentDevice, 4098);
            if (OpenAlUtil.checkALCError(this.currentDevice, "Get attributes size")) {
                throw new IllegalStateException("Failed to get OpenAL attributes");
            }
            IntBuffer $$2 = $$0.mallocInt($$1);
            ALC10.alcGetIntegerv(this.currentDevice, 4099, $$2);
            if (OpenAlUtil.checkALCError(this.currentDevice, "Get attributes")) {
                throw new IllegalStateException("Failed to get OpenAL attributes");
            }
            int $$3 = 0;
            while ($$3 < $$1) {
                int i = $$3;
                int $$32 = $$3 + 1;
                int $$4 = $$2.get(i);
                if ($$4 == 0) {
                    break;
                }
                $$3 = $$32 + 1;
                int $$5 = $$2.get($$32);
                if ($$4 == 4112) {
                    if ($$0 != null) {
                        $$0.close();
                    }
                    return $$5;
                }
            }
            if ($$0 != null) {
                $$0.close();
                return 30;
            }
            return 30;
        } catch (Throwable th) {
            if ($$0 != null) {
                try {
                    $$0.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public static String getDefaultDeviceName() {
        if (!ALC10.alcIsExtensionPresent(0L, "ALC_ENUMERATE_ALL_EXT")) {
            return null;
        }
        ALUtil.getStringList(0L, 4115);
        return ALC10.alcGetString(0L, 4114);
    }

    public String getCurrentDeviceName() {
        String $$0 = ALC10.alcGetString(this.currentDevice, 4115);
        if ($$0 == null) {
            $$0 = ALC10.alcGetString(this.currentDevice, 4101);
        }
        if ($$0 == null) {
            $$0 = "Unknown";
        }
        return $$0;
    }

    public synchronized boolean hasDefaultDeviceChanged() {
        String $$0 = getDefaultDeviceName();
        if (Objects.equals(this.defaultDeviceName, $$0)) {
            return false;
        }
        this.defaultDeviceName = $$0;
        return true;
    }

    private static long openDeviceOrFallback(String $$0) {
        OptionalLong $$1 = OptionalLong.empty();
        if ($$0 != null) {
            $$1 = tryOpenDevice($$0);
        }
        if ($$1.isEmpty()) {
            $$1 = tryOpenDevice(getDefaultDeviceName());
        }
        if ($$1.isEmpty()) {
            $$1 = tryOpenDevice(null);
        }
        if ($$1.isEmpty()) {
            throw new IllegalStateException("Failed to open OpenAL device");
        }
        return $$1.getAsLong();
    }

    private static OptionalLong tryOpenDevice(String $$0) {
        long $$1 = ALC10.alcOpenDevice($$0);
        if ($$1 != 0 && !OpenAlUtil.checkALCError($$1, "Open device")) {
            return OptionalLong.of($$1);
        }
        return OptionalLong.empty();
    }

    public void cleanup() {
        this.staticChannels.cleanup();
        this.streamingChannels.cleanup();
        ALC10.alcDestroyContext(this.context);
        if (this.currentDevice != 0) {
            ALC10.alcCloseDevice(this.currentDevice);
        }
    }

    public Listener getListener() {
        return this.listener;
    }

    public Channel acquireChannel(Pool $$0) {
        return ($$0 == Pool.STREAMING ? this.streamingChannels : this.staticChannels).acquire();
    }

    public void releaseChannel(Channel $$0) {
        if (!this.staticChannels.release($$0) && !this.streamingChannels.release($$0)) {
            throw new IllegalStateException("Tried to release unknown channel");
        }
    }

    public String getDebugString() {
        return String.format(Locale.ROOT, "Sounds: %d/%d + %d/%d", Integer.valueOf(this.staticChannels.getUsedCount()), Integer.valueOf(this.staticChannels.getMaxCount()), Integer.valueOf(this.streamingChannels.getUsedCount()), Integer.valueOf(this.streamingChannels.getMaxCount()));
    }

    public List<String> getAvailableSoundDevices() {
        List<String> $$0 = ALUtil.getStringList(0L, 4115);
        if ($$0 == null) {
            return Collections.emptyList();
        }
        return $$0;
    }

    public boolean isCurrentDeviceDisconnected() {
        return this.supportsDisconnections && ALC11.alcGetInteger(this.currentDevice, 787) == 0;
    }
}
