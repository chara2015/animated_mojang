package net.labymod.core.laby3d.render.queue;

import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.event.laby3d.queue.SubmissionRendererRegistrationEvent;
import net.labymod.api.laby3d.render.buffer.RenderBufferSource;
import net.labymod.api.laby3d.render.queue.Submission;
import net.labymod.api.laby3d.render.queue.SubmissionRenderer;
import net.labymod.api.laby3d.render.queue.SubmissionRendererContext;
import net.labymod.api.laby3d.render.queue.SubmissionRendererRegistry;
import net.labymod.api.laby3d.render.queue.VanillaSubmissionRenderer;
import net.labymod.api.models.Implements;
import net.labymod.api.util.ide.IdeUtil;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.laby3d.render.queue.renderer.CustomGeometrySubmissionRenderer;
import net.labymod.core.laby3d.render.queue.renderer.IconSubmissionRenderer;
import net.labymod.core.laby3d.render.queue.renderer.ModelSubmissionRenderer;
import net.labymod.core.laby3d.render.queue.renderer.RectangleSubmissionRenderer;
import net.labymod.core.laby3d.render.queue.renderer.TextSubmissionRenderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/render/queue/DefaultSubmissionRendererRegistry.class */
@Singleton
@Implements(SubmissionRendererRegistry.class)
public class DefaultSubmissionRendererRegistry implements SubmissionRendererRegistry {
    private static final Set<Class<?>> INVALID_VANILLA_RENDERERS = new HashSet();
    private static final Logging LOGGER = Logging.getLogger();
    private final Reference2ObjectMap<Class<?>, SubmissionRenderer<?>> renderers = new Reference2ObjectOpenHashMap();

    @Inject
    public DefaultSubmissionRendererRegistry() {
        register(new IconSubmissionRenderer());
        register(new TextSubmissionRenderer());
        register(new CustomGeometrySubmissionRenderer());
        register(new RectangleSubmissionRenderer());
        register(new ModelSubmissionRenderer());
        notifyRendererRegistration();
    }

    @Override // net.labymod.api.laby3d.render.queue.SubmissionRendererRegistry
    public <T extends Submission> void register(@NotNull SubmissionRenderer<T> renderer) {
        this.renderers.put(renderer.type(), renderer);
    }

    @Override // net.labymod.api.laby3d.render.queue.SubmissionRendererRegistry
    public void draw(@NotNull SubmissionRendererContext context, @NotNull Submission submission) {
        SubmissionRenderer<?> submissionRendererFindRenderer = findRenderer(submission);
        if (submissionRendererFindRenderer == null) {
            if (IdeUtil.RUNNING_IN_IDE) {
                throw new IllegalStateException("Tried to draw submission " + String.valueOf(submission) + " but no renderer was found");
            }
            return;
        }
        RenderBufferSource source = context.source();
        if (context.forceVanillaPath()) {
            if (submissionRendererFindRenderer instanceof VanillaSubmissionRenderer) {
                VanillaSubmissionRenderer vanillaRenderer = (VanillaSubmissionRenderer) submissionRendererFindRenderer;
                vanillaRenderer.renderVanilla(source, submission);
                return;
            } else {
                if (INVALID_VANILLA_RENDERERS.add(submissionRendererFindRenderer.getClass())) {
                    LOGGER.error("Tried to draw submission {} but the renderer {} is not a vanilla renderer", submission.getClass().getName(), submissionRendererFindRenderer.getClass().getName());
                    return;
                }
                return;
            }
        }
        submissionRendererFindRenderer.render(source, submission);
    }

    @Nullable
    public SubmissionRenderer<?> findRenderer(@NotNull Submission submission) {
        SubmissionRenderer<?> renderer = (SubmissionRenderer) this.renderers.get(submission.getClass());
        if (renderer != null) {
            return renderer;
        }
        ObjectIterator it = this.renderers.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Class<?>, SubmissionRenderer<?>> entry = (Map.Entry) it.next();
            if (entry.getKey().isInstance(submission)) {
                this.renderers.put(submission.getClass(), entry.getValue());
                return entry.getValue();
            }
        }
        return null;
    }

    private void notifyRendererRegistration() {
        SubmissionRendererRegistrationEvent event = new SubmissionRendererRegistrationEvent(new SubmissionRendererRegistrationEvent.Context(this::register));
        Laby.fireEvent(event);
    }
}
