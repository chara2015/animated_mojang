package net.labymod.core.laby3d.render.queue;

import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer;
import net.labymod.api.client.gfx.pipeline.renderer.text.FormattedTextLayout;
import net.labymod.api.client.gfx.pipeline.renderer.text.TextRenderer;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.laby3d.math.JomlMath;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.pipeline.material.LevelMaterial;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.laby3d.render.queue.Channel;
import net.labymod.api.laby3d.render.queue.ChannelType;
import net.labymod.api.laby3d.render.queue.ChannelTypes;
import net.labymod.api.laby3d.render.queue.CustomGeometryRenderer;
import net.labymod.api.laby3d.render.queue.Submission;
import net.labymod.api.laby3d.render.queue.SubmissionBus;
import net.labymod.api.laby3d.render.queue.SubmissionCollector;
import net.labymod.api.laby3d.render.queue.submissions.IconSubmission;
import net.labymod.api.models.Implements;
import net.labymod.api.util.color.GradientDirection;
import net.labymod.core.laby3d.render.queue.CustomGeometrySubmission;
import net.labymod.core.laby3d.render.queue.ModelSubmission;
import net.labymod.laby3d.api.pipeline.RenderState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/render/queue/DefaultSubmissionCollector.class */
@Singleton
@Implements(SubmissionCollector.class)
public class DefaultSubmissionCollector implements SubmissionCollector {
    private final SubmissionBus bus = new SubmissionBus();
    private final TextRenderer textRenderer;
    private ChannelType currentChannel;
    private int order;

    public DefaultSubmissionCollector() {
        this.bus.register(ChannelTypes.LEVEL, new Channel(new DefaultScheduler()));
        this.bus.register(ChannelTypes.HAND, new Channel(new DefaultScheduler()));
        this.bus.register(ChannelTypes.UI, new Channel(new DefaultScheduler()));
        this.textRenderer = Laby.references().textRendererProvider().getRenderer();
    }

    @Override // net.labymod.api.laby3d.render.queue.SubmissionCollector
    @NotNull
    public SubmissionCollector order(int order) {
        this.order = order;
        return this;
    }

    @Override // net.labymod.api.laby3d.render.queue.SubmissionCollector
    public void submitComponent(@NotNull Stack stack, @NotNull Component component, float x, float y, int argb, int lightCoords, int backgroundArgb, int flags) {
        FontRenderer currentRenderer = this.textRenderer.getCurrent();
        FormattedTextLayout.Factory factory = Laby.references().formattedTextLayoutFactory();
        TextSubmission submission = new TextSubmission(currentRenderer, JomlMath.extractMatrix(stack.getProvider().getPose()), factory.create(component), x, y, argb, lightCoords, backgroundArgb, flags);
        submit(submission);
    }

    @Override // net.labymod.api.laby3d.render.queue.SubmissionCollector
    public void submitIcon(@NotNull Stack stack, @NotNull Icon icon, @NotNull IconSubmission.DisplayMode displayMode, float x, float y, float width, float height, int argb) {
        submitIcon(null, stack, icon, displayMode, x, y, width, height, argb);
    }

    @Override // net.labymod.api.laby3d.render.queue.SubmissionCollector
    public void submitIcon(@Nullable RenderState customRenderState, @NotNull Stack stack, @NotNull Icon icon, @NotNull IconSubmission.DisplayMode displayMode, float x, float y, float width, float height, int argb) {
        Matrix4f pose = JomlMath.extractMatrix(stack.getProvider().getPose());
        DefaultIconSubmission submission = new DefaultIconSubmission(customRenderState, displayMode, icon, pose, x, y, width, height, argb);
        submit(submission);
    }

    @Override // net.labymod.api.laby3d.render.queue.SubmissionCollector
    public void submitModel(@NotNull Stack stack, @NotNull Model model, @NotNull Material material, int lightCoords, int overlayCoords) {
        Matrix4f pose = JomlMath.extractMatrix(stack.getProvider().getPose());
        ModelSubmission submission = new ModelSubmission(material, model, pose, lightCoords, overlayCoords);
        if (material.supportsBlending()) {
            Vector3f position = pose.transformPosition(new Vector3f());
            submission = new ModelSubmission.Translucent(submission, position);
        }
        submit(submission);
    }

