package net.minecraft.world.level.entity;

import com.mojang.logging.LogUtils;
import java.util.Collection;
import java.util.stream.Stream;
import net.minecraft.util.AbortableIterationConsumer;
import net.minecraft.util.ClassInstanceMultiMap;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.phys.AABB;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/entity/EntitySection.class */
public class EntitySection<T extends EntityAccess> {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final ClassInstanceMultiMap<T> storage;
    private Visibility chunkStatus;

    public EntitySection(Class<T> $$0, Visibility $$1) {
        this.chunkStatus = $$1;
        this.storage = new ClassInstanceMultiMap<>($$0);
    }

    public void add(T $$0) {
        this.storage.add($$0);
    }

    public boolean remove(T $$0) {
        return this.storage.remove($$0);
    }

    public AbortableIterationConsumer.Continuation getEntities(AABB $$0, AbortableIterationConsumer<T> $$1) {
        for (T $$2 : this.storage) {
            if ($$2.getBoundingBox().intersects($$0) && $$1.accept($$2).shouldAbort()) {
                return AbortableIterationConsumer.Continuation.ABORT;
            }
        }
        return AbortableIterationConsumer.Continuation.CONTINUE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <U extends T> AbortableIterationConsumer.Continuation getEntities(EntityTypeTest<T, U> entityTypeTest, AABB $$1, AbortableIterationConsumer<? super U> $$2) {
        Collection<S> collectionFind = this.storage.find(entityTypeTest.getBaseClass());
        if (collectionFind.isEmpty()) {
            return AbortableIterationConsumer.Continuation.CONTINUE;
        }
        for (S s : collectionFind) {
            EntityAccess entityAccess = (EntityAccess) entityTypeTest.tryCast(s);
            if (entityAccess != null && s.getBoundingBox().intersects($$1) && $$2.accept(entityAccess).shouldAbort()) {
                return AbortableIterationConsumer.Continuation.ABORT;
            }
        }
        return AbortableIterationConsumer.Continuation.CONTINUE;
    }

    public boolean isEmpty() {
        return this.storage.isEmpty();
    }

    public Stream<T> getEntities() {
        return this.storage.stream();
    }

    public Visibility getStatus() {
        return this.chunkStatus;
    }

    public Visibility updateChunkStatus(Visibility $$0) {
        Visibility $$1 = this.chunkStatus;
        this.chunkStatus = $$0;
        return $$1;
    }

    @VisibleForDebug
    public int size() {
        return this.storage.size();
    }
}
