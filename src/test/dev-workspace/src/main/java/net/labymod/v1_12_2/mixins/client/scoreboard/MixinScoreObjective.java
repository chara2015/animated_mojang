package net.labymod.v1_12_2.mixins.client.scoreboard;

import java.util.Map;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.serializer.legacy.LegacyComponentSerializer;
import net.labymod.api.client.scoreboard.ObjectiveRenderType;
import net.labymod.api.client.scoreboard.ScoreboardObjective;
import net.labymod.api.client.scoreboard.ScoreboardScore;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/scoreboard/MixinScoreObjective.class */
@Mixin({bhg.class})
public class MixinScoreObjective implements ScoreboardObjective {

    @Shadow
    @Final
    private bhk a;

    @Shadow
    @Final
    private String b;

    @Shadow
    private String e;

    @Shadow
    private a d;
    private Component labyMod$title;

    @Override // net.labymod.api.client.scoreboard.ScoreboardObjective
    @NotNull
    public String getName() {
        return this.b;
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardObjective
    @NotNull
    public Component getTitle() {
        if (this.labyMod$title == null) {
            this.labyMod$title = LegacyComponentSerializer.legacySection().deserialize(this.e);
        }
        return this.labyMod$title;
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardObjective
    @NotNull
    public ObjectiveRenderType getRenderType() {
        if (this.d == null || this.d == a.a) {
            return ObjectiveRenderType.INTEGER;
        }
        return ObjectiveRenderType.HEARTS;
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardObjective
    @NotNull
    public ScoreboardScore getOrCreateScore(@NotNull String entry) {
        return this.a.c(entry, (bhg) this);
    }

    @Override // net.labymod.api.client.scoreboard.ScoreboardObjective
    @Nullable
    public ScoreboardScore getScore(@NotNull String entry) {
        Map<bhg, bhi> scores = this.a.c(entry);
        if (scores != null) {
            return (ScoreboardScore) scores.get((bhg) this);
        }
        return null;
    }

    @Inject(method = {"setDisplayName"}, at = {@At("HEAD")})
    public void labyMod$resetCachedTitle(String p_setDisplayName_1_, CallbackInfo ci) {
        this.labyMod$title = null;
    }
}
