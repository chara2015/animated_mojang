package net.labymod.api.event.client.world.chunk;

import net.labymod.api.client.world.lighting.LightType;
import net.labymod.api.event.Event;
import net.labymod.api.util.math.vector.IntVector3;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/world/chunk/LightUpdateEvent.class */
public class LightUpdateEvent implements Event {
    private final LightType type;
    private final IntVector3 blockPosition;

    public LightUpdateEvent(LightType type, IntVector3 blockPosition) {
        this.type = type;
        this.blockPosition = blockPosition;
    }

    public LightType getType() {
        return this.type;
    }

    public IntVector3 getBlockPosition() {
        return this.blockPosition;
    }
}
