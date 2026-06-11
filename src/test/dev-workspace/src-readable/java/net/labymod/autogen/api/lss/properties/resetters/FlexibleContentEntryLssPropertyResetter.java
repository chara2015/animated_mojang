package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.entry.FlexibleContentEntry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/FlexibleContentEntryLssPropertyResetter.class */
public class FlexibleContentEntryLssPropertyResetter extends WrappedWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.WrappedWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if ((widget instanceof FlexibleContentEntry) && ((FlexibleContentEntry) widget).ignoreBounds() != null) {
            ((FlexibleContentEntry) widget).ignoreBounds().reset();
        }
        super.reset(widget);
    }
}
