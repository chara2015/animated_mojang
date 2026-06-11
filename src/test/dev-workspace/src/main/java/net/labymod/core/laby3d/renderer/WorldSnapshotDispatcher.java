package net.labymod.core.laby3d.renderer;

import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.state.world.CameraSnapshot;
import net.labymod.api.client.render.state.world.WorldSnapshot;
import net.labymod.api.client.render.state.world.WorldSnapshotContext;
import net.labymod.api.client.render.state.world.WorldSnapshotContributor;
import net.labymod.api.client.render.state.world.WorldSnapshotContributorRegistry;
import net.labymod.api.debug.DebugRegistry;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.world.snapshot.PrepareWorldSnapshotEvent;
import net.labymod.api.event.client.world.snapshot.SubmitWorldSnapshotEvent;
import net.labymod.api.laby3d.render.queue.ChannelTypes;
import net.labymod.api.laby3d.render.queue.SubmissionCollector;
import net.labymod.api.laby3d.renderer.snapshot.ExtraKey;
import net.labymod.api.laby3d.util.Frustum;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.client.render.state.world.DefaultWorldSnapshotContext;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/renderer/WorldSnapshotDispatcher.class */
public final class WorldSnapshotDispatcher {
    private static final Logging LOGGER = Logging.create((Class<?>) WorldSnapshotDispatcher.class);
    private final FrustumDebugRenderer frustumDebugRenderer = new FrustumDebugRenderer();
    private final SubmissionCollector collector = Laby.references().submissionCollector();
    private final WorldSnapshotContributorRegistry registry = Laby.references().worldSnapshotContributorRegistry();

    public void dispatch(@NotNull Stack stack, @NotNull CameraSnapshot camera, float partialTicks) {
        Frustum frustumFrozenFrustum;
        Frustum frustum = Frustum.createGameFrustum();
        boolean frustumDebug = DebugRegistry.FRUSTUM_DEBUG.isEnabled();
        if (frustumDebug) {
            this.frustumDebugRenderer.freeze(frustum);
        } else {
            this.frustumDebugRenderer.clear();
        }
        if (this.frustumDebugRenderer.isFrozen()) {
            frustumFrozenFrustum = this.frustumDebugRenderer.frozenFrustum();
        } else {
            frustumFrozenFrustum = frustum;
        }
        Frustum cullFrustum = frustumFrozenFrustum;
        WorldSnapshotContext context = new DefaultWorldSnapshotContext(cullFrustum, camera, stack, partialTicks);
        List<WorldSnapshotContributor<?>> contributors = this.registry.values();
        WorldSnapshot snapshot = prepareSnapshot(contributors, context, partialTicks);
        SubmissionCollector.SubmissionChannel channel = this.collector.channel(ChannelTypes.LEVEL);
        try {
            submitSnapshot(contributors, stack, snapshot);
            if (frustumDebug) {
                this.frustumDebugRenderer.submit(stack, this.collector, frustum.camX(), frustum.camY(), frustum.camZ());
            }
            if (channel != null) {
                channel.close();
            }
        } catch (Throwable th) {
            if (channel != null) {
                try {
                    channel.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private WorldSnapshot prepareSnapshot(List<WorldSnapshotContributor<?>> contributors, WorldSnapshotContext context, float partialTicks) {
        WorldSnapshot.Builder builder = WorldSnapshot.builder(partialTicks);
        for (WorldSnapshotContributor<?> contributor : contributors) {
            try {
                prepareContributor(contributor, context, builder);
            } catch (Exception exception) {
                LOGGER.error("Failed to prepare world snapshot contributor: {}", contributor.getId(), exception);
            }
        }
        Laby.fireEvent(new PrepareWorldSnapshotEvent(builder, context));
        return builder.build();
    }

    private <T> void prepareContributor(WorldSnapshotContributor<T> contributor, WorldSnapshotContext context, WorldSnapshot.Builder builder) {
        T data = contributor.prepare(context);
        if (data != null) {
            builder.put(contributor.key(), data);
        }
    }

    private void submitSnapshot(List<WorldSnapshotContributor<?>> contributors, Stack stack, WorldSnapshot snapshot) {
        Laby.fireEvent(new SubmitWorldSnapshotEvent(Phase.PRE, snapshot, stack, this.collector));
        for (WorldSnapshotContributor<?> contributor : contributors) {
            try {
                submitContributor(contributor, stack, snapshot);
            } catch (Exception exception) {
                LOGGER.error("Failed to submit world snapshot contributor: {}", contributor.getId(), exception);
            }
        }
        Laby.fireEvent(new SubmitWorldSnapshotEvent(Phase.POST, snapshot, stack, this.collector));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <T> void submitContributor(WorldSnapshotContributor<T> worldSnapshotContributor, Stack stack, WorldSnapshot snapshot) {
        ExtraKey<T> key = worldSnapshotContributor.key();
        Object obj = snapshot.get(key);
        if (obj != null) {
            worldSnapshotContributor.submit(stack, this.collector, obj);
        }
    }
}
