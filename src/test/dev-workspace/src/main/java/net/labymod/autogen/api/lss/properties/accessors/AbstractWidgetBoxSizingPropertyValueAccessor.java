package net.labymod.autogen.api.lss.properties.accessors;

import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.BoxSizing;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/accessors/AbstractWidgetBoxSizingPropertyValueAccessor.class */
public final class AbstractWidgetBoxSizingPropertyValueAccessor implements PropertyValueAccessor<AbstractWidget, BoxSizing, BoxSizing> {
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public LssProperty getProperty(AbstractWidget widget) {
        return widget.boxSizing();
    }

    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Class<?> type() {
        return BoxSizing.class;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public BoxSizing[] toArray(Object[] objects) {
        BoxSizing[] array = new BoxSizing[objects.length];
        for (int i = 0; i < objects.length; i++) {
            array[i] = (BoxSizing) objects[i];
        }
        return array;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public BoxSizing[] toArray(Collection<BoxSizing> collection) {
        return (BoxSizing[]) collection.toArray(new BoxSizing[0]);
    }
}
