package net.labymod.api.profiler.frame;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/profiler/frame/FrameStatistics.class */
public final class FrameStatistics {
    private static final double MS_PER_SECOND = 1000.0d;
    private static final int HISTOGRAM_BUCKET_COUNT = 10;
    private static final int TOP_SECTIONS_LIMIT = 20;
    private static final double PERCENTILE_95 = 95.0d;
    private static final double PERCENTILE_99 = 99.0d;
    private static final double PERCENTILE_999 = 99.9d;
    private final long totalFrames;
    private final int slowFrameCount;
    private final double slowFramePercentage;
    private final double minFrameTimeMs;
    private final double maxFrameTimeMs;
    private final double avgFrameTimeMs;
    private final double medianFrameTimeMs;
    private final double stdDevFrameTimeMs;
    private final double percentile95Ms;
    private final double percentile99Ms;
    private final double percentile999Ms;
    private final double avgFps;
    private final double minFps;
    private final double percentile1Fps;
    private final int[] frameTimeHistogram;
    private final String[] histogramLabels;
    private final SectionStatistics[] topSections;
    private static final double PERCENTILE_MEDIAN = 50.0d;
    private static final double PERCENTAGE_MULTIPLIER = 100.0d;
    private static final double[] HISTOGRAM_BUCKETS = {8.33d, 11.11d, 16.67d, 20.0d, 25.0d, 33.33d, PERCENTILE_MEDIAN, 66.67d, PERCENTAGE_MULTIPLIER, Double.MAX_VALUE};
    private static final String[] HISTOGRAM_BUCKET_LABELS = {"<8.3ms (120+ fps)", "8.3-11ms (90-120 fps)", "11-16.7ms (60-90 fps)", "16.7-20ms (50-60 fps)", "20-25ms (40-50 fps)", "25-33ms (30-40 fps)", "33-50ms (20-30 fps)", "50-67ms (15-20 fps)", "67-100ms (10-15 fps)", ">100ms (<10 fps)"};

    private FrameStatistics(Builder builder) {
        this.totalFrames = builder.totalFrames;
        this.slowFrameCount = builder.slowFrameCount;
        this.slowFramePercentage = builder.slowFramePercentage;
        this.minFrameTimeMs = builder.minFrameTimeMs;
        this.maxFrameTimeMs = builder.maxFrameTimeMs;
        this.avgFrameTimeMs = builder.avgFrameTimeMs;
        this.medianFrameTimeMs = builder.medianFrameTimeMs;
        this.stdDevFrameTimeMs = builder.stdDevFrameTimeMs;
        this.percentile95Ms = builder.percentile95Ms;
        this.percentile99Ms = builder.percentile99Ms;
        this.percentile999Ms = builder.percentile999Ms;
        this.avgFps = builder.avgFps;
        this.minFps = builder.minFps;
        this.percentile1Fps = builder.percentile1Fps;
        this.frameTimeHistogram = builder.frameTimeHistogram;
        this.histogramLabels = builder.histogramLabels;
        this.topSections = builder.topSections;
    }

