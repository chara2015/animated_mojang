package net.minecraft.world.scores;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.numbers.NumberFormat;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/scores/Scoreboard.class */
public class Scoreboard {
    public static final String HIDDEN_SCORE_PREFIX = "#";
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Object2ObjectMap<String, Objective> objectivesByName = new Object2ObjectOpenHashMap(16, 0.5f);
    private final Reference2ObjectMap<ObjectiveCriteria, List<Objective>> objectivesByCriteria = new Reference2ObjectOpenHashMap();
    private final Map<String, PlayerScores> playerScores = new Object2ObjectOpenHashMap(16, 0.5f);
    private final Map<DisplaySlot, Objective> displayObjectives = new EnumMap(DisplaySlot.class);
    private final Object2ObjectMap<String, PlayerTeam> teamsByName = new Object2ObjectOpenHashMap();
    private final Object2ObjectMap<String, PlayerTeam> teamsByPlayer = new Object2ObjectOpenHashMap();

    public Objective getObjective(String $$0) {
        return (Objective) this.objectivesByName.get($$0);
    }

    public Objective addObjective(String $$0, ObjectiveCriteria $$1, Component $$2, ObjectiveCriteria.RenderType $$3, boolean $$4, NumberFormat $$5) {
        if (this.objectivesByName.containsKey($$0)) {
            throw new IllegalArgumentException("An objective with the name '" + $$0 + "' already exists!");
        }
        Objective $$6 = new Objective(this, $$0, $$1, $$2, $$3, $$4, $$5);
        ((List) this.objectivesByCriteria.computeIfAbsent($$1, $$02 -> {
            return Lists.newArrayList();
        })).add($$6);
        this.objectivesByName.put($$0, $$6);
        onObjectiveAdded($$6);
        return $$6;
    }

    public final void forAllObjectives(ObjectiveCriteria $$0, ScoreHolder $$1, Consumer<ScoreAccess> $$2) {
        ((List) this.objectivesByCriteria.getOrDefault($$0, Collections.emptyList())).forEach($$22 -> {
            $$2.accept(getOrCreatePlayerScore($$1, $$22, true));
        });
    }

    private PlayerScores getOrCreatePlayerInfo(String $$0) {
        return this.playerScores.computeIfAbsent($$0, $$02 -> {
            return new PlayerScores();
        });
    }

    public ScoreAccess getOrCreatePlayerScore(ScoreHolder $$0, Objective $$1) {
        return getOrCreatePlayerScore($$0, $$1, false);
    }

    public ScoreAccess getOrCreatePlayerScore(final ScoreHolder $$0, final Objective $$1, boolean $$2) {
        final boolean $$3 = $$2 || !$$1.getCriteria().isReadOnly();
        PlayerScores $$4 = getOrCreatePlayerInfo($$0.getScoreboardName());
        final MutableBoolean $$5 = new MutableBoolean();
        final Score $$6 = $$4.getOrCreate($$1, $$12 -> {
            $$5.setTrue();
        });
        return new ScoreAccess() { // from class: net.minecraft.world.scores.Scoreboard.1
            @Override // net.minecraft.world.scores.ScoreAccess
            public int get() {
                return $$6.value();
            }

            @Override // net.minecraft.world.scores.ScoreAccess
            public void set(int $$02) {
                Component $$22;
                if (!$$3) {
                    throw new IllegalStateException("Cannot modify read-only score");
                }
                boolean $$13 = $$5.isTrue();
                if ($$1.displayAutoUpdate() && ($$22 = $$0.getDisplayName()) != null && !$$22.equals($$6.display())) {
                    $$6.display($$22);
                    $$13 = true;
                }
                if ($$02 != $$6.value()) {
                    $$6.value($$02);
                    $$13 = true;
                }
                if ($$13) {
                    sendScoreToPlayers();
                }
            }

            @Override // net.minecraft.world.scores.ScoreAccess
            public Component display() {
                return $$6.display();
            }

            @Override // net.minecraft.world.scores.ScoreAccess
            public void display(Component $$02) {
                if ($$5.isTrue() || !Objects.equals($$02, $$6.display())) {
                    $$6.display($$02);
                    sendScoreToPlayers();
                }
            }

            @Override // net.minecraft.world.scores.ScoreAccess
            public void numberFormatOverride(NumberFormat $$02) {
                $$6.numberFormat($$02);
                sendScoreToPlayers();
            }

            @Override // net.minecraft.world.scores.ScoreAccess
            public boolean locked() {
                return $$6.isLocked();
            }

            @Override // net.minecraft.world.scores.ScoreAccess
            public void unlock() {
                setLocked(false);
            }

            @Override // net.minecraft.world.scores.ScoreAccess
            public void lock() {
                setLocked(true);
            }

            private void setLocked(boolean $$02) {
                $$6.setLocked($$02);
                if ($$5.isTrue()) {
                    sendScoreToPlayers();
                }
                Scoreboard.this.onScoreLockChanged($$0, $$1);
            }

            private void sendScoreToPlayers() {
                Scoreboard.this.onScoreChanged($$0, $$1, $$6);
                $$5.setFalse();
            }
        };
    }

