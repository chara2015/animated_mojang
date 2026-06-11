package net.labymod.api.client.render.state.world;

import net.labymod.api.laby3d.renderer.snapshot.AbstractLabySnapshot;
import net.labymod.api.laby3d.renderer.snapshot.ExtraKey;
import net.labymod.api.laby3d.renderer.snapshot.Extras;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/state/world/WorldSnapshot.class */
public class WorldSnapshot extends AbstractLabySnapshot {
    private final float partialTicks;

    public WorldSnapshot(@NotNull Extras extras, float partialTicks) {
        super(extras);
        this.partialTicks = partialTicks;
    }

    public float partialTicks() {
        return this.partialTicks;
    }

    @Nullable
    public <T> T getContributorData(@NotNull ExtraKey<T> extraKey) {
        return (T) get(extraKey);
    }

    public boolean hasContributorData(@NotNull ExtraKey<?> key) {
        return has(key);
    }

    @NotNull
    public static Builder builder(float partialTicks) {
        return new Builder(partialTicks);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/state/world/WorldSnapshot$Builder.class */
    public static class Builder {
        private final Extras.Builder extrasBuilder = Extras.builder();
        private final float partialTicks;

        private Builder(float partialTicks) {
            this.partialTicks = partialTicks;
        }

        @NotNull
        public <T> Builder put(@NotNull ExtraKey<T> key, @NotNull T value) {
            this.extrasBuilder.put(key, value);
            return this;
        }

        @NotNull
        public Extras.Builder extras() {
            return this.extrasBuilder;
        }

        @NotNull
        public WorldSnapshot build() {
            return new WorldSnapshot(this.extrasBuilder.build(), this.partialTicks);
        }
    }
}
