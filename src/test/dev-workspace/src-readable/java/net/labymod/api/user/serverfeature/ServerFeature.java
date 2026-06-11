package net.labymod.api.user.serverfeature;

import java.util.Map;
import java.util.Optional;
import net.labymod.serverapi.core.model.feature.Feature;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/user/serverfeature/ServerFeature.class */
public interface ServerFeature {
    Optional<Feature.StatedFeature> findFeature(@NotNull Feature feature);

    boolean isFeatureEnabled(@NotNull Feature feature, boolean z);

    @NotNull
    Map<Feature, Feature.StatedFeature> features();
}
