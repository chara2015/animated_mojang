package net.labymod.autogen.api.lss.properties.accessors;

import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.api.client.gui.screen.widget.widgets.layout.grid.LazyGridFeedWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/accessors/LazyGridFeedWidgetLoadingTextPropertyValueAccessor.class */
public final class LazyGridFeedWidgetLoadingTextPropertyValueAccessor implements PropertyValueAccessor<LazyGridFeedWidget, String, String> {
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public LssProperty getProperty(LazyGridFeedWidget widget) {
        return widget.loadingText();
    }

    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Class<?> type() {
        return String.class;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public String[] toArray(Object[] objects) {
        String[] array = new String[objects.length];
        for (int i = 0; i < objects.length; i++) {
            array[i] = (String) objects[i];
        }
        return array;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public String[] toArray(Collection<String> collection) {
        return (String[]) collection.toArray(new String[0]);
    }
}
