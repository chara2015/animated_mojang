package net.labymod.api.client.entity.player.interaction;

import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.service.Registry;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/player/interaction/InteractionMenuRegistry.class */
@Referenceable
public interface InteractionMenuRegistry extends Registry<BulletPoint> {
    void unregisterServerBulletPoints();

    void registerPlaceHolder(@NotNull InteractionMenuPlaceholder interactionMenuPlaceholder);
}
