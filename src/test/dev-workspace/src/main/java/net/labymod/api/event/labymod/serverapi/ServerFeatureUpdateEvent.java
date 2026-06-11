package net.labymod.api.event.labymod.serverapi;

import java.util.Map;
import net.labymod.api.event.Event;
import net.labymod.serverapi.core.model.feature.Feature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/serverapi/ServerFeatureUpdateEvent.class */
public class ServerFeatureUpdateEvent implements Event {
    private final Map<Feature, Feature.StatedFeature> features;

    public ServerFeatureUpdateEvent(@NotNull Map<Feature, Feature.StatedFeature> features) {
        this.features = features;
    }

    @NotNull
    public Map<Feature, Feature.StatedFeature> features() {
        return this.features;
    }

    @Nullable
    public Feature.StatedFeature get(@NotNull Feature feature) {
        return this.features.get(feature);
    }

    public boolean isEnabled(@NotNull Feature feature, boolean def) {
        Feature.StatedFeature stated = this.features.get(feature);
        return stated != null ? stated.isEnabled() : def;
    }
}
