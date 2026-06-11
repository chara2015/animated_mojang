package net.labymod.core.laby3d.pipeline;

import java.util.HashMap;
import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.event.laby3d.RenderStateLinkerSetupEvent;
import net.labymod.laby3d.api.pipeline.RenderState;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/pipeline/RenderStateLinker.class */
public abstract class RenderStateLinker<T> {
    private final Map<RenderState, T> linked;
    private final Class<T> targetType;

    protected RenderStateLinker(Class<T> targetType) {
        this(new HashMap(), targetType);
    }

    protected RenderStateLinker(Map<RenderState, T> linked, Class<T> targetType) {
        this.linked = linked;
        this.targetType = targetType;
        fireLinkerSetup();
    }

    public void link(@NotNull RenderState key, T target) {
        if (this.linked.containsKey(key)) {
            throw new IllegalStateException("RenderState '" + String.valueOf(key.id()) + "' is already linked");
        }
        this.linked.put(key, target);
    }

    public T get(@NotNull RenderState key) {
        T t = this.linked.get(key);
        if (t == null) {
            throw new IllegalStateException("RenderState '" + String.valueOf(key.id()) + "' is not linked");
        }
        return t;
    }

    private void fireLinkerSetup() {
        RenderStateLinkerSetupEvent event = new RenderStateLinkerSetupEvent(this::associateWithTarget);
        Laby.fireEvent(event);
    }

    private void associateWithTarget(RenderState key, Object target) {
        link(key, this.targetType.cast(target));
    }
}
