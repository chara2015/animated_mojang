package net.labymod.api.client.world.object;

import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import javax.inject.Singleton;
import net.labymod.api.client.render.state.world.CameraSnapshot;
import net.labymod.api.client.world.object.snapshot.WorldObjectSnapshot;
import net.labymod.api.client.world.object.submit.WorldObjectSubmitter;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/object/WorldObjectDispatcher.class */
@Singleton
@Referenceable
public final class WorldObjectDispatcher {
    private final Reference2ObjectMap<Class<? extends WorldObject>, WorldObjectSubmitter<?, ?>> submitters = new Reference2ObjectOpenHashMap();

    public <T extends WorldObject> void registerSubmitter(@NotNull Class<T> worldObjectClass, @NotNull WorldObjectSubmitter<T, ?> submitter) {
        this.submitters.put(worldObjectClass, submitter);
    }

    @NotNull
    public <W extends WorldObject> WorldObjectSnapshot createSnapshot(@NotNull W object, @NotNull CameraSnapshot camera, float partialTicks) {
        WorldObjectSubmitter<W, ?> submitter = getSubmitter(object);
        return submitter.createSnapshot(object, camera, partialTicks);
    }

    @NotNull
    public <T extends WorldObject> WorldObjectSubmitter<T, ?> getSubmitter(@NotNull T object) {
        WorldObjectSubmitter<T, ?> worldObjectSubmitter = (WorldObjectSubmitter) this.submitters.get(object.getClass());
        if (worldObjectSubmitter == null) {
            throw new IllegalStateException("No submitter registered for world object type " + String.valueOf(object.getClass()));
        }
        return worldObjectSubmitter;
    }
}
