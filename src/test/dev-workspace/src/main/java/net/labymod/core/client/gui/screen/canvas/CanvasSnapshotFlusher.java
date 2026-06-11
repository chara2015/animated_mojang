package net.labymod.core.client.gui.screen.canvas;

import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gui.screen.state.CanvasSnapshot;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.generated.ReferenceStorage;
import net.labymod.api.laby3d.util.DirtyFramebufferClearer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/canvas/CanvasSnapshotFlusher.class */
public final class CanvasSnapshotFlusher {
    private CanvasSnapshotFlusher() {
    }

    public static void flushPending() {
        ReferenceStorage references = Laby.references();
        RenderEnvironmentContext context = references.renderEnvironmentContext();
        if (!context.isScreenContext()) {
            return;
        }
        ScreenCanvas canvas = context.screenContext().canvas();
        List<CanvasSnapshot> snapshots = canvas.captureSnapshot();
        if (snapshots.isEmpty()) {
            return;
        }
        DirtyFramebufferClearer clearer = references.getDirtyFramebufferClearer();
        if (clearer != null) {
            clearer.clear();
        }
        for (CanvasSnapshot snapshot : snapshots) {
            context.screenContext().renderSnapshot(snapshot, false);
        }
    }
}
