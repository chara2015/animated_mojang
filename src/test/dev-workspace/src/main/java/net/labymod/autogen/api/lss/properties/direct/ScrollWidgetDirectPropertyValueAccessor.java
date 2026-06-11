package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ScrollWidgetAutoScrollPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ScrollWidgetChildAlignPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ScrollWidgetEnableScrollContentPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ScrollWidgetFixedPositionPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ScrollWidgetModifyContentWidthPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ScrollWidgetMoveDirtBackgroundPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ScrollWidgetScrollAlwaysVisiblePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ScrollWidgetScrollSpeedPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.resetters.ScrollWidgetLssPropertyResetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/direct/ScrollWidgetDirectPropertyValueAccessor.class */
public class ScrollWidgetDirectPropertyValueAccessor extends SimpleWidgetDirectPropertyValueAccessor {
    protected PropertyValueAccessor<?, ?, ?> childAlign = new ScrollWidgetChildAlignPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> enableScrollContent = new ScrollWidgetEnableScrollContentPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> scrollSpeed = new ScrollWidgetScrollSpeedPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> scrollAlwaysVisible = new ScrollWidgetScrollAlwaysVisiblePropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> moveDirtBackground = new ScrollWidgetMoveDirtBackgroundPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> fixedPosition = new ScrollWidgetFixedPositionPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> autoScroll = new ScrollWidgetAutoScrollPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> modifyContentWidth = new ScrollWidgetModifyContentWidthPropertyValueAccessor();
    LssPropertyResetter ScrollWidgetResetter = new ScrollWidgetLssPropertyResetter();

    @Override // net.labymod.autogen.api.lss.properties.direct.SimpleWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String key) {
        switch (key) {
            case "childAlign":
                return this.childAlign;
            case "enableScrollContent":
                return this.enableScrollContent;
            case "scrollSpeed":
                return this.scrollSpeed;
            case "scrollAlwaysVisible":
                return this.scrollAlwaysVisible;
            case "moveDirtBackground":
                return this.moveDirtBackground;
            case "fixedPosition":
                return this.fixedPosition;
            case "autoScroll":
                return this.autoScroll;
            case "modifyContentWidth":
                return this.modifyContentWidth;
            default:
                return super.getPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.SimpleWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public boolean hasPropertyValueAccessor(String key) {
        switch (key) {
            case "childAlign":
                return true;
            case "enableScrollContent":
                return true;
            case "scrollSpeed":
                return true;
            case "scrollAlwaysVisible":
                return true;
            case "moveDirtBackground":
                return true;
            case "fixedPosition":
                return true;
            case "autoScroll":
                return true;
            case "modifyContentWidth":
                return true;
            default:
                return super.hasPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.SimpleWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public LssPropertyResetter getPropertyResetter() {
        return this.ScrollWidgetResetter;
    }
}
