package net.labymod.api.client.gui.screen.widget.attributes.animation;

import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.api.client.gui.screen.widget.Widget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/attributes/animation/PropertyValueAccessorAttributeAnimationAccessor.class */
public class PropertyValueAccessorAttributeAnimationAccessor<T> implements AttributeAnimationAccessor<T> {
    private final Widget widget;
    private final PropertyValueAccessor accessor;
    private final String attributeName;

    public PropertyValueAccessorAttributeAnimationAccessor(Widget widget, PropertyValueAccessor accessor, String attributeName) {
        this.widget = widget;
        this.accessor = accessor;
        this.attributeName = attributeName;
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.animation.AttributeAnimationAccessor
    public T get() {
        return (T) this.accessor.get(this.widget);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.animation.AttributeAnimationAccessor
    public void set(T value) {
        this.accessor.set(this.widget, value);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.animation.AttributeAnimationAccessor
    public String getName() {
        return this.attributeName;
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.animation.AttributeAnimationAccessor
    public Class<T> getType() {
        return (Class<T>) this.accessor.type();
    }
}
