package net.labymod.api.event.laby3d;

import java.util.function.BiConsumer;
import net.labymod.api.event.Event;
import net.labymod.api.event.ReplayableEvent;
import net.labymod.laby3d.api.pipeline.RenderState;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/laby3d/RenderStateLinkerSetupEvent.class */
@ReplayableEvent
public final class RenderStateLinkerSetupEvent implements Event {
    private final BiConsumer<RenderState, Object> linker;

    public RenderStateLinkerSetupEvent(BiConsumer<RenderState, Object> linker) {
        this.linker = linker;
    }

    public <T> void link(RenderState key, T target) {
        this.linker.accept(key, target);
    }
}