    public ReadOnlyScoreInfo getPlayerScoreInfo(ScoreHolder $$0, Objective $$1) {
        PlayerScores $$2 = this.playerScores.get($$0.getScoreboardName());
        if ($$2 != null) {
            return $$2.get($$1);
        }
        return null;
    }

    public Collection<PlayerScoreEntry> listPlayerScores(Objective $$0) {
        List<PlayerScoreEntry> $$1 = new ArrayList<>();
        this.playerScores.forEach(($$2, $$3) -> {
            Score $$4 = $$3.get($$0);
            if ($$4 != null) {
                $$1.add(new PlayerScoreEntry($$2, $$4.value(), $$4.display(), $$4.numberFormat()));
            }
        });
        return $$1;
    }

    public Collection<Objective> getObjectives() {
        return this.objectivesByName.values();
    }

    public Collection<String> getObjectiveNames() {
        return this.objectivesByName.keySet();
    }

    public Collection<ScoreHolder> getTrackedPlayers() {
        return this.playerScores.keySet().stream().map(ScoreHolder::forNameOnly).toList();
    }

    public void resetAllPlayerScores(ScoreHolder $$0) {
        PlayerScores $$1 = this.playerScores.remove($$0.getScoreboardName());
        if ($$1 != null) {
            onPlayerRemoved($$0);
        }
    }

    public void resetSinglePlayerScore(ScoreHolder $$0, Objective $$1) {
        PlayerScores $$2 = this.playerScores.get($$0.getScoreboardName());
        if ($$2 != null) {
            boolean $$3 = $$2.remove($$1);
            if ($$2.hasScores()) {
                if ($$3) {
                    onPlayerScoreRemoved($$0, $$1);
                }
            } else {
                PlayerScores $$4 = this.playerScores.remove($$0.getScoreboardName());
                if ($$4 != null) {
                    onPlayerRemoved($$0);
                }
            }
        }
    }

    public Object2IntMap<Objective> listPlayerScores(ScoreHolder $$0) {
        PlayerScores $$1 = this.playerScores.get($$0.getScoreboardName());
        return $$1 != null ? $$1.listScores() : Object2IntMaps.emptyMap();
    }

    public void removeObjective(Objective $$0) {
        this.objectivesByName.remove($$0.getName());
        for (DisplaySlot $$1 : DisplaySlot.values()) {
            if (getDisplayObjective($$1) == $$0) {
                setDisplayObjective($$1, null);
            }
        }
        List<Objective> $$2 = (List) this.objectivesByCriteria.get($$0.getCriteria());
        if ($$2 != null) {
            $$2.remove($$0);
        }
        for (PlayerScores $$3 : this.playerScores.values()) {
            $$3.remove($$0);
        }
        onObjectiveRemoved($$0);
    }

    public void setDisplayObjective(DisplaySlot $$0, Objective $$1) {
        this.displayObjectives.put($$0, $$1);
    }

    public Objective getDisplayObjective(DisplaySlot $$0) {
        return this.displayObjectives.get($$0);
    }

