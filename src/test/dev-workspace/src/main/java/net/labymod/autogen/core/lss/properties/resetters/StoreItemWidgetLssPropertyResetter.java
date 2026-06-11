package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.core.client.gui.screen.widget.widgets.store.StoreItemWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/resetters/StoreItemWidgetLssPropertyResetter.class */
public class StoreItemWidgetLssPropertyResetter extends SimpleWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.core.lss.properties.resetters.SimpleWidgetLssPropertyResetter, net.labymod.autogen.core.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.core.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof StoreItemWidget) {
            if (((StoreItemWidget) widget).installedColor() != null) {
                ((StoreItemWidget) widget).installedColor().reset();
            }
            if (((StoreItemWidget) widget).deletedColor() != null) {
                ((StoreItemWidget) widget).deletedColor().reset();
            }
        }
        super.reset(widget);
    }
}
