package net.labymod.v1_17_1.mixins.client.scoreboard;

import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.scoreboard.ObjectiveRenderType;
import net.labymod.api.client.scoreboard.ScoreboardObjective;
import net.labymod.api.client.scoreboard.ScoreboardScore;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/client/scoreboard/MixinObjective.class */
@Mixin({dnv.class})
public class MixinObjective implements ScoreboardObjective {

    @Shadow
    @Final
    private dny b;

    @Shadow
    @Final
    private String c;

    @Shadow
    private a g;

    @Shadow
    private os e;

    @Override // net.labymod.api.client.scoreboard.ScoreboardObjective
    @NotNull
    public String getName() {
        return this.c;
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardObjective
    @NotNull
    public Component getTitle() {
        return Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(this.e);
    }

    /* JADX INFO: renamed from: net.labymod.v1_17_1.mixins.client.scoreboard.MixinObjective$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/client/scoreboard/MixinObjective$1.class */
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
        switch (AnonymousClass1.$SwitchMap$net$minecraft$world$scores$criteria$ObjectiveCriteria$RenderType[this.g.ordinal()]) {
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
        return this.b.c(entry, (dnv) this);
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardObjective
    @Nullable
    public ScoreboardScore getScore(@NotNull String entry) {
        Map<dnv, dnx> scores = this.b.e(entry);
        if (scores != null) {
            return (ScoreboardScore) scores.get((dnv) this);
        }
        return null;
    }
}
