package net.labymod.v1_21_10.client.network.chat;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.ComponentService;
import net.labymod.api.client.component.ScoreComponent;
import net.labymod.api.client.scoreboard.Scoreboard;
import net.labymod.api.client.scoreboard.ScoreboardObjective;
import net.labymod.v1_21_10.client.network.chat.contents.ScoreContentsAccessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/client/network/chat/VersionedScoreComponent.class */
public class VersionedScoreComponent extends VersionedBaseComponent<ScoreComponent, xy> implements ScoreComponent {
    public VersionedScoreComponent(ym holder) {
        super(holder);
    }

    @Override // net.labymod.api.client.component.ScoreComponent
    public String getName() {
        ze zeVar = (xy) getContents();
        if (zeVar instanceof ze) {
            ze score = zeVar;
            return (String) score.b().right().orElseThrow();
        }
        return zeVar.toString();
    }

    @Override // net.labymod.api.client.component.ScoreComponent
    public String getObjective() {
        ze zeVar = (xy) getContents();
        if (zeVar instanceof ze) {
            ze score = zeVar;
            return score.c();
        }
        return zeVar.toString();
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
        fpn scoreboard;
        fpf objective;
        gzn level = fzz.W().r;
        if (level != null && (objective = (scoreboard = level.W()).a(getObjective())) != null) {
            String name = getName();
            fpm scoreHolder = fpm.c(name);
            fpj scoreInfo = scoreboard.d(scoreHolder, objective);
            if (scoreInfo != null) {
                return scoreInfo.a(objective.a(zz.b));
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
