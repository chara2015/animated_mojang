package com.mojang.blaze3d.resource;

import com.google.common.annotations.VisibleForTesting;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/resource/CrossFrameResourcePool.class */
public class CrossFrameResourcePool implements GraphicsResourceAllocator, AutoCloseable {
    private final int framesToKeepResource;
    private final Deque<ResourceEntry<?>> pool = new ArrayDeque();

    public CrossFrameResourcePool(int $$0) {
        this.framesToKeepResource = $$0;
    }

    public void endFrame() {
        Iterator<? extends ResourceEntry<?>> $$0 = this.pool.iterator();
        while ($$0.hasNext()) {
            ResourceEntry<?> $$1 = $$0.next();
            int i = $$1.framesToLive;
            $$1.framesToLive = i - 1;
            if (i == 0) {
                $$1.close();
                $$0.remove();
            }
        }
    }

    @Override // com.mojang.blaze3d.resource.GraphicsResourceAllocator
    public <T> T acquire(ResourceDescriptor<T> resourceDescriptor) {
        T t = (T) acquireWithoutPreparing(resourceDescriptor);
        resourceDescriptor.prepare(t);
        return t;
    }

    private <T> T acquireWithoutPreparing(ResourceDescriptor<T> $$0) {
        Iterator<? extends ResourceEntry<?>> $$1 = this.pool.iterator();
        while ($$1.hasNext()) {
            ResourceEntry<?> $$2 = $$1.next();
            if ($$0.canUsePhysicalResource($$2.descriptor)) {
                $$1.remove();
                return $$2.value;
            }
        }
        return $$0.allocate();
    }

    @Override // com.mojang.blaze3d.resource.GraphicsResourceAllocator
    public <T> void release(ResourceDescriptor<T> $$0, T $$1) {
        this.pool.addFirst(new ResourceEntry<>($$0, $$1, this.framesToKeepResource));
    }

    public void clear() {
        this.pool.forEach((v0) -> {
            v0.close();
        });
        this.pool.clear();
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        clear();
    }

    @VisibleForTesting
    protected Collection<ResourceEntry<?>> entries() {
        return this.pool;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/resource/CrossFrameResourcePool$ResourceEntry.class */
    @VisibleForTesting
    protected static final class ResourceEntry<T> implements AutoCloseable {
        final ResourceDescriptor<T> descriptor;
        final T value;
        int framesToLive;

        ResourceEntry(ResourceDescriptor<T> $$0, T $$1, int $$2) {
            this.descriptor = $$0;
            this.value = $$1;
            this.framesToLive = $$2;
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            this.descriptor.free(this.value);
        }
    }
}
