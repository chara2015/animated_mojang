package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/ComponentWidgetLssPropertyResetter.class */
public class ComponentWidgetLssPropertyResetter extends SimpleWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.SimpleWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof ComponentWidget) {
            if (((ComponentWidget) widget).forceVanillaFont() != null) {
                ((ComponentWidget) widget).forceVanillaFont().reset();
            }
            if (((ComponentWidget) widget).overflowStrategy() != null) {
                ((ComponentWidget) widget).overflowStrategy().reset();
            }
            if (((ComponentWidget) widget).renderHover() != null) {
                ((ComponentWidget) widget).renderHover().reset();
            }
            if (((ComponentWidget) widget).textColor() != null) {
                ((ComponentWidget) widget).textColor().reset();
            }
            if (((ComponentWidget) widget).iconColor() != null) {
                ((ComponentWidget) widget).iconColor().reset();
            }
            if (((ComponentWidget) widget).allowColors() != null) {
                ((ComponentWidget) widget).allowColors().reset();
            }
            if (((ComponentWidget) widget).shadow() != null) {
                ((ComponentWidget) widget).shadow().reset();
            }
            if (((ComponentWidget) widget).lineSpacing() != null) {
                ((ComponentWidget) widget).lineSpacing().reset();
            }
            if (((ComponentWidget) widget).fontSize() != null) {
                ((ComponentWidget) widget).fontSize().reset();
            }
            if (((ComponentWidget) widget).scaleToFit() != null) {
                ((ComponentWidget) widget).scaleToFit().reset();
            }
            if (((ComponentWidget) widget).cache() != null) {
                ((ComponentWidget) widget).cache().reset();
            }
            if (((ComponentWidget) widget).maxLines() != null) {
                ((ComponentWidget) widget).maxLines().reset();
            }
            if (((ComponentWidget) widget).leadingSpaces() != null) {
                ((ComponentWidget) widget).leadingSpaces().reset();
            }
            if (((ComponentWidget) widget).useChatOptions() != null) {
                ((ComponentWidget) widget).useChatOptions().reset();
            }
            if (((ComponentWidget) widget).clippingTextTooltip() != null) {
                ((ComponentWidget) widget).clippingTextTooltip().reset();
            }
            if (((ComponentWidget) widget).maxLinesClipText() != null) {
                ((ComponentWidget) widget).maxLinesClipText().reset();
            }
            if (((ComponentWidget) widget).visualShift() != null) {
                ((ComponentWidget) widget).visualShift().reset();
            }
            if (((ComponentWidget) widget).textColorTransitionDuration() != null) {
                ((ComponentWidget) widget).textColorTransitionDuration().reset();
            }
        }
        super.reset(widget);
    }
}
