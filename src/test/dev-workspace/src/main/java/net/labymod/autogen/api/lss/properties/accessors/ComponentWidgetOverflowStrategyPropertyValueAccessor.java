package net.labymod.autogen.api.lss.properties.accessors;

import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.render.font.TextOverflowStrategy;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/accessors/ComponentWidgetOverflowStrategyPropertyValueAccessor.class */
public final class ComponentWidgetOverflowStrategyPropertyValueAccessor implements PropertyValueAccessor<ComponentWidget, TextOverflowStrategy, TextOverflowStrategy> {
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public LssProperty getProperty(ComponentWidget widget) {
        return widget.overflowStrategy();
    }

    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Class<?> type() {
        return TextOverflowStrategy.class;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public TextOverflowStrategy[] toArray(Object[] objects) {
        TextOverflowStrategy[] array = new TextOverflowStrategy[objects.length];
        for (int i = 0; i < objects.length; i++) {
            array[i] = (TextOverflowStrategy) objects[i];
        }
        return array;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public TextOverflowStrategy[] toArray(Collection<TextOverflowStrategy> collection) {
        return (TextOverflowStrategy[]) collection.toArray(new TextOverflowStrategy[0]);
    }
}
