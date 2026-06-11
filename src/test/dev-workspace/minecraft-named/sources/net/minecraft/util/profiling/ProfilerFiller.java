package net.minecraft.util.profiling;

import java.util.function.Supplier;
import net.minecraft.util.profiling.metrics.MetricCategory;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/ProfilerFiller.class */
public interface ProfilerFiller {
    public static final String ROOT = "root";

    void startTick();

    void endTick();

    void push(String str);

    void push(Supplier<String> supplier);

    void pop();

    void popPush(String str);

    void popPush(Supplier<String> supplier);

    void markForCharting(MetricCategory metricCategory);

    void incrementCounter(String str, int i);

    void incrementCounter(Supplier<String> supplier, int i);

    default void addZoneText(String $$0) {
    }

    default void addZoneValue(long $$0) {
    }

    default void setZoneColor(int $$0) {
    }

    default Zone zone(String $$0) {
        push($$0);
        return new Zone(this);
    }

    default Zone zone(Supplier<String> $$0) {
        push($$0);
        return new Zone(this);
    }

    default void incrementCounter(String $$0) {
        incrementCounter($$0, 1);
    }

    default void incrementCounter(Supplier<String> $$0) {
        incrementCounter($$0, 1);
    }

    static ProfilerFiller combine(ProfilerFiller $$0, ProfilerFiller $$1) {
        if ($$0 == InactiveProfiler.INSTANCE) {
            return $$1;
        }
        if ($$1 == InactiveProfiler.INSTANCE) {
            return $$0;
        }
        return new CombinedProfileFiller($$0, $$1);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/ProfilerFiller$CombinedProfileFiller.class */
    public static class CombinedProfileFiller implements ProfilerFiller {
        private final ProfilerFiller first;
        private final ProfilerFiller second;

        public CombinedProfileFiller(ProfilerFiller $$0, ProfilerFiller $$1) {
            this.first = $$0;
            this.second = $$1;
        }

        @Override // net.minecraft.util.profiling.ProfilerFiller
        public void startTick() {
            this.first.startTick();
            this.second.startTick();
        }

        @Override // net.minecraft.util.profiling.ProfilerFiller
        public void endTick() {
            this.first.endTick();
            this.second.endTick();
        }

        @Override // net.minecraft.util.profiling.ProfilerFiller
        public void push(String $$0) {
            this.first.push($$0);
            this.second.push($$0);
        }

        @Override // net.minecraft.util.profiling.ProfilerFiller
        public void push(Supplier<String> $$0) {
            this.first.push($$0);
            this.second.push($$0);
        }

        @Override // net.minecraft.util.profiling.ProfilerFiller
        public void markForCharting(MetricCategory $$0) {
            this.first.markForCharting($$0);
            this.second.markForCharting($$0);
        }

        @Override // net.minecraft.util.profiling.ProfilerFiller
        public void pop() {
            this.first.pop();
            this.second.pop();
        }

        @Override // net.minecraft.util.profiling.ProfilerFiller
        public void popPush(String $$0) {
            this.first.popPush($$0);
            this.second.popPush($$0);
        }

        @Override // net.minecraft.util.profiling.ProfilerFiller
        public void popPush(Supplier<String> $$0) {
            this.first.popPush($$0);
            this.second.popPush($$0);
        }

        @Override // net.minecraft.util.profiling.ProfilerFiller
        public void incrementCounter(String $$0, int $$1) {
            this.first.incrementCounter($$0, $$1);
            this.second.incrementCounter($$0, $$1);
        }

        @Override // net.minecraft.util.profiling.ProfilerFiller
        public void incrementCounter(Supplier<String> $$0, int $$1) {
            this.first.incrementCounter($$0, $$1);
            this.second.incrementCounter($$0, $$1);
        }

        @Override // net.minecraft.util.profiling.ProfilerFiller
        public void addZoneText(String $$0) {
            this.first.addZoneText($$0);
            this.second.addZoneText($$0);
        }

        @Override // net.minecraft.util.profiling.ProfilerFiller
        public void addZoneValue(long $$0) {
            this.first.addZoneValue($$0);
            this.second.addZoneValue($$0);
        }

        @Override // net.minecraft.util.profiling.ProfilerFiller
        public void setZoneColor(int $$0) {
            this.first.setZoneColor($$0);
            this.second.setZoneColor($$0);
        }
    }
}
