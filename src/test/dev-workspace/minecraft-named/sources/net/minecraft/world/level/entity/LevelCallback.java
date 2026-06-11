package net.minecraft.world.level.entity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/entity/LevelCallback.class */
public interface LevelCallback<T> {
    void onCreated(T t);

    void onDestroyed(T t);

    void onTickingStart(T t);

    void onTickingEnd(T t);

    void onTrackingStart(T t);

    void onTrackingEnd(T t);

    void onSectionChange(T t);
}
