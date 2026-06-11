package net.labymod.autogen.api.lss.properties.accessors;

import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/accessors/IconWidgetColorTransitionDurationPropertyValueAccessor.class */
public final class IconWidgetColorTransitionDurationPropertyValueAccessor implements PropertyValueAccessor<IconWidget, Long, Long> {
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public LssProperty getProperty(IconWidget widget) {
        return widget.colorTransitionDuration();
    }

    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Class<?> type() {
        return Long.class;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Long[] toArray(Object[] objects) {
        Long[] array = new Long[objects.length];
        for (int i = 0; i < objects.length; i++) {
            array[i] = (Long) objects[i];
        }
        return array;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Long[] toArray(Collection<Long> collection) {
        return (Long[]) collection.toArray(new Long[0]);
    }
}
