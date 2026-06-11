package net.labymod.autogen.core.lss.properties.accessors;

import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.PriorityLayer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/accessors/AbstractWidgetPriorityLayerPropertyValueAccessor.class */
public final class AbstractWidgetPriorityLayerPropertyValueAccessor implements PropertyValueAccessor<AbstractWidget, PriorityLayer, PriorityLayer> {
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public LssProperty getProperty(AbstractWidget widget) {
        return widget.priorityLayer();
    }

    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Class<?> type() {
        return PriorityLayer.class;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public PriorityLayer[] toArray(Object[] objects) {
        PriorityLayer[] array = new PriorityLayer[objects.length];
        for (int i = 0; i < objects.length; i++) {
            array[i] = (PriorityLayer) objects[i];
        }
        return array;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public PriorityLayer[] toArray(Collection<PriorityLayer> collection) {
        return (PriorityLayer[]) collection.toArray(new PriorityLayer[0]);
    }
}
