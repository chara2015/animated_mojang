package net.labymod.api.client.world.canvas;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/canvas/CanvasRendererFactory.class */
@Deprecated(forRemoval = true, since = "4.2.13")
public interface CanvasRendererFactory {
    @Nullable
    CanvasRenderer createRenderer(@NotNull CanvasMeta canvasMeta);
}
