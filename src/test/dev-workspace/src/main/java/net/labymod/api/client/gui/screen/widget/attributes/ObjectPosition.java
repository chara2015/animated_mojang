package net.labymod.api.client.gui.screen.widget.attributes;

import java.util.function.Supplier;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.gui.VerticalAlignment;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/attributes/ObjectPosition.class */
public class ObjectPosition {
    private HorizontalAlignment horizontalAlignment;
    private Supplier<Float> horizontalOffset;
    private VerticalAlignment verticalAlignment;
    private Supplier<Float> verticalOffset;

    public HorizontalAlignment getHorizontalAlignment() {
        return this.horizontalAlignment != null ? this.horizontalAlignment : HorizontalAlignment.CENTER;
    }

    public void setHorizontalAlignment(HorizontalAlignment horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
    }

    public float getHorizontalOffset() {
        if (this.horizontalOffset != null) {
            return this.horizontalOffset.get().floatValue();
        }
        return 0.0f;
    }

    public void setHorizontalOffset(Supplier<Float> horizontalOffset) {
        this.horizontalOffset = horizontalOffset;
    }

    public VerticalAlignment getVerticalAlignment() {
        return this.verticalAlignment != null ? this.verticalAlignment : VerticalAlignment.CENTER;
    }

    public void setVerticalAlignment(VerticalAlignment verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
    }

    public float getVerticalOffset() {
        if (this.verticalOffset != null) {
            return this.verticalOffset.get().floatValue();
        }
        return 0.0f;
    }

    public void setVerticalOffset(Supplier<Float> verticalOffset) {
        this.verticalOffset = verticalOffset;
    }
}
