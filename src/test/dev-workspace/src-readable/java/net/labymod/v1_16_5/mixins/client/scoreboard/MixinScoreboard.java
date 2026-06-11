package net.labymod.v1_16_5.mixins.client.scoreboard;

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
import net.labymod.v1_16_5.client.util.MinecraftUtil;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/scoreboard/MixinScoreboard.class */
@Mixin({ddn.class})
@Implements({@Interface(iface = Scoreboard.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinScoreboard implements Scoreboard {
    private final Map<String, List<String>> labyMod$entriesByObjective = new Object2ObjectOpenHashMap();

    @Shadow
    @Final
    private Map<String, ddl> f;

    @Shadow
    @Final
    private Map<String, ddl> e;

    @Shadow
    @Final
    private Map<String, ddk> a;

    @Shadow
    @Final
    private Map<String, Map<ddk, ddm>> c;

    @Shadow
    public abstract ddk a(int i);

    @Shadow
    public abstract Collection<ddm> i(ddk ddkVar);

    @Shadow
    public abstract ddk shadow$d(@Nullable String str);

    @Inject(method = {"addPlayerToTeam"}, at = {@At(value = "INVOKE", target = "Ljava/util/Collection;add(Ljava/lang/Object;)Z", shift = At.Shift.AFTER)})
    private void addPlayerToTeam(String entry, ddl team, CallbackInfoReturnable<Boolean> callback) {
        MinecraftUtil.fireDelayedEvent(() -> {
            return new ScoreboardTeamEntryAddEvent((ScoreboardTeam) team, entry);
        });
    }

    @Inject(method = {"removePlayerFromTeam(Ljava/lang/String;Lnet/minecraft/world/scores/PlayerTeam;)V"}, at = {@At(value = "INVOKE", target = "Ljava/lang/IllegalStateException;<init>(Ljava/lang/String;)V", shift = At.Shift.BEFORE, remap = false)}, cancellable = true)
    private void fixServerSideExceptionSpam(String $$0, ddl $$1, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = {"removePlayerFromTeam(Ljava/lang/String;Lnet/minecraft/world/scores/PlayerTeam;)V"}, at = {@At(value = "INVOKE", target = "Ljava/util/Collection;remove(Ljava/lang/Object;)Z", shift = At.Shift.AFTER)})
    private void removePlayerFromTeam(String entry, ddl team, CallbackInfo callback) {
        MinecraftUtil.fireDelayedEvent(() -> {
            return new ScoreboardTeamEntryRemoveEvent((ScoreboardTeam) team, entry);
        });
    }

    @Inject(method = {"removePlayerTeam"}, at = {@At("HEAD")})
    private void labyMod4$onRemoveTeam(ddl team, CallbackInfo callback) {
        if (team != null) {
            for (String player : team.g()) {
                MinecraftUtil.fireDelayedEvent(() -> {
                    return new ScoreboardTeamEntryRemoveEvent((ScoreboardTeam) team, player);
                });
            }
        }
    }

    @Override // net.labymod.api.client.scoreboard.Scoreboard
    @NotNull
    public Collection<ScoreboardTeam> getTeams() {
        return CastUtil.cast((Collection<?>) this.e.values());
    }

    @Override // net.labymod.api.client.scoreboard.Scoreboard
    public ScoreboardTeam teamByEntry(@NotNull String entry) {
        return this.f.get(entry);
    }

    @Override // net.labymod.api.client.scoreboard.Scoreboard
    @Nullable
    public ScoreboardObjective getObjective(@NotNull DisplaySlot slot) {
        int rawSlot = -1;
        switch (slot) {
            case PLAYER_LIST:
                rawSlot = 0;
                break;
            case SIDEBAR:
                rawSlot = 1;
                break;
            case BELOW_NAME:
                rawSlot = 2;
                break;
            case OTHER:
                rawSlot = -1;
                break;
        }
        return a(rawSlot);
    }

    @Intrinsic
    @Nullable
    public ScoreboardObjective labyMod$getObjective(@NotNull String objective) {
        return shadow$d(objective);
    }

    @Override // net.labymod.api.client.scoreboard.Scoreboard
    @NotNull
    public Collection<String> getEntries(ScoreboardObjective scoreboardObjective) {
        ddk objective = this.a.get(scoreboardObjective.getName());
        if (objective == null) {
            return List.of();
        }
        List<String> entries = this.labyMod$entriesByObjective.computeIfAbsent(scoreboardObjective.getName(), obj -> {
            return new ArrayList();
        });
        entries.clear();
        for (Map.Entry<String, Map<ddk, ddm>> entry : this.c.entrySet()) {
            Map<ddk, ddm> value = entry.getValue();
            if (value.containsKey(objective)) {
                entries.add(entry.getKey());
            }
        }
        return entries;
    }

    @Override // net.labymod.api.client.scoreboard.Scoreboard
    @NotNull
    public Collection<ScoreboardScore> getScores(ScoreboardObjective objective) {
        return CastUtil.cast((Collection<?>) i((ddk) objective));
    }

    @Insert(method = {"onObjectiveChanged"}, at = @At("TAIL"))
    private void onObjectiveChanged(ddk objective, InsertInfo callback) {
        MinecraftUtil.fireDelayedEvent(() -> {
            return new ScoreboardObjectiveUpdateEvent((ScoreboardObjective) objective);
        });
    }

    @Insert(method = {"onScoreChanged"}, at = @At("TAIL"))
    private void onScoreChanged(ddm score, InsertInfo callback) {
        MinecraftUtil.fireDelayedEvent(() -> {
            return new ScoreboardScoreUpdateEvent((ScoreboardScore) score, score.d());
        });
    }

    @Insert(method = {"onObjectiveRemoved"}, at = @At("TAIL"))
    private void onObjectiveRemoved(ddk objective, InsertInfo callback) {
        this.labyMod$entriesByObjective.remove(objective.b());
    }
}
