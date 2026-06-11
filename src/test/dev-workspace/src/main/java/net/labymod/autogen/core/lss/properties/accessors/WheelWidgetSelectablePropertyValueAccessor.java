package net.labymod.autogen.core.lss.properties.accessors;

import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.api.client.gui.screen.widget.widgets.WheelWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/accessors/WheelWidgetSelectablePropertyValueAccessor.class */
public final class WheelWidgetSelectablePropertyValueAccessor implements PropertyValueAccessor<WheelWidget, Boolean, Boolean> {
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public LssProperty getProperty(WheelWidget widget) {
        return widget.selectable();
    }

    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Class<?> type() {
        return Boolean.class;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Boolean[] toArray(Object[] objects) {
        Boolean[] array = new Boolean[objects.length];
        for (int i = 0; i < objects.length; i++) {
            array[i] = (Boolean) objects[i];
        }
        return array;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Boolean[] toArray(Collection<Boolean> collection) {
        return (Boolean[]) collection.toArray(new Boolean[0]);
    }
}
