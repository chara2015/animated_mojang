package net.labymod.autogen.api.lss.properties.accessors;

import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.api.client.gui.screen.widget.widgets.minecraft.entity.HealthStatusWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/accessors/HealthStatusWidgetHeartSizePropertyValueAccessor.class */
public final class HealthStatusWidgetHeartSizePropertyValueAccessor implements PropertyValueAccessor<HealthStatusWidget, Integer, Integer> {
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public LssProperty getProperty(HealthStatusWidget widget) {
        return widget.heartSize();
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
