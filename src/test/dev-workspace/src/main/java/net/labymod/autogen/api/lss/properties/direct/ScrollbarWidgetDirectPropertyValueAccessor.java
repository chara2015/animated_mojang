package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ScrollbarWidgetMinScrollHeightPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ScrollbarWidgetScrollBackgroundColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ScrollbarWidgetScrollButtonClickOffsetPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ScrollbarWidgetScrollColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ScrollbarWidgetScrollHoverColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ScrollbarWidgetUseLssPositionPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.resetters.ScrollbarWidgetLssPropertyResetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/direct/ScrollbarWidgetDirectPropertyValueAccessor.class */
public class ScrollbarWidgetDirectPropertyValueAccessor extends SimpleWidgetDirectPropertyValueAccessor {
    protected PropertyValueAccessor<?, ?, ?> scrollButtonClickOffset = new ScrollbarWidgetScrollButtonClickOffsetPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> useLssPosition = new ScrollbarWidgetUseLssPositionPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> scrollColor = new ScrollbarWidgetScrollColorPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> scrollHoverColor = new ScrollbarWidgetScrollHoverColorPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> scrollBackgroundColor = new ScrollbarWidgetScrollBackgroundColorPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> minScrollHeight = new ScrollbarWidgetMinScrollHeightPropertyValueAccessor();
    LssPropertyResetter ScrollbarWidgetResetter = new ScrollbarWidgetLssPropertyResetter();

    @Override // net.labymod.autogen.api.lss.properties.direct.SimpleWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String key) {
        switch (key) {
            case "scrollButtonClickOffset":
                return this.scrollButtonClickOffset;
            case "useLssPosition":
                return this.useLssPosition;
            case "scrollColor":
                return this.scrollColor;
            case "scrollHoverColor":
                return this.scrollHoverColor;
            case "scrollBackgroundColor":
                return this.scrollBackgroundColor;
            case "minScrollHeight":
                return this.minScrollHeight;
            default:
                return super.getPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.SimpleWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public boolean hasPropertyValueAccessor(String key) {
        switch (key) {
            case "scrollButtonClickOffset":
                return true;
            case "useLssPosition":
                return true;
            case "scrollColor":
                return true;
            case "scrollHoverColor":
                return true;
            case "scrollBackgroundColor":
                return true;
            case "minScrollHeight":
                return true;
            default:
                return super.hasPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.SimpleWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public LssPropertyResetter getPropertyResetter() {
        return this.ScrollbarWidgetResetter;
    }
}
