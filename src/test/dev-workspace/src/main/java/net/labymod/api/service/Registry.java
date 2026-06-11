package net.labymod.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.labymod.api.util.KeyValue;
import net.labymod.api.util.function.Consumers;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/service/Registry.class */
public interface Registry<T> {
    List<KeyValue<T>> getElements();

    default List<T> values() {
        List<T> values = new ArrayList<>();
        for (KeyValue<T> element : getElements()) {
            values.add(element.getValue());
        }
        return values;
    }

    default void register(List<? extends T> elements) {
        for (T element : elements) {
            register(element);
        }
    }

    default void register(T element) {
        String key = element.toString();
        if (element instanceof Identifiable) {
            key = ((Identifiable) element).getId();
        }
        register(key, element);
    }

    default void register(Supplier<T> elementSupplier, Consumer<Throwable> errorHandler) {
        try {
            register(elementSupplier.get());
        } catch (Throwable throwable) {
            errorHandler.accept(throwable);
        }
    }

    default void register(String id, T element) {
        getElements().add(new KeyValue<>(id, element));
    }

    default T getOrRegister(String id, T element) {
        KeyValue<T> value = getElementById(id);
        if (value == null) {
            register(id, element);
            return element;
        }
        return value.getValue();
    }

    default void registerAfter(String afterId, T element) {
        String id = element.toString();
        if (element instanceof Identifiable) {
            id = ((Identifiable) element).getId();
        }
        registerAfter(afterId, id, element);
    }

    default void registerAfter(String afterId, String id, T element) {
        getElements().add(indexOf(afterId) + 1, new KeyValue<>(id, element));
    }

    default T getOrRegisterAfter(String afterId, String id, T element) {
        KeyValue<T> value = getElementById(id);
        if (value == null) {
            registerAfter(afterId, id, element);
            return element;
        }
        return value.getValue();
    }

    default void registerBefore(String beforeId, T element) {
        String id = element.toString();
        if (element instanceof Identifiable) {
            id = ((Identifiable) element).getId();
        }
        registerBefore(beforeId, id, element);
    }

    default void registerBefore(String beforeId, String id, T element) {
        getElements().add(indexOf(beforeId), new KeyValue<>(id, element));
    }

    default T getOrRegisterBefore(String beforeId, String id, T element) {
        KeyValue<T> value = getElementById(id);
        if (value == null) {
            registerBefore(beforeId, id, element);
            return element;
        }
        return value.getValue();
    }

    default void unregister(String id) {
        KeyValue<T> element = getElementById(id);
        if (element != null) {
            getElements().remove(element);
        }
    }

    default void unregister(Predicate<KeyValue<T>> predicate) {
        getElements().removeIf(predicate);
    }

    default void unregisterByClassLoader(ClassLoader classLoader) {
        getElements().removeIf(entry -> {
            return entry.getValue().getClass().getClassLoader().equals(classLoader);
        });
    }

    default int indexOf(String id) {
        KeyValue<T> element = getElementById(id);
        if (element == null) {
            return 0;
        }
        return getElements().indexOf(element);
    }

    default int indexOf(T value) {
        if (value == null) {
            return 0;
        }
        return values().indexOf(value);
    }

    @Nullable
    default KeyValue<T> getElementById(String id) {
        for (KeyValue<T> element : getElements()) {
            if (element.getKey().equals(id)) {
                return element;
            }
        }
        return null;
    }

    default T getById(String id) {
        KeyValue<T> element = getElementById(id);
        if (element == null) {
            return null;
        }
        return element.getValue();
    }

    default Optional<T> getOptionalById(String id) {
        for (KeyValue<T> element : getElements()) {
            if (element.getKey().equals(id)) {
                return Optional.of(element.getValue());
            }
        }
        return Optional.empty();
    }

    default void forEach(Consumer<T> consumer) {
        for (KeyValue<T> element : getElements()) {
            Consumers.accept(consumer, element.getValue());
        }
    }

    default String getId(T target) {
        for (KeyValue<T> element : getElements()) {
            if (element.getValue() == target) {
                return element.getKey();
            }
        }
        return null;
    }
}
