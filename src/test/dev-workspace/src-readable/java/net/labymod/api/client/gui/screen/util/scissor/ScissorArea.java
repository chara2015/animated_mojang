package net.labymod.api.client.gui.screen.util.scissor;

import java.util.Objects;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.debug.DebugFlags;
import net.labymod.api.util.bounds.Rectangle;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/util/scissor/ScissorArea.class */
public final class ScissorArea {
    public static final ScissorArea EMPTY = new ScissorArea(null, Rectangle.EMPTY);

    @Nullable
    private final Matrix4f pose;
    private final Rectangle bounds;

    private ScissorArea(@Nullable Matrix4f pose, Rectangle bounds) {
        this.pose = pose;
        this.bounds = bounds;
    }

    public static ScissorArea fromRectangle(Stack stack, Rectangle bounds) {
        Matrix4f pose;
        if (DebugFlags.VISUAL_SCISSOR_AREA) {
            pose = stack.getProvider().getPose();
        } else {
            pose = null;
        }
        return new ScissorArea(pose, bounds);
    }

    @Nullable
    public Matrix4f getPose() {
        return this.pose;
    }

    public Rectangle bounds() {
        return this.bounds;
    }

    public ScissorArea intersection(ScissorArea other) {
        Rectangle result = (Rectangle) Objects.requireNonNullElse(bounds().intersection(other.bounds()), Rectangle.EMPTY);
        return new ScissorArea(getPose(), result);
    }

    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ScissorArea that = (ScissorArea) o;
        return Objects.equals(this.bounds, that.bounds);
    }

    public int hashCode() {
        return Objects.hashCode(this.bounds);
    }
}
