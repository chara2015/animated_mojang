package net.labymod.api.laby3d.render.queue;

import java.util.List;
import net.labymod.api.tag.Tag;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/render/queue/Feature.class */
public interface Feature {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/render/queue/Feature$Context.class */
    public interface Context {
        void emit(Submission submission);

        boolean hasTag(Tag tag);
    }

    List<Submission> process(Submission submission, Context context);
}
