package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.settings.SettingWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/SettingWidgetLssPropertyResetter.class */
public class SettingWidgetLssPropertyResetter extends FlexibleContentWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.FlexibleContentWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof SettingWidget) {
        }
        super.reset(widget);
    }
}
