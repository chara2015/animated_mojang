package net.labymod.api.profiler.frame;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.labymod.api.profiler.frame.stream.ProfilerStreamWriter;
import net.labymod.api.util.ThreadSafe;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/profiler/frame/FrameProfiler.class */
public final class FrameProfiler {
    private static final int MAX_SLOW_FRAMES = 500;
    private static final int MAX_FRAMES = 120;
    private static final FrameProfile[] frameHistory = new FrameProfile[MAX_FRAMES];
    private static final FrameProfile[] slowFrameHistory = new FrameProfile[500];
    private static final Object lock = new Object();
    private static volatile boolean enabled = false;
    private static volatile ProfilerMode mode = ProfilerMode.GROUPED;
    private static final double DEFAULT_SLOW_THRESHOLD_MS = 16.67d;
    private static volatile double slowThresholdMillis = DEFAULT_SLOW_THRESHOLD_MS;
    private static final double DEFAULT_DISPLAY_THRESHOLD_MS = 0.5d;
    private static volatile double displayThresholdMillis = DEFAULT_DISPLAY_THRESHOLD_MS;
    private static long frameCounter = 0;
    private static int historyIndex = 0;
    private static int historySize = 0;
    private static FrameProfile currentFrame = null;
    private static ProfilerSection currentSection = null;
    private static int slowFrameCount = 0;
    private static int slowFrameHistorySize = 0;
    private static double worstFrameTimeMillis = 0.0d;
    private static long worstFrameNumber = 0;
    private static volatile Consumer<String> warningConsumer = null;
    private static volatile ProfilerStreamWriter streamWriter = null;
    private static volatile boolean streamingEnabled = false;

    static {
        for (int i = 0; i < MAX_FRAMES; i++) {
            frameHistory[i] = new FrameProfile(0L);
        }
    }

    private FrameProfiler() {
    }

    public static void setEnabled(boolean enabled2) {
        enabled = enabled2;
    }

    public static boolean isEnabled() {
        return enabled;
    }

    private static boolean isFrameProfilingAllowed() {
        return enabled && ThreadSafe.isRenderThread();
    }

    public static void setMode(ProfilerMode mode2) {
        mode = mode2;
    }

    public static ProfilerMode getMode() {
        return mode;
    }

    public static void setSlowThresholdMillis(double thresholdMillis) {
        slowThresholdMillis = thresholdMillis;
    }

    public static double getSlowThresholdMillis() {
        return slowThresholdMillis;
    }

    public static void setDisplayThresholdMillis(double thresholdMillis) {
        displayThresholdMillis = thresholdMillis;
    }

    public static double getDisplayThresholdMillis() {
        return displayThresholdMillis;
    }

    public static void setWarningConsumer(Consumer<String> consumer) {
        warningConsumer = consumer;
    }

    private static void warn(String message) {
        Consumer<String> consumer = warningConsumer;
        if (consumer != null) {
            consumer.accept(message);
        }
    }

    public static void setStreamWriter(ProfilerStreamWriter writer) {
        streamWriter = writer;
    }

    public static ProfilerStreamWriter getStreamWriter() {
        return streamWriter;
    }

    public static void startStreaming(Path outputFile) throws IOException {
        ProfilerStreamWriter writer = streamWriter;
        if (writer == null) {
            throw new IllegalStateException("No stream writer set");
        }
        writer.start(outputFile);
        streamingEnabled = true;
    }

    public static void stopStreaming() throws IOException {
        streamingEnabled = false;
        ProfilerStreamWriter writer = streamWriter;
        if (writer != null && writer.isWriting()) {
            writer.stop();
        }
    }

    public static boolean isStreaming() {
        ProfilerStreamWriter writer = streamWriter;
        return streamingEnabled && writer != null && writer.isWriting();
    }

    public static long getStreamedFrameCount() {
        ProfilerStreamWriter writer = streamWriter;
        if (writer != null) {
            return writer.getWrittenFrameCount();
        }
        return 0L;
    }

    public static long getDroppedFrameCount() {
        ProfilerStreamWriter writer = streamWriter;
        if (writer != null) {
            return writer.getDroppedFrameCount();
        }
        return 0L;
    }

    public static void beginFrame() {
        if (!isFrameProfilingAllowed()) {
            return;
        }
        synchronized (lock) {
            frameCounter++;
            currentFrame = frameHistory[historyIndex];
            currentFrame.reset();
            currentFrame = new FrameProfile(frameCounter);
            frameHistory[historyIndex] = currentFrame;
            currentFrame.start();
            currentSection = currentFrame.getRoot();
        }
    }

    public static void endFrame() {
        if (!isFrameProfilingAllowed() || currentFrame == null) {
            return;
        }
        synchronized (lock) {
            if (currentSection != null && currentSection.getParent() != null) {
                StringBuilder unclosedPath = new StringBuilder();
                ProfilerSection section = currentSection;
                int unclosedCount = 0;
                while (section != null && section.getParent() != null) {
                    if (unclosedCount > 0) {
                        unclosedPath.insert(0, " -> ");
                    }
                    unclosedPath.insert(0, section.getName());
                    section.end();
                    section = section.getParent();
                    unclosedCount++;
                }
                currentSection = section;
                warn("[FrameProfiler] Missing " + unclosedCount + " pop() call(s) at frame end. Unclosed sections: " + String.valueOf(unclosedPath));
            }
            currentFrame.end(slowThresholdMillis);
            calculateSelfTimesRecursive(currentFrame.getRoot());
            if (currentFrame.isFlaggedAsSlow()) {
                slowFrameCount++;
                if (slowFrameHistorySize < 500) {
                    slowFrameHistory[slowFrameHistorySize] = currentFrame.copy();
                    slowFrameHistorySize++;
                }
            }
            double frameTime = currentFrame.getDurationMillis();
            if (frameTime > worstFrameTimeMillis) {
                worstFrameTimeMillis = frameTime;
                worstFrameNumber = currentFrame.getFrameNumber();
            }
            if (streamingEnabled && streamWriter != null && streamWriter.isWriting()) {
                streamWriter.writeFrame(currentFrame);
            }
            historyIndex = (historyIndex + 1) % MAX_FRAMES;
            if (historySize < MAX_FRAMES) {
                historySize++;
            }
            currentFrame = null;
            currentSection = null;
        }
    }

