package net.minecraft.world.level.chunk;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/chunk/PaletteResize.class */
public interface PaletteResize<T> {
    int onResize(int i, T t);

    static <T> PaletteResize<T> noResizeExpected() {
        return ($$0, $$1) -> {
            throw new IllegalArgumentException("Unexpected palette resize, bits = " + $$0 + ", added value = " + String.valueOf($$1));
        };
    }
}
