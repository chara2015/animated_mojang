package net.labymod.api.client.scoreboard;

import java.util.Objects;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.numbers.NumberFormat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/scoreboard/ScoreboardObjective.class */
public interface ScoreboardObjective {
    @NotNull
    String getName();

    @NotNull
    Component getTitle();

    @NotNull
    ObjectiveRenderType getRenderType();

    @NotNull
    ScoreboardScore getOrCreateScore(@NotNull String str);

    @Nullable
    ScoreboardScore getScore(@NotNull String str);

    @Nullable
    default NumberFormat getNumberFormat() {
        return NumberFormat.sidebarDefault();
    }

    default NumberFormat getNumberFormatOrDefault(NumberFormat defaultFormat) {
        NumberFormat numberFormat;
        try {
            numberFormat = getNumberFormat();
        } catch (IllegalStateException e) {
            numberFormat = defaultFormat;
        }
        return (NumberFormat) Objects.requireNonNullElse(numberFormat, defaultFormat);
    }
}
