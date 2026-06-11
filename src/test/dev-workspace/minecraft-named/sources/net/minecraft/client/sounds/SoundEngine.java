package net.minecraft.client.sounds;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.audio.Library;
import com.mojang.blaze3d.audio.Listener;
import com.mojang.blaze3d.audio.ListenerTransform;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.SharedConstants;
import net.minecraft.client.Camera;
import net.minecraft.client.Options;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.client.sounds.ChannelAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/sounds/SoundEngine.class */
public class SoundEngine {
    private static final float PITCH_MIN = 0.5f;
    private static final float PITCH_MAX = 2.0f;
    private static final float VOLUME_MIN = 0.0f;
    private static final float VOLUME_MAX = 1.0f;
    private static final int MIN_SOURCE_LIFETIME = 20;
    private static final long DEFAULT_DEVICE_CHECK_INTERVAL_MS = 1000;
    public static final String MISSING_SOUND = "FOR THE DEBUG!";
    private final SoundManager soundManager;
    private final Options options;
    private boolean loaded;
    private final SoundBufferLibrary soundBuffers;
    private int tickCount;
    private long lastDeviceCheckTime;
    private static final Marker MARKER = MarkerFactory.getMarker("SOUNDS");
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Set<Identifier> ONLY_WARN_ONCE = Sets.newHashSet();
    public static final String OPEN_AL_SOFT_PREFIX = "OpenAL Soft on ";
    public static final int OPEN_AL_SOFT_PREFIX_LENGTH = OPEN_AL_SOFT_PREFIX.length();
    private final Library library = new Library();
    private final Listener listener = this.library.getListener();
    private final SoundEngineExecutor executor = new SoundEngineExecutor();
    private final ChannelAccess channelAccess = new ChannelAccess(this.library, this.executor);
    private final AtomicReference<DeviceCheckState> devicePoolState = new AtomicReference<>(DeviceCheckState.NO_CHANGE);
    private final Map<SoundInstance, ChannelAccess.ChannelHandle> instanceToChannel = Maps.newHashMap();
    private final Multimap<SoundSource, SoundInstance> instanceBySource = HashMultimap.create();
    private final Object2FloatMap<SoundSource> gainBySource = (Object2FloatMap) Util.make(new Object2FloatOpenHashMap(), $$0 -> {
        $$0.defaultReturnValue(1.0f);
    });
    private final List<TickableSoundInstance> tickingSounds = Lists.newArrayList();
    private final Map<SoundInstance, Integer> queuedSounds = Maps.newHashMap();
    private final Map<SoundInstance, Integer> soundDeleteTime = Maps.newHashMap();
    private final List<SoundEventListener> listeners = Lists.newArrayList();
    private final List<TickableSoundInstance> queuedTickableSounds = Lists.newArrayList();
    private final List<Sound> preloadQueue = Lists.newArrayList();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/sounds/SoundEngine$DeviceCheckState.class */
    enum DeviceCheckState {
        ONGOING,
        CHANGE_DETECTED,
        NO_CHANGE
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/sounds/SoundEngine$PlayResult.class */
    public enum PlayResult {
        STARTED,
        STARTED_SILENTLY,
        NOT_STARTED
    }

    public SoundEngine(SoundManager $$0, Options $$1, ResourceProvider $$2) {
        this.soundManager = $$0;
        this.options = $$1;
        this.soundBuffers = new SoundBufferLibrary($$2);
    }

    public void reload() {
        ONLY_WARN_ONCE.clear();
        for (SoundEvent $$0 : BuiltInRegistries.SOUND_EVENT) {
            if ($$0 != SoundEvents.EMPTY) {
                Identifier $$1 = $$0.location();
                if (this.soundManager.getSoundEvent($$1) == null) {
                    LOGGER.warn("Missing sound for event: {}", BuiltInRegistries.SOUND_EVENT.getKey($$0));
                    ONLY_WARN_ONCE.add($$1);
                }
            }
        }
        destroy();
        loadLibrary();
    }

