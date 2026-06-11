package net.labymod.autogen.api.lss.properties.accessors;

import java.util.Collection;
import net.labymod.api.client.gui.Orientation;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay.selector.SelectorWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/accessors/SelectorWidgetOrientationPropertyValueAccessor.class */
public final class SelectorWidgetOrientationPropertyValueAccessor implements PropertyValueAccessor<SelectorWidget, Orientation, Orientation> {
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public LssProperty getProperty(SelectorWidget widget) {
        return widget.orientation();
    }

    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Class<?> type() {
        return Orientation.class;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Orientation[] toArray(Object[] objects) {
        Orientation[] array = new Orientation[objects.length];
        for (int i = 0; i < objects.length; i++) {
            array[i] = (Orientation) objects[i];
        }
        return array;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Orientation[] toArray(Collection<Orientation> collection) {
        return (Orientation[]) collection.toArray(new Orientation[0]);
    }
}
