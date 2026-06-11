package net.labymod.autogen.api.lss.properties.accessors;

import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.render.font.FontSize;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/accessors/ComponentWidgetFontSizePropertyValueAccessor.class */
public final class ComponentWidgetFontSizePropertyValueAccessor implements PropertyValueAccessor<ComponentWidget, FontSize, FontSize> {
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public LssProperty getProperty(ComponentWidget widget) {
        return widget.fontSize();
    }

    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Class<?> type() {
        return FontSize.class;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public FontSize[] toArray(Object[] objects) {
        FontSize[] array = new FontSize[objects.length];
        for (int i = 0; i < objects.length; i++) {
            array[i] = (FontSize) objects[i];
        }
        return array;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public FontSize[] toArray(Collection<FontSize> collection) {
        return (FontSize[]) collection.toArray(new FontSize[0]);
    }
}