    private static void calculateSelfTimesRecursive(ProfilerSection section) {
        if (section == null) {
            return;
        }
        ProfilerSection[] children = section.getChildren();
        int childCount = section.getChildCount();
        for (int i = 0; i < childCount; i++) {
            calculateSelfTimesRecursive(children[i]);
        }
        section.calculateSelfTime();
    }

    public static void push(String name) {
        if (!isFrameProfilingAllowed() || currentSection == null) {
            return;
        }
        pushSection(name);
    }

    public static void push(Supplier<String> nameSupplier) {
        if (!isFrameProfilingAllowed() || currentSection == null) {
            return;
        }
        pushSection(nameSupplier.get());
    }

    private static void pushSection(String name) {
        if (mode == ProfilerMode.INDIVIDUAL) {
            currentSection = currentSection.createIndexedChild(name);
        } else {
            currentSection = currentSection.getOrCreateChild(name);
        }
    }

    public static void pop() {
        if (!isFrameProfilingAllowed() || currentSection == null || currentSection.getParent() == null) {
            return;
        }
        currentSection.end();
        currentSection = currentSection.getParent();
    }

    public static void swap(String name) {
        pop();
        push(name);
    }

    public static void swap(Supplier<String> nameSupplier) {
        pop();
        push(nameSupplier);
    }

    public static void reset() {
        synchronized (lock) {
            frameCounter = 0L;
            historyIndex = 0;
            historySize = 0;
            slowFrameCount = 0;
            slowFrameHistorySize = 0;
            worstFrameTimeMillis = 0.0d;
            worstFrameNumber = 0L;
            currentFrame = null;
            currentSection = null;
            for (int i = 0; i < MAX_FRAMES; i++) {
                frameHistory[i].reset();
            }
            for (int i2 = 0; i2 < 500; i2++) {
                slowFrameHistory[i2] = null;
            }
        }
    }

    public static long getFrameCounter() {
        return frameCounter;
    }

    public static int getHistorySize() {
        return historySize;
    }

    public static int getSlowFrameCount() {
        return slowFrameCount;
    }

    public static int getSlowFrameHistorySize() {
        return slowFrameHistorySize;
    }

    public static int getMaxSlowFrames() {
        return 500;
    }

    public static FrameProfile getSlowFrameFromHistory(int index) {
        if (index < 0 || index >= slowFrameHistorySize) {
            return null;
        }
        return slowFrameHistory[index];
    }

    public static double getWorstFrameTimeMillis() {
        return worstFrameTimeMillis;
    }

    public static long getWorstFrameNumber() {
        return worstFrameNumber;
    }

    public static FrameProfile getFrame(int index) {
        FrameProfile frameProfile;
        if (index < 0 || index >= historySize) {
            return null;
        }
        synchronized (lock) {
            int actualIndex = (((historyIndex - historySize) + index) + MAX_FRAMES) % MAX_FRAMES;
            frameProfile = frameHistory[actualIndex];
        }
        return frameProfile;
    }

    public static FrameProfile getLastFrame() {
        if (historySize == 0) {
            return null;
        }
        return getFrame(historySize - 1);
    }

    public static FrameProfile findLastSlowFrame() {
        synchronized (lock) {
            for (int i = historySize - 1; i >= 0; i--) {
                FrameProfile frame = getFrame(i);
                if (frame != null && frame.isFlaggedAsSlow()) {
                    return frame;
                }
            }
            return null;
        }
    }

    public static int getSlowFrames(FrameProfile[] output) {
        int count = 0;
        synchronized (lock) {
            for (int i = 0; i < historySize && count < output.length; i++) {
                FrameProfile frame = getFrame(i);
                if (frame != null && frame.isFlaggedAsSlow()) {
                    int i2 = count;
                    count++;
                    output[i2] = frame;
                }
            }
        }
        return count;
    }

    public static String buildSummary() {
        StringBuilder builder = new StringBuilder();
        builder.append("=== Frame Profiler Summary ===\n");
        builder.append(String.format("Mode: %s\n", mode.name()));
        builder.append(String.format("Total Frames: %d\n", Long.valueOf(frameCounter)));
        builder.append(String.format("Slow Frames: %d (threshold: %.1fms)\n", Integer.valueOf(slowFrameCount), Double.valueOf(slowThresholdMillis)));
        builder.append(String.format("Worst Frame: #%d at %.2fms\n", Long.valueOf(worstFrameNumber), Double.valueOf(worstFrameTimeMillis)));
        FrameProfile lastSlow = findLastSlowFrame();
        if (lastSlow != null) {
            builder.append("\n=== Last Slow Frame ===\n");
            builder.append(lastSlow.buildCallTree(displayThresholdMillis));
        }
        return builder.toString();
    }
}
