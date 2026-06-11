package net.labymod.api.client.gui.screen.widget.attributes.bounds;

import java.util.HashMap;
import java.util.Map;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.bounds.ReasonableMutableRectangle;
import net.labymod.api.util.bounds.RectangleModification;
import net.labymod.api.util.bounds.RectangleState;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/attributes/bounds/InnerBounds.class */
class InnerBounds implements ReasonableMutableRectangle {
    private final Bounds bounds;
    private final BoundsType type;

    InnerBounds(Bounds bounds, BoundsType type) {
        this.bounds = bounds;
        this.type = type;
    }

    @Override // net.labymod.api.util.bounds.Rectangle
    public float getLeft() {
        return this.bounds.getLeft(this.type);
    }

    @Override // net.labymod.api.util.bounds.Rectangle
    public float getTop() {
        return this.bounds.getTop(this.type);
    }

    @Override // net.labymod.api.util.bounds.Rectangle
    public float getRight() {
        return this.bounds.getRight(this.type);
    }

    @Override // net.labymod.api.util.bounds.Rectangle
    public float getBottom() {
        return this.bounds.getBottom(this.type);
    }

    public String toString() {
        return getLeft() + "," + getTop() + " (" + (getRight() - getLeft()) + "x" + (getBottom() - getTop()) + ")";
    }

    @Override // net.labymod.api.util.bounds.ReasonableMutableRectangle
    public void setLeft(float left, ModifyReason reason) {
        this.bounds.setLeft(left, this.type, reason);
    }

    @Override // net.labymod.api.util.bounds.ReasonableMutableRectangle
    public void setTop(float top, ModifyReason reason) {
        this.bounds.setTop(top, this.type, reason);
    }

    @Override // net.labymod.api.util.bounds.ReasonableMutableRectangle
    public void setRight(float right, ModifyReason reason) {
        this.bounds.setRight(right, this.type, reason);
    }

    @Override // net.labymod.api.util.bounds.ReasonableMutableRectangle
    public void setBottom(float bottom, ModifyReason reason) {
        this.bounds.setBottom(bottom, this.type, reason);
    }

    @Override // net.labymod.api.util.bounds.ReasonableMutableRectangle
    public void recordModifications() {
        this.bounds.recordModifications();
    }

    @Override // net.labymod.api.util.bounds.ReasonableMutableRectangle
    public void stopRecordingModifications() {
        this.bounds.stopRecordingModifications();
    }

    @Override // net.labymod.api.util.bounds.ReasonableMutableRectangle
    @NotNull
    public Map<RectangleState, RectangleModification> lastModifications() {
        Map<RectangleState, RectangleModification> base = this.bounds.lastModifications();
        if (base.isEmpty()) {
            return base;
        }
        Map<RectangleState, RectangleModification> innerMods = new HashMap<>(base.size());
        base.forEach((state, mod) -> {
            float offset = this.bounds.getOffset(this.type, state);
            if (state == RectangleState.BOTTOM || state == RectangleState.RIGHT) {
                offset = -offset;
            }
            innerMods.put(state, mod.withOffset(offset, offset));
        });
        return innerMods;
    }
}
