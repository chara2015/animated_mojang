package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.LazyGridFeedWidgetLoadingColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.LazyGridFeedWidgetLoadingTextGapPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.LazyGridFeedWidgetLoadingTextPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.LazyGridFeedWidgetRefreshRadiusPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.LazyGridFeedWidgetRemoveLoadingTextPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.resetters.LazyGridFeedWidgetLssPropertyResetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/direct/LazyGridFeedWidgetDirectPropertyValueAccessor.class */
public class LazyGridFeedWidgetDirectPropertyValueAccessor extends LazyGridWidgetDirectPropertyValueAccessor {
    protected PropertyValueAccessor<?, ?, ?> refreshRadius = new LazyGridFeedWidgetRefreshRadiusPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> loadingTextGap = new LazyGridFeedWidgetLoadingTextGapPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> loadingText = new LazyGridFeedWidgetLoadingTextPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> loadingColor = new LazyGridFeedWidgetLoadingColorPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> removeLoadingText = new LazyGridFeedWidgetRemoveLoadingTextPropertyValueAccessor();
    LssPropertyResetter LazyGridFeedWidgetResetter = new LazyGridFeedWidgetLssPropertyResetter();

    @Override // net.labymod.autogen.api.lss.properties.direct.LazyGridWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.SessionedListWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.ListWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String key) {
        switch (key) {
            case "refreshRadius":
                return this.refreshRadius;
            case "loadingTextGap":
                return this.loadingTextGap;
            case "loadingText":
                return this.loadingText;
            case "loadingColor":
                return this.loadingColor;
            case "removeLoadingText":
                return this.removeLoadingText;
            default:
                return super.getPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.LazyGridWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.SessionedListWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.ListWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public boolean hasPropertyValueAccessor(String key) {
        switch (key) {
            case "refreshRadius":
                return true;
            case "loadingTextGap":
                return true;
            case "loadingText":
                return true;
            case "loadingColor":
                return true;
            case "removeLoadingText":
                return true;
            default:
                return super.hasPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.LazyGridWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.SessionedListWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.ListWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public LssPropertyResetter getPropertyResetter() {
        return this.LazyGridFeedWidgetResetter;
    }
}
