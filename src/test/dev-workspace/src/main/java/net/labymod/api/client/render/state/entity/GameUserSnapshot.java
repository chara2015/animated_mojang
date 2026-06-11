package net.labymod.api.client.render.state.entity;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.laby3d.renderer.snapshot.LabySnapshot;
import net.labymod.api.user.badge.ServerBadge;
import net.labymod.api.user.group.Group;
import net.labymod.api.util.CountryCode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/state/entity/GameUserSnapshot.class */
public interface GameUserSnapshot extends LabySnapshot {
    boolean isUsingLabyMod();

    boolean isLegacy();

    @NotNull
    Group group();

    @NotNull
    TextColor displayColor();

    @Nullable
    CountryCode countryCode();

    ServerBadge[] serverBadges();

    float getHighestNameTagOffset();

    @Nullable
    Component groupComponent();

    boolean isFriend();

    boolean showUserIndicatorBesideNames();

    boolean showCountryFlagBesideNames();

    boolean showCosmetics();
}
