package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.entry.HorizontalListEntry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/HorizontalListEntryLssPropertyResetter.class */
public class HorizontalListEntryLssPropertyResetter extends WrappedWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.WrappedWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof HorizontalListEntry) {
            if (((HorizontalListEntry) widget).flex() != null) {
                ((HorizontalListEntry) widget).flex().reset();
            }
            if (((HorizontalListEntry) widget).alignment() != null) {
                ((HorizontalListEntry) widget).alignment().reset();
            }
            if (((HorizontalListEntry) widget).ignoreWidth() != null) {
                ((HorizontalListEntry) widget).ignoreWidth().reset();
            }
            if (((HorizontalListEntry) widget).ignoreHeight() != null) {
                ((HorizontalListEntry) widget).ignoreHeight().reset();
            }
            if (((HorizontalListEntry) widget).skipFill() != null) {
                ((HorizontalListEntry) widget).skipFill().reset();
            }
        }
        super.reset(widget);
    }
}
