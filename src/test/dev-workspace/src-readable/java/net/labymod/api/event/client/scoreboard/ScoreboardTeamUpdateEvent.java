package net.labymod.api.event.client.scoreboard;

import net.labymod.api.client.scoreboard.ScoreboardTeam;
import net.labymod.api.event.Event;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/scoreboard/ScoreboardTeamUpdateEvent.class */
public class ScoreboardTeamUpdateEvent implements Event {
    private final ScoreboardTeam team;

    public ScoreboardTeamUpdateEvent(@NotNull ScoreboardTeam team) {
        this.team = team;
    }

    @NotNull
    public ScoreboardTeam team() {
        return this.team;
    }
}
