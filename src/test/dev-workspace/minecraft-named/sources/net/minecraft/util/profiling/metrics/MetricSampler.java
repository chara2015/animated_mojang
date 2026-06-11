package net.minecraft.util.profiling.metrics;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import it.unimi.dsi.fastutil.ints.Int2DoubleMap;
import it.unimi.dsi.fastutil.ints.Int2DoubleOpenHashMap;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;
import java.util.function.ToDoubleFunction;
import net.minecraft.world.level.lighting.ChunkSkyLightSources;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/metrics/MetricSampler.class */
public class MetricSampler {
    private final String name;
    private final MetricCategory category;
    private final DoubleSupplier sampler;
    private final Runnable beforeTick;
    final ThresholdTest thresholdTest;
    private double currentValue;
    private final ByteBuf values = ByteBufAllocator.DEFAULT.buffer();
    private final ByteBuf ticks = ByteBufAllocator.DEFAULT.buffer();
    private volatile boolean isRunning = true;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/metrics/MetricSampler$ThresholdTest.class */
    public interface ThresholdTest {
        boolean test(double d);
    }

    protected MetricSampler(String $$0, MetricCategory $$1, DoubleSupplier $$2, Runnable $$3, ThresholdTest $$4) {
        this.name = $$0;
        this.category = $$1;
        this.beforeTick = $$3;
        this.sampler = $$2;
        this.thresholdTest = $$4;
    }

    public static MetricSampler create(String $$0, MetricCategory $$1, DoubleSupplier $$2) {
        return new MetricSampler($$0, $$1, $$2, null, null);
    }

    public static <T> MetricSampler create(String $$0, MetricCategory $$1, T $$2, ToDoubleFunction<T> $$3) {
        return builder($$0, $$1, $$3, $$2).build();
    }

    public static <T> MetricSamplerBuilder<T> builder(String $$0, MetricCategory $$1, ToDoubleFunction<T> $$2, T $$3) {
        if ($$2 == null) {
            throw new IllegalStateException();
        }
        return new MetricSamplerBuilder<>($$0, $$1, $$2, $$3);
    }

    public void onStartTick() {
        if (!this.isRunning) {
            throw new IllegalStateException("Not running");
        }
        if (this.beforeTick != null) {
            this.beforeTick.run();
        }
    }

    public void onEndTick(int $$0) {
        verifyRunning();
        this.currentValue = this.sampler.getAsDouble();
        this.values.writeDouble(this.currentValue);
        this.ticks.writeInt($$0);
    }

    public void onFinished() {
        verifyRunning();
        this.values.release();
        this.ticks.release();
        this.isRunning = false;
    }

    private void verifyRunning() {
        if (!this.isRunning) {
            throw new IllegalStateException(String.format(Locale.ROOT, "Sampler for metric %s not started!", this.name));
        }
    }

    DoubleSupplier getSampler() {
        return this.sampler;
    }

    public String getName() {
        return this.name;
    }

    public MetricCategory getCategory() {
        return this.category;
    }

    public SamplerResult result() {
        Int2DoubleOpenHashMap int2DoubleOpenHashMap = new Int2DoubleOpenHashMap();
        int $$1 = Integer.MIN_VALUE;
        int i = ChunkSkyLightSources.NEGATIVE_INFINITY;
        while (true) {
            int $$2 = i;
            if (this.values.isReadable(8)) {
                int $$3 = this.ticks.readInt();
                if ($$1 == Integer.MIN_VALUE) {
                    $$1 = $$3;
                }
                int2DoubleOpenHashMap.put($$3, this.values.readDouble());
                i = $$3;
            } else {
                return new SamplerResult($$1, $$2, int2DoubleOpenHashMap);
            }
        }
    }

    public boolean triggersThreshold() {
        return this.thresholdTest != null && this.thresholdTest.test(this.currentValue);
    }

    public boolean equals(Object $$0) {
        if (this == $$0) {
            return true;
        }
        if ($$0 == null || getClass() != $$0.getClass()) {
            return false;
        }
        MetricSampler $$1 = (MetricSampler) $$0;
        return this.name.equals($$1.name) && this.category.equals($$1.category);
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/metrics/MetricSampler$SamplerResult.class */
    public static class SamplerResult {
        private final Int2DoubleMap recording;
        private final int firstTick;
        private final int lastTick;

        public SamplerResult(int $$0, int $$1, Int2DoubleMap $$2) {
            this.firstTick = $$0;
            this.lastTick = $$1;
            this.recording = $$2;
        }

        public double valueAtTick(int $$0) {
            return this.recording.get($$0);
        }

        public int getFirstTick() {
            return this.firstTick;
        }

        public int getLastTick() {
            return this.lastTick;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/metrics/MetricSampler$ValueIncreasedByPercentage.class */
    public static class ValueIncreasedByPercentage implements ThresholdTest {
        private final float percentageIncreaseThreshold;
        private double previousValue = Double.MIN_VALUE;

        public ValueIncreasedByPercentage(float $$0) {
            this.percentageIncreaseThreshold = $$0;
        }

        @Override // net.minecraft.util.profiling.metrics.MetricSampler.ThresholdTest
        public boolean test(double $$0) {
            boolean $$2;
            if (this.previousValue == Double.MIN_VALUE || $$0 <= this.previousValue) {
                $$2 = false;
            } else {
                $$2 = ($$0 - this.previousValue) / this.previousValue >= ((double) this.percentageIncreaseThreshold);
            }
            this.previousValue = $$0;
            return $$2;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/metrics/MetricSampler$MetricSamplerBuilder.class */
    public static class MetricSamplerBuilder<T> {
        private final String name;
        private final MetricCategory category;
        private final DoubleSupplier sampler;
        private final T context;
        private Runnable beforeTick;
        private ThresholdTest thresholdTest;

        public MetricSamplerBuilder(String $$0, MetricCategory $$1, ToDoubleFunction<T> $$2, T $$3) {
            this.name = $$0;
            this.category = $$1;
            this.sampler = () -> {
                return $$2.applyAsDouble($$3);
            };
            this.context = $$3;
        }

        public MetricSamplerBuilder<T> withBeforeTick(Consumer<T> $$0) {
            this.beforeTick = () -> {
                $$0.accept(this.context);
            };
            return this;
        }

        public MetricSamplerBuilder<T> withThresholdAlert(ThresholdTest $$0) {
            this.thresholdTest = $$0;
            return this;
        }

        public MetricSampler build() {
            return new MetricSampler(this.name, this.category, this.sampler, this.beforeTick, this.thresholdTest);
        }
    }
}
