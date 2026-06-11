package net.minecraft.util.profiling.metrics;

import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.util.profiling.ProfileCollector;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/metrics/MetricsSamplerProvider.class */
public interface MetricsSamplerProvider {
    Set<MetricSampler> samplers(Supplier<ProfileCollector> supplier);
}
