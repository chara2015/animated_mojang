package net.labymod.autogen.core.lss.properties.accessors;

import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.api.client.gui.screen.state.offscreen.OffscreenStrategy;
import net.labymod.api.client.gui.screen.widget.widgets.ModelWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/accessors/ModelWidgetSubmissionStrategyPropertyValueAccessor.class */
public final class ModelWidgetSubmissionStrategyPropertyValueAccessor implements PropertyValueAccessor<ModelWidget, OffscreenStrategy, OffscreenStrategy> {
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public LssProperty getProperty(ModelWidget widget) {
        return widget.submissionStrategy();
    }

    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Class<?> type() {
        return OffscreenStrategy.class;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public OffscreenStrategy[] toArray(Object[] objects) {
        OffscreenStrategy[] array = new OffscreenStrategy[objects.length];
        for (int i = 0; i < objects.length; i++) {
            array[i] = (OffscreenStrategy) objects[i];
        }
        return array;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public OffscreenStrategy[] toArray(Collection<OffscreenStrategy> collection) {
        return (OffscreenStrategy[]) collection.toArray(new OffscreenStrategy[0]);
    }
}
