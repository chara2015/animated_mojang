package net.labymod.api.laby3d.render.queue;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gfx.pipeline.renderer.shader.ShaderUtil;
import net.labymod.api.generated.ReferenceStorage;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.render.buffer.Laby3DRenderBufferSource;
import net.labymod.api.laby3d.render.buffer.RenderBufferSource;
import net.labymod.api.laby3d.render.queue.Feature;
import net.labymod.api.tag.Tag;
import net.labymod.api.util.collection.Lists;
import net.labymod.laby3d.api.buffers.ByteBufferBuilder;
import net.labymod.laby3d.api.pipeline.LoadOp;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import net.labymod.laby3d.api.resource.AssetId;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/render/queue/Channel.class */
public final class Channel {
    private final Scheduler scheduler;
    private final SubmissionRendererRegistry submissionRendererRegistry;
    private final RenderEnvironmentContext renderEnvironmentContext;
    private final ByteBufferBuilder buffer;
    private final Laby3D laby3D;
    private final AtomicLong sequenceId = new AtomicLong(1);
    private final List<Feature> features = Lists.newArrayList();

    public Channel(Scheduler scheduler) {
        this.scheduler = scheduler;
        ReferenceStorage references = Laby.references();
        this.laby3D = references.laby3D();
        this.submissionRendererRegistry = references.submissionRendererRegistry();
        this.renderEnvironmentContext = references.renderEnvironmentContext();
        this.buffer = new ByteBufferBuilder(8388608);
    }

    public Channel addFeature(Feature feature) {
        this.features.add(feature);
        return this;
    }

    public void submit(Submission submission) {
        submit(0, submission);
    }

    public void submit(int order, Submission submission) {
        List<Submission> queue = List.of(submission);
        Feature.Context context = new ChannelContext(this, order, submission);
        for (Feature feature : this.features) {
            List<Submission> next = Lists.newArrayList();
            for (Submission s : queue) {
                List<Submission> processed = feature.process(s, context);
                if (processed != null && !processed.isEmpty()) {
                    next.addAll(processed);
                }
            }
            queue = next;
            if (queue.isEmpty()) {
                break;
            }
        }
        for (Submission s2 : queue) {
            schedule(order, s2);
        }
    }

    public void render(AssetId renderPassId) {
        CommandBuffer cmd = this.laby3D.renderDevice().createCommandBuffer(1);
        try {
            RenderTarget renderTargetResolveDrawRenderTarget = this.laby3D.resolveDrawRenderTarget();
            LoadOp loadOp = LoadOp.LOAD;
            Objects.requireNonNull(renderPassId);
            cmd.beginPass(renderTargetResolveDrawRenderTarget, loadOp, renderPassId::toString);
            render(cmd);
            cmd.endPass();
            cmd.submit();
            if (cmd != null) {
                cmd.close();
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

    public void render(CommandBuffer cmd) {
        RenderBufferSource renderBufferSource = selectBufferSource(cmd);
        List<Submission> submissions = this.scheduler.drainInOrder();
        SubmissionRendererContext context = new SubmissionRendererContext(renderBufferSource, ShaderUtil.isShaderSelected());
        for (Submission submission : submissions) {
            this.submissionRendererRegistry.draw(context, submission);
        }
        renderBufferSource.endBatch();
        clear();
    }

    public void clear() {
        this.scheduler.clear();
        this.sequenceId.set(1L);
    }

    void schedule(int order, Submission submission) {
        submission.setSequenceId(this.sequenceId.getAndIncrement());
        this.scheduler.schedule(order, submission);
    }

    RenderBufferSource selectBufferSource(CommandBuffer cmd) {
        RenderBufferSource bufferSource = null;
        if (requiresVanillaBuffer()) {
            bufferSource = Laby.references().renderBufferSource();
        }
        if (bufferSource == null) {
            bufferSource = new Laby3DRenderBufferSource(cmd, this.buffer);
        }
        return bufferSource;
    }

    boolean requiresVanillaBuffer() {
        return ShaderUtil.isShaderSelected() && !this.renderEnvironmentContext.isScreenContext();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/render/queue/Channel$ChannelContext.class */
    static class ChannelContext implements Feature.Context {
        private final Channel channel;
        private final int order;
        private final Submission submission;

        public ChannelContext(Channel channel, int order, Submission submission) {
            this.channel = channel;
            this.order = order;
            this.submission = submission;
        }

        @Override // net.labymod.api.laby3d.render.queue.Feature.Context
        public void emit(Submission submission) {
            this.channel.schedule(this.order, submission);
        }

        @Override // net.labymod.api.laby3d.render.queue.Feature.Context
        public boolean hasTag(Tag tag) {
            return this.submission.hasTag(tag);
        }
    }
}