    public static FrameStatistics calculate() {
        int historySize = FrameProfiler.getHistorySize();
        if (historySize == 0) {
            return empty();
        }
        double[] frameTimes = new double[historySize];
        Map<String, SectionAggregator> sectionMap = new HashMap<>();
        double sum = 0.0d;
        double min = Double.MAX_VALUE;
        double max = -1.7976931348623157E308d;
        int validFrameCount = 0;
        for (int i = 0; i < historySize; i++) {
            FrameProfile frame = FrameProfiler.getFrame(i);
            if (frame != null && frame.isCompleted()) {
                double time = frame.getDurationMillis();
                if (time > 0.0d) {
                    frameTimes[validFrameCount] = time;
                    validFrameCount++;
                    sum += time;
                    min = Math.min(min, time);
                    max = Math.max(max, time);
                    aggregateSections(frame.getRoot(), sectionMap);
                }
            }
        }
        if (validFrameCount == 0) {
            return empty();
        }
        double[] validFrameTimes = Arrays.copyOf(frameTimes, validFrameCount);
        Arrays.sort(validFrameTimes);
        double avg = sum / ((double) validFrameCount);
        double varianceSum = 0.0d;
        for (int i2 = 0; i2 < validFrameCount; i2++) {
            double diff = validFrameTimes[i2] - avg;
            varianceSum += diff * diff;
        }
        double stdDev = Math.sqrt(varianceSum / ((double) validFrameCount));
        double median = getPercentile(validFrameTimes, PERCENTILE_MEDIAN);
        double p95 = getPercentile(validFrameTimes, PERCENTILE_95);
        double p99 = getPercentile(validFrameTimes, PERCENTILE_99);
        double p999 = getPercentile(validFrameTimes, PERCENTILE_999);
        int slowCount = FrameProfiler.getSlowFrameCount();
        long totalFrames = FrameProfiler.getFrameCounter();
        double slowPercentage = totalFrames > 0 ? (((double) slowCount) * PERCENTAGE_MULTIPLIER) / totalFrames : 0.0d;
        double avgFps = avg > 0.0d ? MS_PER_SECOND / avg : 0.0d;
        double minFps = max > 0.0d ? MS_PER_SECOND / max : 0.0d;
        double p1Fps = p99 > 0.0d ? MS_PER_SECOND / p99 : 0.0d;
        int[] histogram = buildHistogram(validFrameTimes);
        String[] labels = buildHistogramLabels();
        SectionStatistics[] topSections = buildTopSections(sectionMap, validFrameCount);
        return new Builder().totalFrames(totalFrames).slowFrameCount(slowCount).slowFramePercentage(slowPercentage).minFrameTimeMs(min).maxFrameTimeMs(max).avgFrameTimeMs(avg).medianFrameTimeMs(median).stdDevFrameTimeMs(stdDev).percentile95Ms(p95).percentile99Ms(p99).percentile999Ms(p999).avgFps(avgFps).minFps(minFps).percentile1Fps(p1Fps).frameTimeHistogram(histogram).histogramLabels(labels).topSections(topSections).build();
    }

    private static FrameStatistics empty() {
        return new Builder().totalFrames(0L).slowFrameCount(0).slowFramePercentage(0.0d).minFrameTimeMs(0.0d).maxFrameTimeMs(0.0d).avgFrameTimeMs(0.0d).medianFrameTimeMs(0.0d).stdDevFrameTimeMs(0.0d).percentile95Ms(0.0d).percentile99Ms(0.0d).percentile999Ms(0.0d).avgFps(0.0d).minFps(0.0d).percentile1Fps(0.0d).frameTimeHistogram(new int[0]).histogramLabels(new String[0]).topSections(new SectionStatistics[0]).build();
    }

    private static double getPercentile(double[] sortedData, double percentile) {
        if (sortedData.length == 0) {
            return 0.0d;
        }
        int index = ((int) Math.ceil((percentile / PERCENTAGE_MULTIPLIER) * ((double) sortedData.length))) - 1;
        return sortedData[Math.max(0, Math.min(index, sortedData.length - 1))];
    }

    private static int[] buildHistogram(double[] frameTimes) {
        int[] histogram = new int[10];
        for (double time : frameTimes) {
            int i = 0;
            while (true) {
                if (i >= HISTOGRAM_BUCKETS.length) {
                    break;
                }
                if (time > HISTOGRAM_BUCKETS[i]) {
                    i++;
                } else {
                    int i2 = i;
                    histogram[i2] = histogram[i2] + 1;
                    break;
                }
            }
        }
        return histogram;
    }

    private static String[] buildHistogramLabels() {
        return (String[]) HISTOGRAM_BUCKET_LABELS.clone();
    }

    private static void aggregateSections(ProfilerSection section, Map<String, SectionAggregator> map) {
        for (int i = 0; i < section.getChildCount(); i++) {
            ProfilerSection child = section.getChildren()[i];
            String name = child.getName();
            SectionAggregator agg = map.computeIfAbsent(name, k -> {
                return new SectionAggregator(name);
            });
            agg.addSample(child.getDurationMillis(), child.getSelfTimeMillis(), child.getCallCount());
            aggregateSections(child, map);
        }
    }

