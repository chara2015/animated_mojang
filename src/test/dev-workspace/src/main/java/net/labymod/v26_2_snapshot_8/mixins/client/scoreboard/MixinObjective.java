package net.labymod.v26_2_snapshot_8.mixins.client.scoreboard;

import net.labymod.api.Laby;
import net.labymod.api.client.component.format.numbers.NumberFormatMapper;
import net.labymod.api.client.scoreboard.ObjectiveRenderType;
import net.labymod.api.client.scoreboard.ScoreboardObjective;
import net.labymod.api.client.scoreboard.ScoreboardScore;
import net.labymod.core.client.scoreboard.DefaultScoreboardScore;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.numbers.NumberFormat;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.ReadOnlyScoreInfo;
import net.minecraft.world.scores.ScoreAccess;
import net.minecraft.world.scores.ScoreHolder;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/scoreboard/MixinObjective.class */
@Mixin({Objective.class})
public class MixinObjective implements ScoreboardObjective {

    @Shadow
    @Final
    private Scoreboard scoreboard;

    @Shadow
    @Final
    private String name;

    @Shadow
    private ObjectiveCriteria.RenderType renderType;

    @Shadow
    private Component displayName;

    @Shadow
    private NumberFormat numberFormat;

    @Override // net.labymod.api.client.scoreboard.ScoreboardObjective
    @NotNull
    public String getName() {
        return this.name;
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardObjective
    @NotNull
    public net.labymod.api.client.component.Component getTitle() {
        return Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(this.displayName);
    }

    /* JADX INFO: renamed from: net.labymod.v26_2_snapshot_8.mixins.client.scoreboard.MixinObjective$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/scoreboard/MixinObjective$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$scores$criteria$ObjectiveCriteria$RenderType = new int[ObjectiveCriteria.RenderType.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$world$scores$criteria$ObjectiveCriteria$RenderType[ObjectiveCriteria.RenderType.HEARTS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$world$scores$criteria$ObjectiveCriteria$RenderType[ObjectiveCriteria.RenderType.INTEGER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.scoreboard.ScoreboardObjective
    @NotNull
    public ObjectiveRenderType getRenderType() throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$world$scores$criteria$ObjectiveCriteria$RenderType[this.renderType.ordinal()]) {
            case 1:
                return ObjectiveRenderType.HEARTS;
            case 2:
                return ObjectiveRenderType.INTEGER;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardObjective
    @NotNull
    public ScoreboardScore getOrCreateScore(@NotNull String entry) {
        ScoreAccess scoreAccess = this.scoreboard.getOrCreatePlayerScore(ScoreHolder.forNameOnly(entry), (Objective) this);
        return new DefaultScoreboardScore(getName(), scoreAccess.get(), Laby.references().componentMapper().fromMinecraftComponent(scoreAccess.display()), net.labymod.api.client.component.format.numbers.NumberFormat.noStyle());
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardObjective
    @Nullable
    public ScoreboardScore getScore(@NotNull String entry) {
        ReadOnlyScoreInfo scoreInfo = this.scoreboard.getPlayerScoreInfo(ScoreHolder.forNameOnly(entry), (Objective) this);
        if (scoreInfo == null) {
            return null;
        }
        NumberFormatMapper numberFormatMapper = Laby.references().getNumberFormatMapper();
        return new DefaultScoreboardScore(getName(), scoreInfo.value(), null, numberFormatMapper == null ? null : numberFormatMapper.fromMinecraft(scoreInfo.numberFormat()));
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardObjective
    @Nullable
    public net.labymod.api.client.component.format.numbers.NumberFormat getNumberFormat() {
        NumberFormatMapper numberFormatMapper;
        if (this.numberFormat == null || (numberFormatMapper = Laby.references().getNumberFormatMapper()) == null) {
            return null;
        }
        return numberFormatMapper.fromMinecraft(this.numberFormat);
    }
}
