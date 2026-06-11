package net.labymod.api.client.gui.screen.state;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphProperties;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphRenderable;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.StyledGlyphRenderable;
import net.labymod.api.client.gfx.pipeline.renderer.text.state.TextState;
import net.labymod.api.client.gui.screen.state.offscreen.AtlasOffscreenRenderState;
import net.labymod.api.client.gui.screen.state.offscreen.AtlasOffscreenRenderer;
import net.labymod.api.client.gui.screen.state.offscreen.DynamicOffscreenRenderState;
import net.labymod.api.client.gui.screen.state.offscreen.DynamicOffscreenRenderer;
import net.labymod.api.client.gui.screen.state.offscreen.OffscreenRenderState;
import net.labymod.api.client.gui.screen.state.offscreen.OffscreenRenderer;
import net.labymod.api.client.gui.screen.state.recorder.MeshRecorder;
import net.labymod.api.client.gui.screen.state.recorder.MeshRenderer;
import net.labymod.api.client.gui.screen.state.recorder.RecorderState;
import net.labymod.api.client.gui.screen.state.recorder.VertexBufferManager;
import net.labymod.api.client.gui.screen.state.states.GuiEffectGlyphRenderState;
import net.labymod.api.client.gui.screen.state.states.GuiGlyphRenderState;
import net.labymod.api.client.gui.screen.state.states.GuiTextRenderState;
import net.labymod.api.client.gui.screen.state.states.commands.GuiCommandComponent;
import net.labymod.api.client.gui.screen.util.scissor.ScissorArea;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.laby3d.util.DirtyFramebufferClearer;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.thirdparty.LabySentry;
import net.labymod.api.util.RenderUtil;
import net.labymod.api.util.logging.Logging;
import net.labymod.laby3d.api.RenderDeviceException;
import net.labymod.laby3d.api.buffers.BufferBuilder;
import net.labymod.laby3d.api.buffers.ByteBufferBuilder;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/ScreenCanvasRenderer.class */
public class ScreenCanvasRenderer implements AutoCloseable {
    private static final boolean DEPTH_SORTED = MinecraftVersions.V1_21_8.orOlder();
    private static final boolean DEPTH_LAYER_RENDERING = MinecraftVersions.V1_21_8.isCurrent();
    private static final Logging LOGGER = Logging.getLogger();
    private static final Set<String> KNOWN_ERRORS = new HashSet();
    private final ScreenCanvas canvas;
    private final LayeredElementConsumer elementProcessor;

    @Nullable
    private final VertexConsumerProxy vertexConsumerProxy;
    private Material previousMaterial;

    @Nullable
    private ScissorArea previousScissorArea;

    @Nullable
    private ClipShape previousClipShape;
    private RenderTarget previousDestination;
    private BufferBuilder bufferBuilder;
    private final Map<Class<? extends OffscreenRenderState>, OffscreenRenderer<?>> offscreenRenderers = new HashMap();
    private GuiState currentState = GuiState.SNAPSHOT;
    private final ByteBufferBuilder buffer = new ByteBufferBuilder(1048576);
    private final Laby3D laby3D = Laby.references().laby3D();
    private final MeshRecorder meshRecorder = new MeshRecorder(this.laby3D);
    private final MeshRenderer meshRenderer = new MeshRenderer(this.laby3D);
    private final VertexBufferManager vertexBufferManager = new VertexBufferManager(this.laby3D);

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/ScreenCanvasRenderer$GuiState.class */
    private enum GuiState {
        SNAPSHOT,
        LAST_ELEMENTS
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/ScreenCanvasRenderer$TextRenderableFactory.class */
    @FunctionalInterface
    private interface TextRenderableFactory<T> {
        GuiComponent create(Matrix4f matrix4f, T t, ScissorArea scissorArea, GlyphProperties glyphProperties);
    }

    public ScreenCanvasRenderer(ScreenCanvas canvas) {
        this.canvas = canvas;
        LayeredElementConsumer elementConsumer = new RenderElementProcessor(this);
        this.elementProcessor = DEPTH_LAYER_RENDERING ? elementConsumer.mergeWith(component -> {
            if (this.currentState == GuiState.LAST_ELEMENTS && (component instanceof GuiTransform)) {
                GuiTransform transform = (GuiTransform) component;
                transform.pose().translate(0.0f, 0.0f, RenderUtil.getLastElementLayer() + 1.0f);
            }
        }) : elementConsumer;
        this.offscreenRenderers.put(DynamicOffscreenRenderState.class, new DynamicOffscreenRenderer());
        this.offscreenRenderers.put(AtlasOffscreenRenderState.class, new AtlasOffscreenRenderer());
        this.vertexConsumerProxy = DEPTH_SORTED ? new VertexConsumerProxy() : null;
    }