    private static SectionStatistics[] buildTopSections(Map<String, SectionAggregator> map, int frameCount) {
        return (SectionStatistics[]) map.values().stream().map(agg -> {
            return agg.toStatistics(frameCount);
        }).sorted((a, b) -> {
            return Double.compare(b.getTotalSelfTimeMs(), a.getTotalSelfTimeMs());
        }).limit(20L).toArray(x$0 -> {
            return new SectionStatistics[x$0];
        });
    }

    public long getTotalFrames() {
        return this.totalFrames;
    }

    public int getSlowFrameCount() {
        return this.slowFrameCount;
    }

    public double getSlowFramePercentage() {
        return this.slowFramePercentage;
    }

    public double getMinFrameTimeMs() {
        return this.minFrameTimeMs;
    }

    public double getMaxFrameTimeMs() {
        return this.maxFrameTimeMs;
    }

    public double getAvgFrameTimeMs() {
        return this.avgFrameTimeMs;
    }

    public double getMedianFrameTimeMs() {
        return this.medianFrameTimeMs;
    }

    public double getStdDevFrameTimeMs() {
        return this.stdDevFrameTimeMs;
    }

    public double getPercentile95Ms() {
        return this.percentile95Ms;
    }

    public double getPercentile99Ms() {
        return this.percentile99Ms;
    }

    public double getPercentile999Ms() {
        return this.percentile999Ms;
    }

    public double getAvgFps() {
        return this.avgFps;
    }

    public double getMinFps() {
        return this.minFps;
    }

    public double getPercentile1Fps() {
        return this.percentile1Fps;
    }

    public int[] getFrameTimeHistogram() {
        return this.frameTimeHistogram;
    }

    public String[] getHistogramLabels() {
        return this.histogramLabels;
    }

