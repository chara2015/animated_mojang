package net.labymod.api.event.laby3d.queue;

import java.util.function.Consumer;
import net.labymod.api.event.Event;
import net.labymod.api.event.ReplayableEvent;
import net.labymod.api.laby3d.render.queue.Submission;
import net.labymod.api.laby3d.render.queue.SubmissionRenderer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/laby3d/queue/SubmissionRendererRegistrationEvent.class */
@ReplayableEvent
public class SubmissionRendererRegistrationEvent implements Event {
    private final Context context;

    public SubmissionRendererRegistrationEvent(Context context) {
        this.context = context;
    }

    public <T extends Submission> void register(SubmissionRenderer<T> renderer) {
        this.context.register(renderer);
    }

    public Context getContext() {
        return this.context;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/laby3d/queue/SubmissionRendererRegistrationEvent$Context.class */
    public static final class Context {
        private final Consumer<SubmissionRenderer> register;

        public Context(Consumer<SubmissionRenderer> register) {
            this.register = register;
        }

        public <T extends Submission> void register(SubmissionRenderer<T> renderer) {
            this.register.accept(renderer);
        }
    }
}
