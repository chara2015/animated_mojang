package net.labymod.v1_21_11.client.network.chat;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.ComponentService;
import net.labymod.api.client.component.ScoreComponent;
import net.labymod.api.client.scoreboard.ScoreboardObjective;
import net.labymod.api.util.CastUtil;
import net.labymod.v1_21_11.client.network.chat.contents.ScoreContentsAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.ScoreContents;
import net.minecraft.network.chat.numbers.StyledFormat;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.ReadOnlyScoreInfo;
import net.minecraft.world.scores.ScoreHolder;
import net.minecraft.world.scores.Scoreboard;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/network/chat/VersionedScoreComponent.class */
public class VersionedScoreComponent extends VersionedBaseComponent<ScoreComponent, ComponentContents> implements ScoreComponent {
    public VersionedScoreComponent(MutableComponent holder) {
        super(holder);
    }

    public String getName() {
        ScoreContents scoreContents = (ComponentContents) getContents();
        if (scoreContents instanceof ScoreContents) {
            ScoreContents score = scoreContents;
            return (String) score.name().right().orElseThrow();
        }
        return scoreContents.toString();
    }

    public String getObjective() {
        ScoreContents scoreContents = (ComponentContents) getContents();
        if (scoreContents instanceof ScoreContents) {
            ScoreContents score = scoreContents;
            return score.objective();
        }
        return scoreContents.toString();
    }

    /* JADX INFO: renamed from: plainCopy, reason: merged with bridge method [inline-methods] */
    public ScoreComponent m21plainCopy() {
        return ComponentService.scoreComponent(getName(), getObjective());
    }

    public ScoreComponent name(String name) {
        ScoreContentsAccessor contents = getContents();
        if (contents instanceof ScoreContentsAccessor) {
            ScoreContentsAccessor score = contents;
            score.setName(name);
        }
        return this;
    }

    public ScoreComponent objective(String objective) {
        ScoreContentsAccessor contents = getContents();
        if (contents instanceof ScoreContentsAccessor) {
            ScoreContentsAccessor score = contents;
            score.setObjective(objective);
        }
        return this;
    }

    public Component value() {
        Scoreboard scoreboard;
        Objective objective;
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null && (objective = (scoreboard = level.getScoreboard()).getObjective(getObjective())) != null) {
            String name = getName();
            ScoreHolder scoreHolder = ScoreHolder.forNameOnly(name);
            ReadOnlyScoreInfo scoreInfo = scoreboard.getPlayerScoreInfo(scoreHolder, objective);
            if (scoreInfo != null) {
                return (Component) CastUtil.cast(scoreInfo.formatValue(objective.numberFormatOrDefault(StyledFormat.NO_STYLE)));
            }
        }
        return Component.empty();
    }

    public ScoreboardObjective getScoreboardObjective() {
        net.labymod.api.client.scoreboard.Scoreboard scoreboard = Laby.labyAPI().minecraft().getScoreboard();
        if (scoreboard != null) {
            return scoreboard.getObjective(getObjective());
        }
        return null;
    }
}
