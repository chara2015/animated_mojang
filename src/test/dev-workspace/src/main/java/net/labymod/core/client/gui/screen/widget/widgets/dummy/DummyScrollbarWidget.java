package net.labymod.core.client.gui.screen.widget.widgets.dummy;

import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollbarWidget;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.bounds.Rectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/dummy/DummyScrollbarWidget.class */
public class DummyScrollbarWidget extends ScrollbarWidget {
    private static final ModifyReason DUMMY_SCROLLBAR = ModifyReason.of("dummyScrollbar");
    private float scrollButtonOffset;
    private float contentHeight;

    public DummyScrollbarWidget(Rectangle rectangle, float contentHeight, float scrollButtonOffset, ScrollWidget scrollWidget) {
        super(scrollWidget);
        this.scrollButtonOffset = scrollButtonOffset;
        this.contentHeight = contentHeight;
        bounds().set(rectangle, DUMMY_SCROLLBAR);
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollbarWidget
    public float getScrollButtonOffset() {
        return this.scrollButtonOffset;
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollbarWidget
    protected float getContentHeight() {
        return this.contentHeight;
    }
}
