package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ModelWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/resetters/ModelWidgetLssPropertyResetter.class */
public class ModelWidgetLssPropertyResetter extends SimpleWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.core.lss.properties.resetters.SimpleWidgetLssPropertyResetter, net.labymod.autogen.core.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.core.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof ModelWidget) {
            if (((ModelWidget) widget).modelColor() != null) {
                ((ModelWidget) widget).modelColor().reset();
            }
            if (((ModelWidget) widget).baseScale() != null) {
                ((ModelWidget) widget).baseScale().reset();
            }
            if (((ModelWidget) widget).modelOffset() != null) {
                ((ModelWidget) widget).modelOffset().reset();
            }
            if (((ModelWidget) widget).submissionStrategy() != null) {
                ((ModelWidget) widget).submissionStrategy().reset();
            }
        }
        super.reset(widget);
    }
}
