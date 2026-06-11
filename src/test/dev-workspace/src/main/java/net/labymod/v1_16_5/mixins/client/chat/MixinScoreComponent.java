package net.labymod.v1_16_5.mixins.client.chat;

import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.ScoreComponent;
import net.labymod.api.client.scoreboard.Scoreboard;
import net.labymod.api.client.scoreboard.ScoreboardObjective;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/chat/MixinScoreComponent.class */
@Mixin({nz.class})
@Implements({@Interface(iface = ScoreComponent.class, prefix = "scoreComponent$", remap = Interface.Remap.NONE)})
public abstract class MixinScoreComponent extends MixinBaseComponent<ScoreComponent> implements ScoreComponent {

    @Shadow
    @Mutable
    @Final
    private String d;

    @Shadow
    @Mutable
    @Final
    private String f;

    @Shadow
    @Final
    @Nullable
    private fc e;

    @Shadow
    public abstract nz shadow$k();

    @Intrinsic
    public ScoreComponent scoreComponent$plainCopy() {
        return shadow$k();
    }

    @Intrinsic
    public String scoreComponent$getName() {
        return this.d;
    }

    @Intrinsic
    public String scoreComponent$getObjective() {
        return this.f;
    }

    @Override // net.labymod.api.client.component.ScoreComponent
    public ScoreComponent name(String name) {
        this.d = name;
        return this;
    }

    @Override // net.labymod.api.client.component.ScoreComponent
    public ScoreComponent objective(String objective) {
        this.f = objective;
        return this;
    }

    @Override // net.labymod.api.client.component.ScoreComponent
    public ScoreboardObjective getScoreboardObjective() {
        Scoreboard scoreboard = Laby.labyAPI().minecraft().getScoreboard();
        if (scoreboard != null) {
            return scoreboard.getObjective(this.f);
        }
        return null;
    }

    @Override // net.labymod.api.client.component.ScoreComponent
    public Component value() {
        ddn scoreboard;
        ddk objective;
        dwt level = djz.C().r;
        if (level != null && (objective = (scoreboard = level.G()).d(this.f)) != null && scoreboard.b(this.d, objective)) {
            ddm score = scoreboard.c(this.d, objective);
            return Component.text(Integer.valueOf(score.b()));
        }
        return Component.empty();
    }

    public int hashCode() {
        return Objects.hash(c(), this.a, this.d, this.f);
    }
}
