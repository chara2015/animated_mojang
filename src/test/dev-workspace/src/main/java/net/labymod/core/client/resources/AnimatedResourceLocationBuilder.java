package net.labymod.core.client.resources;

import javax.inject.Inject;
import net.labymod.api.client.resources.AnimatedResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.models.Implements;
import net.labymod.api.util.debug.Preconditions;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/AnimatedResourceLocationBuilder.class */
@Implements(AnimatedResourceLocation.Builder.class)
public class AnimatedResourceLocationBuilder implements AnimatedResourceLocation.Builder {
    private ResourceLocation[] locations = new ResourceLocation[0];
    private long delay = 1000;

    @Nullable
    private Runnable completableListener;

    @Inject
    public AnimatedResourceLocationBuilder() {
    }

    @Override // net.labymod.api.client.resources.AnimatedResourceLocation.Builder
    public AnimatedResourceLocation.Builder resourceLocations(ResourceLocation[] locations) {
        this.locations = locations;
        return this;
    }

    @Override // net.labymod.api.client.resources.AnimatedResourceLocation.Builder
    public AnimatedResourceLocation.Builder delay(long delay) {
        this.delay = delay;
        return this;
    }

    @Override // net.labymod.api.client.resources.AnimatedResourceLocation.Builder
    public AnimatedResourceLocation.Builder completableListener(@Nullable Runnable completableListener) {
        this.completableListener = completableListener;
        return this;
    }

    @Override // net.labymod.api.client.resources.AnimatedResourceLocation.Builder
    public AnimatedResourceLocation build() {
        Preconditions.noneNull(this.locations, "locations");
        return new DefaultAnimatedResourceLocation(this.locations, this.delay, this.completableListener);
    }
}