    private synchronized void loadLibrary() {
        if (this.loaded) {
            return;
        }
        try {
            String $$0 = this.options.soundDevice().get();
            this.library.init("".equals($$0) ? null : $$0, this.options.directionalAudio().get().booleanValue());
            this.listener.reset();
            CompletableFuture<?> completableFuturePreload = this.soundBuffers.preload(this.preloadQueue);
            List<Sound> list = this.preloadQueue;
            Objects.requireNonNull(list);
            completableFuturePreload.thenRun(list::clear);
            this.loaded = true;
            LOGGER.info(MARKER, "Sound engine started");
        } catch (RuntimeException $$1) {
            LOGGER.error(MARKER, "Error starting SoundSystem. Turning off sounds & music", $$1);
        }
    }

    public void refreshCategoryVolume(SoundSource $$0) {
        if (!this.loaded) {
            return;
        }
        this.instanceToChannel.forEach(($$1, $$2) -> {
            if ($$0 == $$1.getSource() || $$0 == SoundSource.MASTER) {
                float $$3 = calculateVolume($$1);
                $$2.execute($$1 -> {
                    $$1.setVolume($$3);
                });
            }
        });
    }

    public void destroy() {
        if (this.loaded) {
            stopAll();
            this.soundBuffers.clear();
            this.library.cleanup();
            this.loaded = false;
        }
    }

    public void emergencyShutdown() {
        if (this.loaded) {
            this.library.cleanup();
        }
    }

    public void stop(SoundInstance $$0) {
        ChannelAccess.ChannelHandle $$1;
        if (this.loaded && ($$1 = this.instanceToChannel.get($$0)) != null) {
            $$1.execute((v0) -> {
                v0.stop();
            });
        }
    }

    public void updateCategoryVolume(SoundSource $$0, float $$1) {
        this.gainBySource.put($$0, Mth.clamp($$1, 0.0f, 1.0f));
        refreshCategoryVolume($$0);
    }

    public void stopAll() {
        if (this.loaded) {
            this.executor.shutDown();
            this.instanceToChannel.clear();
            this.channelAccess.clear();
            this.queuedSounds.clear();
            this.tickingSounds.clear();
            this.instanceBySource.clear();
            this.soundDeleteTime.clear();
            this.queuedTickableSounds.clear();
            this.gainBySource.clear();
            this.executor.startUp();
        }
    }

    public void addEventListener(SoundEventListener $$0) {
        this.listeners.add($$0);
    }

    public void removeEventListener(SoundEventListener $$0) {
        this.listeners.remove($$0);
    }

    private boolean shouldChangeDevice() {
        if (this.library.isCurrentDeviceDisconnected()) {
            LOGGER.info("Audio device was lost!");
            return true;
        }
        long $$0 = Util.getMillis();
        boolean $$1 = $$0 - this.lastDeviceCheckTime >= 1000;
        if ($$1) {
            this.lastDeviceCheckTime = $$0;
            if (this.devicePoolState.compareAndSet(DeviceCheckState.NO_CHANGE, DeviceCheckState.ONGOING)) {
                String $$2 = this.options.soundDevice().get();
                Util.ioPool().execute(() -> {
                    if ("".equals($$2)) {
                        if (this.library.hasDefaultDeviceChanged()) {
                            LOGGER.info("System default audio device has changed!");
                            this.devicePoolState.compareAndSet(DeviceCheckState.ONGOING, DeviceCheckState.CHANGE_DETECTED);
                        }
                    } else if (!this.library.getCurrentDeviceName().equals($$2) && this.library.getAvailableSoundDevices().contains($$2)) {
                        LOGGER.info("Preferred audio device has become available!");
                        this.devicePoolState.compareAndSet(DeviceCheckState.ONGOING, DeviceCheckState.CHANGE_DETECTED);
                    }
                    this.devicePoolState.compareAndSet(DeviceCheckState.ONGOING, DeviceCheckState.NO_CHANGE);
                });
            }
        }
        return this.devicePoolState.compareAndSet(DeviceCheckState.CHANGE_DETECTED, DeviceCheckState.NO_CHANGE);
    }

