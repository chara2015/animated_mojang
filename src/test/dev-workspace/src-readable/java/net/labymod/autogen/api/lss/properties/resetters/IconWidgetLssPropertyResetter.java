package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/IconWidgetLssPropertyResetter.class */
public class IconWidgetLssPropertyResetter extends SimpleWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.SimpleWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof IconWidget) {
            if (((IconWidget) widget).icon() != null) {
                ((IconWidget) widget).icon().reset();
            }
            if (((IconWidget) widget).color() != null) {
                ((IconWidget) widget).color().reset();
            }
            if (((IconWidget) widget).colorTransitionDuration() != null) {
                ((IconWidget) widget).colorTransitionDuration().reset();
            }
            if (((IconWidget) widget).clickable() != null) {
                ((IconWidget) widget).clickable().reset();
            }
            if (((IconWidget) widget).objectFit() != null) {
                ((IconWidget) widget).objectFit().reset();
            }
            if (((IconWidget) widget).cleanupOnDispose() != null) {
                ((IconWidget) widget).cleanupOnDispose().reset();
            }
            if (((IconWidget) widget).zoom() != null) {
                ((IconWidget) widget).zoom().reset();
            }
            if (((IconWidget) widget).blurry() != null) {
                ((IconWidget) widget).blurry().reset();
            }
        }
        super.reset(widget);
    }
}
