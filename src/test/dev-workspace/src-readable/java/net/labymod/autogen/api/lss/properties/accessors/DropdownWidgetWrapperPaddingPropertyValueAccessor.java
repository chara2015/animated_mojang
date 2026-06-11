package net.labymod.autogen.api.lss.properties.accessors;

import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/accessors/DropdownWidgetWrapperPaddingPropertyValueAccessor.class */
public final class DropdownWidgetWrapperPaddingPropertyValueAccessor implements PropertyValueAccessor<DropdownWidget, Float, Float> {
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public LssProperty getProperty(DropdownWidget widget) {
        return widget.wrapperPadding();
    }

    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Class<?> type() {
        return Float.class;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Float[] toArray(Object[] objects) {
        Float[] array = new Float[objects.length];
        for (int i = 0; i < objects.length; i++) {
            array[i] = (Float) objects[i];
        }
        return array;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Float[] toArray(Collection<Float> collection) {
        return (Float[]) collection.toArray(new Float[0]);
    }
}
