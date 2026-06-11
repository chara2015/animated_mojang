package net.labymod.core.client.gfx.imgui.window;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.imgui.ImGuiWindow;
import net.labymod.api.client.gfx.imgui.LabyImGui;
import net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.debug.DebugFeature;
import net.labymod.api.debug.DebugRegistry;
import net.labymod.api.profiler.SampleLogger;
import net.labymod.api.util.StringUtil;
import net.labymod.core.client.gfx.imgui.util.DebugStatistics;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import net.labymod.core.main.debug.jvm.AllocationRate;
import net.labymod.core.main.debug.jvm.Memory;
import net.labymod.core.main.profiler.CounterType;
import net.labymod.core.main.profiler.Counters;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/imgui/window/RenderStatisticsImGuiWindow.class */
public class RenderStatisticsImGuiWindow extends ImGuiWindow {
    private final AllocationRate allocationRate;
    private final Memory memory;
    private static final String[] UNITS = {"KB", "MB", "GB"};

    public RenderStatisticsImGuiWindow(@Nullable ImGuiBooleanType visible) {
        super("Game Statistics", visible, 0);
        this.allocationRate = new AllocationRate();
        this.memory = new Memory();
    }

    @Override // net.labymod.api.client.gfx.imgui.ImGuiWindow
    protected void renderContent() {
        LabyImGui.keyValuePair("FPS", Laby.labyAPI().minecraft().getFPS());
        Counters.renderCounters(CounterType.NONE);
        renderMemory();
        renderFrames();
        renderCosmetics();
        renderInput();
        renderDebugFeatures();
        renderBuffers();
    }

    private void renderBuffers() {
        LabyImGui.separator();
        LabyImGui.text("BufferBuilders:");
        Map<UUID, Runnable> bufferBuilders = DebugStatistics.getBufferBuilders();
        int count = bufferBuilders.size();
        int index = 0;
        for (Map.Entry<UUID, Runnable> entry : bufferBuilders.entrySet()) {
            if (index != count) {
                LabyImGui.separator();
            }
            LabyImGui.text(entry.getKey().toString());
            Runnable task = entry.getValue();
            task.run();
            index++;
        }
    }

    private void renderMemory() {
        LabyImGui.separator();
        LabyImGui.text("Memory Statistics:");
        LabyImGui.keyValuePair("Memory", this.memory.getMemory());
        LabyImGui.keyValuePair("Allocated", this.memory.getAllocated());
        LabyImGui.keyValuePair("Allocation Rate", this.allocationRate.getAllocationRate());
    }

    private void renderFrames() {
        LabyImGui.separator();
        LabyImGui.text("Frame Statistics:");
        SampleLogger logger = Laby.labyAPI().minecraft().frameTimeLogger();
        if (logger == null) {
            LabyImGui.text("FrameTimer is disabled");
            return;
        }
        float average = 0.0f;
        float minValue = Float.MAX_VALUE;
        float maxValue = Float.MIN_VALUE;
        int maxIndex = logger.size() - 0;
        for (int index = 0; index < maxIndex; index++) {
            long loggedTime = logger.get(0 + index);
            float time = loggedTime / 1000000.0f;
            minValue = Math.min(minValue, time);
            maxValue = Math.max(maxValue, time);
            average += time;
        }
        float avgMS = average / maxIndex;
        String maxFPS = String.format(Locale.ROOT, "%.1f", Float.valueOf(1000.0f / minValue));
        String minFPS = String.format(Locale.ROOT, "%.1f", Float.valueOf(1000.0f / maxValue));
        String avgFPS = String.format(Locale.ROOT, "%.1f", Float.valueOf(1000.0f / avgMS));
        String text = "  Min: " + String.format(Locale.ROOT, "%.1f", Float.valueOf(minValue)) + "ms Avg: " + String.format(Locale.ROOT, "%.1f", Float.valueOf(avgMS)) + "ms Max: " + String.format(Locale.ROOT, "%.1f", Float.valueOf(maxValue)) + "ms\n  Max: " + maxFPS + "fps Avg: " + avgFPS + "fps Min: " + minFPS + "fps";
        LabyImGui.text(text);
    }

    private void renderCosmetics() {
        LabyImGui.separator();
        LabyImGui.text("Cosmetic Statistics");
        Counters.renderCounters(CounterType.COSMETICS);
    }

    private void renderInput() {
        Int2ObjectArrayMap<Key> pressedKeys = DefaultKeyMapper.getPressedKeys();
        Int2ObjectArrayMap<MouseButton> pressedMouseButtons = DefaultKeyMapper.getPressedMouseButtons();
        boolean hasKeys = !pressedKeys.isEmpty();
        boolean hasMouseButtons = !pressedMouseButtons.isEmpty();
        if (hasKeys || hasMouseButtons) {
            LabyImGui.separator();
            LabyImGui.text("Input Statistics:");
            if (hasKeys) {
                LabyImGui.keyValuePair("Keys", StringUtil.join((Map<?, ?>) pressedKeys));
            }
            if (hasMouseButtons) {
                LabyImGui.keyValuePair("Mouse Buttons", StringUtil.join((Map<?, ?>) pressedMouseButtons));
            }
        }
    }

    private void renderDebugFeatures() {
        Collection<DebugFeature> debugFeatures = DebugRegistry.getDebugFeatures();
        boolean enabledDebugFeature = false;
        Iterator<DebugFeature> it = debugFeatures.iterator();
        while (true) {
            if (it.hasNext()) {
                if (it.next().isEnabled()) {
                    enabledDebugFeature = true;
                    break;
                }
            } else {
                break;
            }
        }
        if (enabledDebugFeature) {
            LabyImGui.separator();
            LabyImGui.text("Debug Features:");
            for (DebugFeature debugFeature : debugFeatures) {
                if (debugFeature.isEnabled()) {
                    LabyImGui.keyValuePair(debugFeature.getName(), "Enabled");
                }
            }
        }
    }

    private String byteToHumanReadable(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        }
        int exp = (int) (Math.log(bytes) / Math.log(1024.0d));
        return String.format(Locale.ROOT, "%.1f %s", Double.valueOf(bytes / Math.pow(1024.0d, exp)), UNITS[exp - 1]);
    }
}
