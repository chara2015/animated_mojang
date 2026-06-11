package net.labymod.core.client.world.canvas;

import javax.inject.Singleton;
import net.labymod.api.client.world.canvas.Canvas;
import net.labymod.api.client.world.canvas.CanvasFactory;
import net.labymod.api.client.world.canvas.CanvasMeta;
import net.labymod.api.client.world.signobject.SignObjectPosition;
import net.labymod.api.models.Implements;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/world/canvas/DefaultCanvasFactory.class */
@Singleton
@Implements(CanvasFactory.class)
public class DefaultCanvasFactory implements CanvasFactory {
    @Override // net.labymod.api.client.world.canvas.CanvasFactory
    @Nullable
    public Canvas createCanvas(@NotNull CanvasMeta meta, @NotNull SignObjectPosition position) {
        return new DefaultCanvas(meta, position);
    }
}
