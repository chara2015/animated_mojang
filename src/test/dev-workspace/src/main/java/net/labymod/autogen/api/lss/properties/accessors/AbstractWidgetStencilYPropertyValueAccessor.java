package net.labymod.autogen.api.lss.properties.accessors;

import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/accessors/AbstractWidgetStencilYPropertyValueAccessor.class */
public final class AbstractWidgetStencilYPropertyValueAccessor implements PropertyValueAccessor<AbstractWidget, Boolean, Boolean> {
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public LssProperty getProperty(AbstractWidget widget) {
        return widget.stencilY();
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
