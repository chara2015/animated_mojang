package net.labymod.core.client.render.state.entity;

import net.laby.lib.cosmetics.AttachmentPoint;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.render.state.entity.GameUserSnapshot;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.api.laby3d.renderer.snapshot.AbstractLabySnapshot;
import net.labymod.api.laby3d.renderer.snapshot.Extras;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.api.user.GameUser;
import net.labymod.api.user.badge.ServerBadge;
import net.labymod.api.user.group.Group;
import net.labymod.api.user.group.GroupDisplayType;
import net.labymod.api.util.CountryCode;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.core.main.user.GameUserData;
import net.labymod.core.main.user.GameUserItem;
import net.labymod.core.main.user.serverfeature.UserServerFeature;
import net.labymod.core.main.user.serverfeature.subtitle.SubtitleComponent;
import net.labymod.core.main.user.shop.cosmetic.CosmeticDefinition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/state/entity/DefaultGameUserSnapshot.class */
public class DefaultGameUserSnapshot extends AbstractLabySnapshot implements GameUserSnapshot {
    private static final Component PREFIX = Component.text("LABY", Style.builder().color(NamedTextColor.WHITE).decorate(TextDecoration.BOLD).build());
    private static final float NAME_TAG_OFFSET_PADDING = 0.2f;
    private final boolean usingLabyMod;
    private final boolean legacy;

    @NotNull
    private final Group group;

    @NotNull
    private final TextColor displayColor;

    @Nullable
    private final CountryCode countryCode;

    @Nullable
    private final ServerBadge[] serverBadges;
    private final float highestNameTagOffset;

    @Nullable
    private final SubtitleComponent subtitle;
    private final Component groupComponent;
    private final boolean friend;
    private final boolean showUserIndicatorBesideNames;
    private final boolean showCountryFlagBesideNames;
    private final boolean showCosmetics;

    /* JADX WARN: Illegal instructions before constructor call */
    public DefaultGameUserSnapshot(GameUser user, @Nullable UserServerFeature userServerFeature, Extras extras, LabyConfig config) {
        CountryCode countryCode;
        ServerBadge[] serverBadgeArr;
        SubtitleComponent subtitle;
        boolean zIsUsingLabyMod = user.isUsingLabyMod();
        boolean zIsLegacy = user.isLegacy();
        Group groupVisibleGroup = user.visibleGroup();
        TextColor textColorDisplayColor = user.displayColor();
        if (userServerFeature == null) {
            countryCode = null;
        } else {
            countryCode = userServerFeature.getCountryCode();
        }
        if (userServerFeature == null) {
            serverBadgeArr = null;
        } else {
            serverBadgeArr = (ServerBadge[]) userServerFeature.getBadges().toArray(new ServerBadge[0]);
        }
        float fCalculateNameTagOffset = calculateNameTagOffset(user);
        if (userServerFeature == null) {
            subtitle = null;
        } else {
            subtitle = userServerFeature.getSubtitle();
        }
        this(zIsUsingLabyMod, zIsLegacy, groupVisibleGroup, textColorDisplayColor, countryCode, serverBadgeArr, fCalculateNameTagOffset, subtitle, createGroupComponent(user.visibleGroup()), isFriend(user), config.ingame().showUserIndicatorBesideName().get().booleanValue(), config.ingame().showCountryFlag().get().booleanValue(), config.ingame().cosmetics().renderCosmetics().get().booleanValue(), extras);
    }

    public DefaultGameUserSnapshot(boolean usingLabyMod, boolean legacy, @NotNull Group group, @NotNull TextColor displayColor, @Nullable CountryCode countryCode, @Nullable ServerBadge[] serverBadges, float highestNameTagOffset, @Nullable SubtitleComponent subtitle, @Nullable Component groupComponent, boolean friend, boolean showUserIndicatorBesideNames, boolean showCountryFlagBesideNames, boolean showCosmetics, Extras extras) {
        super(extras);
        this.usingLabyMod = usingLabyMod;
        this.legacy = legacy;
        this.group = group;
        this.displayColor = displayColor;
        this.countryCode = countryCode;
        this.serverBadges = serverBadges;
        this.highestNameTagOffset = highestNameTagOffset;
        this.subtitle = subtitle;
        this.groupComponent = groupComponent;
        this.friend = friend;
        this.showUserIndicatorBesideNames = showUserIndicatorBesideNames;
        this.showCountryFlagBesideNames = showCountryFlagBesideNames;
        this.showCosmetics = showCosmetics;
    }

