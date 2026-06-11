package net.minecraft.core.component;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/component/DataComponentGetter.class */
public interface DataComponentGetter {
    <T> T get(DataComponentType<? extends T> dataComponentType);

    default <T> T getOrDefault(DataComponentType<? extends T> dataComponentType, T t) {
        T t2 = (T) get(dataComponentType);
        return t2 != null ? t2 : t;
    }

    default <T> TypedDataComponent<T> getTyped(DataComponentType<T> $$0) {
        Object obj = get($$0);
        if (obj != null) {
            return new TypedDataComponent<>($$0, obj);
        }
        return null;
    }
}
