package net.minecraft.client.renderer.chunk;

import java.util.Collections;
import java.util.List;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/chunk/SectionMesh.class */
public interface SectionMesh extends AutoCloseable {
    boolean facesCanSeeEachother(Direction direction, Direction direction2);

    default boolean isDifferentPointOfView(TranslucencyPointOfView $$0) {
        return false;
    }

    default boolean hasRenderableLayers() {
        return false;
    }

    default boolean hasTranslucentGeometry() {
        return false;
    }

    default boolean isEmpty(ChunkSectionLayer $$0) {
        return true;
    }

    default List<BlockEntity> getRenderableBlockEntities() {
        return Collections.emptyList();
    }

    default SectionBuffers getBuffers(ChunkSectionLayer $$0) {
        return null;
    }

    @Override // java.lang.AutoCloseable
    default void close() {
    }
}
