package net.minecraft.client.sounds;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mojang.blaze3d.audio.ListenerTransform;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.SharedConstants;
import net.minecraft.client.Camera;
import net.minecraft.client.Options;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundEventRegistration;
import net.minecraft.client.resources.sounds.SoundEventRegistrationSerializer;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.profiling.Zone;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.MultipliedFloats;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/sounds/SoundManager.class */
public class SoundManager extends SimplePreparableReloadListener<Preparations> {
    private static final String SOUNDS_PATH = "sounds.json";
    private final SoundEngine soundEngine;
    public static final Identifier EMPTY_SOUND_LOCATION = Identifier.withDefaultNamespace("empty");
    public static final Sound EMPTY_SOUND = new Sound(EMPTY_SOUND_LOCATION, ConstantFloat.of(1.0f), ConstantFloat.of(1.0f), 1, Sound.Type.FILE, false, false, 16);
    public static final Identifier INTENTIONALLY_EMPTY_SOUND_LOCATION = Identifier.withDefaultNamespace("intentionally_empty");
    public static final WeighedSoundEvents INTENTIONALLY_EMPTY_SOUND_EVENT = new WeighedSoundEvents(INTENTIONALLY_EMPTY_SOUND_LOCATION, null);
    public static final Sound INTENTIONALLY_EMPTY_SOUND = new Sound(INTENTIONALLY_EMPTY_SOUND_LOCATION, ConstantFloat.of(1.0f), ConstantFloat.of(1.0f), 1, Sound.Type.FILE, false, false, 16);
    static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(SoundEventRegistration.class, new SoundEventRegistrationSerializer()).create();
    private static final TypeToken<Map<String, SoundEventRegistration>> SOUND_EVENT_REGISTRATION_TYPE = new TypeToken<Map<String, SoundEventRegistration>>() { // from class: net.minecraft.client.sounds.SoundManager.1
    };
    private final Map<Identifier, WeighedSoundEvents> registry = Maps.newHashMap();
    private final Map<Identifier, Resource> soundCache = new HashMap();

