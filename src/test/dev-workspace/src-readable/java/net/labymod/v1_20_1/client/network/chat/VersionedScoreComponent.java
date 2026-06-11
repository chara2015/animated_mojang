package net.labymod.v1_20_1.client.network.chat;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.ComponentService;
import net.labymod.api.client.component.ScoreComponent;
import net.labymod.api.client.scoreboard.Scoreboard;
import net.labymod.api.client.scoreboard.ScoreboardObjective;
import net.labymod.v1_20_1.client.network.chat.contents.ScoreContentsAccessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/client/network/chat/VersionedScoreComponent.class */
public class VersionedScoreComponent extends VersionedBaseComponent<ScoreComponent, sx> implements ScoreComponent {
    public VersionedScoreComponent(tj holder) {
        super(holder);
    }

    @Override // net.labymod.api.client.component.ScoreComponent
    public String getName() {
        ud udVar = (sx) getContents();
        if (udVar instanceof ud) {
            ud score = udVar;
            return score.a();
        }
        return udVar.toString();
    }

    @Override // net.labymod.api.client.component.ScoreComponent
    public String getObjective() {
        ud udVar = (sx) getContents();
        if (udVar instanceof ud) {
            ud score = udVar;
            return score.c();
        }
        return udVar.toString();
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
        efg scoreboard;
        efd objective;
        few level = enn.N().s;
        if (level != null && (objective = (scoreboard = level.I()).d(getObjective())) != null) {
            String name = getName();
            if (scoreboard.b(name, objective)) {
                eff score = scoreboard.c(name, objective);
                return Component.text(Integer.valueOf(score.b()));
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
