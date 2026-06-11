package net.labymod.v1_16_5.mixins.client.scoreboard;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/scoreboard/MixinObjective.class */
@Mixin({ddk.class})
public class MixinObjective implements ScoreboardObjective {

    @Shadow
    @Final
    private ddn a;

    @Shadow
    @Final
    private String b;

    @Shadow
    private a f;

    @Shadow
    private nr d;

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

    /* JADX INFO: renamed from: net.labymod.v1_16_5.mixins.client.scoreboard.MixinObjective$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/scoreboard/MixinObjective$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$scores$criteria$ObjectiveCriteria$RenderType = new int[a.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$world$scores$criteria$ObjectiveCriteria$RenderType[a.a.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$world$scores$criteria$ObjectiveCriteria$RenderType[a.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardObjective
    @NotNull
    public ObjectiveRenderType getRenderType() {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$world$scores$criteria$ObjectiveCriteria$RenderType[this.f.ordinal()]) {
            case 1:
                return ObjectiveRenderType.INTEGER;
            case 2:
                return ObjectiveRenderType.HEARTS;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(this.f));
        }
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardObjective
    @NotNull
    public ScoreboardScore getOrCreateScore(@NotNull String entry) {
        return this.a.c(entry, (ddk) this);
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardObjective
    @Nullable
    public ScoreboardScore getScore(@NotNull String entry) {
        Map<ddk, ddm> scores = this.a.e(entry);
        if (scores != null) {
            return (ScoreboardScore) scores.get((ddk) this);
        }
        return null;
    }
}
