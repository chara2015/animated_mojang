package net.labymod.v1_21_3.mixins.client.scoreboard;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.numbers.NumberFormatMapper;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.client.scoreboard.DisplaySlot;
import net.labymod.api.client.scoreboard.Scoreboard;
import net.labymod.api.client.scoreboard.ScoreboardObjective;
import net.labymod.api.client.scoreboard.ScoreboardScore;
import net.labymod.api.client.scoreboard.ScoreboardTeam;
import net.labymod.api.event.client.scoreboard.ScoreboardObjectiveUpdateEvent;
import net.labymod.api.event.client.scoreboard.ScoreboardScoreUpdateEvent;
import net.labymod.api.event.client.scoreboard.ScoreboardTeamEntryAddEvent;
import net.labymod.api.event.client.scoreboard.ScoreboardTeamEntryRemoveEvent;
import net.labymod.api.util.CastUtil;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.scoreboard.DefaultScoreboardScore;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixins/client/scoreboard/MixinScoreboard.class */
@Mixin({fdd.class})
@Implements({@Interface(iface = Scoreboard.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinScoreboard implements Scoreboard {
    private final Map<String, List<String>> labyMod$entriesByObjective = new Object2ObjectOpenHashMap();

    @Shadow
    @Final
    private Object2ObjectMap<String, fcy> h;

    @Shadow
    @Final
    private Object2ObjectMap<String, fcy> g;

    @Shadow
    @Final
    private Map<String, fcx> e;

    @Shadow
    @Final
    private Object2ObjectMap<String, fcv> c;

    @Shadow
    public abstract fcv shadow$a(@Nullable String str);

    @Shadow
    public abstract fcv a(fcu fcuVar);

    @Shadow
    public abstract Object2IntMap<fcv> c(fdc fdcVar);

    @Shadow
    public abstract Collection<fcw> i(fcv fcvVar);

    @Inject(method = {"addPlayerToTeam"}, at = {@At(value = "INVOKE", target = "Ljava/util/Collection;add(Ljava/lang/Object;)Z", shift = At.Shift.AFTER)})
    private void addPlayerToTeam(String entry, fcy team, CallbackInfoReturnable<Boolean> callback) {
        fmg.Q().execute(() -> {
            Laby.fireEvent(new ScoreboardTeamEntryAddEvent((ScoreboardTeam) team, entry));
        });
    }

    @Inject(method = {"removePlayerFromTeam(Ljava/lang/String;Lnet/minecraft/world/scores/PlayerTeam;)V"}, at = {@At(value = "INVOKE", target = "Ljava/lang/IllegalStateException;<init>(Ljava/lang/String;)V", shift = At.Shift.BEFORE, remap = false)}, cancellable = true)
    private void fixServerSideExceptionSpam(String $$0, fcy $$1, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = {"removePlayerFromTeam(Ljava/lang/String;Lnet/minecraft/world/scores/PlayerTeam;)V"}, at = {@At(value = "INVOKE", target = "Ljava/util/Collection;remove(Ljava/lang/Object;)Z", shift = At.Shift.AFTER)})
    private void removePlayerFromTeam(String entry, fcy team, CallbackInfo callback) {
        fmg.Q().execute(() -> {
            Laby.fireEvent(new ScoreboardTeamEntryRemoveEvent((ScoreboardTeam) team, entry));
        });
    }

    @Inject(method = {"removePlayerTeam"}, at = {@At("HEAD")})
    private void labyMod4$onRemoveTeam(fcy team, CallbackInfo callback) {
        if (team != null) {
            for (String player : team.g()) {
                fmg.Q().execute(() -> {
                    Laby.fireEvent(new ScoreboardTeamEntryRemoveEvent((ScoreboardTeam) team, player));
                });
            }
        }
    }

    @Override // net.labymod.api.client.scoreboard.Scoreboard
    @NotNull
    public Collection<ScoreboardTeam> getTeams() {
        return CastUtil.cast((Collection<?>) this.g.values());
    }

    @Override // net.labymod.api.client.scoreboard.Scoreboard
    public ScoreboardTeam teamByEntry(@NotNull String entry) {
        return (ScoreboardTeam) this.h.get(entry);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.scoreboard.Scoreboard
    @Nullable
    public ScoreboardObjective getObjective(@NotNull DisplaySlot slot) throws MatchException {
        fcu fcuVar;
        switch (slot) {
            case PLAYER_LIST:
                fcuVar = fcu.a;
                break;
            case SIDEBAR:
                fcuVar = fcu.b;
                break;
            case BELOW_NAME:
                fcuVar = fcu.c;
                break;
            case OTHER:
                fcuVar = fcu.s;
                break;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
        fcu rawSlot = fcuVar;
        return a(rawSlot);
    }

    @Intrinsic
    @Nullable
    public ScoreboardObjective labyMod$getObjective(@NotNull String objective) {
        return shadow$a(objective);
    }

    @Override // net.labymod.api.client.scoreboard.Scoreboard
    @NotNull
    public Collection<String> getEntries(ScoreboardObjective scoreboardObjective) {
        fcv objective = (fcv) this.c.get(scoreboardObjective.getName());
        if (objective == null) {
            return List.of();
        }
        List<String> entries = this.labyMod$entriesByObjective.computeIfAbsent(scoreboardObjective.getName(), obj -> {
            return new ArrayList();
        });
        entries.clear();
        for (Map.Entry<String, fcx> entry : this.e.entrySet()) {
            fcx value = entry.getValue();
            if (value.a(objective) != null) {
                entries.add(entry.getKey());
            }
        }
        return entries;
    }

    @Override // net.labymod.api.client.scoreboard.Scoreboard
    @NotNull
    public Collection<ScoreboardScore> getScores(ScoreboardObjective objective) {
        List<ScoreboardScore> scores = new ArrayList<>();
        for (Map.Entry<String, fcx> entry : this.e.entrySet()) {
            fcx playerScores = entry.getValue();
            fda score = playerScores.a((fcv) objective);
            if (score != null) {
                NumberFormatMapper numberFormatMapper = Laby.references().getNumberFormatMapper();
                scores.add(new DefaultScoreboardScore(entry.getKey(), score.a(), Laby.references().componentMapper().fromMinecraftComponent(score.d()), numberFormatMapper == null ? null : numberFormatMapper.fromMinecraft(score.c())));
            }
        }
        return scores;
    }

    @Insert(method = {"onObjectiveChanged"}, at = @At("TAIL"))
    private void onObjectiveChanged(fcv objective, InsertInfo callback) {
        fmg.Q().execute(() -> {
            Laby.fireEvent(new ScoreboardObjectiveUpdateEvent((ScoreboardObjective) objective));
        });
    }

    @Insert(method = {"onScoreChanged"}, at = @At("TAIL"))
    private void onScoreChanged(fdc holder, fcv objective, fda score, InsertInfo callback) {
        fmg.Q().execute(() -> {
            NumberFormatMapper numberFormatMapper = Laby.references().getNumberFormatMapper();
            ComponentMapper componentMapper = Laby.references().componentMapper();
            Component mappedComponent = componentMapper.fromMinecraftComponent(holder.hc());
            ScoreboardScore scoreboardScore = new DefaultScoreboardScore(holder.cI(), score.a(), mappedComponent, numberFormatMapper == null ? null : numberFormatMapper.fromMinecraft(score.c()));
            Laby.fireEvent(new ScoreboardScoreUpdateEvent(scoreboardScore, (ScoreboardObjective) objective));
        });
    }

    @Insert(method = {"onObjectiveRemoved"}, at = @At("TAIL"))
    private void onObjectiveRemoved(fcv objective, InsertInfo callback) {
        this.labyMod$entriesByObjective.remove(objective.b());
    }
}
