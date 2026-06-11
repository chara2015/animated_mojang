package net.labymod.autogen.core.lss.properties.accessors;

import java.util.Collection;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/accessors/ButtonWidgetIconPropertyValueAccessor.class */
public final class ButtonWidgetIconPropertyValueAccessor implements PropertyValueAccessor<ButtonWidget, Icon, Icon> {
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public LssProperty getProperty(ButtonWidget widget) {
        return widget.icon();
    }

    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Class<?> type() {
        return Icon.class;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Icon[] toArray(Object[] objects) {
        Icon[] array = new Icon[objects.length];
        for (int i = 0; i < objects.length; i++) {
            array[i] = (Icon) objects[i];
        }
        return array;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Icon[] toArray(Collection<Icon> collection) {
        return (Icon[]) collection.toArray(new Icon[0]);
    }
}
