package net.labymod.autogen.api.lss.properties.accessors;

import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.api.client.gui.screen.widget.widgets.overlay.OverlayPositionStrategy;
import net.labymod.api.client.gui.screen.widget.widgets.overlay.OverlayWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/accessors/OverlayWidgetStrategyXPropertyValueAccessor.class */
public final class OverlayWidgetStrategyXPropertyValueAccessor implements PropertyValueAccessor<OverlayWidget, OverlayPositionStrategy, OverlayPositionStrategy> {
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public LssProperty getProperty(OverlayWidget widget) {
        return widget.strategyX();
    }

    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Class<?> type() {
        return OverlayPositionStrategy.class;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public OverlayPositionStrategy[] toArray(Object[] objects) {
        OverlayPositionStrategy[] array = new OverlayPositionStrategy[objects.length];
        for (int i = 0; i < objects.length; i++) {
            array[i] = (OverlayPositionStrategy) objects[i];
        }
        return array;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public OverlayPositionStrategy[] toArray(Collection<OverlayPositionStrategy> collection) {
        return (OverlayPositionStrategy[]) collection.toArray(new OverlayPositionStrategy[0]);
    }
}
