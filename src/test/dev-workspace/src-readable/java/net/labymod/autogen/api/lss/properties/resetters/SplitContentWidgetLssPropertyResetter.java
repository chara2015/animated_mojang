package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.SplitContentWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/SplitContentWidgetLssPropertyResetter.class */
public class SplitContentWidgetLssPropertyResetter extends AbstractWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof SplitContentWidget) {
            if (((SplitContentWidget) widget).splitButtonWidth() != null) {
                ((SplitContentWidget) widget).splitButtonWidth().reset();
            }
            if (((SplitContentWidget) widget).splitGapWidth() != null) {
                ((SplitContentWidget) widget).splitGapWidth().reset();
            }
            if (((SplitContentWidget) widget).initialPercentage() != null) {
                ((SplitContentWidget) widget).initialPercentage().reset();
            }
            if (((SplitContentWidget) widget).minPercentage() != null) {
                ((SplitContentWidget) widget).minPercentage().reset();
            }
            if (((SplitContentWidget) widget).maxPercentage() != null) {
                ((SplitContentWidget) widget).maxPercentage().reset();
            }
        }
        super.reset(widget);
    }
}
