package net.minecraft.world.level.entity;

import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.util.AbortableIterationConsumer;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.phys.AABB;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/entity/LevelEntityGetter.class */
public interface LevelEntityGetter<T extends EntityAccess> {
    T get(int i);

    T get(UUID uuid);

    Iterable<T> getAll();

    <U extends T> void get(EntityTypeTest<T, U> entityTypeTest, AbortableIterationConsumer<U> abortableIterationConsumer);

    void get(AABB aabb, Consumer<T> consumer);

    <U extends T> void get(EntityTypeTest<T, U> entityTypeTest, AABB aabb, AbortableIterationConsumer<U> abortableIterationConsumer);
}
