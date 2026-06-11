package net.labymod.v26_1_2.mixins.client.scoreboard;

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
import net.minecraft.client.Minecraft;
import net.minecraft.world.scores.DisplaySlot;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerScoreEntry;
import net.minecraft.world.scores.PlayerScores;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.ScoreHolder;
import net.minecraft.world.scores.Scoreboard;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/client/scoreboard/MixinScoreboard.class */
@Mixin({Scoreboard.class})
@Implements({@Interface(iface = net.labymod.api.client.scoreboard.Scoreboard.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinScoreboard implements net.labymod.api.client.scoreboard.Scoreboard {
    private final Map<String, List<String>> labyMod$entriesByObjective = new Object2ObjectOpenHashMap();

    @Shadow
    @Final
    private Object2ObjectMap<String, PlayerTeam> teamsByPlayer;

    @Shadow
    @Final
    private Object2ObjectMap<String, PlayerTeam> teamsByName;

    @Shadow
    @Final
    private Map<String, PlayerScores> playerScores;

    @Shadow
    @Final
    private Object2ObjectMap<String, Objective> objectivesByName;

    @Shadow
    public abstract Objective shadow$getObjective(@Nullable String str);

    @Shadow
    public abstract Objective getDisplayObjective(DisplaySlot displaySlot);

    @Shadow
    public abstract Object2IntMap<Objective> listPlayerScores(ScoreHolder scoreHolder);

    @Shadow
    public abstract Collection<PlayerScoreEntry> listPlayerScores(Objective objective);

    @Inject(method = {"addPlayerToTeam"}, at = {@At(value = "INVOKE", target = "Ljava/util/Collection;add(Ljava/lang/Object;)Z", shift = At.Shift.AFTER)})
    private void addPlayerToTeam(String entry, PlayerTeam team, CallbackInfoReturnable<Boolean> callback) {
        Minecraft.getInstance().execute(() -> {
            Laby.fireEvent(new ScoreboardTeamEntryAddEvent((ScoreboardTeam) team, entry));
        });
    }

    @Inject(method = {"removePlayerFromTeam(Ljava/lang/String;Lnet/minecraft/world/scores/PlayerTeam;)V"}, at = {@At(value = "INVOKE", target = "Ljava/lang/IllegalStateException;<init>(Ljava/lang/String;)V", shift = At.Shift.BEFORE, remap = false)}, cancellable = true)
    private void fixServerSideExceptionSpam(String $$0, PlayerTeam $$1, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = {"removePlayerFromTeam(Ljava/lang/String;Lnet/minecraft/world/scores/PlayerTeam;)V"}, at = {@At(value = "INVOKE", target = "Ljava/util/Collection;remove(Ljava/lang/Object;)Z", shift = At.Shift.AFTER)})
    private void removePlayerFromTeam(String entry, PlayerTeam team, CallbackInfo callback) {
        Minecraft.getInstance().execute(() -> {
            Laby.fireEvent(new ScoreboardTeamEntryRemoveEvent((ScoreboardTeam) team, entry));
        });
    }

    @Inject(method = {"removePlayerTeam"}, at = {@At("HEAD")})
    private void labyMod4$onRemoveTeam(PlayerTeam team, CallbackInfo callback) {
        if (team != null) {
            for (String player : team.getPlayers()) {
                Minecraft.getInstance().execute(() -> {
                    Laby.fireEvent(new ScoreboardTeamEntryRemoveEvent((ScoreboardTeam) team, player));
                });
            }
        }
    }

    @Override // net.labymod.api.client.scoreboard.Scoreboard
    @NotNull
    public Collection<ScoreboardTeam> getTeams() {
        return CastUtil.cast((Collection<?>) this.teamsByName.values());
    }

    @Override // net.labymod.api.client.scoreboard.Scoreboard
    public ScoreboardTeam teamByEntry(@NotNull String entry) {
        return (ScoreboardTeam) this.teamsByPlayer.get(entry);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.scoreboard.Scoreboard
    @Nullable
    public ScoreboardObjective getObjective(@NotNull net.labymod.api.client.scoreboard.DisplaySlot slot) throws MatchException {
        DisplaySlot displaySlot;
        switch (slot) {
            case PLAYER_LIST:
                displaySlot = DisplaySlot.LIST;
                break;
            case SIDEBAR:
                displaySlot = DisplaySlot.SIDEBAR;
                break;
            case BELOW_NAME:
                displaySlot = DisplaySlot.BELOW_NAME;
                break;
            case OTHER:
                displaySlot = DisplaySlot.TEAM_WHITE;
                break;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
        DisplaySlot rawSlot = displaySlot;
        return getDisplayObjective(rawSlot);
    }

    @Intrinsic
    @Nullable
    public ScoreboardObjective labyMod$getObjective(@NotNull String objective) {
        return shadow$getObjective(objective);
    }

    @Override // net.labymod.api.client.scoreboard.Scoreboard
    @NotNull
    public Collection<String> getEntries(ScoreboardObjective scoreboardObjective) {
        Objective objective = (Objective) this.objectivesByName.get(scoreboardObjective.getName());
        if (objective == null) {
            return List.of();
        }
        List<String> entries = this.labyMod$entriesByObjective.computeIfAbsent(scoreboardObjective.getName(), obj -> {
            return new ArrayList();
        });
        entries.clear();
        for (Map.Entry<String, PlayerScores> entry : this.playerScores.entrySet()) {
            PlayerScores value = entry.getValue();
            if (value.get(objective) != null) {
                entries.add(entry.getKey());
            }
        }
        return entries;
    }

    @Override // net.labymod.api.client.scoreboard.Scoreboard
    @NotNull
    public Collection<ScoreboardScore> getScores(ScoreboardObjective objective) {
        List<ScoreboardScore> scores = new ArrayList<>();
        for (Map.Entry<String, PlayerScores> entry : this.playerScores.entrySet()) {
            PlayerScores playerScores = entry.getValue();
            Score score = playerScores.get((Objective) objective);
            if (score != null) {
                NumberFormatMapper numberFormatMapper = Laby.references().getNumberFormatMapper();
                scores.add(new DefaultScoreboardScore(entry.getKey(), score.value(), Laby.references().componentMapper().fromMinecraftComponent(score.display()), numberFormatMapper == null ? null : numberFormatMapper.fromMinecraft(score.numberFormat())));
            }
        }
        return scores;
    }

    @Insert(method = {"onObjectiveChanged"}, at = @At("TAIL"))
    private void onObjectiveChanged(Objective objective, InsertInfo callback) {
        Minecraft.getInstance().execute(() -> {
            Laby.fireEvent(new ScoreboardObjectiveUpdateEvent((ScoreboardObjective) objective));
        });
    }

    @Insert(method = {"onScoreChanged"}, at = @At("TAIL"))
    private void onScoreChanged(ScoreHolder holder, Objective objective, Score score, InsertInfo callback) {
        Minecraft.getInstance().execute(() -> {
            NumberFormatMapper numberFormatMapper = Laby.references().getNumberFormatMapper();
            ComponentMapper componentMapper = Laby.references().componentMapper();
            Component mappedComponent = componentMapper.fromMinecraftComponent(holder.getFeedbackDisplayName());
            ScoreboardScore scoreboardScore = new DefaultScoreboardScore(holder.getScoreboardName(), score.value(), mappedComponent, numberFormatMapper == null ? null : numberFormatMapper.fromMinecraft(score.numberFormat()));
            Laby.fireEvent(new ScoreboardScoreUpdateEvent(scoreboardScore, (ScoreboardObjective) objective));
        });
    }

    @Insert(method = {"onObjectiveRemoved"}, at = @At("TAIL"))
    private void onObjectiveRemoved(Objective objective, InsertInfo callback) {
        this.labyMod$entriesByObjective.remove(objective.getName());
    }
}
