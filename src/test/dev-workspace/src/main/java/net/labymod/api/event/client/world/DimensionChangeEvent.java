package net.labymod.api.event.client.world;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.event.Event;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/world/DimensionChangeEvent.class */
public class DimensionChangeEvent implements Event {
    private final ResourceLocation fromDimension;
    private final ResourceLocation toDimension;

    public DimensionChangeEvent(@NotNull ResourceLocation fromDimension, @NotNull ResourceLocation toDimension) {
        this.fromDimension = fromDimension;
        this.toDimension = toDimension;
    }

    @NotNull
    public ResourceLocation fromDimension() {
        return this.fromDimension;
    }

    @NotNull
    public ResourceLocation toDimension() {
        return this.toDimension;
    }
}
