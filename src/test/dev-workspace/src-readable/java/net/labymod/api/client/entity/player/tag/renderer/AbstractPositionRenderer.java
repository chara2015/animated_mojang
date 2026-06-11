package net.labymod.api.client.entity.player.tag.renderer;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.state.entity.EntitySnapshot;
import net.labymod.api.laby3d.render.queue.SubmissionCollector;
import net.labymod.api.service.Registry;
import net.labymod.api.util.KeyValue;
import net.labymod.api.util.logging.Logging;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/player/tag/renderer/AbstractPositionRenderer.class */
public abstract class AbstractPositionRenderer implements PositionRenderer {
    private static final Logging LOGGER = Logging.getLogger();
    private final Set<Result> queuedForRemovalIds = new HashSet();
    protected float scale;
    protected float height;
    protected float width;

    protected abstract void setupPosition(Stack stack);

    protected abstract void shiftPosition(Stack stack);

    protected AbstractPositionRenderer() {
    }

    @Override // net.labymod.api.client.entity.player.tag.renderer.PositionRenderer
    public void render(Stack stack, EntitySnapshot snapshot, SubmissionCollector submissionCollector, Registry<TagRenderer> registry, float usernameWidth, TagType tagType) {
        int preIndex = stack.index();
        State state = new State();
        boolean keepUsernamePosition = keepUsernamePosition();
        for (KeyValue<TagRenderer> entry : registry.getElements()) {
            String tagId = entry.getKey();
            TagRenderer tag = entry.getValue();
            try {
                tag.begin(snapshot);
                if (!isTagHidden(tag, tagType)) {
                    if (keepUsernamePosition && !state.rendered) {
                        state.rendered = true;
                        stack.push();
                    }
                    updateTagDimensions(tag);
                    stack.push();
                    setupPosition(stack);
                    renderTag(stack, submissionCollector, snapshot, tagId, tag);
                    stack.pop();
                    shiftPosition(stack);
                }
            } catch (Throwable throwable) {
                addQueuedForRemovalId(Result.of(tagId, throwable));
            }
        }
        if (state.rendered) {
            stack.pop();
        }
        processQueuedRemovals(registry);
        int postIndex = stack.index();
        int diff = postIndex - preIndex;
        if (diff != 0) {
            stack.recoverStackDepth(diff);
        }
    }

    protected boolean keepUsernamePosition() {
        return true;
    }

    private void updateTagDimensions(TagRenderer tag) {
        this.scale = tag.getScale();
        this.width = tag.getWidth();
        this.height = tag.getHeight();
    }

    private void renderTag(Stack stack, SubmissionCollector collector, EntitySnapshot snapshot, String tagId, TagRenderer tag) {
        int preIndex = stack.index();
        try {
            tag.render(stack, collector, snapshot);
        } catch (Throwable throwable) {
            addQueuedForRemovalId(Result.of(tagId, throwable));
        }
        int postIndex = stack.index();
        int diff = postIndex - preIndex;
        if (diff != 0) {
            stack.recoverStackDepth(diff);
            addQueuedForRemovalId(Result.of(tagId));
        }
    }

    private boolean isTagHidden(TagRenderer tag, TagType type) {
        return ((type == TagType.MAIN_TAG || tag.isOnlyVisibleOnMainTag()) && tag.isVisible()) ? false : true;
    }

    private void processQueuedRemovals(Registry<TagRenderer> registry) {
        for (Result queuedForRemovalId : this.queuedForRemovalIds) {
            registry.unregister(queuedForRemovalId.id);
            Throwable throwable = queuedForRemovalId.throwable;
            if (throwable == null) {
                LOGGER.error("Tag {} was removed from registry because it did not manage the matrix stack properly.", queuedForRemovalId);
            } else {
                LOGGER.error("Tag {} was removed from registry because it threw an exception.", queuedForRemovalId, throwable);
            }
        }
        this.queuedForRemovalIds.clear();
    }

    private void addQueuedForRemovalId(Result result) {
        this.queuedForRemovalIds.add(result);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/player/tag/renderer/AbstractPositionRenderer$State.class */
    static class State {
        boolean rendered;

        State() {
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/player/tag/renderer/AbstractPositionRenderer$Result.class */
    static class Result {
        private final String id;

        @Nullable
        private final Throwable throwable;

        private Result(String id, @Nullable Throwable throwable) {
            this.id = id;
            this.throwable = throwable;
        }

        public static Result of(String id) {
            return of(id, null);
        }

        public static Result of(String id, @Nullable Throwable throwable) {
            return new Result(id, throwable);
        }

        public boolean equals(Object object) {
            if (object == null || getClass() != object.getClass()) {
                return false;
            }
            Result result = (Result) object;
            return Objects.equals(this.id, result.id);
        }

        public int hashCode() {
            return Objects.hashCode(this.id);
        }

        public String toString() {
            return this.id;
        }
    }
}
