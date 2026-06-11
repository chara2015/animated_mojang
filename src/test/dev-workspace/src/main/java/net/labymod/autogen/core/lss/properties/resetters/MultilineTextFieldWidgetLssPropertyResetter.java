package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.core.client.gui.screen.widget.widgets.MultilineTextFieldWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/resetters/MultilineTextFieldWidgetLssPropertyResetter.class */
public class MultilineTextFieldWidgetLssPropertyResetter extends AbstractWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.core.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.core.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if ((widget instanceof MultilineTextFieldWidget) && ((MultilineTextFieldWidget) widget).textColor() != null) {
            ((MultilineTextFieldWidget) widget).textColor().reset();
        }
        super.reset(widget);
    }
}
