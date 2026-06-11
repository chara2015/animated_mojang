package net.labymod.v1_18_2.mixins.client.chat;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/chat/MixinScoreComponent.class */
@Mixin({qs.class})
@Implements({@Interface(iface = ScoreComponent.class, prefix = "scoreComponent$", remap = Interface.Remap.NONE)})
public abstract class MixinScoreComponent extends MixinBaseComponent<ScoreComponent> implements ScoreComponent {

    @Shadow
    @Mutable
    @Final
    private String e;

    @Shadow
    @Mutable
    @Final
    private String g;

    @Shadow
    @Final
    @Nullable
    private fp f;

    @Shadow
    public abstract qs shadow$k();

    @Intrinsic
    public ScoreComponent scoreComponent$plainCopy() {
        return shadow$k();
    }

    @Intrinsic
    public String scoreComponent$getName() {
        return this.e;
    }

    @Intrinsic
    public String scoreComponent$getObjective() {
        return this.g;
    }

    @Override // net.labymod.api.client.component.ScoreComponent
    public ScoreComponent name(String name) {
        this.e = name;
        return this;
    }

    @Override // net.labymod.api.client.component.ScoreComponent
    public ScoreComponent objective(String objective) {
        this.g = objective;
        return this;
    }

    @Override // net.labymod.api.client.component.ScoreComponent
    public ScoreboardObjective getScoreboardObjective() {
        Scoreboard scoreboard = Laby.labyAPI().minecraft().getScoreboard();
        if (scoreboard != null) {
            return scoreboard.getObjective(this.g);
        }
        return null;
    }

    @Override // net.labymod.api.client.component.ScoreComponent
    public Component value() {
        dqm scoreboard;
        dqj objective;
        ems level = dyr.D().r;
        if (level != null && (objective = (scoreboard = level.J()).d(this.g)) != null && scoreboard.b(this.e, objective)) {
            dql score = scoreboard.c(this.e, objective);
            return Component.text(Integer.valueOf(score.b()));
        }
        return Component.empty();
    }

    public int hashCode() {
        return Objects.hash(c(), this.a, this.e, this.g);
    }
}
