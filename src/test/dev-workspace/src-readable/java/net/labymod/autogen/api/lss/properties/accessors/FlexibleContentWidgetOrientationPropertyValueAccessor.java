package net.labymod.autogen.api.lss.properties.accessors;

import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/accessors/FlexibleContentWidgetOrientationPropertyValueAccessor.class */
public final class FlexibleContentWidgetOrientationPropertyValueAccessor implements PropertyValueAccessor<FlexibleContentWidget, FlexibleContentWidget.FlexibleContentOrientation, FlexibleContentWidget.FlexibleContentOrientation> {
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public LssProperty getProperty(FlexibleContentWidget widget) {
        return widget.orientation();
    }

    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Class<?> type() {
        return FlexibleContentWidget.FlexibleContentOrientation.class;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public FlexibleContentWidget.FlexibleContentOrientation[] toArray(Object[] objects) {
        FlexibleContentWidget.FlexibleContentOrientation[] array = new FlexibleContentWidget.FlexibleContentOrientation[objects.length];
        for (int i = 0; i < objects.length; i++) {
            array[i] = (FlexibleContentWidget.FlexibleContentOrientation) objects[i];
        }
        return array;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public FlexibleContentWidget.FlexibleContentOrientation[] toArray(Collection<FlexibleContentWidget.FlexibleContentOrientation> collection) {
        return (FlexibleContentWidget.FlexibleContentOrientation[]) collection.toArray(new FlexibleContentWidget.FlexibleContentOrientation[0]);
    }
}