    public void render() {
        this.currentState = GuiState.LAST_ELEMENTS;
        DirtyFramebufferClearer dirtyFramebufferClearer = Laby.references().getDirtyFramebufferClearer();
        if (dirtyFramebufferClearer != null) {
            dirtyFramebufferClearer.clear();
        }
        addElementsToMeshes(this.canvas);
        renderMeshes();
        this.laby3D.renderTargetPool().reset();
        Laby.references().blurRenderer().invalidateCache();
        this.vertexBufferManager.rotateBuffers();
    }

    public void renderSnapshot(CanvasSnapshot snapshot, boolean firstBlit) {
        this.currentState = GuiState.SNAPSHOT;
        addElementsToMeshes(snapshot);
        renderMeshes();
    }

    private void renderMeshes() {
        if (this.meshRecorder.isEmpty()) {
            return;
        }
        this.meshRecorder.prepareRecordedStates(this.vertexBufferManager);
        this.meshRenderer.render(this.meshRecorder.renderSteps(), this.meshRecorder.recordedStates());
        this.meshRecorder.invalidate();
    }

    private void addElementsToMeshes(LayeredComponent component) {
        invalidate();
        prepare(component);
        component.forEachItem(this.elementProcessor);
        recordMesh();
    }

    private void prepare(LayeredComponent component) {
        component.markPreparation();
        prepareOffscreenRenderers(component);
        prepareText(component);
        component.finishPreparation();
    }

    private void addElementToMesh(GuiComponent state) {
        if (state instanceof GuiRenderState) {
            GuiRenderState guiRenderState = (GuiRenderState) state;
            try {
                addGuiElementToMesh(guiRenderState);
                return;
            } catch (Throwable throwable) {
                captureError(throwable);
                return;
            }
        }
        if (state instanceof GuiCommandComponent) {
            GuiCommandComponent commandComponent = (GuiCommandComponent) state;
            if (commandComponent.shouldRecordMesh()) {
                recordMesh();
            }
            RecorderState commandState = commandComponent.createState();
            if (commandState == null) {
                throw new IllegalStateException("Unable to create recorder state for command component: " + String.valueOf(commandComponent));
            }
            this.meshRecorder.recordCommand(commandState);
        }
    }

    private void prepareOffscreenRenderers(LayeredComponent component) {
        Map<Class<? extends OffscreenRenderState>, List<OffscreenRenderState>> buckets = new LinkedHashMap<>();
        component.forEachItem(renderState -> {
            if (renderState instanceof OffscreenRenderState) {
                OffscreenRenderState offscreenRenderState = (OffscreenRenderState) renderState;
                ((List) buckets.computeIfAbsent(offscreenRenderState.getClass(), k -> {
                    return new ArrayList();
                })).add(offscreenRenderState);
            }
        });
        float guiScale = Laby.labyAPI().minecraft().minecraftWindow().getScale();
        for (Map.Entry<Class<? extends OffscreenRenderState>, List<OffscreenRenderState>> entry : buckets.entrySet()) {
            try {
                prepareOffscreenBucket(entry.getKey(), entry.getValue(), guiScale);
            } catch (Throwable throwable) {
                captureError(throwable);
            }
        }
        RenderUtil.setOffscreenTarget(null);
    }

    private void prepareOffscreenBucket(Class<? extends OffscreenRenderState> type, List<OffscreenRenderState> states, float guiScale) {
        OffscreenRenderer<?> offscreenRenderer = this.offscreenRenderers.get(type);
        if (offscreenRenderer == null) {
            return;
        }
        offscreenRenderer.prepareAll(this.canvas, states, guiScale);
    }

    private void prepareText(LayeredComponent component) {
        component.forEachItem(renderState -> {
            try {
                prepareGlyph(component, renderState);
            } catch (Throwable throwable) {
                captureError(throwable);
            }
        });
    }

    private void prepareGlyph(LayeredComponent component, GuiComponent renderState) {
        if (!(renderState instanceof GuiTextRenderState)) {
            return;
        }
        GuiTextRenderState textRenderState = (GuiTextRenderState) renderState;
        TextState textState = textRenderState.prepareText();
        ScissorArea scissorArea = textRenderState.getScissorArea();
        Matrix4f pose = textRenderState.pose();
        GlyphProperties properties = textState.getProperties();
        for (StyledGlyphRenderable glyph : textState.getGlyphs()) {
            submitTextRenderable(component, glyph, pose, scissorArea, properties, GuiGlyphRenderState::new);
        }
        for (GlyphRenderable effect : textState.getEffects()) {
            submitTextRenderable(component, effect, pose, scissorArea, properties, GuiEffectGlyphRenderState::new);
        }
    }

