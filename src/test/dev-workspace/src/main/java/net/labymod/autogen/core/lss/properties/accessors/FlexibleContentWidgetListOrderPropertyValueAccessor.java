package net.labymod.autogen.core.lss.properties.accessors;

import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.util.ListOrder;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/accessors/FlexibleContentWidgetListOrderPropertyValueAccessor.class */
public final class FlexibleContentWidgetListOrderPropertyValueAccessor implements PropertyValueAccessor<FlexibleContentWidget, ListOrder, ListOrder> {
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public LssProperty getProperty(FlexibleContentWidget widget) {
        return widget.listOrder();
    }

    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Class<?> type() {
        return ListOrder.class;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public ListOrder[] toArray(Object[] objects) {
        ListOrder[] array = new ListOrder[objects.length];
        for (int i = 0; i < objects.length; i++) {
            array[i] = (ListOrder) objects[i];
        }
        return array;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public ListOrder[] toArray(Collection<ListOrder> collection) {
        return (ListOrder[]) collection.toArray(new ListOrder[0]);
    }
}
