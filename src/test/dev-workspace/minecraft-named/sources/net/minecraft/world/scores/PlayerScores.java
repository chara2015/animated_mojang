package net.minecraft.world.scores;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/scores/PlayerScores.class */
class PlayerScores {
    private final Reference2ObjectOpenHashMap<Objective, Score> scores = new Reference2ObjectOpenHashMap<>(16, 0.5f);

    PlayerScores() {
    }

    public Score get(Objective $$0) {
        return (Score) this.scores.get($$0);
    }

    public Score getOrCreate(Objective $$0, Consumer<Score> $$1) {
        return (Score) this.scores.computeIfAbsent($$0, $$12 -> {
            Score $$2 = new Score();
            $$1.accept($$2);
            return $$2;
        });
    }

    public boolean remove(Objective $$0) {
        return this.scores.remove($$0) != null;
    }

    public boolean hasScores() {
        return !this.scores.isEmpty();
    }

    public Object2IntMap<Objective> listScores() {
        Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap();
        this.scores.forEach(($$1, $$2) -> {
            object2IntOpenHashMap.put($$1, $$2.value());
        });
        return object2IntOpenHashMap;
    }

    void setScore(Objective $$0, Score $$1) {
        this.scores.put($$0, $$1);
    }

    Map<Objective, Score> listRawScores() {
        return Collections.unmodifiableMap(this.scores);
    }
}
