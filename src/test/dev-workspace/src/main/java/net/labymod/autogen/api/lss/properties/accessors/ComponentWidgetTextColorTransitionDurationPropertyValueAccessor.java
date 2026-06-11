package net.labymod.autogen.api.lss.properties.accessors;

import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/accessors/ComponentWidgetTextColorTransitionDurationPropertyValueAccessor.class */
public final class ComponentWidgetTextColorTransitionDurationPropertyValueAccessor implements PropertyValueAccessor<ComponentWidget, Long, Long> {
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public LssProperty getProperty(ComponentWidget widget) {
        return widget.textColorTransitionDuration();
    }

    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Class<?> type() {
        return Long.class;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Long[] toArray(Object[] objects) {
        Long[] array = new Long[objects.length];
        for (int i = 0; i < objects.length; i++) {
            array[i] = (Long) objects[i];
        }
        return array;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Long[] toArray(Collection<Long> collection) {
        return (Long[]) collection.toArray(new Long[0]);
    }
}
