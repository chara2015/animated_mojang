package net.labymod.api.client.world.canvas;

import java.util.function.Supplier;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/canvas/CanvasRegistry.class */
@Referenceable
@Deprecated(forRemoval = true, since = "4.2.13")
public interface CanvasRegistry {
    void registerRenderer(@NotNull ResourceLocation resourceLocation, @NotNull CanvasRendererFactory canvasRendererFactory);

    void registerActivity(@NotNull ResourceLocation resourceLocation, @NotNull Supplier<Activity> supplier);
}
