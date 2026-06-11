package net.labymod.autogen.core.lss.properties.accessors;

import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.WidgetAlignment;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/accessors/AbstractWidgetAlignmentXPropertyValueAccessor.class */
public final class AbstractWidgetAlignmentXPropertyValueAccessor implements PropertyValueAccessor<AbstractWidget, WidgetAlignment, WidgetAlignment> {
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public LssProperty getProperty(AbstractWidget widget) {
        return widget.alignmentX();
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
