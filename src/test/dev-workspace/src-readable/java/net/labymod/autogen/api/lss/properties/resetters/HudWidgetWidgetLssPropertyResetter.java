package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.hud.HudWidgetWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/HudWidgetWidgetLssPropertyResetter.class */
public class HudWidgetWidgetLssPropertyResetter extends InterpolateWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.InterpolateWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.SimpleWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof HudWidgetWidget) {
        }
        super.reset(widget);
    }
}
