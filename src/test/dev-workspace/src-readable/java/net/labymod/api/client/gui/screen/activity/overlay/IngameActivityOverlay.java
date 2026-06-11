package net.labymod.api.client.gui.screen.activity.overlay;

import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/activity/overlay/IngameActivityOverlay.class */
@Referenceable
public interface IngameActivityOverlay {
    List<IngameOverlayActivity> getActivities();

    List<IngameOverlayActivity> getActivities(LoadedAddon loadedAddon);

    Optional<IngameOverlayActivity> getActivity(Class<? extends IngameOverlayActivity> cls);

    void registerActivity(IngameOverlayActivity ingameOverlayActivity);

    void unregisterActivity(IngameOverlayActivity ingameOverlayActivity);

    void unregisterActivities(LoadedAddon loadedAddon);

    void replaceActivities(UnaryOperator<IngameOverlayActivity> unaryOperator);
}
