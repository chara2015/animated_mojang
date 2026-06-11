package net.labymod.core.client.gui.screen.widget.widgets.dummy;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/dummy/DummyScrollWidget.class */
public class DummyScrollWidget extends ScrollWidget {
    private DummyScrollWidget(VerticalListWidget<?> listWidget) {
        super(listWidget);
    }

    private DummyScrollWidget(Widget widget, ListSession<?> session) {
        super(widget, session);
    }

    public DummyScrollWidget() {
        super((VerticalListWidget<?>) new VerticalListWidget());
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget
    public boolean isScrollbarRequired() {
        return true;
    }
}
