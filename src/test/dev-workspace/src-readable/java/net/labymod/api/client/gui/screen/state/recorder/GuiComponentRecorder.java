package net.labymod.api.client.gui.screen.state.recorder;

import java.util.Objects;
import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphProperties;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphRenderable;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.StyledGlyphRenderable;
import net.labymod.api.client.gfx.pipeline.renderer.text.state.TextState;
import net.labymod.api.client.gui.screen.state.DrawCommandContext;
import net.labymod.api.client.gui.screen.state.GuiComponent;
import net.labymod.api.client.gui.screen.state.GuiRenderState;
import net.labymod.api.client.gui.screen.state.states.GuiEffectGlyphRenderState;
import net.labymod.api.client.gui.screen.state.states.GuiGlyphRenderState;
import net.labymod.api.client.gui.screen.state.states.GuiTextRenderState;
import net.labymod.api.client.gui.screen.util.scissor.ScissorArea;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.laby3d.api.buffers.BufferBuilder;
import net.labymod.laby3d.api.buffers.ByteBufferBuilder;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/recorder/GuiComponentRecorder.class */
public class GuiComponentRecorder {
    private final MeshRecorder meshRecorder;
    private final ByteBufferBuilder buffer;
    private final RenderTarget defaultDestination;
    private Material previousMaterial;

    @Nullable
    private ScissorArea previousScissorArea;
    private RenderTarget previousDestination;
    private BufferBuilder bufferBuilder;

    public GuiComponentRecorder(MeshRecorder meshRecorder, ByteBufferBuilder buffer, RenderTarget defaultDestination) {
        this.meshRecorder = meshRecorder;
        this.buffer = buffer;
        this.defaultDestination = defaultDestination;
    }

    public void invalidate() {
        this.previousMaterial = null;
        this.previousScissorArea = null;
        this.previousDestination = null;
        this.bufferBuilder = null;
    }

    public void record(GuiComponent component) {
        if (component instanceof GuiTextRenderState) {
            GuiTextRenderState textRenderState = (GuiTextRenderState) component;
            recordTextComponent(textRenderState);
        } else if (component instanceof GuiRenderState) {
            GuiRenderState renderState = (GuiRenderState) component;
            recordRenderState(renderState);
        }
    }

    public void record(GuiComponent component, Consumer<GuiRenderState> textOutput) {
        if (component instanceof GuiTextRenderState) {
            GuiTextRenderState textRenderState = (GuiTextRenderState) component;
            expandTextComponent(textRenderState, textOutput);
        } else if (component instanceof GuiRenderState) {
            GuiRenderState renderState = (GuiRenderState) component;
            recordRenderState(renderState);
        }
    }

    public void flush() {
        recordMesh();
    }

    private void recordTextComponent(GuiTextRenderState textRenderState) {
        expandTextComponent(textRenderState, this::recordRenderState);
    }

    private void expandTextComponent(GuiTextRenderState textRenderState, Consumer<GuiRenderState> output) {
        try {
            TextState textState = textRenderState.prepareText();
            ScissorArea scissorArea = textRenderState.getScissorArea();
            Matrix4f pose = textRenderState.pose();
            GlyphProperties properties = textState.getProperties();
            for (StyledGlyphRenderable glyph : textState.getGlyphs()) {
                output.accept(new GuiGlyphRenderState(pose, glyph, scissorArea, properties));
            }
            for (GlyphRenderable effect : textState.getEffects()) {
                output.accept(new GuiEffectGlyphRenderState(pose, effect, scissorArea, properties));
            }
        } catch (Exception e) {
        }
    }

    private void recordRenderState(GuiRenderState renderState) {
        Material material = renderState.material();
        ScissorArea scissorArea = renderState.getScissorArea();
        RenderTarget destination = renderState.getDestination();
        if (destination == null || destination == Laby.labyAPI().minecraft().mainTarget()) {
            destination = this.defaultDestination;
        }
        if (hasRenderChanges(material, scissorArea, destination)) {
            recordMesh();
            this.bufferBuilder = prepareRenderBuffer(material);
            this.previousMaterial = material;
            this.previousScissorArea = scissorArea;
            this.previousDestination = destination;
        }
        try {
            renderState.buildVertices(this.bufferBuilder);
        } catch (Exception e) {
        }
        if (renderState.shouldDirectRecord()) {
            Objects.requireNonNull(renderState);
            recordMesh(renderState::consumeCommand);
            this.previousMaterial = null;
            this.bufferBuilder = null;
        }
    }

    private boolean hasRenderChanges(Material material, @Nullable ScissorArea scissorArea, RenderTarget destination) {
        return (Objects.equals(this.previousMaterial, material) && !scissorChanged(scissorArea, this.previousScissorArea) && destination == this.previousDestination) ? false : true;
    }

    private boolean scissorChanged(@Nullable ScissorArea newArea, @Nullable ScissorArea prevArea) {
        if (newArea == prevArea) {
            return false;
        }
        return newArea == null || !newArea.equals(prevArea);
    }

    private BufferBuilder prepareRenderBuffer(Material material) {
        RenderState renderState = material.renderState();
        return new BufferBuilder(this.buffer, renderState.vertexDescription(), renderState.drawingMode());
    }

    private void recordMesh() {
        recordMesh(null);
    }

    private void recordMesh(@Nullable Consumer<DrawCommandContext> contextConsumer) {
        if (this.bufferBuilder == null || this.previousMaterial == null) {
            return;
        }
        if (contextConsumer != null) {
            this.meshRecorder.record(this.bufferBuilder, this.previousMaterial, this.previousScissorArea, null, this.previousDestination, contextConsumer);
        } else {
            this.meshRecorder.record(this.bufferBuilder, this.previousMaterial, this.previousScissorArea, null, this.previousDestination);
        }
    }
}
