package net.labymod.api.event.client.scoreboard;

import net.labymod.api.client.scoreboard.ScoreboardTeam;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/scoreboard/ScoreboardTeamEntryAddEvent.class */
public class ScoreboardTeamEntryAddEvent extends ScoreboardTeamUpdateEvent {
    private final String entry;

    public ScoreboardTeamEntryAddEvent(@NotNull ScoreboardTeam team, @NotNull String entry) {
        super(team);
        this.entry = entry;
    }

    @NotNull
    public String getEntry() {
        return this.entry;
    }
}
