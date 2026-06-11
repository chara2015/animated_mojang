package net.labymod.api.client.gui.screen.widget.widgets.navigation;

import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.util.bounds.Rectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/navigation/TabLayoutWidget.class */
@AutoWidget
@Link("tabbed.lss")
public class TabLayoutWidget extends HorizontalListWidget {
    public TabLayoutWidget() {
        addId("menu");
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.attributes.bounds.WidgetStyleSheetUpdater
    public void onBoundsChanged(Rectangle previousRect, Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public int getSortingValue() {
        return 1;
    }
}
