package net.labymod.api.client.gui.screen.state.offscreen;

import java.util.List;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.render.matrix.DefaultStackProvider;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.laby3d.util.matrix.CachedOrthoProjectionMatrix;
import net.labymod.api.util.RenderUtil;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.math.MathHelper;
import net.labymod.laby3d.api.pipeline.LoadOp;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/offscreen/AtlasOffscreenRenderer.class */
public class AtlasOffscreenRenderer extends OffscreenRenderer<AtlasOffscreenRenderState> {
    private static final int INITIAL_ATLAS_SIZE = 2048;
    private static final int MAX_ATLAS_SIZE = 4096;
    private static final CachedOrthoProjectionMatrix<Matrix4f> ATLAS_PROJECTION_MATRIX = CachedOrthoProjectionMatrix.simple(0.5f, 11000.0f, false);
    private static final Matrix4f MODEL_VIEW_MATRIX = new Matrix4f().setTranslation(0.0f, 0.0f, -11000.0f);
    private RenderTarget atlasTarget;
    private int atlasSize = 2048;
    private final ShelfBinPacker packer = new ShelfBinPacker(this.atlasSize, this.atlasSize);

    @Override // net.labymod.api.client.gui.screen.state.offscreen.OffscreenRenderer
    public void prepareAll(ScreenCanvas canvas, List<AtlasOffscreenRenderState> states, float guiScale) {
        int size = states.size();
        if (size == 0) {
            return;
        }
        int guiScaleInt = MathHelper.ceil(guiScale);
        int packedCount = packStates(states, guiScale);
        if (packedCount == 0) {
            return;
        }
        ensureAtlas();
        renderAtlas(states, guiScaleInt);
        submitBlits(canvas, states);
    }

    private int packStates(List<AtlasOffscreenRenderState> states, float guiScale) {
        int packedCount;
        int size = states.size();
        while (true) {
            this.packer.reset();
            packedCount = 0;
            boolean overflow = false;
            for (int i = 0; i < size; i++) {
                AtlasOffscreenRenderState state = states.get(i);
                state.clearSlot();
                Rectangle bounds = state.bounds();
                int pixelWidth = MathHelper.ceil(bounds.getWidth() * guiScale);
                int pixelHeight = MathHelper.ceil(bounds.getHeight() * guiScale);
                if (pixelWidth > this.atlasSize || pixelHeight > this.atlasSize || !this.packer.pack(pixelWidth, pixelHeight)) {
                    overflow = true;
                } else {
                    state.assignSlot(this.packer.lastX(), this.packer.lastY(), pixelWidth, pixelHeight);
                    packedCount++;
                }
            }
            if (!overflow || this.atlasSize >= 4096) {
                break;
            }
            this.atlasSize = Math.min(4096, this.atlasSize * 2);
            disposeAtlas();
            this.packer.resize(this.atlasSize, this.atlasSize);
        }
        return packedCount;
    }

    private void renderAtlas(List<AtlasOffscreenRenderState> states, int guiScale) {
        int atlasWidth = this.atlasTarget.width();
        int atlasHeight = this.atlasTarget.height();
        this.atlasTarget.clear(true);
        CommandBuffer cmd = this.laby3D.renderDevice().createCommandBuffer(1);
        try {
            RenderUtil.OffscreenScope ignored = RenderUtil.pushOffscreen(this.atlasTarget, ATLAS_PROJECTION_MATRIX.getCached(atlasWidth, atlasHeight), MODEL_VIEW_MATRIX);
            try {
                cmd.beginPass(this.atlasTarget, LoadOp.LOAD);
                int size = states.size();
                for (int i = 0; i < size; i++) {
                    AtlasOffscreenRenderState state = states.get(i);
                    if (state.hasSlot()) {
                        renderSlot(cmd, state, guiScale, atlasHeight);
                    }
                }
                cmd.disableScissor();
                cmd.endPass();
                cmd.submit();
                if (ignored != null) {
                    ignored.close();
                }
                if (cmd != null) {
                    cmd.close();
                }
            } finally {
            }
        } catch (Throwable th) {
            if (cmd != null) {
                try {
                    cmd.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private void renderSlot(CommandBuffer cmd, AtlasOffscreenRenderState state, int guiScale, int atlasHeight) {
        int slotX = state.slotX();
        int slotY = state.slotY();
        int slotWidth = state.slotWidth();
        int slotHeight = state.slotHeight();
        int slotBottomGl = (atlasHeight - slotY) - slotHeight;
        cmd.setScissor(slotX, slotBottomGl, slotWidth, slotHeight);
        float translateY = state.getTranslateY(slotHeight, guiScale);
        float stateScale = guiScale * state.getScale();
        Stack stack = Stack.create((StackProvider) new DefaultStackProvider());
        stack.translate(slotX + (slotWidth / 2.0f), slotBottomGl + translateY, 10000.0f);
        stack.scale(stateScale, stateScale, -stateScale);
        state.render(new OffscreenContext(cmd), stack);
    }

    private void submitBlits(ScreenCanvas canvas, List<AtlasOffscreenRenderState> states) {
        RenderTarget pooled = this.laby3D.renderTargetPool().acquire(this.atlasTarget);
        this.laby3D.copyTextureToTexture(this.atlasTarget, pooled, false);
        float atlasWidth = this.atlasTarget.width();
        float atlasHeight = this.atlasTarget.height();
        int size = states.size();
        for (int i = 0; i < size; i++) {
            AtlasOffscreenRenderState state = states.get(i);
            if (state.hasSlot()) {
                float minU = state.slotX() / atlasWidth;
                float maxU = (state.slotX() + state.slotWidth()) / atlasWidth;
                float minV = 1.0f - ((state.slotY() + state.slotHeight()) / atlasHeight);
                float maxV = 1.0f - (state.slotY() / atlasHeight);
                blitSlice(canvas, state, pooled, minU, minV, maxU, maxV);
            }
        }
    }

    private void ensureAtlas() {
        if (this.atlasTarget != null && this.atlasTarget.width() == this.atlasSize && this.atlasTarget.height() == this.atlasSize) {
            return;
        }
        disposeAtlas();
        this.atlasTarget = createStandardTarget("AtlasOffscreen", "AtlasColor", "AtlasDepth", this.atlasSize, this.atlasSize);
    }

    private void disposeAtlas() {
        if (this.atlasTarget != null) {
            this.atlasTarget.close();
            this.atlasTarget = null;
        }
    }

    @Override // net.labymod.api.client.gui.screen.state.offscreen.OffscreenRenderer
    public void close() {
        super.close();
        disposeAtlas();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.labymod.api.client.gui.screen.state.offscreen.OffscreenRenderer
    public void renderToTexture(OffscreenContext context, AtlasOffscreenRenderState t, Stack stack) {
        t.render(context, stack);
    }
}
