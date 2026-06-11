package net.labymod.api.client.network;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.scoreboard.ScoreboardTeam;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.metadata.MetadataExtension;
import net.labymod.api.mojang.GameProfile;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/network/NetworkPlayerInfo.class */
public interface NetworkPlayerInfo extends Comparable<NetworkPlayerInfo>, MetadataExtension {
    GameProfile profile();

    int getCurrentPing();

    int getHealth();

    Component displayName();

    ScoreboardTeam getTeam();

    GameMode gameMode();

    <T> T getMinecraftInfo();

    PlayerSkin getSkin();

    default int getOrder() {
        return 0;
    }

    default boolean isListed() {
        return true;
    }

    default boolean showHat() {
        return MinecraftVersions.V1_21_3.orOlder();
    }

    default NetworkPlayerInfo toImmutable() {
        return new Immutable(this);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/network/NetworkPlayerInfo$Immutable.class */
    public static class Immutable implements NetworkPlayerInfo {
        private final GameProfile profile;
        private final GameMode gameMode;
        private final PlayerSkin skin;
        private final Component displayName;
        private final ScoreboardTeam team;
        private final int health;
        private final int ping;
        private final int order;
        private final boolean showHat;
        private final boolean listed;
        private final Metadata metadata;

        public Immutable(NetworkPlayerInfo playerInfo) {
            this(playerInfo.profile(), playerInfo.gameMode(), playerInfo.getSkin(), playerInfo.displayName(), playerInfo.getTeam(), playerInfo.getHealth(), playerInfo.getCurrentPing(), playerInfo.getOrder(), playerInfo.showHat(), playerInfo.isListed(), playerInfo.metadata());
        }

        public Immutable(GameProfile profile, GameMode gameMode, PlayerSkin skin, Component displayName, ScoreboardTeam team, int health, int ping, int order, boolean showHat, boolean listed, Metadata metadata) {
            this.profile = profile;
            this.gameMode = gameMode;
            this.skin = skin;
            this.displayName = displayName;
            this.team = team;
            this.health = health;
            this.ping = ping;
            this.order = order;
            this.showHat = showHat;
            this.listed = listed;
            this.metadata = Metadata.immutable(metadata);
        }

        @Override // net.labymod.api.client.network.NetworkPlayerInfo
        public GameProfile profile() {
            return this.profile;
        }

        @Override // net.labymod.api.client.network.NetworkPlayerInfo
        public int getCurrentPing() {
            return this.ping;
        }

        @Override // net.labymod.api.client.network.NetworkPlayerInfo
        public int getHealth() {
            return this.health;
        }

        @Override // net.labymod.api.client.network.NetworkPlayerInfo
        public Component displayName() {
            return this.displayName;
        }

        @Override // net.labymod.api.client.network.NetworkPlayerInfo
        public ScoreboardTeam getTeam() {
            return this.team;
        }

        @Override // net.labymod.api.client.network.NetworkPlayerInfo
        public GameMode gameMode() {
            return this.gameMode;
        }

        @Override // net.labymod.api.client.network.NetworkPlayerInfo
        public <T> T getMinecraftInfo() {
            throw new UnsupportedOperationException("Cannot get minecraft info from immutable network player info");
        }

        @Override // net.labymod.api.client.network.NetworkPlayerInfo
        public PlayerSkin getSkin() {
            return this.skin;
        }

        @Override // net.labymod.api.client.network.NetworkPlayerInfo
        public int getOrder() {
            return this.order;
        }

        @Override // net.labymod.api.client.network.NetworkPlayerInfo
        public boolean showHat() {
            return this.showHat;
        }

        @Override // net.labymod.api.client.network.NetworkPlayerInfo
        public boolean isListed() {
            return this.listed;
        }

        @Override // java.lang.Comparable
        public int compareTo(@NotNull NetworkPlayerInfo o) {
            return 0;
        }

        @Override // net.labymod.api.metadata.MetadataExtension
        public void metadata(Metadata metadata) {
            throw new IllegalStateException("Cannot set metadata on immutable network player info");
        }

        @Override // net.labymod.api.metadata.MetadataExtension
        public Metadata metadata() {
            return this.metadata;
        }
    }
}
