package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.RatingWidgetDisplayEmptyRatingPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.RatingWidgetDisplayExactRatingPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.RatingWidgetEmptyColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.RatingWidgetFillColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.RatingWidgetHoverColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.RatingWidgetSpaceBetweenRatingsPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.resetters.RatingWidgetLssPropertyResetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/direct/RatingWidgetDirectPropertyValueAccessor.class */
public class RatingWidgetDirectPropertyValueAccessor extends SimpleWidgetDirectPropertyValueAccessor {
    protected PropertyValueAccessor<?, ?, ?> displayExactRating = new RatingWidgetDisplayExactRatingPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> displayEmptyRating = new RatingWidgetDisplayEmptyRatingPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> spaceBetweenRatings = new RatingWidgetSpaceBetweenRatingsPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> fillColor = new RatingWidgetFillColorPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> emptyColor = new RatingWidgetEmptyColorPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> hoverColor = new RatingWidgetHoverColorPropertyValueAccessor();
    LssPropertyResetter RatingWidgetResetter = new RatingWidgetLssPropertyResetter();

    @Override // net.labymod.autogen.api.lss.properties.direct.SimpleWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String key) {
        switch (key) {
            case "displayExactRating":
                return this.displayExactRating;
            case "displayEmptyRating":
                return this.displayEmptyRating;
            case "spaceBetweenRatings":
                return this.spaceBetweenRatings;
            case "fillColor":
                return this.fillColor;
            case "emptyColor":
                return this.emptyColor;
            case "hoverColor":
                return this.hoverColor;
            default:
                return super.getPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.SimpleWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public boolean hasPropertyValueAccessor(String key) {
        switch (key) {
            case "displayExactRating":
                return true;
            case "displayEmptyRating":
                return true;
            case "spaceBetweenRatings":
                return true;
            case "fillColor":
                return true;
            case "emptyColor":
                return true;
            case "hoverColor":
                return true;
            default:
                return super.hasPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.SimpleWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public LssPropertyResetter getPropertyResetter() {
        return this.RatingWidgetResetter;
    }
}
