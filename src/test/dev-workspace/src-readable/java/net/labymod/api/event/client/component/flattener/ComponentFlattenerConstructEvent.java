package net.labymod.api.event.client.component.flattener;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.client.component.flattener.ComponentFlattener;
import net.labymod.api.event.Event;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/component/flattener/ComponentFlattenerConstructEvent.class */
@Deprecated(forRemoval = true, since = "4.1.3")
public final class ComponentFlattenerConstructEvent extends Record implements Event {

    @Nullable
    private final ComponentFlattener.Builder builder;
    private final String identifier;

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ComponentFlattenerConstructEvent.class), ComponentFlattenerConstructEvent.class, "builder;identifier", "FIELD:Lnet/labymod/api/event/client/component/flattener/ComponentFlattenerConstructEvent;->builder:Lnet/labymod/api/client/component/flattener/ComponentFlattener$Builder;", "FIELD:Lnet/labymod/api/event/client/component/flattener/ComponentFlattenerConstructEvent;->identifier:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ComponentFlattenerConstructEvent.class), ComponentFlattenerConstructEvent.class, "builder;identifier", "FIELD:Lnet/labymod/api/event/client/component/flattener/ComponentFlattenerConstructEvent;->builder:Lnet/labymod/api/client/component/flattener/ComponentFlattener$Builder;", "FIELD:Lnet/labymod/api/event/client/component/flattener/ComponentFlattenerConstructEvent;->identifier:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ComponentFlattenerConstructEvent.class, Object.class), ComponentFlattenerConstructEvent.class, "builder;identifier", "FIELD:Lnet/labymod/api/event/client/component/flattener/ComponentFlattenerConstructEvent;->builder:Lnet/labymod/api/client/component/flattener/ComponentFlattener$Builder;", "FIELD:Lnet/labymod/api/event/client/component/flattener/ComponentFlattenerConstructEvent;->identifier:Ljava/lang/String;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    @Nullable
    public ComponentFlattener.Builder builder() {
        return this.builder;
    }

    public String identifier() {
        return this.identifier;
    }

    public ComponentFlattenerConstructEvent(@Nullable ComponentFlattener.Builder builder, String identifier) {
        this.builder = builder;
        this.identifier = identifier;
    }
}
