package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.OverlayWidgetPlayInteractSoundPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.OverlayWidgetStrategyXPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.OverlayWidgetStrategyYPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.resetters.OverlayWidgetLssPropertyResetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/direct/OverlayWidgetDirectPropertyValueAccessor.class */
public class OverlayWidgetDirectPropertyValueAccessor extends AbstractWidgetDirectPropertyValueAccessor {
    protected PropertyValueAccessor<?, ?, ?> strategyX = new OverlayWidgetStrategyXPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> strategyY = new OverlayWidgetStrategyYPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> playInteractSound = new OverlayWidgetPlayInteractSoundPropertyValueAccessor();
    LssPropertyResetter OverlayWidgetResetter = new OverlayWidgetLssPropertyResetter();

    @Override // net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String key) {
        switch (key) {
            case "strategyX":
                return this.strategyX;
            case "strategyY":
                return this.strategyY;
            case "playInteractSound":
                return this.playInteractSound;
            default:
                return super.getPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public boolean hasPropertyValueAccessor(String key) {
        switch (key) {
            case "strategyX":
                return true;
            case "strategyY":
                return true;
            case "playInteractSound":
                return true;
            default:
                return super.hasPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.AbstractWidgetDirectPropertyValueAccessor, net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public LssPropertyResetter getPropertyResetter() {
        return this.OverlayWidgetResetter;
    }
}
