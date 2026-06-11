package net.labymod.api.client.gui.screen.widget.attributes.bounds;

import net.labymod.api.Laby;
import net.labymod.api.util.bounds.DefaultReasonableRectangle;
import net.labymod.api.util.bounds.DefaultRectangle;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.bounds.ReasonableMutableRectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/attributes/bounds/PositionedBounds.class */
public class PositionedBounds extends DefaultReasonableRectangle implements DefaultBounds {
    private final transient WidgetStyleSheetUpdater updater;
    private final transient DefaultRectangle prevRectangle;
    private final transient ReasonableMutableRectangle[] innerRectangles;
    private transient boolean changed;
    private int lastUpdatedFrame;

    public PositionedBounds() {
        this(null);
    }

    public PositionedBounds(Bounds parent, BoundsType type) {
        this(parent.getLeft(type), parent.getTop(type), parent.getRight(type), parent.getBottom(type));
    }

    public PositionedBounds(WidgetStyleSheetUpdater updater, Bounds parent, BoundsType type) {
        this(updater, parent.getLeft(type), parent.getTop(type), parent.getRight(type), parent.getBottom(type));
    }

    public PositionedBounds(float left, float top, float right, float bottom) {
        this();
        setBounds(left, top, right, bottom, INIT);
    }

    public PositionedBounds(WidgetStyleSheetUpdater updater, float left, float top, float right, float bottom) {
        this(updater);
        setBounds(left, top, right, bottom, INIT);
    }

    public PositionedBounds(WidgetStyleSheetUpdater updater) {
        this.prevRectangle = new DefaultRectangle();
        this.innerRectangles = new ReasonableMutableRectangle[BoundsType.VALUES.length];
        this.changed = false;
        this.lastUpdatedFrame = -1;
        this.updater = updater;
        for (BoundsType type : BoundsType.VALUES) {
            this.innerRectangles[type.ordinal()] = new InnerBounds(this, type);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    public void checkForChanges() {
        if (this.changed) {
            this.changed = false;
            if (this.updater != null && !this.prevRectangle.equalsBounds(this)) {
                this.updater.onBoundsChanged(this.prevRectangle, this);
                this.prevRectangle.set(this);
            }
        }
    }

    private void setChanged() {
        this.changed = true;
        this.lastUpdatedFrame = Laby.references().frameTimer().getFrame();
    }

    @Override // net.labymod.api.util.bounds.DefaultReasonableRectangle, net.labymod.api.util.bounds.ReasonableMutableRectangle
    public void setLeft(float left, ModifyReason reason) {
        if (this.left != left) {
            setChanged();
            super.setLeft(left, reason);
        }
    }

    @Override // net.labymod.api.util.bounds.DefaultReasonableRectangle, net.labymod.api.util.bounds.ReasonableMutableRectangle
    public void setTop(float top, ModifyReason reason) {
        if (this.top != top) {
            setChanged();
            super.setTop(top, reason);
        }
    }

    @Override // net.labymod.api.util.bounds.DefaultReasonableRectangle, net.labymod.api.util.bounds.ReasonableMutableRectangle
    public void setRight(float right, ModifyReason reason) {
        if (this.right != right) {
            setChanged();
            super.setRight(right, reason);
        }
    }

    @Override // net.labymod.api.util.bounds.DefaultReasonableRectangle, net.labymod.api.util.bounds.ReasonableMutableRectangle
    public void setBottom(float bottom, ModifyReason reason) {
        if (this.bottom != bottom) {
            setChanged();
            super.setBottom(bottom, reason);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    public float getOffset(BoundsType type, OffsetSide side) {
        if (this.updater == null) {
            return 0.0f;
        }
        if (type == BoundsType.MIDDLE) {
            return this.updater.getPadding(side).floatValue();
        }
        if (type == BoundsType.BORDER) {
            return this.updater.getPadding(side).floatValue() + this.updater.getBorder(side).floatValue();
        }
        if (type == BoundsType.OUTER) {
            return this.updater.getPadding(side).floatValue() + this.updater.getBorder(side).floatValue() + this.updater.getMargin(side).floatValue();
        }
        return 0.0f;
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    public ReasonableMutableRectangle rectangle(BoundsType type) {
        return this.innerRectangles[type.ordinal()];
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    public WidgetStyleSheetUpdater getUpdater() {
        return this.updater;
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    public DefaultRectangle prevRectangle() {
        return this.prevRectangle;
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    public boolean wasUpdatedThisFrame() {
        return this.lastUpdatedFrame == Laby.references().frameTimer().getFrame();
    }
}