    private <T> void submitTextRenderable(LayeredComponent component, T t, Matrix4f pose, ScissorArea scissorArea, GlyphProperties properties, TextRenderableFactory<T> factory) {
        try {
            component.submitComponent(factory.create(pose, t, scissorArea, properties));
        } catch (Throwable throwable) {
            captureError(throwable);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: net.labymod.laby3d.api.RenderDeviceException */
    private void addGuiElementToMesh(GuiRenderState guiRenderState) throws RenderDeviceException {
        ClipShape clipShape = guiRenderState.clipShape();
        Material material = guiRenderState.material();
        ScissorArea scissorArea = guiRenderState.getScissorArea();
        RenderTarget destination = guiRenderState.getDestination();
        if (hasRenderChanges(material, scissorArea, clipShape, destination)) {
            recordMesh();
            this.bufferBuilder = prepareRenderBuffer(material);
            this.previousMaterial = material;
            this.previousScissorArea = guiRenderState.getScissorArea();
            this.previousClipShape = clipShape;
            this.previousDestination = destination;
        }
        try {
            guiRenderState.buildVertices(prepareVertexConsumer());
            if (guiRenderState.shouldDirectRecord()) {
                Objects.requireNonNull(guiRenderState);
                recordMesh(guiRenderState::consumeCommand);
                this.previousMaterial = null;
                invalidateBuffer();
            }
        } catch (Throwable throwable) {
            throw new RenderDeviceException("Unable to build vertices for render state: " + String.valueOf(material.id()), throwable);
        }
    }

    private boolean hasRenderChanges(Material material, ScissorArea scissorArea, @Nullable ClipShape clipShape, RenderTarget destination) {
        return (Objects.equals(this.previousMaterial, material) && !scissorChanged(scissorArea, this.previousScissorArea) && Objects.equals(clipShape, this.previousClipShape) && destination == this.previousDestination) ? false : true;
    }

    private void recordMesh() {
        if (this.bufferBuilder == null) {
            return;
        }
        this.meshRecorder.record(this.bufferBuilder, this.previousMaterial, this.previousScissorArea, this.previousClipShape, this.previousDestination);
    }

    private void recordMesh(Consumer<DrawCommandContext> contextConsumer) {
        this.meshRecorder.record(this.bufferBuilder, this.previousMaterial, this.previousScissorArea, this.previousClipShape, this.previousDestination, contextConsumer);
    }

    private BufferBuilder prepareRenderBuffer(Material material) {
        invalidateBuffer();
        RenderState renderState = material.renderState();
        return new BufferBuilder(this.buffer, renderState.vertexDescription(), renderState.drawingMode());
    }

    private VertexConsumer prepareVertexConsumer() {
        if (this.vertexConsumerProxy == null) {
            return this.bufferBuilder;
        }
        return this.vertexConsumerProxy.withDelegate(this.bufferBuilder);
    }

    private void invalidate() {
        this.previousMaterial = null;
        this.previousScissorArea = null;
        this.previousClipShape = null;
        this.previousDestination = null;
        invalidateBuffer();
    }

    private void invalidateBuffer() {
        this.bufferBuilder = null;
    }

    private boolean scissorChanged(@Nullable ScissorArea newArea, @Nullable ScissorArea prevArea) {
        if (newArea == prevArea) {
            return false;
        }
        return newArea == null || !newArea.equals(prevArea);
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.vertexBufferManager.close();
        for (OffscreenRenderer<?> renderer : this.offscreenRenderers.values()) {
            renderer.close();
        }
        this.offscreenRenderers.clear();
    }

    private void captureError(Throwable throwable) {
        LabySentry.capture(throwable);
        String message = throwable.getMessage();
        if (message != null && KNOWN_ERRORS.add(message)) {
            LOGGER.error(message, throwable);
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/ScreenCanvasRenderer$RenderElementProcessor.class */
    private static final class RenderElementProcessor extends Record implements LayeredElementConsumer {
        private final ScreenCanvasRenderer renderer;

        private RenderElementProcessor(ScreenCanvasRenderer renderer) {
            this.renderer = renderer;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, RenderElementProcessor.class), RenderElementProcessor.class, "renderer", "FIELD:Lnet/labymod/api/client/gui/screen/state/ScreenCanvasRenderer$RenderElementProcessor;->renderer:Lnet/labymod/api/client/gui/screen/state/ScreenCanvasRenderer;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, RenderElementProcessor.class), RenderElementProcessor.class, "renderer", "FIELD:Lnet/labymod/api/client/gui/screen/state/ScreenCanvasRenderer$RenderElementProcessor;->renderer:Lnet/labymod/api/client/gui/screen/state/ScreenCanvasRenderer;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, RenderElementProcessor.class, Object.class), RenderElementProcessor.class, "renderer", "FIELD:Lnet/labymod/api/client/gui/screen/state/ScreenCanvasRenderer$RenderElementProcessor;->renderer:Lnet/labymod/api/client/gui/screen/state/ScreenCanvasRenderer;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public ScreenCanvasRenderer renderer() {
            return this.renderer;
        }

        @Override // net.labymod.api.client.gui.screen.state.LayeredElementConsumer
        public void accept(GuiComponent renderState) {
            this.renderer.addElementToMesh(renderState);
        }
    }
}