    public PlayerTeam getPlayerTeam(String $$0) {
        return (PlayerTeam) this.teamsByName.get($$0);
    }

    public PlayerTeam addPlayerTeam(String $$0) {
        PlayerTeam $$1 = getPlayerTeam($$0);
        if ($$1 != null) {
            LOGGER.warn("Requested creation of existing team '{}'", $$0);
            return $$1;
        }
        PlayerTeam $$12 = new PlayerTeam(this, $$0);
        this.teamsByName.put($$0, $$12);
        onTeamAdded($$12);
        return $$12;
    }

    public void removePlayerTeam(PlayerTeam $$0) {
        this.teamsByName.remove($$0.getName());
        for (String $$1 : $$0.getPlayers()) {
            this.teamsByPlayer.remove($$1);
        }
        onTeamRemoved($$0);
    }

    public boolean addPlayerToTeam(String $$0, PlayerTeam $$1) {
        if (getPlayersTeam($$0) != null) {
            removePlayerFromTeam($$0);
        }
        this.teamsByPlayer.put($$0, $$1);
        return $$1.getPlayers().add($$0);
    }

    public boolean removePlayerFromTeam(String $$0) {
        PlayerTeam $$1 = getPlayersTeam($$0);
        if ($$1 != null) {
            removePlayerFromTeam($$0, $$1);
            return true;
        }
        return false;
    }

    public void removePlayerFromTeam(String $$0, PlayerTeam $$1) {
        if (getPlayersTeam($$0) != $$1) {
            throw new IllegalStateException("Player is either on another team or not on any team. Cannot remove from team '" + $$1.getName() + "'.");
        }
        this.teamsByPlayer.remove($$0);
        $$1.getPlayers().remove($$0);
    }

    public Collection<String> getTeamNames() {
        return this.teamsByName.keySet();
    }

    public Collection<PlayerTeam> getPlayerTeams() {
        return this.teamsByName.values();
    }

    public PlayerTeam getPlayersTeam(String $$0) {
        return (PlayerTeam) this.teamsByPlayer.get($$0);
    }

    public void onObjectiveAdded(Objective $$0) {
    }

    public void onObjectiveChanged(Objective $$0) {
    }

    public void onObjectiveRemoved(Objective $$0) {
    }

    protected void onScoreChanged(ScoreHolder $$0, Objective $$1, Score $$2) {
    }

    protected void onScoreLockChanged(ScoreHolder $$0, Objective $$1) {
    }

    public void onPlayerRemoved(ScoreHolder $$0) {
    }

    public void onPlayerScoreRemoved(ScoreHolder $$0, Objective $$1) {
    }

    public void onTeamAdded(PlayerTeam $$0) {
    }

    public void onTeamChanged(PlayerTeam $$0) {
    }

    public void onTeamRemoved(PlayerTeam $$0) {
    }

    public void entityRemoved(Entity $$0) {
        if (($$0 instanceof Player) || $$0.isAlive()) {
            return;
        }
        resetAllPlayerScores($$0);
        removePlayerFromTeam($$0.getScoreboardName());
    }

