package net.labymod.core.client.gui.screen.activity.activities.ingame.playerlist;

import java.util.Objects;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.network.ClientPacketListener;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.scoreboard.ScoreboardTeam;
import net.labymod.api.event.client.render.PlayerNameTagRenderEvent;
import net.labymod.api.mojang.GameProfile;
import net.labymod.api.user.GameUser;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/ingame/playerlist/PlayerListUser.class */
public class PlayerListUser extends CachedComponent {
    private static final String ACTUAL_PLAYER_KEY = "actual_player_name";
    public final UUID uniqueId;
    private final String userName;
    private final NetworkPlayerInfo playerInfo;
    public int x;
    public int y;
    private String actualUserName;
    private UUID actualUniqueId;
    private NetworkPlayerInfo actualNetworkPlayerInfo;
    private ScoreboardTeam team;
    private String identifier;
    private GameUser gameUser;
    private boolean spectator;

    @Override // net.labymod.core.client.gui.screen.activity.activities.ingame.playerlist.CachedComponent
    public /* bridge */ /* synthetic */ void tick() {
        super.tick();
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.ingame.playerlist.CachedComponent
    public /* bridge */ /* synthetic */ boolean isEmpty() {
        return super.isEmpty();
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.ingame.playerlist.CachedComponent
    public /* bridge */ /* synthetic */ void refresh() {
        super.refresh();
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.ingame.playerlist.CachedComponent
    public /* bridge */ /* synthetic */ void invalidate() {
        super.invalidate();
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.ingame.playerlist.CachedComponent
    public /* bridge */ /* synthetic */ RenderableComponent renderableComponent() {
        return super.renderableComponent();
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.ingame.playerlist.CachedComponent
    public /* bridge */ /* synthetic */ void update(Component component, float f) {
        super.update(component, f);
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.ingame.playerlist.CachedComponent
    public /* bridge */ /* synthetic */ void update(Component component) {
        super.update(component);
    }

    protected PlayerListUser(NetworkPlayerInfo info) {
        this.playerInfo = info;
        this.team = info.getTeam();
        GameProfile profile = info.profile();
        this.userName = profile.getUsername();
        this.uniqueId = profile.getUniqueId();
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.ingame.playerlist.CachedComponent
    public Component component() {
        if (this.component == null) {
            this.component = loadComponent();
        }
        return super.component();
    }

    public boolean updateTeam(ScoreboardTeam team) {
        if (this.team != null && this.team.equals(team)) {
            return false;
        }
        this.team = team;
        this.identifier = null;
        invalidate();
        return true;
    }

    public void update(float maxWidth) {
        if (isEmpty()) {
            update(loadComponent(), maxWidth);
        }
    }

    public void refreshComponent(float maxWidth) {
        if (isEmpty()) {
            return;
        }
        if (this.renderableComponent == null || this.renderableComponent.getWidth() > maxWidth) {
            invalidate();
        }
    }

    public Component loadComponent() {
        Component displayName = this.playerInfo.displayName();
        this.spectator = this.playerInfo.gameMode() == GameMode.SPECTATOR;
        if (this.spectator) {
            displayName = displayName.copy().decorate(TextDecoration.ITALIC);
        }
        PlayerNameTagRenderEvent event = new PlayerNameTagRenderEvent(PlayerNameTagRenderEvent.Context.TAB_LIST, this.playerInfo, displayName, TagType.MAIN_TAG);
        Laby.fireEvent(event);
        if (!Objects.equals(displayName, event.nameTag())) {
            displayName = event.nameTag().append(PlayerNameTagRenderEvent.EDITED_COMPONENT);
        }
        return displayName;
    }

    public boolean isSpectator() {
        return this.spectator;
    }

    public String getIdentifier() {
        if (this.identifier == null) {
            if (this.team == null) {
                this.identifier = this.userName;
            } else {
                this.identifier = this.team.getTeamName() + this.userName;
            }
        }
        return this.identifier;
    }

    public String getUserName() {
        return this.userName;
    }

    public NetworkPlayerInfo playerInfo() {
        refreshActualUser();
        if (this.actualNetworkPlayerInfo != null) {
            return this.actualNetworkPlayerInfo;
        }
        return this.playerInfo;
    }

    public ScoreboardTeam getTeam() {
        return this.team;
    }

    public GameUser gameUser() {
        if (this.gameUser == null || this.gameUser.isDisposed()) {
            this.gameUser = Laby.references().gameUserService().gameUser(getUniqueId());
        }
        return this.gameUser;
    }

    public UUID getUniqueId() {
        if (this.actualUniqueId != null) {
            return this.actualUniqueId;
        }
        return this.uniqueId;
    }

    private UUID refreshActualUser() {
        String actualUserName = (String) this.playerInfo.metadata().get(ACTUAL_PLAYER_KEY);
        if (actualUserName == null) {
            resetActualUser();
            return this.uniqueId;
        }
        if (actualUserName.equals(this.actualUserName)) {
            return this.actualUniqueId;
        }
        ClientPacketListener clientPacketListener = Laby.labyAPI().minecraft().getClientPacketListener();
        if (clientPacketListener == null) {
            resetActualUser();
            return this.uniqueId;
        }
        NetworkPlayerInfo playerInfo = clientPacketListener.getNetworkPlayerInfo(actualUserName);
        if (playerInfo == null) {
            resetActualUser();
            return this.uniqueId;
        }
        this.actualUserName = actualUserName;
        this.actualUniqueId = playerInfo.profile().getUniqueId();
        this.actualNetworkPlayerInfo = playerInfo;
        this.gameUser = null;
        return this.actualUniqueId;
    }

    private void resetActualUser() {
        if (this.actualUniqueId == null) {
            return;
        }
        this.actualUserName = null;
        this.actualUniqueId = null;
        this.actualNetworkPlayerInfo = null;
        this.gameUser = null;
    }
}
