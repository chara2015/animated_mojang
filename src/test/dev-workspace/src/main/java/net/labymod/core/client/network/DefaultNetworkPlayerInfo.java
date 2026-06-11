package net.labymod.core.client.network;

import java.util.Comparator;
import java.util.UUID;
import java.util.function.Function;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.scoreboard.DisplaySlot;
import net.labymod.api.client.scoreboard.Scoreboard;
import net.labymod.api.client.scoreboard.ScoreboardObjective;
import net.labymod.api.client.scoreboard.ScoreboardTeam;
import net.labymod.core.client.gui.screen.activity.activities.ingame.playerlist.PlayerListUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/network/DefaultNetworkPlayerInfo.class */
public abstract class DefaultNetworkPlayerInfo implements NetworkPlayerInfo {
    private static final Comparator<NetworkPlayerInfo> PLAYER_COMPARATOR = createComparator((v0) -> {
        return v0.getOrder();
    }, (v0) -> {
        return v0.gameMode();
    }, (v0) -> {
        return v0.getTeam();
    }, networkPlayerInfo -> {
        return networkPlayerInfo.profile().getUsername();
    }, null);
    public static final Comparator<PlayerListUser> PLAYER_LIST_COMPARATOR = createComparator(user -> {
        return Integer.valueOf(user.playerInfo().getOrder());
    }, user2 -> {
        return user2.playerInfo().gameMode();
    }, (v0) -> {
        return v0.getTeam();
    }, (v0) -> {
        return v0.getUserName();
    }, (v0) -> {
        return v0.getUniqueId();
    });

    @Override // net.labymod.api.client.network.NetworkPlayerInfo
    public int getHealth() {
        ScoreboardObjective objective;
        Scoreboard scoreboard = Laby.labyAPI().minecraft().getScoreboard();
        if (scoreboard == null || (objective = scoreboard.getObjective(DisplaySlot.PLAYER_LIST)) == null) {
            return 0;
        }
        return objective.getOrCreateScore(profile().getUsername()).getValue();
    }

    @Override // net.labymod.api.client.network.NetworkPlayerInfo
    public ScoreboardTeam getTeam() {
        Scoreboard scoreboard = Laby.labyAPI().minecraft().getScoreboard();
        if (scoreboard != null) {
            return scoreboard.teamByEntry(profile().getUsername());
        }
        return null;
    }

    @Override // java.lang.Comparable
    public int compareTo(@NotNull NetworkPlayerInfo p2) {
        return PLAYER_COMPARATOR.compare(this, p2);
    }

    private static <T> Comparator<T> createComparator(Function<T, Integer> orderGetter, Function<T, GameMode> gameModeGetter, Function<T, ScoreboardTeam> teamGetter, Function<T, String> usernameGetter, @Nullable Function<T, UUID> uuidGetter) {
        Comparator<T> comparator = Comparator.comparingInt(value -> {
            return -((Integer) orderGetter.apply(value)).intValue();
        }).thenComparing(value2 -> {
            return Integer.valueOf(gameModeGetter.apply(value2) == GameMode.SPECTATOR ? 1 : 0);
        }).thenComparing(value3 -> {
            ScoreboardTeam team = (ScoreboardTeam) teamGetter.apply(value3);
            return team == null ? "" : team.getTeamName();
        }).thenComparing(usernameGetter, (v0, v1) -> {
            return v0.compareToIgnoreCase(v1);
        });
        if (uuidGetter != null) {
            comparator = comparator.thenComparing(uuidGetter, (v0, v1) -> {
                return v0.compareTo(v1);
            });
        }
        return comparator;
    }
}
