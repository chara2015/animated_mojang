package net.labymod.autogen.api.lss.properties.accessors;

import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.api.client.gui.screen.widget.attributes.ObjectFitType;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/accessors/IconWidgetObjectFitPropertyValueAccessor.class */
public final class IconWidgetObjectFitPropertyValueAccessor implements PropertyValueAccessor<IconWidget, ObjectFitType, ObjectFitType> {
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public LssProperty getProperty(IconWidget widget) {
        return widget.objectFit();
    }

    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Class<?> type() {
        return ObjectFitType.class;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public ObjectFitType[] toArray(Object[] objects) {
        ObjectFitType[] array = new ObjectFitType[objects.length];
        for (int i = 0; i < objects.length; i++) {
            array[i] = (ObjectFitType) objects[i];
        }
        return array;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public ObjectFitType[] toArray(Collection<ObjectFitType> collection) {
        return (ObjectFitType[]) collection.toArray(new ObjectFitType[0]);
    }
}
