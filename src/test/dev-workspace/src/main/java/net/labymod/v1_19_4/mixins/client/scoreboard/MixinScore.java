package net.labymod.v1_19_4.mixins.client.scoreboard;

import net.labymod.api.client.scoreboard.ScoreboardScore;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/scoreboard/MixinScore.class */
@Mixin({eeb.class})
public class MixinScore implements ScoreboardScore {

    @Shadow
    @Final
    private String d;

    @Shadow
    private int e;

    @Override // net.labymod.api.client.scoreboard.ScoreboardScore
    public String getName() {
        return this.d;
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardScore
    public int getValue() {
        return this.e;
    }
}
