package net.minecraft.client.telemetry;

import com.mojang.authlib.minecraft.TelemetryEvent;
import com.mojang.authlib.minecraft.TelemetrySession;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/telemetry/TelemetryEventType.class */
public class TelemetryEventType {
    static final Map<String, TelemetryEventType> REGISTRY = new Object2ObjectLinkedOpenHashMap();
    public static final Codec<TelemetryEventType> CODEC = Codec.STRING.comapFlatMap($$0 -> {
        TelemetryEventType $$1 = REGISTRY.get($$0);
        if ($$1 != null) {
            return DataResult.success($$1);
        }
        return DataResult.error(() -> {
            return "No TelemetryEventType with key: '" + $$0 + "'";
        });
    }, (v0) -> {
        return v0.id();
    });
    private static final List<TelemetryProperty<?>> GLOBAL_PROPERTIES = List.of(TelemetryProperty.USER_ID, TelemetryProperty.CLIENT_ID, TelemetryProperty.MINECRAFT_SESSION_ID, TelemetryProperty.GAME_VERSION, TelemetryProperty.OPERATING_SYSTEM, TelemetryProperty.PLATFORM, TelemetryProperty.CLIENT_MODDED, TelemetryProperty.LAUNCHER_NAME, TelemetryProperty.EVENT_TIMESTAMP_UTC, TelemetryProperty.OPT_IN);
    private static final List<TelemetryProperty<?>> WORLD_SESSION_PROPERTIES = Stream.concat(GLOBAL_PROPERTIES.stream(), Stream.of((Object[]) new TelemetryProperty[]{TelemetryProperty.WORLD_SESSION_ID, TelemetryProperty.SERVER_MODDED, TelemetryProperty.SERVER_TYPE})).toList();
    public static final TelemetryEventType WORLD_LOADED = builder("world_loaded", "WorldLoaded").defineAll(WORLD_SESSION_PROPERTIES).define(TelemetryProperty.GAME_MODE).define(TelemetryProperty.REALMS_MAP_CONTENT).register();
    public static final TelemetryEventType PERFORMANCE_METRICS = builder("performance_metrics", "PerformanceMetrics").defineAll(WORLD_SESSION_PROPERTIES).define(TelemetryProperty.FRAME_RATE_SAMPLES).define(TelemetryProperty.RENDER_TIME_SAMPLES).define(TelemetryProperty.USED_MEMORY_SAMPLES).define(TelemetryProperty.NUMBER_OF_SAMPLES).define(TelemetryProperty.RENDER_DISTANCE).define(TelemetryProperty.DEDICATED_MEMORY_KB).optIn().register();
    public static final TelemetryEventType WORLD_LOAD_TIMES = builder("world_load_times", "WorldLoadTimes").defineAll(WORLD_SESSION_PROPERTIES).define(TelemetryProperty.WORLD_LOAD_TIME_MS).define(TelemetryProperty.NEW_WORLD).optIn().register();
    public static final TelemetryEventType WORLD_UNLOADED = builder("world_unloaded", "WorldUnloaded").defineAll(WORLD_SESSION_PROPERTIES).define(TelemetryProperty.SECONDS_SINCE_LOAD).define(TelemetryProperty.TICKS_SINCE_LOAD).register();
    public static final TelemetryEventType ADVANCEMENT_MADE = builder("advancement_made", "AdvancementMade").defineAll(WORLD_SESSION_PROPERTIES).define(TelemetryProperty.ADVANCEMENT_ID).define(TelemetryProperty.ADVANCEMENT_GAME_TIME).optIn().register();
    public static final TelemetryEventType GAME_LOAD_TIMES = builder("game_load_times", "GameLoadTimes").defineAll(GLOBAL_PROPERTIES).define(TelemetryProperty.LOAD_TIME_TOTAL_TIME_MS).define(TelemetryProperty.LOAD_TIME_PRE_WINDOW_MS).define(TelemetryProperty.LOAD_TIME_BOOTSTRAP_MS).define(TelemetryProperty.LOAD_TIME_LOADING_OVERLAY_MS).optIn().register();
    private final String id;
    private final String exportKey;
    private final List<TelemetryProperty<?>> properties;
    private final boolean isOptIn;
    private final MapCodec<TelemetryEventInstance> codec;

    TelemetryEventType(String $$0, String $$1, List<TelemetryProperty<?>> $$2, boolean $$3) {
        this.id = $$0;
        this.exportKey = $$1;
        this.properties = $$2;
        this.isOptIn = $$3;
        this.codec = TelemetryPropertyMap.createCodec($$2).xmap($$02 -> {
            return new TelemetryEventInstance(this, $$02);
        }, (v0) -> {
            return v0.properties();
        });
    }

    public static Builder builder(String $$0, String $$1) {
        return new Builder($$0, $$1);
    }

    public String id() {
        return this.id;
    }

    public List<TelemetryProperty<?>> properties() {
        return this.properties;
    }

    public MapCodec<TelemetryEventInstance> codec() {
        return this.codec;
    }

    public boolean isOptIn() {
        return this.isOptIn;
    }

    public TelemetryEvent export(TelemetrySession $$0, TelemetryPropertyMap $$1) {
        TelemetryEvent $$2 = $$0.createNewEvent(this.exportKey);
        for (TelemetryProperty<?> $$3 : this.properties) {
            $$3.export($$1, $$2);
        }
        return $$2;
    }

    public <T> boolean contains(TelemetryProperty<T> $$0) {
        return this.properties.contains($$0);
    }

    public String toString() {
        return "TelemetryEventType[" + this.id + "]";
    }

    public MutableComponent title() {
        return makeTranslation("title");
    }

    public MutableComponent description() {
        return makeTranslation("description");
    }

    private MutableComponent makeTranslation(String $$0) {
        return Component.translatable("telemetry.event." + this.id + "." + $$0);
    }

    public static List<TelemetryEventType> values() {
        return List.copyOf(REGISTRY.values());
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/telemetry/TelemetryEventType$Builder.class */
    public static class Builder {
        private final String id;
        private final String exportKey;
        private final List<TelemetryProperty<?>> properties = new ArrayList();
        private boolean isOptIn;

        Builder(String $$0, String $$1) {
            this.id = $$0;
            this.exportKey = $$1;
        }

        public Builder defineAll(List<TelemetryProperty<?>> $$0) {
            this.properties.addAll($$0);
            return this;
        }

        public <T> Builder define(TelemetryProperty<T> $$0) {
            this.properties.add($$0);
            return this;
        }

        public Builder optIn() {
            this.isOptIn = true;
            return this;
        }

        public TelemetryEventType register() {
            TelemetryEventType $$0 = new TelemetryEventType(this.id, this.exportKey, List.copyOf(this.properties), this.isOptIn);
            if (TelemetryEventType.REGISTRY.putIfAbsent(this.id, $$0) != null) {
                throw new IllegalStateException("Duplicate TelemetryEventType with key: '" + this.id + "'");
            }
            return $$0;
        }
    }
}
