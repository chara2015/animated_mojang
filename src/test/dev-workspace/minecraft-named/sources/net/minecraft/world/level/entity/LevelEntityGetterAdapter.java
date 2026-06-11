package net.minecraft.world.level.entity;

import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.util.AbortableIterationConsumer;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.phys.AABB;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/entity/LevelEntityGetterAdapter.class */
public class LevelEntityGetterAdapter<T extends EntityAccess> implements LevelEntityGetter<T> {
    private final EntityLookup<T> visibleEntities;
    private final EntitySectionStorage<T> sectionStorage;

    public LevelEntityGetterAdapter(EntityLookup<T> $$0, EntitySectionStorage<T> $$1) {
        this.visibleEntities = $$0;
        this.sectionStorage = $$1;
    }

    @Override // net.minecraft.world.level.entity.LevelEntityGetter
    public T get(int i) {
        return (T) this.visibleEntities.getEntity(i);
    }

    @Override // net.minecraft.world.level.entity.LevelEntityGetter
    public T get(UUID uuid) {
        return (T) this.visibleEntities.getEntity(uuid);
    }

    @Override // net.minecraft.world.level.entity.LevelEntityGetter
    public Iterable<T> getAll() {
        return this.visibleEntities.getAllEntities();
    }

    @Override // net.minecraft.world.level.entity.LevelEntityGetter
    public <U extends T> void get(EntityTypeTest<T, U> $$0, AbortableIterationConsumer<U> $$1) {
        this.visibleEntities.getEntities($$0, $$1);
    }

    @Override // net.minecraft.world.level.entity.LevelEntityGetter
    public void get(AABB $$0, Consumer<T> $$1) {
        this.sectionStorage.getEntities($$0, AbortableIterationConsumer.forConsumer($$1));
    }

    @Override // net.minecraft.world.level.entity.LevelEntityGetter
    public <U extends T> void get(EntityTypeTest<T, U> $$0, AABB $$1, AbortableIterationConsumer<U> $$2) {
        this.sectionStorage.getEntities($$0, $$1, $$2);
    }
}
