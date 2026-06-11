package net.labymod.core.main.profiler;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/profiler/Counters.class */
public final class Counters {
    static final List<Counter> COUNTERS = new ArrayList();
    public static final Counter RENDER_CALL = create(CounterType.NONE, "Render Calls");
    public static final Counter RENDERED_COSMETICS = create(CounterType.COSMETICS, "Rendered Cosmetics");
    public static final Counter COSMETIC_TEXTURE_BINDINGS = create(CounterType.COSMETICS, "Cosmetic Texture Bindings");

    public static void renderCounters(int type) {
        for (Counter counter : getCounters()) {
            if (counter.getType() == type) {
                counter.renderImGui();
            }
        }
    }

    public static List<Counter> getCounters() {
        return COUNTERS;
    }

    private static Counter create(int type, String name) {
        Counter counter = new Counter(type, name);
        COUNTERS.add(counter);
        return counter;
    }
}
