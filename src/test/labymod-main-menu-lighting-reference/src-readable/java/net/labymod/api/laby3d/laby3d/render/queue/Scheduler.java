package net.labymod.api.laby3d.render.queue;

import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/render/queue/Scheduler.class */
public interface Scheduler {
    void schedule(int i, Submission submission);

    List<Submission> drainInOrder();

    void clear();

    default void schedule(Submission submission) {
        schedule(0, submission);
    }
}
