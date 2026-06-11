package net.labymod.api.laby3d.renderer.snapshot;

import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/renderer/snapshot/Extras.class */
public interface Extras {
    boolean has(@NotNull ExtraKey<?> extraKey);

    @Nullable
    <T> T get(@NotNull ExtraKey<T> extraKey);

    void forEach(@NotNull BiConsumer<ExtraKey<?>, Object> biConsumer);

    @NotNull
    static Extras empty() {
        return ExtrasUtil.EMPTY;
    }

    @NotNull
    static Builder builder() {
        return new Builder();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/renderer/snapshot/Extras$Immutable.class */
    public static final class Immutable implements Extras {
        private final Reference2ObjectMap<ExtraKey<?>, Object> extras;

        public Immutable(@NotNull Reference2ObjectMap<ExtraKey<?>, Object> extras) {
            this.extras = Reference2ObjectMaps.unmodifiable(extras);
        }

        @Override // net.labymod.api.laby3d.renderer.snapshot.Extras
        public boolean has(ExtraKey<?> key) {
            return this.extras.containsKey(key);
        }

        @Override // net.labymod.api.laby3d.renderer.snapshot.Extras
        public <T> T get(ExtraKey<T> extraKey) {
            return (T) this.extras.get(extraKey);
        }

        @Override // net.labymod.api.laby3d.renderer.snapshot.Extras
        public void forEach(BiConsumer<ExtraKey<?>, Object> consumer) {
            ObjectIterator it = this.extras.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<ExtraKey<?>, Object> entry = (Map.Entry) it.next();
                consumer.accept(entry.getKey(), entry.getValue());
            }
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/renderer/snapshot/Extras$Builder.class */
    public static class Builder implements ExtrasWriter {
        private final Reference2ObjectMap<ExtraKey<?>, Object> extras = new Reference2ObjectOpenHashMap();

        @Override // net.labymod.api.laby3d.renderer.snapshot.ExtrasWriter
        @NotNull
        public <T> ExtrasWriter put(@NotNull ExtraKey<T> key, @Nullable T value) {
            if (value == null) {
                this.extras.remove(key);
            } else {
                this.extras.put(key, value);
            }
            return this;
        }

        @NotNull
        public Immutable build() {
            return new Immutable(this.extras);
        }
    }
}
