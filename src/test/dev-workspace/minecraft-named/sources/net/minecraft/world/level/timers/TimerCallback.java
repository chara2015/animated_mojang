package net.minecraft.world.level.timers;

import com.mojang.serialization.MapCodec;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/timers/TimerCallback.class */
public interface TimerCallback<T> {
    void handle(T t, TimerQueue<T> timerQueue, long j);

    MapCodec<? extends TimerCallback<T>> codec();
}
