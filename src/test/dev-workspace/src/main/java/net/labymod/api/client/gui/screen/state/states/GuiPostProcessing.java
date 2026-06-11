package net.labymod.api.client.gui.screen.state.states;

import java.util.function.Consumer;
import net.labymod.api.client.gui.screen.state.DrawCommandContext;
import net.labymod.api.client.gui.screen.state.StateUtil;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/states/GuiPostProcessing.class */
public class GuiPostProcessing extends AbstractGuiRenderState {
    private final RenderTarget source;
    private final RenderTarget destination;
    private final Consumer<CommandBuffer> commandConsumer;

    public GuiPostProcessing(Material material, RenderTarget source, RenderTarget destination, Matrix4f pose, float x, float y, float width, float height, Consumer<CommandBuffer> commandConsumer) {
        super(material, pose, x, y, width, height, null);
        this.source = source;
        this.destination = destination;
        this.commandConsumer = commandConsumer;
    }

    @Override // net.labymod.api.client.gui.screen.state.GuiRenderState
    public void buildVertices(VertexConsumer consumer) {
        StateUtil.buildPostProcessingVertices(consumer, pose(), this.left, this.top, this.right, this.bottom);
    }

    @Override // net.labymod.api.client.gui.screen.state.GuiRenderState
    public void consumeCommand(@NotNull DrawCommandContext context) {
        super.consumeCommand(context);
        this.commandConsumer.accept(context.commandBuffer());
    }

    @Override // net.labymod.api.client.gui.screen.state.GuiRenderState
    public boolean shouldDirectRecord() {
        return true;
    }

    @Override // net.labymod.api.client.gui.screen.state.GuiRenderState
    @NotNull
    public RenderTarget getDestination() {
        return this.destination;
    }
}
