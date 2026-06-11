package net.labymod.v1_21_5.mixins.client.scoreboard;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.numbers.NumberFormat;
import net.labymod.api.client.component.format.numbers.NumberFormatMapper;
import net.labymod.api.client.scoreboard.ObjectiveRenderType;
import net.labymod.api.client.scoreboard.ScoreboardObjective;
import net.labymod.api.client.scoreboard.ScoreboardScore;
import net.labymod.core.client.scoreboard.DefaultScoreboardScore;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/scoreboard/MixinObjective.class */
@Mixin({fgz.class})
public class MixinObjective implements ScoreboardObjective {

    @Shadow
    @Final
    private fhh a;

    @Shadow
    @Final
    private String b;

    @Shadow
    private a f;

    @Shadow
    private xg d;

    @Shadow
    private yw h;

    @Override // net.labymod.api.client.scoreboard.ScoreboardObjective
    @NotNull
    public String getName() {
        return this.b;
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardObjective
    @NotNull
    public Component getTitle() {
        return Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(this.d);
    }

    /* JADX INFO: renamed from: net.labymod.v1_21_5.mixins.client.scoreboard.MixinObjective$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/scoreboard/MixinObjective$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$scores$criteria$ObjectiveCriteria$RenderType = new int[a.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$world$scores$criteria$ObjectiveCriteria$RenderType[a.b.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$world$scores$criteria$ObjectiveCriteria$RenderType[a.a.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.scoreboard.ScoreboardObjective
    @NotNull
    public ObjectiveRenderType getRenderType() throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$world$scores$criteria$ObjectiveCriteria$RenderType[this.f.ordinal()]) {
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
        fhf scoreAccess = this.a.c(fhg.c(entry), (fgz) this);
        return new DefaultScoreboardScore(getName(), scoreAccess.a(), Laby.references().componentMapper().fromMinecraftComponent(scoreAccess.g()), NumberFormat.noStyle());
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardObjective
    @Nullable
    public ScoreboardScore getScore(@NotNull String entry) {
        fhd scoreInfo = this.a.d(fhg.c(entry), (fgz) this);
        if (scoreInfo == null) {
            return null;
        }
        NumberFormatMapper numberFormatMapper = Laby.references().getNumberFormatMapper();
        return new DefaultScoreboardScore(getName(), scoreInfo.a(), null, numberFormatMapper == null ? null : numberFormatMapper.fromMinecraft(scoreInfo.c()));
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardObjective
    @Nullable
    public NumberFormat getNumberFormat() {
        NumberFormatMapper numberFormatMapper;
        if (this.h == null || (numberFormatMapper = Laby.references().getNumberFormatMapper()) == null) {
            return null;
        }
        return numberFormatMapper.fromMinecraft(this.h);
    }
}
