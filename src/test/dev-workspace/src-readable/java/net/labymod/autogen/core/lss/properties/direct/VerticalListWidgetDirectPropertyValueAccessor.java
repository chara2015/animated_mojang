package net.labymod.autogen.core.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.VerticalListWidgetListAlignmentPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.VerticalListWidgetListOrderPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.VerticalListWidgetOverwriteWidthPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.VerticalListWidgetRenderOutOfBoundsPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.VerticalListWidgetSelectablePropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.VerticalListWidgetSpaceBetweenEntriesPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.VerticalListWidgetSqueezeHeightPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.resetters.VerticalListWidgetLssPropertyResetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/direct/VerticalListWidgetDirectPropertyValueAccessor.class */
public class VerticalListWidgetDirectPropertyValueAccessor extends SessionedListWidgetDirectPropertyValueAccessor {
    protected PropertyValueAccessor<?, ?, ?> overwriteWidth = new VerticalListWidgetOverwriteWidthPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> squeezeHeight = new VerticalListWidgetSqueezeHeightPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> selectable = new VerticalListWidgetSelectablePropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> spaceBetweenEntries = new VerticalListWidgetSpaceBetweenEntriesPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> renderOutOfBounds = new VerticalListWidgetRenderOutOfBoundsPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> listAlignment = new VerticalListWidgetListAlignmentPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> listOrder = new VerticalListWidgetListOrderPropertyValueAccessor();
    LssPropertyResetter VerticalListWidgetResetter = new VerticalListWidgetLssPropertyResetter();

    @Override // net.labymod.autogen.core.lss.properties.direct.SessionedListWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.ListWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String key) {
        switch (key) {
            case "overwriteWidth":
                return this.overwriteWidth;
            case "squeezeHeight":
                return this.squeezeHeight;
            case "selectable":
                return this.selectable;
            case "spaceBetweenEntries":
                return this.spaceBetweenEntries;
            case "renderOutOfBounds":
                return this.renderOutOfBounds;
            case "listAlignment":
                return this.listAlignment;
            case "listOrder":
                return this.listOrder;
            default:
                return super.getPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.core.lss.properties.direct.SessionedListWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.ListWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public boolean hasPropertyValueAccessor(String key) {
        switch (key) {
            case "overwriteWidth":
                return true;
            case "squeezeHeight":
                return true;
            case "selectable":
                return true;
            case "spaceBetweenEntries":
                return true;
            case "renderOutOfBounds":
                return true;
            case "listAlignment":
                return true;
            case "listOrder":
                return true;
            default:
                return super.hasPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.core.lss.properties.direct.SessionedListWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.ListWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.core.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public LssPropertyResetter getPropertyResetter() {
        return this.VerticalListWidgetResetter;
    }
}