    private static float calculateNameTagOffset(GameUser user) {
        if (!(user instanceof DefaultGameUser)) {
            return 0.0f;
        }
        DefaultGameUser defaultGameUser = (DefaultGameUser) user;
        GameUserData data = defaultGameUser.getUserData();
        if (data == null) {
            return 0.0f;
        }
        float offset = 0.0f;
        for (GameUserItem entry : data.getItems()) {
            CosmeticDefinition definition = entry.definition();
            if (definition != null && definition.details().getAttachmentPoint() == AttachmentPoint.HEAD && definition.isAssetsLoaded()) {
                offset = Math.max(offset, definition.details().getNameTagOffset());
            }
        }
        if (offset > 0.0f) {
            return ((offset + 0.2f) / Laby.references().renderConstants().nameTagScale()) - 1.0f;
        }
        return 0.0f;
    }

    private static Component createGroupComponent(Group group) {
        if (group.getDisplayType() != GroupDisplayType.ABOVE_HEAD) {
            return null;
        }
        return Component.text().append(PREFIX).append(Component.space()).append(Component.text(group.getTagName(), group.getTextColor())).build();
    }

    private static boolean isFriend(GameUser user) {
        LabyConnectSession session;
        LabyConnect labyConnect = Laby.references().labyConnect();
        if (!labyConnect.isAuthenticated() || (session = labyConnect.getSession()) == null) {
            return false;
        }
        Friend friend = session.getFriend(user.getUniqueId());
        return friend != null;
    }

    @Override // net.labymod.api.client.render.state.entity.GameUserSnapshot
    public boolean isUsingLabyMod() {
        return this.usingLabyMod;
    }

    @Override // net.labymod.api.client.render.state.entity.GameUserSnapshot
    public boolean isLegacy() {
        return this.legacy;
    }

    @Override // net.labymod.api.client.render.state.entity.GameUserSnapshot
    @NotNull
    public Group group() {
        return this.group;
    }

    @Override // net.labymod.api.client.render.state.entity.GameUserSnapshot
    @NotNull
    public TextColor displayColor() {
        return this.displayColor;
    }

    @Override // net.labymod.api.client.render.state.entity.GameUserSnapshot
    @Nullable
    public CountryCode countryCode() {
        return this.countryCode;
    }

    @Override // net.labymod.api.client.render.state.entity.GameUserSnapshot
    @Nullable
    public ServerBadge[] serverBadges() {
        return this.serverBadges;
    }

    @Override // net.labymod.api.client.render.state.entity.GameUserSnapshot
    public float getHighestNameTagOffset() {
        return this.highestNameTagOffset;
    }

    @Override // net.labymod.api.client.render.state.entity.GameUserSnapshot
    @Nullable
    public Component groupComponent() {
        return this.groupComponent;
    }

    @Override // net.labymod.api.client.render.state.entity.GameUserSnapshot
    public boolean isFriend() {
        return this.friend;
    }

    @Override // net.labymod.api.client.render.state.entity.GameUserSnapshot
    public boolean showUserIndicatorBesideNames() {
        return this.showUserIndicatorBesideNames;
    }

    @Override // net.labymod.api.client.render.state.entity.GameUserSnapshot
    public boolean showCountryFlagBesideNames() {
        return this.showCountryFlagBesideNames;
    }

    @Override // net.labymod.api.client.render.state.entity.GameUserSnapshot
    public boolean showCosmetics() {
        return this.showCosmetics;
    }

    @Nullable
    public SubtitleComponent subtitle() {
        return this.subtitle;
    }
}
