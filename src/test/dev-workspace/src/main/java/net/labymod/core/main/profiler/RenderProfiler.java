package net.labymod.core.main.profiler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/profiler/RenderProfiler.class */
public final class RenderProfiler {
    public static void increaseRenderCall() {
        Counters.RENDER_CALL.update();
    }

    public static int getRenderCalls() {
        return Counters.RENDER_CALL.getCount();
    }

    public static void increaseRenderedCosmeticCount() {
        Counters.RENDERED_COSMETICS.update();
    }

    public static void increaseCosmeticTextureBindingCalls() {
        Counters.COSMETIC_TEXTURE_BINDINGS.update();
    }

    public static void resetRenderCalls() {
        for (Counter counter : Counters.getCounters()) {
            counter.reset();
        }
    }
}