    @Override // net.labymod.api.laby3d.render.queue.SubmissionCollector
    public void submitCustomGeometry(@NotNull Stack stack, @NotNull Material material, @NotNull CustomGeometryRenderer renderer) {
        Matrix4f pose = JomlMath.extractMatrix(stack.getProvider().getPose());
        CustomGeometrySubmission submission = new CustomGeometrySubmission(material, pose, renderer);
        if (material.supportsBlending()) {
            Vector3f position = pose.transformPosition(new Vector3f());
            submission = new CustomGeometrySubmission.Translucent(submission, position);
        }
        submit(submission);
    }

    @Override // net.labymod.api.laby3d.render.queue.SubmissionCollector
    public void submitRectangle(@NotNull Stack stack, float x, float y, float width, float height, int argb, int lightCoords) {
        submitGradientRectangle(stack, GradientDirection.TOP_TO_BOTTOM, x, y, width, height, argb, argb, lightCoords);
    }

    @Override // net.labymod.api.laby3d.render.queue.SubmissionCollector
    public void submitRectangle(@NotNull Material material, @NotNull Stack stack, float x, float y, float width, float height, int argb, int lightCoords) {
        submitGradientRectangle(material, stack, GradientDirection.TOP_TO_BOTTOM, x, y, width, height, argb, argb, lightCoords);
    }

    @Override // net.labymod.api.laby3d.render.queue.SubmissionCollector
    public void submitGradientRectangle(@NotNull Stack stack, @NotNull GradientDirection direction, float x, float y, float width, float height, int startArgb, int endArgb, int lightCoords) {
        Material material = LevelMaterial.builder(RenderStates.SIMPLE_LEVEL_GEOMETRY).setTexture(0, Textures.WHITE).build();
        submitGradientRectangle(material, stack, direction, x, y, width, height, startArgb, endArgb, lightCoords);
    }

    @Override // net.labymod.api.laby3d.render.queue.SubmissionCollector
    public void submitGradientRectangle(@NotNull Material material, @NotNull Stack stack, @NotNull GradientDirection direction, float x, float y, float width, float height, int startArgb, int endArgb, int lightCoords) {
        Matrix4f pose = JomlMath.extractMatrix(stack.getProvider().getPose());
        RectangleSubmission submission = new RectangleSubmission(material, pose, direction, x, y, width, height, startArgb, endArgb, lightCoords);
        submit(submission);
    }

    @Override // net.labymod.api.laby3d.render.queue.SubmissionCollector
    @NotNull
    public SubmissionCollector.SubmissionChannel channel(@Nullable ChannelType type) {
        ChannelType next = type == null ? this.currentChannel : type;
        return new DefaultSubmissionChannel(this, next, this.currentChannel);
    }

    @Override // net.labymod.api.laby3d.render.queue.SubmissionCollector
    public void submit(@NotNull Submission submission) {
        this.bus.channel(this.currentChannel).submit(this.order, submission);
        this.order = 0;
    }

    @Override // net.labymod.api.laby3d.render.queue.SubmissionCollector
    @NotNull
    public SubmissionBus bus() {
        return this.bus;
    }

    void pushChannel(ChannelType newChannel) {
        this.currentChannel = newChannel;
    }

    void popChannel(ChannelType oldChannel) {
        this.currentChannel = oldChannel;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/render/queue/DefaultSubmissionCollector$DefaultSubmissionChannel.class */
    static class DefaultSubmissionChannel implements SubmissionCollector.SubmissionChannel {
        private final DefaultSubmissionCollector collector;
        private final ChannelType current;
        private final ChannelType previous;

        public DefaultSubmissionChannel(@NotNull DefaultSubmissionCollector collector, @NotNull ChannelType current, @Nullable ChannelType previous) {
            this.collector = collector;
            this.current = current;
            this.previous = previous;
            this.collector.pushChannel(current);
        }

        @Override // net.labymod.api.laby3d.render.queue.SubmissionCollector.SubmissionChannel, java.lang.AutoCloseable
        public void close() {
            this.collector.popChannel(this.previous);
        }

        @NotNull
        public String toString() {
            return "ChannelScope(current=" + String.valueOf(this.current) + ", previous=" + String.valueOf(this.previous) + ")";
        }
    }
}
