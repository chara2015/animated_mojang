package net.minecraft.world.entity.ai.memory;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.level.lighting.DynamicGraphMinFixedPoint;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/ai/memory/ExpirableValue.class */
public class ExpirableValue<T> {
    private final T value;
    private long timeToLive;

    public ExpirableValue(T $$0, long $$1) {
        this.value = $$0;
        this.timeToLive = $$1;
    }

    public void tick() {
        if (canExpire()) {
            this.timeToLive--;
        }
    }

    public static <T> ExpirableValue<T> of(T $$0) {
        return new ExpirableValue<>($$0, DynamicGraphMinFixedPoint.SOURCE);
    }

    public static <T> ExpirableValue<T> of(T $$0, long $$1) {
        return new ExpirableValue<>($$0, $$1);
    }

    public long getTimeToLive() {
        return this.timeToLive;
    }

    public T getValue() {
        return this.value;
    }

    public boolean hasExpired() {
        return this.timeToLive <= 0;
    }

    public String toString() {
        return String.valueOf(this.value) + (canExpire() ? " (ttl: " + this.timeToLive + ")" : "");
    }

    @VisibleForDebug
    public boolean canExpire() {
        return this.timeToLive != DynamicGraphMinFixedPoint.SOURCE;
    }

    public static <T> Codec<ExpirableValue<T>> codec(Codec<T> $$0) {
        return RecordCodecBuilder.create($$1 -> {
            return $$1.group($$0.fieldOf("value").forGetter($$02 -> {
                return $$02.value;
            }), Codec.LONG.lenientOptionalFieldOf("ttl").forGetter($$03 -> {
                return $$03.canExpire() ? Optional.of(Long.valueOf($$03.timeToLive)) : Optional.empty();
            })).apply($$1, ($$04, $$1) -> {
                return new ExpirableValue($$04, ((Long) $$1.orElse(Long.valueOf(DynamicGraphMinFixedPoint.SOURCE))).longValue());
            });
        });
    }
}
