package net.labymod.autogen.core.lss.properties.accessors;

import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/accessors/AbstractWidgetDestroyDelayPropertyValueAccessor.class */
public final class AbstractWidgetDestroyDelayPropertyValueAccessor implements PropertyValueAccessor<AbstractWidget, Long, Long> {
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public LssProperty getProperty(AbstractWidget widget) {
        return widget.destroyDelay();
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
