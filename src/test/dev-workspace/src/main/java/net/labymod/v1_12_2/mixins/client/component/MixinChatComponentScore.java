package net.labymod.v1_12_2.mixins.client.component;

import java.util.Objects;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.ScoreComponent;
import net.labymod.api.client.scoreboard.ScoreboardObjective;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/component/MixinChatComponentScore.class */
@Mixin({hl.class})
@Implements({@Interface(iface = ScoreComponent.class, prefix = "scoreComponent$", remap = Interface.Remap.NONE)})
public abstract class MixinChatComponentScore extends MixinChatComponentStyle<ScoreComponent> implements ScoreComponent {

    @Shadow
    @Mutable
    @Final
    private String b;

    @Shadow
    @Mutable
    @Final
    private String c;

    @Shadow
    public abstract String e();

    @Intrinsic
    public String scoreComponent$getName() {
        return this.b;
    }

    @Intrinsic
    public String scoreComponent$getObjective() {
        return this.c;
    }

    @Override // net.labymod.api.client.component.ScoreComponent
    public ScoreComponent name(String name) {
        this.b = name;
        return this;
    }

    @Override // net.labymod.api.client.component.ScoreComponent
    public ScoreComponent objective(String objective) {
        this.c = objective;
        return this;
    }

    @Override // net.labymod.api.client.component.ScoreComponent
    public Component value() {
        return Component.text(e());
    }

    @Override // net.labymod.api.client.component.ScoreComponent
    public ScoreboardObjective getScoreboardObjective() {
        chd integratedServer = bib.z().F();
        if (integratedServer != null && integratedServer.M()) {
            bhk scoreboard = integratedServer.C_().a(0).af();
            return scoreboard.b(this.c);
        }
        return null;
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public ScoreComponent plainCopy() {
        return new hl(this.b, this.c);
    }

    @Override // net.labymod.v1_12_2.mixins.client.component.MixinChatComponentStyle
    public int hashCode() {
        return Objects.hash(b(), this.a, this.b, this.c);
    }
}