    public void tick(boolean $$0) {
        if (shouldChangeDevice()) {
            reload();
        }
        if (!$$0) {
            tickInGameSound();
        } else {
            tickMusicWhenPaused();
        }
        this.channelAccess.scheduleTick();
    }

    private void tickInGameSound() {
        this.tickCount++;
        this.queuedTickableSounds.stream().filter((v0) -> {
            return v0.canPlaySound();
        }).forEach((v1) -> {
            play(v1);
        });
        this.queuedTickableSounds.clear();
        for (TickableSoundInstance $$0 : this.tickingSounds) {
            if (!$$0.canPlaySound()) {
                stop($$0);
            }
            $$0.tick();
            if ($$0.isStopped()) {
                stop($$0);
            } else {
                float $$1 = calculateVolume($$0);
                float $$2 = calculatePitch($$0);
                Vec3 $$3 = new Vec3($$0.getX(), $$0.getY(), $$0.getZ());
                ChannelAccess.ChannelHandle $$4 = this.instanceToChannel.get($$0);
                if ($$4 != null) {
                    $$4.execute($$32 -> {
                        $$32.setVolume($$1);
                        $$32.setPitch($$2);
                        $$32.setSelfPosition($$3);
                    });
                }
            }
        }
        Iterator<Map.Entry<SoundInstance, ChannelAccess.ChannelHandle>> $$5 = this.instanceToChannel.entrySet().iterator();
        while ($$5.hasNext()) {
            Map.Entry<SoundInstance, ChannelAccess.ChannelHandle> $$6 = $$5.next();
            ChannelAccess.ChannelHandle $$7 = $$6.getValue();
            SoundInstance $$8 = $$6.getKey();
            if ($$7.isStopped()) {
                int $$9 = this.soundDeleteTime.get($$8).intValue();
                if ($$9 <= this.tickCount) {
                    if (shouldLoopManually($$8)) {
                        this.queuedSounds.put($$8, Integer.valueOf(this.tickCount + $$8.getDelay()));
                    }
                    $$5.remove();
                    LOGGER.debug(MARKER, "Removed channel {} because it's not playing anymore", $$7);
                    this.soundDeleteTime.remove($$8);
                    try {
                        this.instanceBySource.remove($$8.getSource(), $$8);
                    } catch (RuntimeException e) {
                    }
                    if ($$8 instanceof TickableSoundInstance) {
                        this.tickingSounds.remove($$8);
                    }
                }
            }
        }
        Iterator<Map.Entry<SoundInstance, Integer>> $$10 = this.queuedSounds.entrySet().iterator();
        while ($$10.hasNext()) {
            Map.Entry<SoundInstance, Integer> $$11 = $$10.next();
            if (this.tickCount >= $$11.getValue().intValue()) {
                SoundInstance $$12 = $$11.getKey();
                if ($$12 instanceof TickableSoundInstance) {
                    ((TickableSoundInstance) $$12).tick();
                }
                play($$12);
                $$10.remove();
            }
        }
    }

    private void tickMusicWhenPaused() {
        Iterator<Map.Entry<SoundInstance, ChannelAccess.ChannelHandle>> $$0 = this.instanceToChannel.entrySet().iterator();
        while ($$0.hasNext()) {
            Map.Entry<SoundInstance, ChannelAccess.ChannelHandle> $$1 = $$0.next();
            ChannelAccess.ChannelHandle $$2 = $$1.getValue();
            SoundInstance $$3 = $$1.getKey();
            if ($$3.getSource() == SoundSource.MUSIC && $$2.isStopped()) {
                $$0.remove();
                LOGGER.debug(MARKER, "Removed channel {} because it's not playing anymore", $$2);
                this.soundDeleteTime.remove($$3);
                this.instanceBySource.remove($$3.getSource(), $$3);
            }
        }
    }

