package net.minecraft.util.profiling.metrics.profiling;

import com.google.common.base.Stopwatch;
import com.google.common.base.Ticker;
import com.google.common.collect.ImmutableSet;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.SystemReport;
import net.minecraft.util.profiling.ProfileCollector;
import net.minecraft.util.profiling.metrics.MetricCategory;
import net.minecraft.util.profiling.metrics.MetricSampler;
import net.minecraft.util.profiling.metrics.MetricsRegistry;
import net.minecraft.util.profiling.metrics.MetricsSamplerProvider;
import org.slf4j.Logger;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/metrics/profiling/ServerMetricsSamplersProvider.class */
public class ServerMetricsSamplersProvider implements MetricsSamplerProvider {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Set<MetricSampler> samplers = new ObjectOpenHashSet();
    private final ProfilerSamplerAdapter samplerFactory = new ProfilerSamplerAdapter();

    public ServerMetricsSamplersProvider(LongSupplier $$0, boolean $$1) {
        this.samplers.add(tickTimeSampler($$0));
        if ($$1) {
            this.samplers.addAll(runtimeIndependentSamplers());
        }
    }

    public static Set<MetricSampler> runtimeIndependentSamplers() {
        ImmutableSet.Builder<MetricSampler> $$0 = ImmutableSet.builder();
        try {
            CpuStats $$1 = new CpuStats();
            Stream streamMapToObj = IntStream.range(0, $$1.nrOfCpus).mapToObj($$12 -> {
                return MetricSampler.create("cpu#" + $$12, MetricCategory.CPU, () -> {
                    return $$1.loadForCpu($$12);
                });
            });
            Objects.requireNonNull($$0);
            streamMapToObj.forEach((v1) -> {
                r1.add(v1);
            });
        } catch (Throwable $$2) {
            LOGGER.warn("Failed to query cpu, no cpu stats will be recorded", $$2);
        }
        $$0.add(MetricSampler.create("heap MiB", MetricCategory.JVM, () -> {
            return SystemReport.sizeInMiB(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
        }));
        $$0.addAll(MetricsRegistry.INSTANCE.getRegisteredSamplers());
        return $$0.build();
    }

    @Override // net.minecraft.util.profiling.metrics.MetricsSamplerProvider
    public Set<MetricSampler> samplers(Supplier<ProfileCollector> $$0) {
        this.samplers.addAll(this.samplerFactory.newSamplersFoundInProfiler($$0));
        return this.samplers;
    }

    public static MetricSampler tickTimeSampler(final LongSupplier $$0) {
        Stopwatch $$1 = Stopwatch.createUnstarted(new Ticker() { // from class: net.minecraft.util.profiling.metrics.profiling.ServerMetricsSamplersProvider.1
            public long read() {
                return $$0.getAsLong();
            }
        });
        ToDoubleFunction<Stopwatch> $$2 = $$02 -> {
            if ($$02.isRunning()) {
                $$02.stop();
            }
            long $$12 = $$02.elapsed(TimeUnit.NANOSECONDS);
            $$02.reset();
            return $$12;
        };
        MetricSampler.ValueIncreasedByPercentage $$3 = new MetricSampler.ValueIncreasedByPercentage(2.0f);
        return MetricSampler.builder("ticktime", MetricCategory.TICK_LOOP, $$2, $$1).withBeforeTick((v0) -> {
            v0.start();
        }).withThresholdAlert($$3).build();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/metrics/profiling/ServerMetricsSamplersProvider$CpuStats.class */
    static class CpuStats {
        private final SystemInfo systemInfo = new SystemInfo();
        private final CentralProcessor processor = this.systemInfo.getHardware().getProcessor();
        public final int nrOfCpus = this.processor.getLogicalProcessorCount();
        private long[][] previousCpuLoadTick = this.processor.getProcessorCpuLoadTicks();
        private double[] currentLoad = this.processor.getProcessorCpuLoadBetweenTicks(this.previousCpuLoadTick);
        private long lastPollMs;

        CpuStats() {
        }

        public double loadForCpu(int $$0) {
            long $$1 = System.currentTimeMillis();
            if (this.lastPollMs == 0 || this.lastPollMs + 501 < $$1) {
                this.currentLoad = this.processor.getProcessorCpuLoadBetweenTicks(this.previousCpuLoadTick);
                this.previousCpuLoadTick = this.processor.getProcessorCpuLoadTicks();
                this.lastPollMs = $$1;
            }
            return this.currentLoad[$$0] * 100.0d;
        }
    }
}
