package net.labymod.v26_1_1.client.player;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.network.PlayerSkin;
import net.labymod.api.client.scoreboard.ScoreboardTeam;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.mojang.GameProfile;
import net.labymod.api.util.CastUtil;
import net.labymod.core.client.network.DefaultNetworkPlayerInfo;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.GameType;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/client/player/VersionedNetworkPlayerInfo.class */
public class VersionedNetworkPlayerInfo extends DefaultNetworkPlayerInfo {
    private final PlayerInfo wrapped;
    private final Component usernameComponent = Component.text(profile().getUsername());
    private Metadata metadata = Metadata.create();

    public VersionedNetworkPlayerInfo(PlayerInfo wrapped) {
        this.wrapped = wrapped;
    }

    @Override // net.labymod.api.client.network.NetworkPlayerInfo
    public GameProfile profile() {
        return (GameProfile) CastUtil.cast(this.wrapped.getProfile());
    }

    @Override // net.labymod.api.client.network.NetworkPlayerInfo
    public int getCurrentPing() {
        return this.wrapped.getLatency();
    }

    @Override // net.labymod.api.client.network.NetworkPlayerInfo
    public Component displayName() {
        net.minecraft.network.chat.Component displayName = this.wrapped.getTabListDisplayName();
        if (displayName == null) {
            ScoreboardTeam team = getTeam();
            return team == null ? this.usernameComponent : team.formatDisplayName(this.usernameComponent.copy());
        }
        return Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(decorateComponent(this.wrapped, displayName.copy()));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.network.NetworkPlayerInfo
    public GameMode gameMode() throws MatchException {
        if (this.wrapped.getGameMode() == null) {
            return GameMode.UNKNOWN;
        }
        switch (AnonymousClass1.$SwitchMap$net$minecraft$world$level$GameType[this.wrapped.getGameMode().ordinal()]) {
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

    /* JADX INFO: renamed from: net.labymod.v26_1_1.client.player.VersionedNetworkPlayerInfo$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/client/player/VersionedNetworkPlayerInfo$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$level$GameType = new int[GameType.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$world$level$GameType[GameType.SURVIVAL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[GameType.CREATIVE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[GameType.ADVENTURE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$GameType[GameType.SPECTATOR.ordinal()] = 4;
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
        return this.wrapped.getSkin();
    }

    @Override // net.labymod.api.client.network.NetworkPlayerInfo
    public boolean isListed() {
        ClientPacketListener connection = Minecraft.getInstance().getConnection();
        if (connection == null) {
            return true;
        }
        return connection.getListedOnlinePlayers().contains(this.wrapped);
    }

    @Override // net.labymod.api.client.network.NetworkPlayerInfo
    public int getOrder() {
        return this.wrapped.getTabListOrder();
    }

    @Override // net.labymod.api.client.network.NetworkPlayerInfo
    public boolean showHat() {
        return this.wrapped.showHat();
    }

    public PlayerInfo getWrapped() {
        return this.wrapped;
    }

    private net.minecraft.network.chat.Component decorateComponent(@NotNull PlayerInfo info, MutableComponent mutableComponent) {
        if (info.getGameMode() == GameType.SPECTATOR) {
            return mutableComponent.withStyle(ChatFormatting.ITALIC);
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
