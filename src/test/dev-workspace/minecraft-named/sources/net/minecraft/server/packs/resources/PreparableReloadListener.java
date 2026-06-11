package net.minecraft.server.packs.resources;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/resources/PreparableReloadListener.class */
@FunctionalInterface
public interface PreparableReloadListener {

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/resources/PreparableReloadListener$PreparationBarrier.class */
    @FunctionalInterface
    public interface PreparationBarrier {
        <T> CompletableFuture<T> wait(T t);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/resources/PreparableReloadListener$StateKey.class */
    public static final class StateKey<T> {
    }

    CompletableFuture<Void> reload(SharedState sharedState, Executor executor, PreparationBarrier preparationBarrier, Executor executor2);

    default void prepareSharedState(SharedState $$0) {
    }

    default String getName() {
        return getClass().getSimpleName();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/resources/PreparableReloadListener$SharedState.class */
    public static final class SharedState {
        private final ResourceManager manager;
        private final Map<StateKey<?>, Object> state = new IdentityHashMap();

        public SharedState(ResourceManager $$0) {
            this.manager = $$0;
        }

        public ResourceManager resourceManager() {
            return this.manager;
        }

        public <T> void set(StateKey<T> $$0, T $$1) {
            this.state.put($$0, $$1);
        }

        public <T> T get(StateKey<T> stateKey) {
            return (T) Objects.requireNonNull(this.state.get(stateKey));
        }
    }
}
