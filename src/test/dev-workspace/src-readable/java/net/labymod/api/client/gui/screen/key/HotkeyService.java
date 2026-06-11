package net.labymod.api.client.gui.screen.key;

import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/key/HotkeyService.class */
@Referenceable
public interface HotkeyService {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/key/HotkeyService$Type.class */
    public enum Type {
        TOGGLE,
        HOLD
    }

    void register(String str, BooleanSupplier booleanSupplier, Supplier<Key> supplier, Supplier<Type> supplier2, Consumer<Boolean> consumer);

    boolean unregister(String str);

    default void register(String identifier, Supplier<Key> keySupplier, Supplier<Type> typeSupplier, Consumer<Boolean> active) {
        register(identifier, () -> {
            return true;
        }, keySupplier, typeSupplier, active);
    }

    default void register(String identifier, ConfigProperty<? extends Key> keyConfigProperty, Supplier<Type> typeSupplier, Consumer<Boolean> active) {
        Objects.requireNonNull(keyConfigProperty);
        register(identifier, keyConfigProperty::get, typeSupplier, active);
    }

    default void register(String identifier, ConfigProperty<? extends Key> keyConfigProperty, BooleanSupplier condition, Supplier<Type> typeSupplier, Consumer<Boolean> active) {
        Objects.requireNonNull(keyConfigProperty);
        register(identifier, condition, keyConfigProperty::get, typeSupplier, active);
    }
}