    protected List<PackedScore> packPlayerScores() {
        return this.playerScores.entrySet().stream().flatMap($$0 -> {
            String $$1 = (String) $$0.getKey();
            return ((PlayerScores) $$0.getValue()).listRawScores().entrySet().stream().map($$12 -> {
                return new PackedScore($$1, ((Objective) $$12.getKey()).getName(), ((Score) $$12.getValue()).pack());
            });
        }).toList();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void loadPlayerScore(PackedScore $$0) {
        Objective $$1 = getObjective($$0.objective);
        if ($$1 == null) {
            LOGGER.error("Unknown objective {} for name {}, ignoring", $$0.objective, $$0.owner);
        } else {
            getOrCreatePlayerInfo($$0.owner).setScore($$1, new Score($$0.score));
        }
    }

    protected List<PlayerTeam.Packed> packPlayerTeams() {
        return getPlayerTeams().stream().map((v0) -> {
            return v0.pack();
        }).toList();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void loadPlayerTeam(PlayerTeam.Packed $$0) {
        PlayerTeam $$1 = addPlayerTeam($$0.name());
        Optional<Component> optionalDisplayName = $$0.displayName();
        Objects.requireNonNull($$1);
        optionalDisplayName.ifPresent($$1::setDisplayName);
        Optional<ChatFormatting> optionalColor = $$0.color();
        Objects.requireNonNull($$1);
        optionalColor.ifPresent($$1::setColor);
        $$1.setAllowFriendlyFire($$0.allowFriendlyFire());
        $$1.setSeeFriendlyInvisibles($$0.seeFriendlyInvisibles());
        $$1.setPlayerPrefix($$0.memberNamePrefix());
        $$1.setPlayerSuffix($$0.memberNameSuffix());
        $$1.setNameTagVisibility($$0.nameTagVisibility());
        $$1.setDeathMessageVisibility($$0.deathMessageVisibility());
        $$1.setCollisionRule($$0.collisionRule());
        for (String $$2 : $$0.players()) {
            addPlayerToTeam($$2, $$1);
        }
    }

    protected List<Objective.Packed> packObjectives() {
        return getObjectives().stream().map((v0) -> {
            return v0.pack();
        }).toList();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void loadObjective(Objective.Packed $$0) {
        addObjective($$0.name(), $$0.criteria(), $$0.displayName(), $$0.renderType(), $$0.displayAutoUpdate(), $$0.numberFormat().orElse(null));
    }

    protected Map<DisplaySlot, String> packDisplaySlots() {
        Map<DisplaySlot, String> $$0 = new EnumMap<>(DisplaySlot.class);
        for (DisplaySlot $$1 : DisplaySlot.values()) {
            Objective $$2 = getDisplayObjective($$1);
            if ($$2 != null) {
                $$0.put($$1, $$2.getName());
            }
        }
        return $$0;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/scores/Scoreboard$PackedScore.class */
    public static final class PackedScore extends Record {
        private final String owner;
        private final String objective;
        private final Score.Packed score;
        public static final Codec<PackedScore> CODEC = RecordCodecBuilder.create($$0 -> {
            return $$0.group(Codec.STRING.fieldOf(StateHolder.NAME_TAG).forGetter((v0) -> {
                return v0.owner();
            }), Codec.STRING.fieldOf("Objective").forGetter((v0) -> {
                return v0.objective();
            }), Score.Packed.MAP_CODEC.forGetter((v0) -> {
                return v0.score();
            })).apply($$0, PackedScore::new);
        });

        public PackedScore(String $$0, String $$1, Score.Packed $$2) {
            this.owner = $$0;
            this.objective = $$1;
            this.score = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, PackedScore.class), PackedScore.class, "owner;objective;score", "FIELD:Lnet/minecraft/world/scores/Scoreboard$PackedScore;->owner:Ljava/lang/String;", "FIELD:Lnet/minecraft/world/scores/Scoreboard$PackedScore;->objective:Ljava/lang/String;", "FIELD:Lnet/minecraft/world/scores/Scoreboard$PackedScore;->score:Lnet/minecraft/world/scores/Score$Packed;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, PackedScore.class), PackedScore.class, "owner;objective;score", "FIELD:Lnet/minecraft/world/scores/Scoreboard$PackedScore;->owner:Ljava/lang/String;", "FIELD:Lnet/minecraft/world/scores/Scoreboard$PackedScore;->objective:Ljava/lang/String;", "FIELD:Lnet/minecraft/world/scores/Scoreboard$PackedScore;->score:Lnet/minecraft/world/scores/Score$Packed;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, PackedScore.class, Object.class), PackedScore.class, "owner;objective;score", "FIELD:Lnet/minecraft/world/scores/Scoreboard$PackedScore;->owner:Ljava/lang/String;", "FIELD:Lnet/minecraft/world/scores/Scoreboard$PackedScore;->objective:Ljava/lang/String;", "FIELD:Lnet/minecraft/world/scores/Scoreboard$PackedScore;->score:Lnet/minecraft/world/scores/Score$Packed;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public String owner() {
            return this.owner;
        }

        public String objective() {
            return this.objective;
        }

        public Score.Packed score() {
            return this.score;
        }
    }
}
