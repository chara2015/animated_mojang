package net.labymod.api.client.scoreboard;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.numbers.NumberFormat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/scoreboard/ScoreboardScore.class */
public interface ScoreboardScore {
    String getName();

    int getValue();

    default boolean isHidden() {
        String name = getName();
        return name == null || name.startsWith("#");
    }

    default Component getOwnerName() {
        return Component.text(getName());
    }

    default Component formatValue(NumberFormat format) {
        return format.format(getValue());
    }

    static Component formatValue(ScoreboardScore score, NumberFormat format) {
        return score == null ? format.format(0) : score.formatValue(format);
    }
}
