package net.labymod.api.labyconnect.object.marker;

import java.util.UUID;
import net.labymod.api.client.entity.Entity;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/labyconnect/object/marker/Marker.class */
public interface Marker {
    UUID getOwner();

    @Nullable
    Entity getTargetEntity();

    boolean isLarge();
}
