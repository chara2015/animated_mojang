package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.shop.widgets.SectionedListWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/resetters/SectionedListWidgetLssPropertyResetter.class */
public class SectionedListWidgetLssPropertyResetter extends SessionedListWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.core.lss.properties.resetters.SessionedListWidgetLssPropertyResetter, net.labymod.autogen.core.lss.properties.resetters.ListWidgetLssPropertyResetter, net.labymod.autogen.core.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.core.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if ((widget instanceof SectionedListWidget) && ((SectionedListWidget) widget).spaceBetweenEntries() != null) {
            ((SectionedListWidget) widget).spaceBetweenEntries().reset();
        }
        super.reset(widget);
    }
}
