package net.labymod.autogen.api.lss.properties.accessors;

import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.Filter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/accessors/AbstractWidgetFilterPropertyValueAccessor.class */
public final class AbstractWidgetFilterPropertyValueAccessor implements PropertyValueAccessor<AbstractWidget, Filter[], Filter> {
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public LssProperty getProperty(AbstractWidget widget) {
        return widget.filter();
    }

    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Class<?> type() {
        return Filter[].class;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Filter[] toArray(Object[] objects) {
        Filter[] array = new Filter[objects.length];
        for (int i = 0; i < objects.length; i++) {
            array[i] = (Filter) objects[i];
        }
        return array;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Filter[] toArray(Collection<Filter> collection) {
        return (Filter[]) collection.toArray(new Filter[0]);
    }
}