    public SectionStatistics[] getTopSections() {
        return this.topSections;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/profiler/frame/FrameStatistics$SectionAggregator.class */
    private static class SectionAggregator {
        private final String name;
        private double totalTimeMs;
        private double totalSelfTimeMs;
        private double maxTimeMs;
        private double maxSelfTimeMs;
        private int totalCalls;
        private int sampleCount;

        SectionAggregator(String name) {
            this.name = name;
        }

        void addSample(double timeMs, double selfTimeMs, int callCount) {
            this.totalTimeMs += timeMs;
            this.totalSelfTimeMs += selfTimeMs;
            this.maxTimeMs = Math.max(this.maxTimeMs, timeMs);
            this.maxSelfTimeMs = Math.max(this.maxSelfTimeMs, selfTimeMs);
            this.totalCalls += callCount;
            this.sampleCount++;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public SectionStatistics toStatistics(int frameCount) {
            double avgTimeMs = this.sampleCount > 0 ? this.totalTimeMs / ((double) this.sampleCount) : 0.0d;
            double avgSelfTimeMs = this.sampleCount > 0 ? this.totalSelfTimeMs / ((double) this.sampleCount) : 0.0d;
            double avgCallsPerFrame = frameCount > 0 ? ((double) this.totalCalls) / ((double) frameCount) : 0.0d;
            return new SectionStatistics(this.name, this.totalTimeMs, this.totalSelfTimeMs, avgTimeMs, avgSelfTimeMs, this.maxTimeMs, this.maxSelfTimeMs, this.totalCalls, avgCallsPerFrame);
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/profiler/frame/FrameStatistics$SectionStatistics.class */
    public static final class SectionStatistics {
        private final String name;
        private final double totalTimeMs;
        private final double totalSelfTimeMs;
        private final double avgTimeMs;
        private final double avgSelfTimeMs;
        private final double maxTimeMs;
        private final double maxSelfTimeMs;
        private final int totalCalls;
        private final double avgCallsPerFrame;

        SectionStatistics(String name, double totalTimeMs, double totalSelfTimeMs, double avgTimeMs, double avgSelfTimeMs, double maxTimeMs, double maxSelfTimeMs, int totalCalls, double avgCallsPerFrame) {
            this.name = name;
            this.totalTimeMs = totalTimeMs;
            this.totalSelfTimeMs = totalSelfTimeMs;
            this.avgTimeMs = avgTimeMs;
            this.avgSelfTimeMs = avgSelfTimeMs;
            this.maxTimeMs = maxTimeMs;
            this.maxSelfTimeMs = maxSelfTimeMs;
            this.totalCalls = totalCalls;
            this.avgCallsPerFrame = avgCallsPerFrame;
        }

        public String getName() {
            return this.name;
        }

        public double getTotalTimeMs() {
            return this.totalTimeMs;
        }

        public double getTotalSelfTimeMs() {
            return this.totalSelfTimeMs;
        }

        public double getAvgTimeMs() {
            return this.avgTimeMs;
        }

        public double getAvgSelfTimeMs() {
            return this.avgSelfTimeMs;
        }

        public double getMaxTimeMs() {
            return this.maxTimeMs;
        }

        public double getMaxSelfTimeMs() {
            return this.maxSelfTimeMs;
        }

        public int getTotalCalls() {
            return this.totalCalls;
        }

        public double getAvgCallsPerFrame() {
            return this.avgCallsPerFrame;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/profiler/frame/FrameStatistics$Builder.class */
    private static class Builder {
        private long totalFrames;
        private int slowFrameCount;
        private double slowFramePercentage;
        private double minFrameTimeMs;
        private double maxFrameTimeMs;
        private double avgFrameTimeMs;
        private double medianFrameTimeMs;
        private double stdDevFrameTimeMs;
        private double percentile95Ms;
        private double percentile99Ms;
        private double percentile999Ms;
        private double avgFps;
        private double minFps;
        private double percentile1Fps;
        private int[] frameTimeHistogram;
        private String[] histogramLabels;
        private SectionStatistics[] topSections;

        private Builder() {
        }

        Builder totalFrames(long totalFrames) {
            this.totalFrames = totalFrames;
            return this;
        }

        Builder slowFrameCount(int slowFrameCount) {
            this.slowFrameCount = slowFrameCount;
            return this;
        }

        Builder slowFramePercentage(double slowFramePercentage) {
            this.slowFramePercentage = slowFramePercentage;
            return this;
        }

        Builder minFrameTimeMs(double minFrameTimeMs) {
            this.minFrameTimeMs = minFrameTimeMs;
            return this;
        }

        Builder maxFrameTimeMs(double maxFrameTimeMs) {
            this.maxFrameTimeMs = maxFrameTimeMs;
            return this;
        }

        Builder avgFrameTimeMs(double avgFrameTimeMs) {
            this.avgFrameTimeMs = avgFrameTimeMs;
            return this;
        }

        Builder medianFrameTimeMs(double medianFrameTimeMs) {
            this.medianFrameTimeMs = medianFrameTimeMs;
            return this;
        }

        Builder stdDevFrameTimeMs(double stdDevFrameTimeMs) {
            this.stdDevFrameTimeMs = stdDevFrameTimeMs;
            return this;
        }

        Builder percentile95Ms(double percentile95Ms) {
            this.percentile95Ms = percentile95Ms;
            return this;
        }

        Builder percentile99Ms(double percentile99Ms) {
            this.percentile99Ms = percentile99Ms;
            return this;
        }

        Builder percentile999Ms(double percentile999Ms) {
            this.percentile999Ms = percentile999Ms;
            return this;
        }

        Builder avgFps(double avgFps) {
            this.avgFps = avgFps;
            return this;
        }

        Builder minFps(double minFps) {
            this.minFps = minFps;
            return this;
        }

        Builder percentile1Fps(double percentile1Fps) {
            this.percentile1Fps = percentile1Fps;
            return this;
        }

        Builder frameTimeHistogram(int[] frameTimeHistogram) {
            this.frameTimeHistogram = frameTimeHistogram;
            return this;
        }

        Builder histogramLabels(String[] histogramLabels) {
            this.histogramLabels = histogramLabels;
            return this;
        }

        Builder topSections(SectionStatistics[] topSections) {
            this.topSections = topSections;
            return this;
        }

        FrameStatistics build() {
            return new FrameStatistics(this);
        }
    }
}
