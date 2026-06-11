package net.labymod.api.client.scoreboard;

import java.util.Collection;
import net.labymod.api.client.component.Component;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/scoreboard/ScoreboardTeam.class */
public interface ScoreboardTeam {
    @NotNull
    String getTeamName();

    @NotNull
    Collection<String> getEntries();

    boolean hasEntry(@NotNull String str);

    @NotNull
    Component getPrefix();

    @NotNull
    Component getSuffix();

    @NotNull
    Component formatDisplayName(@NotNull Component component);
}
