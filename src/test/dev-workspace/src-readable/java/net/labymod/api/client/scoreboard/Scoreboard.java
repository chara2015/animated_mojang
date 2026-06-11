package net.labymod.api.client.scoreboard;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/scoreboard/Scoreboard.class */
public interface Scoreboard {
    @NotNull
    Collection<ScoreboardTeam> getTeams();

    @Nullable
    ScoreboardTeam teamByEntry(@NotNull String str);

    @Nullable
    ScoreboardObjective getObjective(@NotNull DisplaySlot displaySlot);

    @Nullable
    ScoreboardObjective getObjective(@NotNull String str);

    @NotNull
    Collection<ScoreboardScore> getScores(ScoreboardObjective scoreboardObjective);

    @NotNull
    Collection<String> getEntries(ScoreboardObjective scoreboardObjective);

    @Deprecated
    @Nullable
    default ScoreboardObjective objective(@NotNull DisplaySlot slot) {
        return getObjective(slot);
    }
}
