package net.labymod.api.event.client.render.state;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.event.Event;
import net.labymod.api.laby3d.renderer.snapshot.ExtrasWriter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/state/EntityRenderStateCreationEvent.class */
public final class EntityRenderStateCreationEvent extends Record implements Event {
    private final Entity entity;
    private final float partialTicks;
    private final ExtrasWriter extrasWriter;

    public EntityRenderStateCreationEvent(Entity entity, float partialTicks, ExtrasWriter extrasWriter) {
        this.entity = entity;
        this.partialTicks = partialTicks;
        this.extrasWriter = extrasWriter;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, EntityRenderStateCreationEvent.class), EntityRenderStateCreationEvent.class, "entity;partialTicks;extrasWriter", "FIELD:Lnet/labymod/api/event/client/render/state/EntityRenderStateCreationEvent;->entity:Lnet/labymod/api/client/entity/Entity;", "FIELD:Lnet/labymod/api/event/client/render/state/EntityRenderStateCreationEvent;->partialTicks:F", "FIELD:Lnet/labymod/api/event/client/render/state/EntityRenderStateCreationEvent;->extrasWriter:Lnet/labymod/api/laby3d/renderer/snapshot/ExtrasWriter;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, EntityRenderStateCreationEvent.class), EntityRenderStateCreationEvent.class, "entity;partialTicks;extrasWriter", "FIELD:Lnet/labymod/api/event/client/render/state/EntityRenderStateCreationEvent;->entity:Lnet/labymod/api/client/entity/Entity;", "FIELD:Lnet/labymod/api/event/client/render/state/EntityRenderStateCreationEvent;->partialTicks:F", "FIELD:Lnet/labymod/api/event/client/render/state/EntityRenderStateCreationEvent;->extrasWriter:Lnet/labymod/api/laby3d/renderer/snapshot/ExtrasWriter;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, EntityRenderStateCreationEvent.class, Object.class), EntityRenderStateCreationEvent.class, "entity;partialTicks;extrasWriter", "FIELD:Lnet/labymod/api/event/client/render/state/EntityRenderStateCreationEvent;->entity:Lnet/labymod/api/client/entity/Entity;", "FIELD:Lnet/labymod/api/event/client/render/state/EntityRenderStateCreationEvent;->partialTicks:F", "FIELD:Lnet/labymod/api/event/client/render/state/EntityRenderStateCreationEvent;->extrasWriter:Lnet/labymod/api/laby3d/renderer/snapshot/ExtrasWriter;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public Entity entity() {
        return this.entity;
    }

    public float partialTicks() {
        return this.partialTicks;
    }

    public ExtrasWriter extrasWriter() {
        return this.extrasWriter;
    }
}
