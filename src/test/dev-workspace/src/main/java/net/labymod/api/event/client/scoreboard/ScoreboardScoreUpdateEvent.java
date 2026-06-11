package net.labymod.api.event.client.scoreboard;

import net.labymod.api.client.scoreboard.ScoreboardObjective;
import net.labymod.api.client.scoreboard.ScoreboardScore;
import net.labymod.api.event.Event;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/scoreboard/ScoreboardScoreUpdateEvent.class */
public class ScoreboardScoreUpdateEvent implements Event {
    private final ScoreboardScore score;
    private final ScoreboardObjective objective;

    public ScoreboardScoreUpdateEvent(@NotNull ScoreboardScore score, @NotNull ScoreboardObjective objective) {
        this.score = score;
        this.objective = objective;
    }

    @NotNull
    public ScoreboardScore score() {
        return this.score;
    }

    @NotNull
    public ScoreboardObjective objective() {
        return this.objective;
    }
}
