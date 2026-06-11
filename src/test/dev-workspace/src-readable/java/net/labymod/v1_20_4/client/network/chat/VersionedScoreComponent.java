package net.labymod.v1_20_4.client.network.chat;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.ComponentService;
import net.labymod.api.client.component.ScoreComponent;
import net.labymod.api.client.scoreboard.Scoreboard;
import net.labymod.api.client.scoreboard.ScoreboardObjective;
import net.labymod.v1_20_4.client.network.chat.contents.ScoreContentsAccessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/client/network/chat/VersionedScoreComponent.class */
public class VersionedScoreComponent extends VersionedBaseComponent<ScoreComponent, vg> implements ScoreComponent {
    public VersionedScoreComponent(vt holder) {
        super(holder);
    }

    @Override // net.labymod.api.client.component.ScoreComponent
    public String getName() {
        wn wnVar = (vg) getContents();
        if (wnVar instanceof wn) {
            wn score = wnVar;
            return score.b();
        }
        return wnVar.toString();
    }

    @Override // net.labymod.api.client.component.ScoreComponent
    public String getObjective() {
        wn wnVar = (vg) getContents();
        if (wnVar instanceof wn) {
            wn score = wnVar;
            return score.d();
        }
        return wnVar.toString();
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
        emx scoreboard;
        emp objective;
        fns level = evi.O().r;
        if (level != null && (objective = (scoreboard = level.K()).a(getObjective())) != null) {
            String name = getName();
            emw scoreHolder = emw.d(name);
            emt scoreInfo = scoreboard.d(scoreHolder, objective);
            if (scoreInfo != null) {
                return scoreInfo.a(objective.a(wy.b));
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