    public SoundManager(Options $$0) {
        this.soundEngine = new SoundEngine(this, $$0, ResourceProvider.fromMap(this.soundCache));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.minecraft.server.packs.resources.SimplePreparableReloadListener
    public Preparations prepare(ResourceManager $$0, ProfilerFiller $$1) {
        Zone $$5;
        Reader $$8;
        Preparations $$2 = new Preparations();
        Zone $$3 = $$1.zone("list");
        try {
            $$2.listResources($$0);
            if ($$3 != null) {
                $$3.close();
            }
            for (String $$4 : $$0.getNamespaces()) {
                try {
                    $$5 = $$1.zone($$4);
                } catch (IOException e) {
                }
                try {
                    List<Resource> $$6 = $$0.getResourceStack(Identifier.fromNamespaceAndPath($$4, SOUNDS_PATH));
                    for (Resource $$7 : $$6) {
                        $$1.push($$7.sourcePackId());
                        try {
                            $$8 = $$7.openAsReader();
                        } catch (RuntimeException $$11) {
                            LOGGER.warn("Invalid {} in resourcepack: '{}'", new Object[]{SOUNDS_PATH, $$7.sourcePackId(), $$11});
                        }
                        try {
                            $$1.push("parse");
                            Map<String, SoundEventRegistration> $$9 = (Map) GsonHelper.fromJson(GSON, $$8, SOUND_EVENT_REGISTRATION_TYPE);
                            $$1.popPush("register");
                            for (Map.Entry<String, SoundEventRegistration> $$10 : $$9.entrySet()) {
                                $$2.handleRegistration(Identifier.fromNamespaceAndPath($$4, $$10.getKey()), $$10.getValue());
                            }
                            $$1.pop();
                            if ($$8 != null) {
                                $$8.close();
                            }
                            $$1.pop();
                        } catch (Throwable th) {
                            if ($$8 != null) {
                                try {
                                    $$8.close();
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                            }
                            throw th;
                        }
                    }
                    if ($$5 != null) {
                        $$5.close();
                    }
                } catch (Throwable th3) {
                    if ($$5 != null) {
                        try {
                            $$5.close();
                        } catch (Throwable th4) {
                            th3.addSuppressed(th4);
                        }
                    }
                    throw th3;
                }
            }
            return $$2;
        } catch (Throwable th5) {
            if ($$3 != null) {
                try {
                    $$3.close();
                } catch (Throwable th6) {
                    th5.addSuppressed(th6);
                }
            }
            throw th5;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.minecraft.server.packs.resources.SimplePreparableReloadListener
    public void apply(Preparations $$0, ResourceManager $$1, ProfilerFiller $$2) {
        $$0.apply(this.registry, this.soundCache, this.soundEngine);
        if (SharedConstants.IS_RUNNING_IN_IDE) {
            for (Identifier $$3 : this.registry.keySet()) {
                WeighedSoundEvents $$4 = this.registry.get($$3);
                if (!ComponentUtils.isTranslationResolvable($$4.getSubtitle()) && BuiltInRegistries.SOUND_EVENT.containsKey($$3)) {
                    LOGGER.error("Missing subtitle {} for sound event: {}", $$4.getSubtitle(), $$3);
                }
            }
        }
        if (LOGGER.isDebugEnabled()) {
            for (Identifier $$5 : this.registry.keySet()) {
                if (!BuiltInRegistries.SOUND_EVENT.containsKey($$5)) {
                    LOGGER.debug("Not having sound event for: {}", $$5);
                }
            }
        }
        this.soundEngine.reload();
    }

    public List<String> getAvailableSoundDevices() {
        return this.soundEngine.getAvailableSoundDevices();
    }

    public ListenerTransform getListenerTransform() {
        return this.soundEngine.getListenerTransform();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/sounds/SoundManager$Preparations.class */
    protected static class Preparations {
        final Map<Identifier, WeighedSoundEvents> registry = Maps.newHashMap();
        private Map<Identifier, Resource> soundCache = Map.of();

        protected Preparations() {
        }

        void listResources(ResourceManager $$0) {
            this.soundCache = Sound.SOUND_LISTER.listMatchingResources($$0);
        }

        void handleRegistration(Identifier $$0, SoundEventRegistration $$1) {
            Weighted<Sound> weighted;
            WeighedSoundEvents $$2 = this.registry.get($$0);
            boolean $$3 = $$2 == null;
            if ($$3 || $$1.isReplace()) {
                if (!$$3) {
                    SoundManager.LOGGER.debug("Replaced sound event location {}", $$0);
                }
                $$2 = new WeighedSoundEvents($$0, $$1.getSubtitle());
                this.registry.put($$0, $$2);
            }
            ResourceProvider $$4 = ResourceProvider.fromMap(this.soundCache);
            for (final Sound $$5 : $$1.getSounds()) {
                final Identifier $$6 = $$5.getLocation();
                switch ($$5.getType()) {
                    case FILE:
                        if (SoundManager.validateSoundResource($$5, $$0, $$4)) {
                            weighted = $$5;
                            Weighted<Sound> $$9 = weighted;
                            $$2.addSound($$9);
                        }
                        break;
                    case SOUND_EVENT:
                        weighted = new Weighted<Sound>() { // from class: net.minecraft.client.sounds.SoundManager.Preparations.1
                            @Override // net.minecraft.client.sounds.Weighted
                            public int getWeight() {
                                WeighedSoundEvents $$02 = Preparations.this.registry.get($$6);
                                if ($$02 == null) {
                                    return 0;
                                }
                                return $$02.getWeight();
                            }

                            /* JADX WARN: Can't rename method to resolve collision */
                            @Override // net.minecraft.client.sounds.Weighted
                            public Sound getSound(RandomSource $$02) {
                                WeighedSoundEvents $$12 = Preparations.this.registry.get($$6);
                                if ($$12 == null) {
                                    return SoundManager.EMPTY_SOUND;
                                }
                                Sound $$22 = $$12.getSound($$02);
                                return new Sound($$22.getLocation(), new MultipliedFloats($$22.getVolume(), $$5.getVolume()), new MultipliedFloats($$22.getPitch(), $$5.getPitch()), $$5.getWeight(), Sound.Type.FILE, $$22.shouldStream() || $$5.shouldStream(), $$22.shouldPreload(), $$22.getAttenuationDistance());
                            }

                            @Override // net.minecraft.client.sounds.Weighted
                            public void preloadIfRequired(SoundEngine $$02) {
                                WeighedSoundEvents $$12 = Preparations.this.registry.get($$6);
                                if ($$12 == null) {
                                    return;
                                }
                                $$12.preloadIfRequired($$02);
                            }
                        };
                        Weighted<Sound> $$92 = weighted;
                        $$2.addSound($$92);
                        break;
                    default:
                        throw new IllegalStateException("Unknown SoundEventRegistration type: " + String.valueOf($$5.getType()));
                }
            }
        }

        public void apply(Map<Identifier, WeighedSoundEvents> $$0, Map<Identifier, Resource> $$1, SoundEngine $$2) {
            $$0.clear();
            $$1.clear();
            $$1.putAll(this.soundCache);
            for (Map.Entry<Identifier, WeighedSoundEvents> $$3 : this.registry.entrySet()) {
                $$0.put($$3.getKey(), $$3.getValue());
                $$3.getValue().preloadIfRequired($$2);
            }
        }
    }

    static boolean validateSoundResource(Sound $$0, Identifier $$1, ResourceProvider $$2) {
        Identifier $$3 = $$0.getPath();
        if ($$2.getResource($$3).isEmpty()) {
            LOGGER.warn("File {} does not exist, cannot add it to event {}", $$3, $$1);
            return false;
        }
        return true;
    }

    public WeighedSoundEvents getSoundEvent(Identifier $$0) {
        return this.registry.get($$0);
    }

    public Collection<Identifier> getAvailableSounds() {
        return this.registry.keySet();
    }

    public void queueTickingSound(TickableSoundInstance $$0) {
        this.soundEngine.queueTickingSound($$0);
    }

    public SoundEngine.PlayResult play(SoundInstance $$0) {
        return this.soundEngine.play($$0);
    }

    public void playDelayed(SoundInstance $$0, int $$1) {
        this.soundEngine.playDelayed($$0, $$1);
    }

    public void updateSource(Camera $$0) {
        this.soundEngine.updateSource($$0);
    }

    public void pauseAllExcept(SoundSource... $$0) {
        this.soundEngine.pauseAllExcept($$0);
    }

    public void stop() {
        this.soundEngine.stopAll();
    }

    public void destroy() {
        this.soundEngine.destroy();
    }

    public void emergencyShutdown() {
        this.soundEngine.emergencyShutdown();
    }

    public void tick(boolean $$0) {
        this.soundEngine.tick($$0);
    }

    public void resume() {
        this.soundEngine.resume();
    }

    public void refreshCategoryVolume(SoundSource $$0) {
        this.soundEngine.refreshCategoryVolume($$0);
    }

    public void stop(SoundInstance $$0) {
        this.soundEngine.stop($$0);
    }

    public void updateCategoryVolume(SoundSource $$0, float $$1) {
        this.soundEngine.updateCategoryVolume($$0, $$1);
    }

    public boolean isActive(SoundInstance $$0) {
        return this.soundEngine.isActive($$0);
    }

    public void addListener(SoundEventListener $$0) {
        this.soundEngine.addEventListener($$0);
    }

    public void removeListener(SoundEventListener $$0) {
        this.soundEngine.removeEventListener($$0);
    }

    public void stop(Identifier $$0, SoundSource $$1) {
        this.soundEngine.stop($$0, $$1);
    }

    public String getDebugString() {
        return this.soundEngine.getDebugString();
    }

    public void reload() {
        this.soundEngine.reload();
    }
}
