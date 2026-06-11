package net.labymod.v1_21_4.client.network.chat;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.ComponentService;
import net.labymod.api.client.component.ScoreComponent;
import net.labymod.api.client.scoreboard.Scoreboard;
import net.labymod.api.client.scoreboard.ScoreboardObjective;
import net.labymod.v1_21_4.client.network.chat.contents.ScoreContentsAccessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/client/network/chat/VersionedScoreComponent.class */
public class VersionedScoreComponent extends VersionedBaseComponent<ScoreComponent, wq> implements ScoreComponent {
    public VersionedScoreComponent(xd holder) {
        super(holder);
    }

    @Override // net.labymod.api.client.component.ScoreComponent
    public String getName() {
        xx xxVar = (wq) getContents();
        if (xxVar instanceof xx) {
            xx score = xxVar;
            return (String) score.b().right().orElseThrow();
        }
        return xxVar.toString();
    }

    @Override // net.labymod.api.client.component.ScoreComponent
    public String getObjective() {
        xx xxVar = (wq) getContents();
        if (xxVar instanceof xx) {
            xx score = xxVar;
            return score.c();
        }
        return xxVar.toString();
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
        fcg scoreboard;
        fby objective;
        gga level = flk.Q().s;
        if (level != null && (objective = (scoreboard = level.R()).a(getObjective())) != null) {
            String name = getName();
            fcf scoreHolder = fcf.c(name);
            fcc scoreInfo = scoreboard.d(scoreHolder, objective);
            if (scoreInfo != null) {
                return scoreInfo.a(objective.a(yi.b));
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
