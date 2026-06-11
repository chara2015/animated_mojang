package net.minecraft.util.profiling.metrics;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.stream.Collectors;
import net.minecraft.util.profiling.metrics.MetricSampler;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/metrics/MetricsRegistry.class */
public class MetricsRegistry {
    public static final MetricsRegistry INSTANCE = new MetricsRegistry();
    private final WeakHashMap<ProfilerMeasured, Void> measuredInstances = new WeakHashMap<>();

    private MetricsRegistry() {
    }

    public void add(ProfilerMeasured $$0) {
        this.measuredInstances.put($$0, null);
    }

    public List<MetricSampler> getRegisteredSamplers() {
        Map<String, List<MetricSampler>> $$0 = (Map) this.measuredInstances.keySet().stream().flatMap($$02 -> {
            return $$02.profiledMetrics().stream();
        }).collect(Collectors.groupingBy((v0) -> {
            return v0.getName();
        }));
        return aggregateDuplicates($$0);
    }

    private static List<MetricSampler> aggregateDuplicates(Map<String, List<MetricSampler>> $$0) {
        return (List) $$0.entrySet().stream().map($$02 -> {
            String $$1 = (String) $$02.getKey();
            List<MetricSampler> $$2 = (List) $$02.getValue();
            return $$2.size() > 1 ? new AggregatedMetricSampler($$1, $$2) : $$2.get(0);
        }).collect(Collectors.toList());
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/metrics/MetricsRegistry$AggregatedMetricSampler.class */
    static class AggregatedMetricSampler extends MetricSampler {
        private final List<MetricSampler> delegates;

        AggregatedMetricSampler(String $$0, List<MetricSampler> $$1) {
            super($$0, $$1.get(0).getCategory(), () -> {
                return averageValueFromDelegates($$1);
            }, () -> {
                beforeTick($$1);
            }, thresholdTest($$1));
            this.delegates = $$1;
        }

        private static MetricSampler.ThresholdTest thresholdTest(List<MetricSampler> $$0) {
            return $$1 -> {
                return $$0.stream().anyMatch($$1 -> {
                    if ($$1.thresholdTest != null) {
                        return $$1.thresholdTest.test($$1);
                    }
                    return false;
                });
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void beforeTick(List<MetricSampler> $$0) {
            for (MetricSampler $$1 : $$0) {
                $$1.onStartTick();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static double averageValueFromDelegates(List<MetricSampler> $$0) {
            double $$1 = 0.0d;
            for (MetricSampler $$2 : $$0) {
                $$1 += $$2.getSampler().getAsDouble();
            }
            return $$1 / ((double) $$0.size());
        }

        @Override // net.minecraft.util.profiling.metrics.MetricSampler
        public boolean equals(Object $$0) {
            if (this == $$0) {
                return true;
            }
            if ($$0 == null || getClass() != $$0.getClass() || !super.equals($$0)) {
                return false;
            }
            AggregatedMetricSampler $$1 = (AggregatedMetricSampler) $$0;
            return this.delegates.equals($$1.delegates);
        }

        @Override // net.minecraft.util.profiling.metrics.MetricSampler
        public int hashCode() {
            return Objects.hash(Integer.valueOf(super.hashCode()), this.delegates);
        }
    }
}
