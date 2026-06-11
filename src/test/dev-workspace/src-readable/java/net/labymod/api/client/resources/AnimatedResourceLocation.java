package net.labymod.api.client.resources;

import java.util.List;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.time.TimeUtil;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/AnimatedResourceLocation.class */
public interface AnimatedResourceLocation {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/AnimatedResourceLocation$AnimatedFrameResourceLocation.class */
    public interface AnimatedFrameResourceLocation extends ResourceLocation {
    }

    AnimatedFrameResourceLocation getDefault();

    AnimatedFrameResourceLocation getCurrentResourceLocation();

    AnimatedFrameResourceLocation getResourceLocationAt(int i);

    void update(long j);

    List<AnimatedFrameResourceLocation> getAnimatedResourceLocations();

    default void update() {
        update(TimeUtil.getMillis());
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/AnimatedResourceLocation$Builder.class */
    @Referenceable
    public interface Builder {
        Builder resourceLocations(ResourceLocation[] resourceLocationArr);

        Builder delay(long j);

        Builder completableListener(@Nullable Runnable runnable);

        AnimatedResourceLocation build();

        default Builder resourceLocations(String namespace, String path, int frames) {
            ResourceLocation[] locations = new ResourceLocation[frames];
            for (int frame = 0; frame < frames; frame++) {
                locations[frame] = ResourceLocation.create(namespace, path + "_" + frame + ".png");
            }
            return resourceLocations(locations);
        }
    }
}
