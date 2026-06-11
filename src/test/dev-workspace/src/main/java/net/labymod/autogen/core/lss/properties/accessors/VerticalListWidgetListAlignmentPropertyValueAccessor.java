package net.labymod.autogen.core.lss.properties.accessors;

import java.util.Collection;
import net.labymod.api.client.gui.VerticalAlignment;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/accessors/VerticalListWidgetListAlignmentPropertyValueAccessor.class */
public final class VerticalListWidgetListAlignmentPropertyValueAccessor implements PropertyValueAccessor<VerticalListWidget, VerticalAlignment, VerticalAlignment> {
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public LssProperty getProperty(VerticalListWidget widget) {
        return widget.listAlignment();
    }

    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Class<?> type() {
        return VerticalAlignment.class;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public VerticalAlignment[] toArray(Object[] objects) {
        VerticalAlignment[] array = new VerticalAlignment[objects.length];
        for (int i = 0; i < objects.length; i++) {
            array[i] = (VerticalAlignment) objects[i];
        }
        return array;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public VerticalAlignment[] toArray(Collection<VerticalAlignment> collection) {
        return (VerticalAlignment[]) collection.toArray(new VerticalAlignment[0]);
    }
}
