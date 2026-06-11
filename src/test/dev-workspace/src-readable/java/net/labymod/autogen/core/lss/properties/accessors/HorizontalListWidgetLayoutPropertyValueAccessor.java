package net.labymod.autogen.core.lss.properties.accessors;

import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/accessors/HorizontalListWidgetLayoutPropertyValueAccessor.class */
public final class HorizontalListWidgetLayoutPropertyValueAccessor implements PropertyValueAccessor<HorizontalListWidget, HorizontalListWidget.HorizontalListLayout, HorizontalListWidget.HorizontalListLayout> {
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public LssProperty getProperty(HorizontalListWidget widget) {
        return widget.layout();
    }

    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Class<?> type() {
        return HorizontalListWidget.HorizontalListLayout.class;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public HorizontalListWidget.HorizontalListLayout[] toArray(Object[] objects) {
        HorizontalListWidget.HorizontalListLayout[] array = new HorizontalListWidget.HorizontalListLayout[objects.length];
        for (int i = 0; i < objects.length; i++) {
            array[i] = (HorizontalListWidget.HorizontalListLayout) objects[i];
        }
        return array;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public HorizontalListWidget.HorizontalListLayout[] toArray(Collection<HorizontalListWidget.HorizontalListLayout> collection) {
        return (HorizontalListWidget.HorizontalListLayout[]) collection.toArray(new HorizontalListWidget.HorizontalListLayout[0]);
    }
}
