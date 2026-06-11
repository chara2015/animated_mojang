package net.labymod.autogen.core.lss.properties.accessors;

import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/accessors/AbstractWidgetRendererPropertyValueAccessor.class */
public final class AbstractWidgetRendererPropertyValueAccessor implements PropertyValueAccessor<AbstractWidget, ThemeRenderer, ThemeRenderer> {
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public LssProperty getProperty(AbstractWidget widget) {
        return widget.renderer();
    }

    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Class<?> type() {
        return ThemeRenderer.class;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public ThemeRenderer[] toArray(Object[] objects) {
        ThemeRenderer[] array = new ThemeRenderer[objects.length];
        for (int i = 0; i < objects.length; i++) {
            array[i] = (ThemeRenderer) objects[i];
        }
        return array;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public ThemeRenderer[] toArray(Collection<ThemeRenderer> collection) {
        return (ThemeRenderer[]) collection.toArray(new ThemeRenderer[0]);
    }
}