    private static boolean requiresManualLooping(SoundInstance $$0) {
        return $$0.getDelay() > 0;
    }

    private static boolean shouldLoopManually(SoundInstance $$0) {
        return $$0.isLooping() && requiresManualLooping($$0);
    }

    private static boolean shouldLoopAutomatically(SoundInstance $$0) {
        return $$0.isLooping() && !requiresManualLooping($$0);
    }

    public boolean isActive(SoundInstance $$0) {
        if (!this.loaded) {
            return false;
        }
        if (this.soundDeleteTime.containsKey($$0) && this.soundDeleteTime.get($$0).intValue() <= this.tickCount) {
            return true;
        }
        return this.instanceToChannel.containsKey($$0);
    }

    public PlayResult play(SoundInstance $$0) {
        if (!this.loaded) {
            return PlayResult.NOT_STARTED;
        }
        if (!$$0.canPlaySound()) {
            return PlayResult.NOT_STARTED;
        }
        WeighedSoundEvents $$1 = $$0.resolve(this.soundManager);
        Identifier $$2 = $$0.getIdentifier();
        if ($$1 == null) {
            if (ONLY_WARN_ONCE.add($$2)) {
                LOGGER.warn(MARKER, "Unable to play unknown soundEvent: {}", $$2);
            }
            if (!SharedConstants.DEBUG_SUBTITLES) {
                return PlayResult.NOT_STARTED;
            }
            $$1 = new WeighedSoundEvents($$2, MISSING_SOUND);
        }
        Sound $$3 = $$0.getSound();
        if ($$3 == SoundManager.INTENTIONALLY_EMPTY_SOUND) {
            return PlayResult.NOT_STARTED;
        }
        if ($$3 == SoundManager.EMPTY_SOUND) {
            if (ONLY_WARN_ONCE.add($$2)) {
                LOGGER.warn(MARKER, "Unable to play empty soundEvent: {}", $$2);
            }
            return PlayResult.NOT_STARTED;
        }
        float $$4 = $$0.getVolume();
        float $$5 = Math.max($$4, 1.0f) * $$3.getAttenuationDistance();
        SoundSource $$6 = $$0.getSource();
        float $$7 = calculateVolume($$4, $$6);
        float $$8 = calculatePitch($$0);
        SoundInstance.Attenuation $$9 = $$0.getAttenuation();
        boolean $$10 = $$0.isRelative();
        if (!this.listeners.isEmpty()) {
            float $$11 = ($$10 || $$9 == SoundInstance.Attenuation.NONE) ? Float.POSITIVE_INFINITY : $$5;
            for (SoundEventListener $$12 : this.listeners) {
                $$12.onPlaySound($$0, $$1, $$11);
            }
        }
        boolean $$13 = false;
        if ($$7 == 0.0f) {
            if ($$0.canStartSilent() || $$6 == SoundSource.MUSIC) {
                $$13 = true;
            } else {
                LOGGER.debug(MARKER, "Skipped playing sound {}, volume was zero.", $$3.getLocation());
                return PlayResult.NOT_STARTED;
            }
        }
        Vec3 $$14 = new Vec3($$0.getX(), $$0.getY(), $$0.getZ());
        boolean $$15 = shouldLoopAutomatically($$0);
        boolean $$16 = $$3.shouldStream();
        CompletableFuture<ChannelAccess.ChannelHandle> $$17 = this.channelAccess.createHandle($$3.shouldStream() ? Library.Pool.STREAMING : Library.Pool.STATIC);
        ChannelAccess.ChannelHandle $$18 = $$17.join();
        if ($$18 == null) {
            if (SharedConstants.IS_RUNNING_IN_IDE) {
                LOGGER.warn("Failed to create new sound handle");
            }
            return PlayResult.NOT_STARTED;
        }
        LOGGER.debug(MARKER, "Playing sound {} for event {}", $$3.getLocation(), $$2);
        this.soundDeleteTime.put($$0, Integer.valueOf(this.tickCount + 20));
        this.instanceToChannel.put($$0, $$18);
        this.instanceBySource.put($$6, $$0);
        $$18.execute($$82 -> {
            $$82.setPitch($$8);
            $$82.setVolume($$7);
            if ($$9 == SoundInstance.Attenuation.LINEAR) {
                $$82.linearAttenuation($$5);
            } else {
                $$82.disableAttenuation();
            }
            $$82.setLooping($$15 && !$$16);
            $$82.setSelfPosition($$14);
            $$82.setRelative($$10);
        });
        if (!$$16) {
            this.soundBuffers.getCompleteBuffer($$3.getPath()).thenAccept($$19 -> {
                $$18.execute($$19 -> {
                    $$19.attachStaticBuffer($$19);
                    $$19.play();
                });
            });
        } else {
            this.soundBuffers.getStream($$3.getPath(), $$15).thenAccept($$110 -> {
                $$18.execute($$110 -> {
                    $$110.attachBufferStream($$110);
                    $$110.play();
                });
            });
        }
        if ($$0 instanceof TickableSoundInstance) {
            this.tickingSounds.add((TickableSoundInstance) $$0);
        }
        if ($$13) {
            return PlayResult.STARTED_SILENTLY;
        }
        return PlayResult.STARTED;
    }

