package net.labymod.autogen.core.lss.properties.accessors;

import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/accessors/FlexibleContentWidgetSpaceBetweenEntriesPropertyValueAccessor.class */
public final class FlexibleContentWidgetSpaceBetweenEntriesPropertyValueAccessor implements PropertyValueAccessor<FlexibleContentWidget, Integer, Integer> {
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public LssProperty getProperty(FlexibleContentWidget widget) {
        return widget.spaceBetweenEntries();
    }

    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Class<?> type() {
        return Integer.class;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Integer[] toArray(Object[] objects) {
        Integer[] array = new Integer[objects.length];
        for (int i = 0; i < objects.length; i++) {
            array[i] = (Integer) objects[i];
        }
        return array;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Integer[] toArray(Collection<Integer> collection) {
        return (Integer[]) collection.toArray(new Integer[0]);
    }
}
