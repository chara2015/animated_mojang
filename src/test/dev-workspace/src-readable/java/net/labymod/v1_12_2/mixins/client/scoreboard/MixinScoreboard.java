package net.labymod.v1_12_2.mixins.client.scoreboard;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
import net.labymod.v1_12_2.client.util.MinecraftUtil;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/scoreboard/MixinScoreboard.class */
@Mixin({bhk.class})
@Implements({@Interface(iface = Scoreboard.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinScoreboard implements Scoreboard {
    private final Map<String, List<String>> labyMod$entriesByObjective = new Object2ObjectOpenHashMap();

    @Shadow
    @Final
    private Map<String, bhh> e;

    @Shadow
    @Final
    private Map<String, bhh> f;

    @Shadow
    @Final
    private Map<String, bhg> a;

    @Shadow
    @Final
    private Map<String, Map<bhg, bhi>> c;

    @Shadow
    public abstract bhg a(int i);

    @Shadow
    public abstract bhg shadow$b(@Nullable String str);

    @Shadow
    public abstract bhh d(String str);

    @Shadow
    public abstract Collection<bhi> i(bhg bhgVar);

    @Inject(method = {"addPlayerToTeam(Ljava/lang/String;Ljava/lang/String;)Z"}, at = {@At(value = "INVOKE", target = "Ljava/util/Collection;add(Ljava/lang/Object;)Z", shift = At.Shift.AFTER, remap = false)})
    private void addPlayerToTeam(String entry, String teamName, CallbackInfoReturnable<Boolean> callback) {
        bhh bhhVarD = d(teamName);
        if (bhhVarD != null) {
            MinecraftUtil.fireDelayedEvent(() -> {
                return new ScoreboardTeamEntryAddEvent((ScoreboardTeam) bhhVarD, entry);
            });
        }
    }

    @Inject(method = {"removePlayerFromTeam"}, at = {@At(value = "INVOKE", target = "Ljava/lang/IllegalStateException;<init>(Ljava/lang/String;)V", shift = At.Shift.BEFORE, remap = false)}, cancellable = true)
    private void fixServerSideExceptionSpam(String lvt_1_1_, bhh lvt_2_1_, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = {"removeTeam"}, at = {@At("HEAD")})
    private void labyMod4$onRemoveTeam(bhh team, CallbackInfo callback) {
        if (team != null) {
            for (String entry : team.d()) {
                MinecraftUtil.fireDelayedEvent(() -> {
                    return new ScoreboardTeamEntryRemoveEvent((ScoreboardTeam) team, entry);
                });
            }
        }
    }

    @Inject(method = {"removePlayerFromTeam(Ljava/lang/String;Lnet/minecraft/scoreboard/ScorePlayerTeam;)V"}, at = {@At(value = "INVOKE", target = "Ljava/util/Collection;remove(Ljava/lang/Object;)Z", shift = At.Shift.AFTER, remap = false)})
    private void removePlayerFromTeam(String entry, bhh team, CallbackInfo callback) {
        MinecraftUtil.fireDelayedEvent(() -> {
            return new ScoreboardTeamEntryRemoveEvent((ScoreboardTeam) team, entry);
        });
    }

    @Override // net.labymod.api.client.scoreboard.Scoreboard
    @NotNull
    public Collection<ScoreboardTeam> getTeams() {
        return CastUtil.cast((Collection<?>) this.e.values());
    }

    @Override // net.labymod.api.client.scoreboard.Scoreboard
    @NotNull
    public ScoreboardTeam teamByEntry(@NotNull String entry) {
        return this.f.get(entry);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.scoreboard.Scoreboard
    @Nullable
    public ScoreboardObjective getObjective(@NotNull DisplaySlot slot) throws MatchException {
        int i;
        switch (slot) {
            case PLAYER_LIST:
                i = 0;
                break;
            case SIDEBAR:
                i = 1;
                break;
            case BELOW_NAME:
                i = 2;
                break;
            case OTHER:
                i = -1;
                break;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
        int rawSlot = i;
        return a(rawSlot);
    }

    @Intrinsic
    @Nullable
    public ScoreboardObjective labyMod$getObjective(@NotNull String objective) {
        return shadow$b(objective);
    }

    @Override // net.labymod.api.client.scoreboard.Scoreboard
    @NotNull
    public Collection<String> getEntries(ScoreboardObjective scoreboardObjective) {
        bhg objective = this.a.get(scoreboardObjective.getName());
        if (objective == null) {
            return List.of();
        }
        List<String> entries = this.labyMod$entriesByObjective.computeIfAbsent(scoreboardObjective.getName(), obj -> {
            return new ArrayList();
        });
        entries.clear();
        for (Map.Entry<String, Map<bhg, bhi>> entry : this.c.entrySet()) {
            Map<bhg, bhi> value = entry.getValue();
            if (value.containsKey(objective)) {
                entries.add(entry.getKey());
            }
        }
        return entries;
    }

    @Override // net.labymod.api.client.scoreboard.Scoreboard
    @NotNull
    public Collection<ScoreboardScore> getScores(ScoreboardObjective objective) {
        return CastUtil.cast((Collection<?>) i((bhg) objective));
    }

    @Inject(method = {"onObjectiveDisplayNameChanged(Lnet/minecraft/scoreboard/ScoreObjective;)V"}, at = {@At("TAIL")})
    private void onObjectiveChanged(bhg objective, CallbackInfo callback) {
        MinecraftUtil.fireDelayedEvent(() -> {
            return new ScoreboardObjectiveUpdateEvent((ScoreboardObjective) objective);
        });
    }

    @Inject(method = {"onScoreUpdated(Lnet/minecraft/scoreboard/Score;)V"}, at = {@At("TAIL")})
    private void onScoreChanged(bhi score, CallbackInfo callback) {
        MinecraftUtil.fireDelayedEvent(() -> {
            return new ScoreboardScoreUpdateEvent((ScoreboardScore) score, score.d());
        });
    }

    @Insert(method = {"onScoreObjectiveRemoved"}, at = @At("TAIL"))
    private void onObjectiveRemoved(bhg objective, InsertInfo callback) {
        this.labyMod$entriesByObjective.remove(objective.b());
    }
}
