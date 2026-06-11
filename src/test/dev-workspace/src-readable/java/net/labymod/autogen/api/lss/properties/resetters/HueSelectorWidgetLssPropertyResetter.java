package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay.selector.HueSelectorWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/HueSelectorWidgetLssPropertyResetter.class */
public class HueSelectorWidgetLssPropertyResetter extends SelectorWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.SelectorWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof HueSelectorWidget) {
        }
        super.reset(widget);
    }
}
