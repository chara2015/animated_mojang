package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.core.client.gui.screen.widget.widgets.store.GradientWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/resetters/GradientWidgetLssPropertyResetter.class */
public class GradientWidgetLssPropertyResetter extends SimpleWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.core.lss.properties.resetters.SimpleWidgetLssPropertyResetter, net.labymod.autogen.core.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.core.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof GradientWidget) {
            if (((GradientWidget) widget).direction() != null) {
                ((GradientWidget) widget).direction().reset();
            }
            if (((GradientWidget) widget).colorStart() != null) {
                ((GradientWidget) widget).colorStart().reset();
            }
            if (((GradientWidget) widget).colorEnd() != null) {
                ((GradientWidget) widget).colorEnd().reset();
            }
        }
        super.reset(widget);
    }
}
