package net.labymod.api.client.gui.lss.property;

import java.util.Collection;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.screen.widget.Widget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/lss/property/PropertyValueAccessor.class */
public interface PropertyValueAccessor<W extends Widget, V, A> {
    LssProperty<V> getProperty(W w);

    A[] toArray(Object[] objArr);

    A[] toArray(Collection<A> collection);

    default void set(W widget, V value) {
        getProperty(widget).set(value);
    }

    default void set(W widget, V value, ProcessedObject processedValue) {
        getProperty(widget).set(value, processedValue);
    }

    default void reset(W widget) {
        getProperty(widget).reset();
    }

    default V get(W widget) {
        return getProperty(widget).get();
    }

    default Class<?> type() {
        return null;
    }
}
