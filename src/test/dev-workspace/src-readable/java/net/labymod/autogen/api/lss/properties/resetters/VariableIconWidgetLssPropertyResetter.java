package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.VariableIconWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/VariableIconWidgetLssPropertyResetter.class */
public class VariableIconWidgetLssPropertyResetter extends IconWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.IconWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.SimpleWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof VariableIconWidget) {
            if (((VariableIconWidget) widget).iconWidth() != null) {
                ((VariableIconWidget) widget).iconWidth().reset();
            }
            if (((VariableIconWidget) widget).iconHeight() != null) {
                ((VariableIconWidget) widget).iconHeight().reset();
            }
        }
        super.reset(widget);
    }
}
