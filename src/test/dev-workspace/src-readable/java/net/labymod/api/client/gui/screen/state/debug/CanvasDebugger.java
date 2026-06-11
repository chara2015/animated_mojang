package net.labymod.api.client.gui.screen.state.debug;

import java.util.Collections;
import java.util.List;
import net.labymod.api.util.bounds.Rectangle;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/debug/CanvasDebugger.class */
public final class CanvasDebugger {

    @Nullable
    private static Rectangle selectedBounds;
    private static List<CanvasDebugSnapshot> snapshots = Collections.emptyList();
    private static long captureTimeNanos = 0;
    private static boolean captureEnabled = false;
    private static int selectedRenderIndex = -1;
    private static int currentRenderIndex = 0;
    private static boolean previewEnabled = false;

    private CanvasDebugger() {
    }

    public static void setCaptureEnabled(boolean enabled) {
        captureEnabled = enabled;
        if (!enabled) {
            snapshots = Collections.emptyList();
            captureTimeNanos = 0L;
        }
    }

    public static boolean isCaptureEnabled() {
        return captureEnabled;
    }

    public static List<CanvasDebugSnapshot> getSnapshots() {
        return snapshots;
    }

    public static long getCaptureTimeNanos() {
        return captureTimeNanos;
    }

    public static void setSnapshots(List<CanvasDebugSnapshot> newSnapshots) {
        if (!captureEnabled) {
            return;
        }
        snapshots = newSnapshots;
        captureTimeNanos = System.nanoTime();
    }

    public static void clearSnapshots() {
        snapshots = Collections.emptyList();
        captureTimeNanos = 0L;
    }

    public static void setSelectedBounds(@Nullable Rectangle bounds) {
        selectedBounds = bounds;
    }

    @Nullable
    public static Rectangle getSelectedBounds() {
        return selectedBounds;
    }

    public static void setSelectedRenderIndex(int index) {
        selectedRenderIndex = index;
    }

    public static int getSelectedRenderIndex() {
        return selectedRenderIndex;
    }

    public static void setPreviewEnabled(boolean enabled) {
        previewEnabled = enabled;
        if (!enabled) {
            selectedRenderIndex = -1;
        }
    }

    public static boolean isPreviewEnabled() {
        return previewEnabled && captureEnabled && selectedRenderIndex >= 0;
    }

    public static void resetRenderIndex() {
        currentRenderIndex = 0;
    }

    public static int nextRenderIndex() {
        int i = currentRenderIndex;
        currentRenderIndex = i + 1;
        return i;
    }

    public static int getCurrentRenderIndex() {
        return currentRenderIndex;
    }

    public static boolean shouldRenderToPreview(int index) {
        return previewEnabled && captureEnabled && index <= selectedRenderIndex;
    }
}
