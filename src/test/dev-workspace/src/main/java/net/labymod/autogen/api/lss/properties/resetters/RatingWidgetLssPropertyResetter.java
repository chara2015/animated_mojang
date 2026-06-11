package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.RatingWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/resetters/RatingWidgetLssPropertyResetter.class */
public class RatingWidgetLssPropertyResetter extends SimpleWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.api.lss.properties.resetters.SimpleWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter, net.labymod.autogen.api.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof RatingWidget) {
            if (((RatingWidget) widget).displayExactRating() != null) {
                ((RatingWidget) widget).displayExactRating().reset();
            }
            if (((RatingWidget) widget).displayEmptyRating() != null) {
                ((RatingWidget) widget).displayEmptyRating().reset();
            }
            if (((RatingWidget) widget).spaceBetweenRatings() != null) {
                ((RatingWidget) widget).spaceBetweenRatings().reset();
            }
            if (((RatingWidget) widget).fillColor() != null) {
                ((RatingWidget) widget).fillColor().reset();
            }
            if (((RatingWidget) widget).emptyColor() != null) {
                ((RatingWidget) widget).emptyColor().reset();
            }
            if (((RatingWidget) widget).hoverColor() != null) {
                ((RatingWidget) widget).hoverColor().reset();
            }
        }
        super.reset(widget);
    }
}
