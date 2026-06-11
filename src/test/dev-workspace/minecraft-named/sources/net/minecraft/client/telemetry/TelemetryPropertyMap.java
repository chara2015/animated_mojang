package net.minecraft.client.telemetry;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/telemetry/TelemetryPropertyMap.class */
public class TelemetryPropertyMap {
    final Map<TelemetryProperty<?>, Object> entries;

    TelemetryPropertyMap(Map<TelemetryProperty<?>, Object> $$0) {
        this.entries = $$0;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static MapCodec<TelemetryPropertyMap> createCodec(final List<TelemetryProperty<?>> $$0) {
        return new MapCodec<TelemetryPropertyMap>() { // from class: net.minecraft.client.telemetry.TelemetryPropertyMap.1
            public <T> RecordBuilder<T> encode(TelemetryPropertyMap $$02, DynamicOps<T> $$1, RecordBuilder<T> $$2) {
                RecordBuilder<T> $$3 = $$2;
                for (TelemetryProperty<?> $$4 : $$0) {
                    $$3 = encodeProperty($$02, $$3, $$4);
                }
                return $$3;
            }

            private <T, V> RecordBuilder<T> encodeProperty(TelemetryPropertyMap $$02, RecordBuilder<T> $$1, TelemetryProperty<V> $$2) {
                Object obj = $$02.get($$2);
                if (obj != null) {
                    return $$1.add($$2.id(), obj, $$2.codec());
                }
                return $$1;
            }

            public <T> DataResult<TelemetryPropertyMap> decode(DynamicOps<T> $$02, MapLike<T> $$1) {
                DataResult<Builder> $$2 = DataResult.success(new Builder());
                for (TelemetryProperty<?> $$3 : $$0) {
                    $$2 = decodeProperty($$2, $$02, $$1, $$3);
                }
                return $$2.map((v0) -> {
                    return v0.build();
                });
            }

            private <T, V> DataResult<Builder> decodeProperty(DataResult<Builder> $$02, DynamicOps<T> $$1, MapLike<T> $$2, TelemetryProperty<V> $$3) {
                Object obj = $$2.get($$3.id());
                if (obj != null) {
                    DataResult<V> $$5 = $$3.codec().parse($$1, obj);
                    return $$02.apply2stable(($$12, $$22) -> {
                        return $$12.put($$3, $$22);
                    }, $$5);
                }
                return $$02;
            }

            public <T> Stream<T> keys(DynamicOps<T> $$02) {
                Stream map = $$0.stream().map((v0) -> {
                    return v0.id();
                });
                Objects.requireNonNull($$02);
                return map.map($$02::createString);
            }
        };
    }

    public <T> T get(TelemetryProperty<T> telemetryProperty) {
        return (T) this.entries.get(telemetryProperty);
    }

    public String toString() {
        return this.entries.toString();
    }

    public Set<TelemetryProperty<?>> propertySet() {
        return this.entries.keySet();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/telemetry/TelemetryPropertyMap$Builder.class */
    public static class Builder {
        private final Map<TelemetryProperty<?>, Object> entries = new Reference2ObjectOpenHashMap();

        Builder() {
        }

        public <T> Builder put(TelemetryProperty<T> $$0, T $$1) {
            this.entries.put($$0, $$1);
            return this;
        }

        public <T> Builder putIfNotNull(TelemetryProperty<T> $$0, T $$1) {
            if ($$1 != null) {
                this.entries.put($$0, $$1);
            }
            return this;
        }

        public Builder putAll(TelemetryPropertyMap $$0) {
            this.entries.putAll($$0.entries);
            return this;
        }

        public TelemetryPropertyMap build() {
            return new TelemetryPropertyMap(this.entries);
        }
    }
}
