package net.labymod.api.client.gui.screen.widget.size;

import net.labymod.api.Laby;
import net.labymod.api.client.render.statistics.FrameTimer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/size/WidgetSizeList.class */
public class WidgetSizeList {
    private static final FrameTimer FRAME_TIMER = Laby.references().frameTimer();
    private static final SizeType[] TYPES = SizeType.values();
    private final WidgetSize[][] sizes = new WidgetSize[TYPES.length];
    private int lastUpdatedFrame;

    /* JADX WARN: Type inference failed for: r1v2, types: [net.labymod.api.client.gui.screen.widget.size.WidgetSize[], net.labymod.api.client.gui.screen.widget.size.WidgetSize[][]] */
    public WidgetSizeList() {
        reset();
    }

    public void reset() {
        for (SizeType type : TYPES) {
            this.sizes[type.ordinal()] = new WidgetSize[WidgetSide.VALUES.length];
        }
    }

    public WidgetSize getSize(SizeType type, WidgetSide side) {
        return this.sizes[type.ordinal()][side.ordinal()];
    }

    public void setSize(SizeType type, WidgetSide side, WidgetSize size) {
        this.sizes[type.ordinal()][side.ordinal()] = size;
        this.lastUpdatedFrame = FRAME_TIMER.getFrame();
    }

    public boolean hasAnySize(WidgetSide side) {
        for (WidgetSize[] size : this.sizes) {
            if (size[side.ordinal()] != null) {
                return true;
            }
        }
        return false;
    }

    public boolean wasUpdatedThisFrame() {
        return this.lastUpdatedFrame == FRAME_TIMER.getFrame();
    }
}
