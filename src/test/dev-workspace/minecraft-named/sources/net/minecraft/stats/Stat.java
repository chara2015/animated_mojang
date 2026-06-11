package net.minecraft.stats;

import java.util.Objects;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/stats/Stat.class */
public class Stat<T> extends ObjectiveCriteria {
    public static final StreamCodec<RegistryFriendlyByteBuf, Stat<?>> STREAM_CODEC = ByteBufCodecs.registry(Registries.STAT_TYPE).dispatch((v0) -> {
        return v0.getType();
    }, (v0) -> {
        return v0.streamCodec();
    });
    private final StatFormatter formatter;
    private final T value;
    private final StatType<T> type;

    protected Stat(StatType<T> $$0, T $$1, StatFormatter $$2) {
        super(buildName($$0, $$1));
        this.type = $$0;
        this.formatter = $$2;
        this.value = $$1;
    }

    public static <T> String buildName(StatType<T> $$0, T $$1) {
        return locationToKey(BuiltInRegistries.STAT_TYPE.getKey($$0)) + ":" + locationToKey($$0.getRegistry().getKey($$1));
    }

    private static String locationToKey(Identifier $$0) {
        return $$0.toString().replace(':', '.');
    }

    public StatType<T> getType() {
        return this.type;
    }

    public T getValue() {
        return this.value;
    }

    public String format(int $$0) {
        return this.formatter.format($$0);
    }

    public boolean equals(Object $$0) {
        return this == $$0 || (($$0 instanceof Stat) && Objects.equals(getName(), ((Stat) $$0).getName()));
    }

    public int hashCode() {
        return getName().hashCode();
    }

    public String toString() {
        return "Stat{name=" + getName() + ", formatter=" + String.valueOf(this.formatter) + "}";
    }
}
