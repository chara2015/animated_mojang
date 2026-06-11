package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.input.KeybindWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/KeybindWidgetLssPropertyResetter.class */
public class KeybindWidgetLssPropertyResetter extends TextFieldWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.TextFieldWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.SimpleWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof KeybindWidget) {
        }
        super.reset(widget);
    }
}
