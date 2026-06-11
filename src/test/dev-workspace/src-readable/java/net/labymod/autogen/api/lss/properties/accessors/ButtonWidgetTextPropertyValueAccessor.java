package net.labymod.autogen.api.lss.properties.accessors;

import java.util.Collection;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/accessors/ButtonWidgetTextPropertyValueAccessor.class */
public final class ButtonWidgetTextPropertyValueAccessor implements PropertyValueAccessor<ButtonWidget, Component, Component> {
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public LssProperty getProperty(ButtonWidget widget) {
        return widget.text();
    }

    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Class<?> type() {
        return Component.class;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Component[] toArray(Object[] objects) {
        Component[] array = new Component[objects.length];
        for (int i = 0; i < objects.length; i++) {
            array[i] = (Component) objects[i];
        }
        return array;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Component[] toArray(Collection<Component> collection) {
        return (Component[]) collection.toArray(new Component[0]);
    }
}
