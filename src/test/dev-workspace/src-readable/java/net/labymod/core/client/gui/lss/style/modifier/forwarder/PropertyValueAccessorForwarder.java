package net.labymod.core.client.gui.lss.style.modifier.forwarder;

import java.util.Collection;
import java.util.Collections;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.PropertyRegistry;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.api.client.gui.lss.style.modifier.Forwarder;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.WrappedWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/modifier/forwarder/PropertyValueAccessorForwarder.class */
public class PropertyValueAccessorForwarder implements Forwarder {
    private final PropertyRegistry propertyRegistry = Laby.references().propertyRegistry();

    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public boolean requiresForwarding(Widget widget, String key) {
        return true;
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public void forward(Widget widget, String key, ProcessedObject object) throws Exception {
        Collection<?> collectionSingletonList;
        PropertyValueAccessor<?, ?, ?> valueAccessor = this.propertyRegistry.getValueAccessor(widget, key);
        if (valueAccessor == null) {
            return;
        }
        if (object == null) {
            valueAccessor.set(widget, null);
            return;
        }
        Object value = object.value();
        if (valueAccessor.type().isArray()) {
            if (value instanceof Collection) {
                collectionSingletonList = (Collection) value;
            } else {
                collectionSingletonList = Collections.singletonList(value);
            }
            valueAccessor.set(widget, valueAccessor.toArray(collectionSingletonList), object);
            return;
        }
        valueAccessor.set(widget, value, object);
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public void forwardArray(Widget widget, String key, ProcessedObject... objects) throws Exception {
        if (objects.length == 1) {
            forward(widget, key, objects[0]);
            return;
        }
        PropertyValueAccessor<?, ?, ?> valueAccessor = this.propertyRegistry.getValueAccessor(widget, key);
        if (valueAccessor == null) {
            return;
        }
        int length = objects.length;
        Object[] values = new Object[length];
        for (int i = 0; i < length; i++) {
            values[i] = objects[i].value();
        }
        valueAccessor.set(widget, valueAccessor.toArray(values));
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public void reset(Widget widget, String key) {
        PropertyValueAccessor<?, ?, ?> accessor = getAccessor(widget, key);
        if (accessor == null) {
            return;
        }
        accessor.reset(widget instanceof WrappedWidget ? ((WrappedWidget) widget).childWidget() : widget);
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public Class<?> getType(Widget widget, String key, String value) {
        PropertyValueAccessor<?, ?, ?> updater = getAccessor(widget, key);
        if (updater == null) {
            return null;
        }
        return updater.type();
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.Forwarder
    public int getPriority() {
        return 0;
    }

    private PropertyValueAccessor<?, ?, ?> getAccessor(Widget widget, String key) {
        DirectPropertyValueAccessor directAccessor = widget.getDirectPropertyValueAccessor();
        if (directAccessor == null) {
            return null;
        }
        return directAccessor.getPropertyValueAccessor(key);
    }
}
