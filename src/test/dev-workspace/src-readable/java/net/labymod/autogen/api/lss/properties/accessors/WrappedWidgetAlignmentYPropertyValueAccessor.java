package net.labymod.autogen.api.lss.properties.accessors;

import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.api.client.gui.screen.widget.WrappedWidget;
import net.labymod.api.client.gui.screen.widget.attributes.WidgetAlignment;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/accessors/WrappedWidgetAlignmentYPropertyValueAccessor.class */
public final class WrappedWidgetAlignmentYPropertyValueAccessor implements PropertyValueAccessor<WrappedWidget, WidgetAlignment, WidgetAlignment> {
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public LssProperty getProperty(WrappedWidget widget) {
        return widget.alignmentY();
    }

    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Class<?> type() {
        return WidgetAlignment.class;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public WidgetAlignment[] toArray(Object[] objects) {
        WidgetAlignment[] array = new WidgetAlignment[objects.length];
        for (int i = 0; i < objects.length; i++) {
            array[i] = (WidgetAlignment) objects[i];
        }
        return array;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public WidgetAlignment[] toArray(Collection<WidgetAlignment> collection) {
        return (WidgetAlignment[]) collection.toArray(new WidgetAlignment[0]);
    }
}
