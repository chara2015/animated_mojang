package net.labymod.api.util.bounds;

import java.util.Arrays;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.DefaultBounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.PositionedBounds;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/bounds/RectangleModification.class */
public class RectangleModification {
    private static final StackWalker WALKER = StackWalker.getInstance();
    private static final List<String> STACKTRACE_EXCLUSIONS = Arrays.asList(Thread.class.getName(), RectangleModification.class.getName(), Bounds.class.getName(), PositionedBounds.class.getName(), DefaultBounds.class.getName(), "net.labymod.api.client.gui.screen.widget.attributes.bounds.InnerBounds", DefaultReasonableRectangle.class.getName(), ReasonableMutableRectangle.class.getName());
    private final RectangleState state;
    private final float from;
    private final float to;
    private final ModifyReason reason;
    private final long timestamp;
    private final RectangleModification previousModification;
    private final int frame = Laby.references().frameTimer().getFrame();
    private final StackTraceElement[] stackTrace = (StackTraceElement[]) ((List) WALKER.walk(stream -> {
        return stream.map((v0) -> {
            return v0.toStackTraceElement();
        }).toList();
    })).toArray(x$0 -> {
        return new StackTraceElement[x$0];
    });

    public RectangleModification(@NotNull RectangleState state, float from, float to, @NotNull ModifyReason reason, long timestamp, @Nullable RectangleModification previousModification) {
        this.state = state;
        this.from = from;
        this.to = to;
        this.reason = reason;
        this.timestamp = timestamp;
        this.previousModification = previousModification;
    }

    @NotNull
    public RectangleState state() {
        return this.state;
    }

    public float from() {
        return this.from;
    }

    public float to() {
        return this.to;
    }

    @NotNull
    public ModifyReason reason() {
        return this.reason;
    }

    public long timestamp() {
        return this.timestamp;
    }

    @Nullable
    public RectangleModification getPreviousModification() {
        return this.previousModification;
    }

    public int frame() {
        return this.frame;
    }

    @NotNull
    public StackTraceElement[] stackTrace() {
        return this.stackTrace;
    }

    public RectangleModification withOffset(float xOffset, float yOffset) {
        return withOffset(this, xOffset, yOffset);
    }

    private RectangleModification withOffset(RectangleModification mod, float xOffset, float yOffset) {
        if (mod == null) {
            return null;
        }
        return new RectangleModification(mod.state(), mod.from() + xOffset, mod.to() + yOffset, mod.reason(), mod.timestamp(), withOffset(mod.getPreviousModification(), xOffset, yOffset));
    }

    @NotNull
    public StackTraceElement lastExternalTrace() {
        for (StackTraceElement stackTraceElement : this.stackTrace) {
            if (!STACKTRACE_EXCLUSIONS.contains(stackTraceElement.getClassName())) {
                return stackTraceElement;
            }
        }
        throw new IllegalArgumentException("No external stacktrace element found");
    }

    @NotNull
    public StackTraceElement[] externalStackTrace() {
        for (int i = 0; i < this.stackTrace.length; i++) {
            if (!STACKTRACE_EXCLUSIONS.contains(this.stackTrace[i].getClassName())) {
                return (StackTraceElement[]) Arrays.copyOfRange(this.stackTrace, i, this.stackTrace.length - 1);
            }
        }
        throw new IllegalArgumentException("No external stacktrace element found");
    }

    public String toString() {
        return "RectangleModification{" + this.from + " -> " + this.to + " @ " + this.reason.reason() + "}";
    }
}
