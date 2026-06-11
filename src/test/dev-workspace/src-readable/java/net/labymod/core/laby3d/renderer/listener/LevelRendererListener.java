package net.labymod.core.laby3d.renderer.listener;

import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.state.world.CameraSnapshot;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.render.world.RenderWorldEvent;
import net.labymod.api.generated.ReferenceStorage;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.render.queue.ChannelTypes;
import net.labymod.api.laby3d.render.queue.SubmissionCollector;
import net.labymod.api.laby3d.renderer.GeometrySubmitter;
import net.labymod.core.laby3d.renderer.WorldSnapshotDispatcher;
import net.labymod.laby3d.api.pipeline.LoadOp;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import net.labymod.laby3d.api.resource.AssetId;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/renderer/listener/LevelRendererListener.class */
public class LevelRendererListener {
    private static final AssetId LEVEL_PASS = AssetId.of("labymod", "pass/level");
    private final Laby3D laby3D;
    private final GeometrySubmitter geometrySubmitter;
    private final SubmissionCollector submissionCollector;
    private final WorldSnapshotDispatcher worldSnapshotDispatcher;

    public LevelRendererListener() {
        ReferenceStorage references = Laby.references();
        this.laby3D = references.laby3D();
        this.geometrySubmitter = references.geometrySubmitter();
        this.submissionCollector = Laby.references().submissionCollector();
        this.worldSnapshotDispatcher = new WorldSnapshotDispatcher();
    }

    @Subscribe
    public void dispatchWorldSnapshot(@NotNull RenderWorldEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        Stack stack = event.stack();
        CameraSnapshot camera = new CameraSnapshot(event.camera());
        float partialTicks = event.getPartialTicks();
        this.worldSnapshotDispatcher.dispatch(stack, camera, partialTicks);
    }

    @Subscribe(126)
    public void renderSubmissions(@NotNull RenderWorldEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        CommandBuffer cmd = this.laby3D.renderDevice().createCommandBuffer(1);
        try {
            cmd.beginPass(this.laby3D.resolveDrawRenderTarget(), LoadOp.LOAD);
            this.geometrySubmitter.render(cmd, false);
            cmd.endPass();
            cmd.submit();
            if (cmd != null) {
                cmd.close();
            }
            this.submissionCollector.bus().channel(ChannelTypes.LEVEL).render(LEVEL_PASS);
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
}
