package net.labymod.core.main.user.serverfeature;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import net.labymod.api.user.badge.ServerBadge;
import net.labymod.api.user.serverfeature.ServerFeature;
import net.labymod.serverapi.core.model.feature.Feature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/serverfeature/DefaultServerFeature.class */
public class DefaultServerFeature implements ServerFeature {
    private boolean userIndicatorVisible = true;
    private final Map<UUID, UserServerFeature> users = new HashMap();
    private final Map<Integer, ServerBadge> badges = new HashMap();
    private final Map<Feature, Feature.StatedFeature> features = new HashMap();
    private final Map<Feature, Feature.StatedFeature> unmodifiableFeatures = Collections.unmodifiableMap(this.features);

    @Nullable
    public UserServerFeature getUserFeature(UUID uuid) {
        return this.users.get(uuid);
    }

    @NotNull
    public UserServerFeature getOrCreateUserFeature(UUID uuid) {
        return this.users.computeIfAbsent(uuid, k -> {
            return new UserServerFeature();
        });
    }

    public Map<Integer, ServerBadge> getBadges() {
        return this.badges;
    }

    public void setUserIndicatorVisible(boolean userIndicatorVisible) {
        this.userIndicatorVisible = userIndicatorVisible;
    }

    public boolean isUserIndicatorVisible() {
        return this.userIndicatorVisible;
    }

    public void setFeature(Feature feature, Feature.StatedFeature statedFeature) {
        this.features.put(feature, statedFeature);
    }

    @Override // net.labymod.api.user.serverfeature.ServerFeature
    @NotNull
    public Map<Feature, Feature.StatedFeature> features() {
        return this.unmodifiableFeatures;
    }

    @Override // net.labymod.api.user.serverfeature.ServerFeature
    public Optional<Feature.StatedFeature> findFeature(@NotNull Feature feature) {
        return Optional.ofNullable(this.features.get(feature));
    }

    @Override // net.labymod.api.user.serverfeature.ServerFeature
    public boolean isFeatureEnabled(@NotNull Feature feature, boolean def) {
        return ((Boolean) findFeature(feature).map((v0) -> {
            return v0.isEnabled();
        }).orElse(Boolean.valueOf(def))).booleanValue();
    }
}
