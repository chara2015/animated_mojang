package net.labymod.core.client.gui.screen.state.debug;

import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.state.GuiComponent;
import net.labymod.api.client.gui.screen.state.debug.CanvasDebugSnapshot;
import net.labymod.api.client.gui.screen.state.recorder.GuiComponentRecorder;
import net.labymod.api.client.gui.screen.state.recorder.MeshRecorder;
import net.labymod.api.client.gui.screen.state.recorder.MeshRenderer;
import net.labymod.api.client.gui.screen.state.recorder.VertexBufferManager;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.laby3d.api.buffers.ByteBufferBuilder;
import net.labymod.laby3d.api.opengl.GlResource;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import net.labymod.laby3d.api.pipeline.target.RenderTargetDescription;
import net.labymod.laby3d.api.pipeline.target.attachment.AttachmentType;
import net.labymod.laby3d.api.pipeline.target.attachment.ClearValue;
import net.labymod.laby3d.api.pipeline.target.attachment.RenderTargetAttachmentDescription;
import net.labymod.laby3d.api.textures.DeviceTexture;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import net.labymod.laby3d.api.textures.SamplerDescription;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/state/debug/CanvasDebugPreviewRenderer.class */
public final class CanvasDebugPreviewRenderer {
    private static final int PREVIEW_WIDTH = 853;
    private static final int PREVIEW_HEIGHT = 480;

    @Nullable
    private static CanvasDebugPreviewRenderer instance;
    private RenderTarget previewTarget;
    private int lastWidth = PREVIEW_WIDTH;
    private int lastHeight = PREVIEW_HEIGHT;
    private final Laby3D laby3D = Laby.references().laby3D();
    private final ByteBufferBuilder buffer = new ByteBufferBuilder(1048576);
    private final MeshRecorder meshRecorder = new MeshRecorder(this.laby3D);
    private final MeshRenderer meshRenderer = new MeshRenderer(this.laby3D);
    private final VertexBufferManager vertexBufferManager = new VertexBufferManager(this.laby3D);

    private CanvasDebugPreviewRenderer() {
        createRenderTarget(PREVIEW_WIDTH, PREVIEW_HEIGHT);
    }

    public static CanvasDebugPreviewRenderer getInstance() {
        if (instance == null) {
            instance = new CanvasDebugPreviewRenderer();
        }
        return instance;
    }

    private void createRenderTarget(int width, int height) {
        if (this.previewTarget != null) {
            this.previewTarget.close();
        }
        RenderTargetDescription description = RenderTargetDescription.builder().addColorAttachment(RenderTargetAttachmentDescription.builder().setName("CanvasDebugPreview").setFormat(DeviceTexture.Format.R8G8B8A8_UNORM).setType(AttachmentType.COLOR).setClearValue(ClearValue.color(0.1f, 0.1f, 0.1f, 1.0f)).setSamplerDescription(builder -> {
            builder.setFilter(SamplerDescription.Filter.LINEAR);
        }).build()).setSize(width, height).build();
        this.previewTarget = this.laby3D.renderDevice().createTarget("Canvas Debug Preview", description);
        this.lastWidth = width;
        this.lastHeight = height;
    }

    public void resize(int width, int height) {
        if (width != this.lastWidth || height != this.lastHeight) {
            createRenderTarget(width, height);
        }
    }

    public void renderPreview(List<CanvasDebugSnapshot> snapshots, int selectedIndex) {
        if (this.previewTarget == null || snapshots == null || snapshots.isEmpty()) {
            return;
        }
        GuiComponentRecorder recorder = new GuiComponentRecorder(this.meshRecorder, this.buffer, this.previewTarget);
        recorder.invalidate();
        int currentIndex = 0;
        loop0: for (CanvasDebugSnapshot snapshot : snapshots) {
            for (CanvasDebugSnapshot.ComponentSnapshot componentSnapshot : snapshot.getComponents()) {
                if (currentIndex > selectedIndex) {
                    break loop0;
                }
                GuiComponent component = componentSnapshot.getComponent();
                recorder.record(component);
                currentIndex++;
            }
        }
        recorder.flush();
        this.meshRecorder.prepareRecordedStates(this.vertexBufferManager);
        this.meshRenderer.render(this.meshRecorder.renderSteps(), this.meshRecorder.recordedStates());
        this.meshRecorder.invalidate();
        this.vertexBufferManager.rotateBuffers();
    }

    public RenderTarget getPreviewTarget() {
        return this.previewTarget;
    }

    public int getPreviewTextureId() {
        DeviceTextureView textureView;
        if (this.previewTarget == null || (textureView = this.previewTarget.findColorTexture(0)) == null) {
            return 0;
        }
        GlResource glResourceTexture = textureView.texture();
        if (glResourceTexture instanceof GlResource) {
            GlResource glResource = glResourceTexture;
            return glResource.getId();
        }
        return 0;
    }

    public int getWidth() {
        return this.lastWidth;
    }

    public int getHeight() {
        return this.lastHeight;
    }

    public void clear() {
        if (this.previewTarget != null) {
            this.previewTarget.clear(true);
        }
    }

    public void close() {
        if (this.previewTarget != null) {
            this.previewTarget.close();
            this.previewTarget = null;
        }
        instance = null;
    }
}
