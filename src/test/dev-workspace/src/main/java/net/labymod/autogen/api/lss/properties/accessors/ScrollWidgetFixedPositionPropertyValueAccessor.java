package net.labymod.autogen.api.lss.properties.accessors;

import java.util.Collection;
import net.labymod.api.client.gui.VerticalAlignment;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/accessors/ScrollWidgetFixedPositionPropertyValueAccessor.class */
public final class ScrollWidgetFixedPositionPropertyValueAccessor implements PropertyValueAccessor<ScrollWidget, VerticalAlignment, VerticalAlignment> {
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public LssProperty getProperty(ScrollWidget widget) {
        return widget.fixedPosition();
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
