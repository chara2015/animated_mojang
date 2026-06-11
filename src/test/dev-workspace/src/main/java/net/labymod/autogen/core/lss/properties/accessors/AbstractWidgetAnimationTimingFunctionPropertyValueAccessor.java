package net.labymod.autogen.core.lss.properties.accessors;

import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/accessors/AbstractWidgetAnimationTimingFunctionPropertyValueAccessor.class */
public final class AbstractWidgetAnimationTimingFunctionPropertyValueAccessor implements PropertyValueAccessor<AbstractWidget, CubicBezier, CubicBezier> {
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public LssProperty getProperty(AbstractWidget widget) {
        return widget.animationTimingFunction();
    }

    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Class<?> type() {
        return CubicBezier.class;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public CubicBezier[] toArray(Object[] objects) {
        CubicBezier[] array = new CubicBezier[objects.length];
        for (int i = 0; i < objects.length; i++) {
            array[i] = (CubicBezier) objects[i];
        }
        return array;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public CubicBezier[] toArray(Collection<CubicBezier> collection) {
        return (CubicBezier[]) collection.toArray(new CubicBezier[0]);
    }
}
