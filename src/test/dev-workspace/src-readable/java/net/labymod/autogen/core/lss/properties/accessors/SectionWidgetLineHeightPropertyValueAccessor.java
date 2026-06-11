package net.labymod.autogen.core.lss.properties.accessors;

import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.shop.widgets.SectionedListWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/accessors/SectionWidgetLineHeightPropertyValueAccessor.class */
public final class SectionWidgetLineHeightPropertyValueAccessor implements PropertyValueAccessor<SectionedListWidget.SectionWidget, Float, Float> {
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public LssProperty getProperty(SectionedListWidget.SectionWidget widget) {
        return widget.lineHeight();
    }

    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Class<?> type() {
        return Float.class;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Float[] toArray(Object[] objects) {
        Float[] array = new Float[objects.length];
        for (int i = 0; i < objects.length; i++) {
            array[i] = (Float) objects[i];
        }
        return array;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Float[] toArray(Collection<Float> collection) {
        return (Float[]) collection.toArray(new Float[0]);
    }
}
