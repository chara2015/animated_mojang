package net.labymod.api.client.render.model;

import java.util.function.Consumer;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.Disposable;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/ModelBuffer.class */
public interface ModelBuffer extends Disposable {
    void render(@NotNull CommandBuffer commandBuffer, @NotNull Stack stack);

    void submitToCanvas(@NotNull ScreenContext screenContext, float f, float f2, float f3, float f4, float f5, @Nullable Consumer<Stack> consumer);

    void rebuildModel();

    @NotNull
    ResourceLocation getResourceLocation();

    void setResourceLocation(@Nullable ResourceLocation resourceLocation);

    boolean isImmediate();

    void setImmediate(boolean z);

    int getARGB();

    void setARGB(int i);

    default void submitToCanvas(@NotNull ScreenContext context, @NotNull Rectangle bounds, float scale) {
        submitToCanvas(context, bounds, scale, null);
    }

    default void submitToCanvas(@NotNull ScreenContext context, @NotNull Rectangle bounds, float scale, @Nullable Consumer<Stack> offscreenConsumer) {
        submitToCanvas(context, bounds.getLeft(), bounds.getTop(), bounds.getRight(), bounds.getBottom(), scale, offscreenConsumer);
    }

    default void submitToCanvas(@NotNull ScreenContext context, float left, float top, float right, float bottom, float scale) {
        submitToCanvas(context, left, top, right, bottom, scale, null);
    }
}
