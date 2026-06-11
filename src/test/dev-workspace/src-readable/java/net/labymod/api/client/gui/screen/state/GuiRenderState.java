package net.labymod.api.client.gui.screen.state;

import net.labymod.api.Laby;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/GuiRenderState.class */
public interface GuiRenderState extends GuiComponent, GuiTransform {
    @NotNull
    Material material();

    void buildVertices(VertexConsumer vertexConsumer);

    @NotNull
    default RenderTarget getDestination() {
        return Laby.labyAPI().minecraft().mainTarget();
    }

    default boolean shouldDirectRecord() {
        return false;
    }

    default void consumeCommand(@NotNull DrawCommandContext context) {
    }
}
