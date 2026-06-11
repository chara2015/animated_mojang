package net.labymod.api.util.bounds;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import net.labymod.api.util.time.TimeUtil;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/bounds/DefaultReasonableRectangle.class */
public class DefaultReasonableRectangle implements ReasonableMutableRectangle {
    private static final long MODIFICATION_TIMEOUT = TimeUnit.SECONDS.toMillis(15);
    protected static final ModifyReason INIT = ModifyReason.of("init");
    protected float left;
    protected float top;
    protected float right;
    protected float bottom;
    private Map<RectangleState, RectangleModification> recordedModifications;

    public DefaultReasonableRectangle() {
    }

    public DefaultReasonableRectangle(Rectangle rectangle) {
        set(rectangle, INIT);
    }

    protected DefaultReasonableRectangle(float left, float top, float right, float bottom) {
        setBounds(left, top, right, bottom, INIT);
    }

    @Override // net.labymod.api.util.bounds.ReasonableMutableRectangle
    public void setLeft(float left, ModifyReason reason) {
        if (this.recordedModifications != null) {
            record(RectangleState.LEFT, this.left, left, reason);
        }
        this.left = left;
    }

    @Override // net.labymod.api.util.bounds.ReasonableMutableRectangle
    public void setTop(float top, ModifyReason reason) {
        if (this.recordedModifications != null) {
            record(RectangleState.TOP, this.top, top, reason);
        }
        this.top = top;
    }

    @Override // net.labymod.api.util.bounds.ReasonableMutableRectangle
    public void setRight(float right, ModifyReason reason) {
        if (this.recordedModifications != null) {
            record(RectangleState.RIGHT, this.right, right, reason);
        }
        this.right = right;
    }

    @Override // net.labymod.api.util.bounds.ReasonableMutableRectangle
    public void setBottom(float bottom, ModifyReason reason) {
        if (this.recordedModifications != null) {
            record(RectangleState.BOTTOM, this.bottom, bottom, reason);
        }
        this.bottom = bottom;
    }

    @Override // net.labymod.api.util.bounds.ReasonableMutableRectangle
    public void setWidth(float width, ModifyReason reason) {
        if (this.recordedModifications != null && getWidth() != width) {
            record(RectangleState.WIDTH, getWidth(), width, reason);
        }
        super.setWidth(width, reason);
    }

    @Override // net.labymod.api.util.bounds.ReasonableMutableRectangle
    public void setHeight(float height, ModifyReason reason) {
        if (this.recordedModifications != null && getHeight() != height) {
            record(RectangleState.HEIGHT, getHeight(), height, reason);
        }
        super.setHeight(height, reason);
    }

    private void record(RectangleState state, float from, float to, ModifyReason reason) {
        record(new RectangleModification(state, from, to, reason, TimeUtil.getMillis(), this.recordedModifications.get(state)));
    }

    private void record(RectangleModification modification) {
        this.recordedModifications.put(modification.state(), modification);
    }

    @Override // net.labymod.api.util.bounds.ReasonableMutableRectangle
    public void recordModifications() {
        if (this.recordedModifications == null) {
            this.recordedModifications = new HashMap();
        }
    }

    @Override // net.labymod.api.util.bounds.ReasonableMutableRectangle
    public void stopRecordingModifications() {
        this.recordedModifications = null;
    }

    @Override // net.labymod.api.util.bounds.ReasonableMutableRectangle
    @NotNull
    public Map<RectangleState, RectangleModification> lastModifications() {
        if (this.recordedModifications == null) {
            return Collections.emptyMap();
        }
        this.recordedModifications.values().removeIf(mod -> {
            return TimeUtil.getMillis() - mod.timestamp() > MODIFICATION_TIMEOUT;
        });
        return this.recordedModifications;
    }

    @Override // net.labymod.api.util.bounds.Rectangle
    public float getLeft() {
        return this.left;
    }

    @Override // net.labymod.api.util.bounds.Rectangle
    public float getTop() {
        return this.top;
    }

    @Override // net.labymod.api.util.bounds.Rectangle
    public float getRight() {
        return this.right;
    }

    @Override // net.labymod.api.util.bounds.Rectangle
    public float getBottom() {
        return this.bottom;
    }

    public String toString() {
        return String.format(Locale.ROOT, "%s,%s (%sx%s)", Float.valueOf(this.left), Float.valueOf(this.top), Float.valueOf(getWidth()), Float.valueOf(getHeight()));
    }
}
