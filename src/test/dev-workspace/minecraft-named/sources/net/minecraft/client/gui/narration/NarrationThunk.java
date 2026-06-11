package net.minecraft.client.gui.narration;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Unit;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/narration/NarrationThunk.class */
public class NarrationThunk<T> {
    private final T contents;
    private final BiConsumer<Consumer<String>, T> converter;
    public static final NarrationThunk<?> EMPTY = new NarrationThunk<>(Unit.INSTANCE, ($$0, $$1) -> {
    });

    private NarrationThunk(T $$0, BiConsumer<Consumer<String>, T> $$1) {
        this.contents = $$0;
        this.converter = $$1;
    }

    public static NarrationThunk<?> from(String $$0) {
        return new NarrationThunk<>($$0, (v0, v1) -> {
            v0.accept(v1);
        });
    }

    public static NarrationThunk<?> from(Component $$0) {
        return new NarrationThunk<>($$0, ($$02, $$1) -> {
            $$02.accept($$1.getString());
        });
    }

    public static NarrationThunk<?> from(List<Component> $$0) {
        return new NarrationThunk<>($$0, ($$1, $$2) -> {
            $$0.stream().map((v0) -> {
                return v0.getString();
            }).forEach($$1);
        });
    }

    public void getText(Consumer<String> $$0) {
        this.converter.accept($$0, this.contents);
    }

    public boolean equals(Object $$0) {
        if (this == $$0) {
            return true;
        }
        if (!($$0 instanceof NarrationThunk)) {
            return false;
        }
        NarrationThunk<?> $$1 = (NarrationThunk) $$0;
        return $$1.converter == this.converter && $$1.contents.equals(this.contents);
    }

    public int hashCode() {
        int $$0 = this.contents.hashCode();
        return (31 * $$0) + this.converter.hashCode();
    }
}
