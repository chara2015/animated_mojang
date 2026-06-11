package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.core.client.gui.screen.widget.widgets.navigation.NavigationListWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/resetters/NavigationListWidgetLssPropertyResetter.class */
public class NavigationListWidgetLssPropertyResetter extends HorizontalListWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.core.lss.properties.resetters.HorizontalListWidgetLssPropertyResetter, net.labymod.autogen.core.lss.properties.resetters.ListWidgetLssPropertyResetter, net.labymod.autogen.core.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.core.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof NavigationListWidget) {
            if (((NavigationListWidget) widget).priorityAlignment() != null) {
                ((NavigationListWidget) widget).priorityAlignment().reset();
            }
            if (((NavigationListWidget) widget).maxPadding() != null) {
                ((NavigationListWidget) widget).maxPadding().reset();
            }
        }
        super.reset(widget);
    }
}
