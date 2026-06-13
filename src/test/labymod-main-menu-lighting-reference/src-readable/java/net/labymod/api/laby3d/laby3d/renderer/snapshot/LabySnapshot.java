package net.labymod.api.laby3d.renderer.snapshot;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/renderer/snapshot/LabySnapshot.class */
public interface LabySnapshot {
    Extras extras();

    default boolean has(ExtraKey<?> key) {
        return extras().has(key);
    }

    default <T> T get(ExtraKey<T> extraKey) {
        return (T) extras().get(extraKey);
    }
}
