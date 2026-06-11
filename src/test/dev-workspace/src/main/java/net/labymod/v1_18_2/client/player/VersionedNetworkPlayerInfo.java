package net.labymod.v1_18_2.client.player;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.network.PlayerSkin;
import net.labymod.api.client.scoreboard.ScoreboardTeam;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.mojang.GameProfile;
import net.labymod.core.client.network.DefaultNetworkPlayerInfo;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/client/player/VersionedNetworkPlayerInfo.class */
public class VersionedNetworkPlayerInfo extends DefaultNetworkPlayerInfo {
    private final emw wrapped;
    private final Component usernameComponent = Component.text(profile().getUsername());
    private Metadata metadata = Metadata.create();

    public VersionedNetworkPlayerInfo(emw wrapped) {
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
        qk displayName = this.wrapped.l();
        if (displayName == null) {
            ScoreboardTeam team = getTeam();
            return team == null ? this.usernameComponent : team.formatDisplayName(this.usernameComponent.copy());
        }
        return Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(decorateComponent(this.wrapped, displayName.e()));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.network.NetworkPlayerInfo
    public GameMode gameMode() throws MatchException {
        if (this.wrapped.b() == null) {
            return GameMode.UNKNOWN;
        }
        switch (AnonymousClass1.$SwitchMap$net$minecraft$world$level$GameType[this.wrapped.b().ordinal()]) {
            case 1:
                return GameMode.SURVIVAL;
            case 2:
                return GameMode.CREATIVE;
            case 3:
                return GameMode.ADVENTURE;
            case 4:
                return GameMode.SPECTATOR;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: renamed from: net.labymod.v1_18_2.client.player.VersionedNetworkPlayerInfo$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/client/player/VersionedNetworkPlayerInfo$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$level$GameType = new int[cas.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$world$level$GameType[cas.a.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[cas.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[cas.c.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[cas.d.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    @Override // net.labymod.api.client.network.NetworkPlayerInfo
    public <T> T getMinecraftInfo() {
        return (T) this.wrapped;
    }

    @Override // net.labymod.api.client.network.NetworkPlayerInfo
    public PlayerSkin getSkin() {
        return this.wrapped;
    }

    public emw getWrapped() {
        return this.wrapped;
    }

    private qk decorateComponent(@NotNull emw info, qq mutableComponent) {
        if (info.b() == cas.d) {
            return mutableComponent.a(p.u);
        }
        return mutableComponent;
    }

    @Override // net.labymod.api.metadata.MetadataExtension
    public void metadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @Override // net.labymod.api.metadata.MetadataExtension
    public Metadata metadata() {
        return this.metadata;
    }
}
