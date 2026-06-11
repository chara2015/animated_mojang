package net.minecraft.world.level.gamerules;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.registries.BuiltInRegistries;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/gamerules/GameRuleMap.class */
public final class GameRuleMap {
    public static final Codec<GameRuleMap> CODEC = Codec.dispatchedMap(BuiltInRegistries.GAME_RULE.byNameCodec(), (v0) -> {
        return v0.valueCodec();
    }).xmap(GameRuleMap::ofTrusted, (v0) -> {
        return v0.map();
    });
    private final Reference2ObjectMap<GameRule<?>, Object> map;

    GameRuleMap(Reference2ObjectMap<GameRule<?>, Object> $$0) {
        this.map = $$0;
    }

    private static GameRuleMap ofTrusted(Map<GameRule<?>, Object> $$0) {
        return new GameRuleMap(new Reference2ObjectOpenHashMap($$0));
    }

    public static GameRuleMap of() {
        return new GameRuleMap(new Reference2ObjectOpenHashMap());
    }

    public static GameRuleMap of(Stream<GameRule<?>> $$0) {
        Reference2ObjectOpenHashMap<GameRule<?>, Object> $$1 = new Reference2ObjectOpenHashMap<>();
        $$0.forEach($$12 -> {
            $$1.put($$12, $$12.defaultValue());
        });
        return new GameRuleMap($$1);
    }

    public static GameRuleMap copyOf(GameRuleMap $$0) {
        return new GameRuleMap(new Reference2ObjectOpenHashMap($$0.map));
    }

    public boolean has(GameRule<?> $$0) {
        return this.map.containsKey($$0);
    }

    public <T> T get(GameRule<T> gameRule) {
        return (T) this.map.get(gameRule);
    }

    public <T> void set(GameRule<T> $$0, T $$1) {
        this.map.put($$0, $$1);
    }

    public <T> T remove(GameRule<T> gameRule) {
        return (T) this.map.remove(gameRule);
    }

    public Set<GameRule<?>> keySet() {
        return this.map.keySet();
    }

    public int size() {
        return this.map.size();
    }

    public String toString() {
        return this.map.toString();
    }

    public GameRuleMap withOther(GameRuleMap $$0) {
        GameRuleMap $$1 = copyOf(this);
        $$1.setFromIf($$0, $$02 -> {
            return true;
        });
        return $$1;
    }

    public void setFromIf(GameRuleMap $$0, Predicate<GameRule<?>> $$1) {
        for (GameRule<?> $$2 : $$0.keySet()) {
            if ($$1.test($$2)) {
                setGameRule($$0, $$2, this);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <T> void setGameRule(GameRuleMap $$0, GameRule<T> $$1, GameRuleMap gameRuleMap) {
        gameRuleMap.set($$1, Objects.requireNonNull($$0.get($$1)));
    }

    private Reference2ObjectMap<GameRule<?>, Object> map() {
        return this.map;
    }

    public boolean equals(Object $$0) {
        if ($$0 == this) {
            return true;
        }
        if ($$0 == null || $$0.getClass() != getClass()) {
            return false;
        }
        GameRuleMap $$1 = (GameRuleMap) $$0;
        return Objects.equals(this.map, $$1.map);
    }

    public int hashCode() {
        return Objects.hash(this.map);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/gamerules/GameRuleMap$Builder.class */
    public static class Builder {
        final Reference2ObjectMap<GameRule<?>, Object> map = new Reference2ObjectOpenHashMap();

        public <T> Builder set(GameRule<T> $$0, T $$1) {
            this.map.put($$0, $$1);
            return this;
        }

        public GameRuleMap build() {
            return new GameRuleMap(this.map);
        }
    }
}
