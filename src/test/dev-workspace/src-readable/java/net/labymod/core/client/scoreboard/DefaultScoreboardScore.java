package net.labymod.core.client.scoreboard;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Objects;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.numbers.NumberFormat;
import net.labymod.api.client.scoreboard.ScoreboardScore;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/scoreboard/DefaultScoreboardScore.class */
public final class DefaultScoreboardScore extends Record implements ScoreboardScore {
    private final String owner;
    private final int value;

    @Nullable
    private final Component displayName;

    @Nullable
    private final NumberFormat format;

    public DefaultScoreboardScore(String owner, int value, @Nullable Component displayName, @Nullable NumberFormat format) {
        this.owner = owner;
        this.value = value;
        this.displayName = displayName;
        this.format = format;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, DefaultScoreboardScore.class), DefaultScoreboardScore.class, "owner;value;displayName;format", "FIELD:Lnet/labymod/core/client/scoreboard/DefaultScoreboardScore;->owner:Ljava/lang/String;", "FIELD:Lnet/labymod/core/client/scoreboard/DefaultScoreboardScore;->value:I", "FIELD:Lnet/labymod/core/client/scoreboard/DefaultScoreboardScore;->displayName:Lnet/labymod/api/client/component/Component;", "FIELD:Lnet/labymod/core/client/scoreboard/DefaultScoreboardScore;->format:Lnet/labymod/api/client/component/format/numbers/NumberFormat;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, DefaultScoreboardScore.class), DefaultScoreboardScore.class, "owner;value;displayName;format", "FIELD:Lnet/labymod/core/client/scoreboard/DefaultScoreboardScore;->owner:Ljava/lang/String;", "FIELD:Lnet/labymod/core/client/scoreboard/DefaultScoreboardScore;->value:I", "FIELD:Lnet/labymod/core/client/scoreboard/DefaultScoreboardScore;->displayName:Lnet/labymod/api/client/component/Component;", "FIELD:Lnet/labymod/core/client/scoreboard/DefaultScoreboardScore;->format:Lnet/labymod/api/client/component/format/numbers/NumberFormat;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, DefaultScoreboardScore.class, Object.class), DefaultScoreboardScore.class, "owner;value;displayName;format", "FIELD:Lnet/labymod/core/client/scoreboard/DefaultScoreboardScore;->owner:Ljava/lang/String;", "FIELD:Lnet/labymod/core/client/scoreboard/DefaultScoreboardScore;->value:I", "FIELD:Lnet/labymod/core/client/scoreboard/DefaultScoreboardScore;->displayName:Lnet/labymod/api/client/component/Component;", "FIELD:Lnet/labymod/core/client/scoreboard/DefaultScoreboardScore;->format:Lnet/labymod/api/client/component/format/numbers/NumberFormat;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public String owner() {
        return this.owner;
    }

    public int value() {
        return this.value;
    }

    @Nullable
    public Component displayName() {
        return this.displayName;
    }

    @Nullable
    public NumberFormat format() {
        return this.format;
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardScore
    public String getName() {
        return this.owner;
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardScore
    public int getValue() {
        return this.value;
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardScore
    public Component getOwnerName() {
        return this.displayName == null ? Component.text(this.owner) : this.displayName;
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardScore
    public Component formatValue(NumberFormat format) {
        return ((NumberFormat) Objects.requireNonNullElse(this.format, format)).format(this.value);
    }
}
