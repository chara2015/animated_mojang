package net.labymod.v1_12_2.client.player;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.network.PlayerSkin;
import net.labymod.api.client.scoreboard.ScoreboardTeam;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.mojang.GameProfile;
import net.labymod.core.client.network.DefaultNetworkPlayerInfo;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/player/VersionedNetworkPlayerInfo.class */
public class VersionedNetworkPlayerInfo extends DefaultNetworkPlayerInfo {
    private final bsc wrapped;
    private Metadata metadata = Metadata.create();

    public VersionedNetworkPlayerInfo(bsc wrapped) {
        this.wrapped = wrapped;
    }

    @Override // net.labymod.api.client.network.NetworkPlayerInfo
    public GameProfile profile() {
        return this.wrapped.a();
    }

    @Override // net.labymod.api.client.network.NetworkPlayerInfo
    public int getCurrentPing() {
        return this.wrapped.c();
    }

    @Override // net.labymod.api.client.network.NetworkPlayerInfo
    public Component displayName() {
        hh displayName = this.wrapped.l();
        if (displayName == null) {
            TextComponent username = Component.text(profile().getUsername());
            ScoreboardTeam team = getTeam();
            if (team != null) {
                return team.formatDisplayName(username);
            }
            return username;
        }
        return Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(displayName);
    }

    @Override // net.labymod.api.client.network.NetworkPlayerInfo
    public GameMode gameMode() {
        if (this.wrapped.b() == null) {
            return GameMode.UNKNOWN;
        }
        switch (AnonymousClass1.$SwitchMap$net$minecraft$world$GameType[this.wrapped.b().ordinal()]) {
            case 1:
                return GameMode.UNKNOWN;
            case 2:
                return GameMode.SURVIVAL;
            case 3:
                return GameMode.CREATIVE;
            case 4:
                return GameMode.ADVENTURE;
            case 5:
                return GameMode.SPECTATOR;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(this.wrapped.b()));
        }
    }

    /* JADX INFO: renamed from: net.labymod.v1_12_2.client.player.VersionedNetworkPlayerInfo$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/player/VersionedNetworkPlayerInfo$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$GameType = new int[ams.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$world$GameType[ams.a.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$world$GameType[ams.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$world$GameType[ams.c.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$world$GameType[ams.d.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$minecraft$world$GameType[ams.e.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public bsc getWrapped() {
        return this.wrapped;
    }

    @Override // net.labymod.api.client.network.NetworkPlayerInfo
    public PlayerSkin getSkin() {
        return this.wrapped;
    }

    @Contract("_, _ -> new")
    @NotNull
    private hh formatPlayerName(bhm team, String username) {
        if (team == null) {
            return new ho(username);
        }
        return new ho(team.d(username));
    }

    @Override // net.labymod.api.metadata.MetadataExtension
    public void metadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @Override // net.labymod.api.metadata.MetadataExtension
    public Metadata metadata() {
        return this.metadata;
    }

    @Override // net.labymod.api.client.network.NetworkPlayerInfo
    public <T> T getMinecraftInfo() {
        return (T) this.wrapped;
    }
}
