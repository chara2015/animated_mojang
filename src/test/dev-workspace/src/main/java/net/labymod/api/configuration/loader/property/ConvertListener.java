package net.labymod.api.configuration.loader.property;

import java.util.function.Predicate;
import net.labymod.api.property.Property;
import net.labymod.api.util.function.ChangeListener;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/loader/property/ConvertListener.class */
public class ConvertListener<T> implements ChangeListener<Property<T>, T> {
    private final ConfigProperty<?> newProperty;
    private final Predicate<T> predicate;

    private ConvertListener(ConfigProperty<?> newProperty, Predicate<T> predicate) {
        this.newProperty = newProperty;
        this.predicate = predicate;
    }

    public static <T> ConvertListener<T> of(ConfigProperty<?> newProperty, Predicate<T> predicate) {
        return new ConvertListener<>(newProperty, predicate);
    }

    @Override // net.labymod.api.util.function.ChangeListener
    public void changed(Property<T> property, T oldValue, T newValue) {
        if (!property.isDefaultValue(newValue) && this.newProperty.isDefaultValue() && this.predicate.test(newValue)) {
            property.reset();
        }
    }
}
