package net.labymod.autogen.core.lss.properties.accessors;

import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.DirtBackgroundType;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/accessors/AbstractWidgetBackgroundDirtTypePropertyValueAccessor.class */
public final class AbstractWidgetBackgroundDirtTypePropertyValueAccessor implements PropertyValueAccessor<AbstractWidget, DirtBackgroundType, DirtBackgroundType> {
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public LssProperty getProperty(AbstractWidget widget) {
        return widget.backgroundDirtType();
    }

    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Class<?> type() {
        return DirtBackgroundType.class;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public DirtBackgroundType[] toArray(Object[] objects) {
        DirtBackgroundType[] array = new DirtBackgroundType[objects.length];
        for (int i = 0; i < objects.length; i++) {
            array[i] = (DirtBackgroundType) objects[i];
        }
        return array;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public DirtBackgroundType[] toArray(Collection<DirtBackgroundType> collection) {
        return (DirtBackgroundType[]) collection.toArray(new DirtBackgroundType[0]);
    }
}
