package net.labymod.api.client.entity.player.tag.renderer;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.state.entity.EntitySnapshot;
import net.labymod.api.laby3d.render.queue.SubmissionCollector;
import org.jetbrains.annotations.MustBeInvokedByOverriders;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/player/tag/renderer/TagRenderer.class */
public interface TagRenderer {
    @MustBeInvokedByOverriders
    void begin(EntitySnapshot entitySnapshot);

    void render(Stack stack, SubmissionCollector submissionCollector, EntitySnapshot entitySnapshot);

    boolean isVisible();

    float getWidth();

    float getHeight();

    float getScale();

    default boolean isDiscrete(EntitySnapshot snapshot) {
        return snapshot.isDiscrete();
    }

    default boolean isOnlyVisibleOnMainTag() {
        return false;
    }

    default boolean shouldCenterName() {
        return false;
    }
}
