package net.labymod.core.event.client.resources;

import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.event.client.resources.ReleaseTextureEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/event/client/resources/ReleaseTextureEventCaller.class */
public final class ReleaseTextureEventCaller {
    public static void call(ResourceLocation location) {
        Laby.fireEvent(new ReleaseTextureEvent(location));
    }
}
