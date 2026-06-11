package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.grid.LazyGridFeedWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/LazyGridFeedWidgetLssPropertyResetter.class */
public class LazyGridFeedWidgetLssPropertyResetter extends LazyGridWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.LazyGridWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.SessionedListWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.ListWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof LazyGridFeedWidget) {
            if (((LazyGridFeedWidget) widget).refreshRadius() != null) {
                ((LazyGridFeedWidget) widget).refreshRadius().reset();
            }
            if (((LazyGridFeedWidget) widget).loadingTextGap() != null) {
                ((LazyGridFeedWidget) widget).loadingTextGap().reset();
            }
            if (((LazyGridFeedWidget) widget).loadingText() != null) {
                ((LazyGridFeedWidget) widget).loadingText().reset();
            }
            if (((LazyGridFeedWidget) widget).loadingColor() != null) {
                ((LazyGridFeedWidget) widget).loadingColor().reset();
            }
            if (((LazyGridFeedWidget) widget).removeLoadingText() != null) {
                ((LazyGridFeedWidget) widget).removeLoadingText().reset();
            }
        }
        super.reset(widget);
    }
}
