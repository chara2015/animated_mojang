package net.minecraft.core.component;

import java.util.stream.Stream;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/component/DataComponentHolder.class */
public interface DataComponentHolder extends DataComponentGetter {
    DataComponentMap getComponents();

    @Override // net.minecraft.core.component.DataComponentGetter
    default <T> T get(DataComponentType<? extends T> dataComponentType) {
        return (T) getComponents().get(dataComponentType);
    }

    default <T> Stream<T> getAllOfType(Class<? extends T> $$0) {
        return getComponents().stream().map((v0) -> {
            return v0.value();
        }).filter($$1 -> {
            return $$0.isAssignableFrom($$1.getClass());
        }).map($$02 -> {
            return $$02;
        });
    }

    @Override // net.minecraft.core.component.DataComponentGetter
    default <T> T getOrDefault(DataComponentType<? extends T> dataComponentType, T t) {
        return (T) getComponents().getOrDefault(dataComponentType, t);
    }

    default boolean has(DataComponentType<?> $$0) {
        return getComponents().has($$0);
    }
}
