package net.labymod.autogen.core.lss.properties.accessors;

import java.util.Collection;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.core.client.gui.screen.widget.widgets.navigation.NavigationListWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/accessors/NavigationListWidgetPriorityAlignmentPropertyValueAccessor.class */
public final class NavigationListWidgetPriorityAlignmentPropertyValueAccessor implements PropertyValueAccessor<NavigationListWidget, HorizontalAlignment, HorizontalAlignment> {
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public LssProperty getProperty(NavigationListWidget widget) {
        return widget.priorityAlignment();
    }

    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Class<?> type() {
        return HorizontalAlignment.class;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public HorizontalAlignment[] toArray(Object[] objects) {
        HorizontalAlignment[] array = new HorizontalAlignment[objects.length];
        for (int i = 0; i < objects.length; i++) {
            array[i] = (HorizontalAlignment) objects[i];
        }
        return array;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public HorizontalAlignment[] toArray(Collection<HorizontalAlignment> collection) {
        return (HorizontalAlignment[]) collection.toArray(new HorizontalAlignment[0]);
    }
}
