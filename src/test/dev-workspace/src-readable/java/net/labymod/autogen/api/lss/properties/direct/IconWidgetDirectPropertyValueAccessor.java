package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.IconWidgetBlurryPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.IconWidgetCleanupOnDisposePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.IconWidgetClickablePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.IconWidgetColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.IconWidgetColorTransitionDurationPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.IconWidgetIconPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.IconWidgetObjectFitPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.IconWidgetZoomPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.resetters.IconWidgetLssPropertyResetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/direct/IconWidgetDirectPropertyValueAccessor.class */
public class IconWidgetDirectPropertyValueAccessor extends SimpleWidgetDirectPropertyValueAccessor {
    protected PropertyValueAccessor<?, ?, ?> icon = new IconWidgetIconPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> color = new IconWidgetColorPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> colorTransitionDuration = new IconWidgetColorTransitionDurationPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> clickable = new IconWidgetClickablePropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> objectFit = new IconWidgetObjectFitPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> cleanupOnDispose = new IconWidgetCleanupOnDisposePropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> zoom = new IconWidgetZoomPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> blurry = new IconWidgetBlurryPropertyValueAccessor();
    LssPropertyResetter IconWidgetResetter = new IconWidgetLssPropertyResetter();

    @Override // net.labymod.autogen.api.lss.properties.direct.SimpleWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String key) {
        switch (key) {
            case "icon":
                return this.icon;
            case "color":
                return this.color;
            case "colorTransitionDuration":
                return this.colorTransitionDuration;
            case "clickable":
                return this.clickable;
            case "objectFit":
                return this.objectFit;
            case "cleanupOnDispose":
                return this.cleanupOnDispose;
            case "zoom":
                return this.zoom;
            case "blurry":
                return this.blurry;
            default:
                return super.getPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.SimpleWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public boolean hasPropertyValueAccessor(String key) {
        switch (key) {
            case "icon":
                return true;
            case "color":
                return true;
            case "colorTransitionDuration":
                return true;
            case "clickable":
                return true;
            case "objectFit":
                return true;
            case "cleanupOnDispose":
                return true;
            case "zoom":
                return true;
            case "blurry":
                return true;
            default:
                return super.hasPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.SimpleWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public LssPropertyResetter getPropertyResetter() {
        return this.IconWidgetResetter;
    }
}
