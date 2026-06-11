package net.labymod.api.event.client.render.post;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/post/PostProcessingScreenEvent.class */
public final class PostProcessingScreenEvent extends Record implements Event {
    private final Phase phase;
    private final float partialTicks;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/post/PostProcessingScreenEvent$Phase.class */
    public enum Phase {
        BEFORE_HAND,
        WORLD
    }

    public PostProcessingScreenEvent(Phase phase, float partialTicks) {
        this.phase = phase;
        this.partialTicks = partialTicks;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, PostProcessingScreenEvent.class), PostProcessingScreenEvent.class, "phase;partialTicks", "FIELD:Lnet/labymod/api/event/client/render/post/PostProcessingScreenEvent;->phase:Lnet/labymod/api/event/client/render/post/PostProcessingScreenEvent$Phase;", "FIELD:Lnet/labymod/api/event/client/render/post/PostProcessingScreenEvent;->partialTicks:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, PostProcessingScreenEvent.class), PostProcessingScreenEvent.class, "phase;partialTicks", "FIELD:Lnet/labymod/api/event/client/render/post/PostProcessingScreenEvent;->phase:Lnet/labymod/api/event/client/render/post/PostProcessingScreenEvent$Phase;", "FIELD:Lnet/labymod/api/event/client/render/post/PostProcessingScreenEvent;->partialTicks:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, PostProcessingScreenEvent.class, Object.class), PostProcessingScreenEvent.class, "phase;partialTicks", "FIELD:Lnet/labymod/api/event/client/render/post/PostProcessingScreenEvent;->phase:Lnet/labymod/api/event/client/render/post/PostProcessingScreenEvent$Phase;", "FIELD:Lnet/labymod/api/event/client/render/post/PostProcessingScreenEvent;->partialTicks:F").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public Phase phase() {
        return this.phase;
    }

    public float partialTicks() {
        return this.partialTicks;
    }
}
