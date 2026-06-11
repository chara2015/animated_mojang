package net.labymod.v1_21_8.client.network.chat;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.ComponentService;
import net.labymod.api.client.component.ScoreComponent;
import net.labymod.api.client.scoreboard.Scoreboard;
import net.labymod.api.client.scoreboard.ScoreboardObjective;
import net.labymod.v1_21_8.client.network.chat.contents.ScoreContentsAccessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/client/network/chat/VersionedScoreComponent.class */
public class VersionedScoreComponent extends VersionedBaseComponent<ScoreComponent, xp> implements ScoreComponent {
    public VersionedScoreComponent(yc holder) {
        super(holder);
    }

    @Override // net.labymod.api.client.component.ScoreComponent
    public String getName() {
        yw ywVar = (xp) getContents();
        if (ywVar instanceof yw) {
            yw score = ywVar;
            return (String) score.b().right().orElseThrow();
        }
        return ywVar.toString();
    }

    @Override // net.labymod.api.client.component.ScoreComponent
    public String getObjective() {
        yw ywVar = (xp) getContents();
        if (ywVar instanceof yw) {
            yw score = ywVar;
            return score.c();
        }
        return ywVar.toString();
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public ScoreComponent plainCopy() {
        return ComponentService.scoreComponent(getName(), getObjective());
    }

    @Override // net.labymod.api.client.component.ScoreComponent
    public ScoreComponent name(String name) {
        ScoreContentsAccessor contents = getContents();
        if (contents instanceof ScoreContentsAccessor) {
            ScoreContentsAccessor score = contents;
            score.setName(name);
        }
        return this;
    }

    @Override // net.labymod.api.client.component.ScoreComponent
    public ScoreComponent objective(String objective) {
        ScoreContentsAccessor contents = getContents();
        if (contents instanceof ScoreContentsAccessor) {
            ScoreContentsAccessor score = contents;
            score.setObjective(objective);
        }
        return this;
    }

    @Override // net.labymod.api.client.component.ScoreComponent
    public Component value() {
        fjx scoreboard;
        fjp objective;
        grk level = fue.R().s;
        if (level != null && (objective = (scoreboard = level.S()).a(getObjective())) != null) {
            String name = getName();
            fjw scoreHolder = fjw.c(name);
            fjt scoreInfo = scoreboard.d(scoreHolder, objective);
            if (scoreInfo != null) {
                return scoreInfo.a(objective.a(zh.b));
            }
        }
        return Component.empty();
    }

    @Override // net.labymod.api.client.component.ScoreComponent
    public ScoreboardObjective getScoreboardObjective() {
        Scoreboard scoreboard = Laby.labyAPI().minecraft().getScoreboard();
        if (scoreboard != null) {
            return scoreboard.getObjective(getObjective());
        }
        return null;
    }
}
