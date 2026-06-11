package net.labymod.v1_21_11.mixins.client.scoreboard;

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

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/scoreboard/MixinScoreboard.class */
@Mixin({Scoreboard.class})
@Implements({@Interface(iface = net.labymod.api.client.scoreboard.Scoreboard.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinScoreboard implements net.labymod.api.client.scoreboard.Scoreboard {
    private final Map<String, List<String>> labyMod$entriesByObjective = new Object2ObjectOpenHashMap();

    @Shadow
    @Final
    private Object2ObjectMap<String, PlayerTeam> h;

    @Shadow
    @Final
    private Object2ObjectMap<String, PlayerTeam> g;

    @Shadow
    @Final
    private Map<String, PlayerScores> e;

    @Shadow
    @Final
    private Object2ObjectMap<String, Objective> c;

    @Shadow
    public abstract Objective shadow$a(@Nullable String str);

    @Shadow
    public abstract Objective a(DisplaySlot displaySlot);

    @Shadow
    public abstract Object2IntMap<Objective> c(ScoreHolder scoreHolder);

    @Shadow
    public abstract Collection<PlayerScoreEntry> i(Objective objective);

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

    @NotNull
    public Collection<ScoreboardTeam> getTeams() {
        return CastUtil.cast(this.g.values());
    }

    public ScoreboardTeam teamByEntry(@NotNull String entry) {
        return (ScoreboardTeam) this.h.get(entry);
    }

    /* JADX INFO: renamed from: net.labymod.v1_21_11.mixins.client.scoreboard.MixinScoreboard$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/scoreboard/MixinScoreboard$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$labymod$api$client$scoreboard$DisplaySlot = new int[net.labymod.api.client.scoreboard.DisplaySlot.values().length];

        static {
            try {
                $SwitchMap$net$labymod$api$client$scoreboard$DisplaySlot[net.labymod.api.client.scoreboard.DisplaySlot.PLAYER_LIST.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$labymod$api$client$scoreboard$DisplaySlot[net.labymod.api.client.scoreboard.DisplaySlot.SIDEBAR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$labymod$api$client$scoreboard$DisplaySlot[net.labymod.api.client.scoreboard.DisplaySlot.BELOW_NAME.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$labymod$api$client$scoreboard$DisplaySlot[net.labymod.api.client.scoreboard.DisplaySlot.OTHER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Nullable
    public ScoreboardObjective getObjective(@NotNull net.labymod.api.client.scoreboard.DisplaySlot slot) throws MatchException {
        DisplaySlot displaySlot;
        switch (AnonymousClass1.$SwitchMap$net$labymod$api$client$scoreboard$DisplaySlot[slot.ordinal()]) {
            case 1:
                displaySlot = DisplaySlot.LIST;
                break;
            case 2:
                displaySlot = DisplaySlot.SIDEBAR;
                break;
            case 3:
                displaySlot = DisplaySlot.BELOW_NAME;
                break;
            case 4:
                displaySlot = DisplaySlot.TEAM_WHITE;
                break;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
        DisplaySlot rawSlot = displaySlot;
        return a(rawSlot);
    }

    @Intrinsic
    @Nullable
    public ScoreboardObjective labyMod$getObjective(@NotNull String objective) {
        return shadow$a(objective);
    }

    @NotNull
    public Collection<String> getEntries(ScoreboardObjective scoreboardObjective) {
        Objective objective = (Objective) this.c.get(scoreboardObjective.getName());
        if (objective == null) {
            return List.of();
        }
        List<String> entries = this.labyMod$entriesByObjective.computeIfAbsent(scoreboardObjective.getName(), obj -> {
            return new ArrayList();
        });
        entries.clear();
        for (Map.Entry<String, PlayerScores> entry : this.e.entrySet()) {
            PlayerScores value = entry.getValue();
            if (value.get(objective) != null) {
                entries.add(entry.getKey());
            }
        }
        return entries;
    }

    @NotNull
    public Collection<ScoreboardScore> getScores(ScoreboardObjective objective) {
        List<ScoreboardScore> scores = new ArrayList<>();
        for (Map.Entry<String, PlayerScores> entry : this.e.entrySet()) {
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
            Laby.fireEvent(new ScoreboardScoreUpdateEvent(new DefaultScoreboardScore(holder.getScoreboardName(), score.value(), mappedComponent, numberFormatMapper == null ? null : numberFormatMapper.fromMinecraft(score.numberFormat())), (ScoreboardObjective) objective));
        });
    }

    @Insert(method = {"onObjectiveRemoved"}, at = @At("TAIL"))
    private void onObjectiveRemoved(Objective objective, InsertInfo callback) {
        this.labyMod$entriesByObjective.remove(objective.getName());
    }
}