    public void queueTickingSound(TickableSoundInstance $$0) {
        this.queuedTickableSounds.add($$0);
    }

    public void requestPreload(Sound $$0) {
        this.preloadQueue.add($$0);
    }

    private float calculatePitch(SoundInstance $$0) {
        return Mth.clamp($$0.getPitch(), 0.5f, 2.0f);
    }

    private float calculateVolume(SoundInstance $$0) {
        return calculateVolume($$0.getVolume(), $$0.getSource());
    }

    private float calculateVolume(float $$0, SoundSource $$1) {
        return Mth.clamp($$0, 0.0f, 1.0f) * Mth.clamp(this.options.getFinalSoundSourceVolume($$1), 0.0f, 1.0f) * this.gainBySource.getFloat($$1);
    }

    public void pauseAllExcept(SoundSource... $$0) {
        if (!this.loaded) {
            return;
        }
        for (Map.Entry<SoundInstance, ChannelAccess.ChannelHandle> $$1 : this.instanceToChannel.entrySet()) {
            if (!List.of((Object[]) $$0).contains($$1.getKey().getSource())) {
                $$1.getValue().execute((v0) -> {
                    v0.pause();
                });
            }
        }
    }

    public void resume() {
        if (this.loaded) {
            this.channelAccess.executeOnChannels($$0 -> {
                $$0.forEach((v0) -> {
                    v0.unpause();
                });
            });
        }
    }

    public void playDelayed(SoundInstance $$0, int $$1) {
        this.queuedSounds.put($$0, Integer.valueOf(this.tickCount + $$1));
    }

    public void updateSource(Camera $$0) {
        if (!this.loaded || !$$0.isInitialized()) {
            return;
        }
        ListenerTransform $$1 = new ListenerTransform($$0.position(), new Vec3($$0.forwardVector()), new Vec3($$0.upVector()));
        this.executor.execute(() -> {
            this.listener.setTransform($$1);
        });
    }

    public void stop(Identifier $$0, SoundSource $$1) {
        if ($$1 != null) {
            for (SoundInstance $$2 : this.instanceBySource.get($$1)) {
                if ($$0 == null || $$2.getIdentifier().equals($$0)) {
                    stop($$2);
                }
            }
            return;
        }
        if ($$0 == null) {
            stopAll();
            return;
        }
        for (SoundInstance $$3 : this.instanceToChannel.keySet()) {
            if ($$3.getIdentifier().equals($$0)) {
                stop($$3);
            }
        }
    }

    public String getDebugString() {
        return this.library.getDebugString();
    }

    public List<String> getAvailableSoundDevices() {
        return this.library.getAvailableSoundDevices();
    }

    public ListenerTransform getListenerTransform() {
        return this.listener.getTransform();
    }
}
