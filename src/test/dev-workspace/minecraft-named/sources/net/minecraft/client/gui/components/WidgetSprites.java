package net.minecraft.client.gui.components;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/components/WidgetSprites.class */
public final class WidgetSprites extends Record {
    private final Identifier enabled;
    private final Identifier disabled;
    private final Identifier enabledFocused;
    private final Identifier disabledFocused;

    public WidgetSprites(Identifier $$0, Identifier $$1, Identifier $$2, Identifier $$3) {
        this.enabled = $$0;
        this.disabled = $$1;
        this.enabledFocused = $$2;
        this.disabledFocused = $$3;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, WidgetSprites.class), WidgetSprites.class, "enabled;disabled;enabledFocused;disabledFocused", "FIELD:Lnet/minecraft/client/gui/components/WidgetSprites;->enabled:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/client/gui/components/WidgetSprites;->disabled:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/client/gui/components/WidgetSprites;->enabledFocused:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/client/gui/components/WidgetSprites;->disabledFocused:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, WidgetSprites.class), WidgetSprites.class, "enabled;disabled;enabledFocused;disabledFocused", "FIELD:Lnet/minecraft/client/gui/components/WidgetSprites;->enabled:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/client/gui/components/WidgetSprites;->disabled:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/client/gui/components/WidgetSprites;->enabledFocused:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/client/gui/components/WidgetSprites;->disabledFocused:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, WidgetSprites.class, Object.class), WidgetSprites.class, "enabled;disabled;enabledFocused;disabledFocused", "FIELD:Lnet/minecraft/client/gui/components/WidgetSprites;->enabled:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/client/gui/components/WidgetSprites;->disabled:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/client/gui/components/WidgetSprites;->enabledFocused:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/client/gui/components/WidgetSprites;->disabledFocused:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Identifier enabled() {
        return this.enabled;
    }

    public Identifier disabled() {
        return this.disabled;
    }

    public Identifier enabledFocused() {
        return this.enabledFocused;
    }

    public Identifier disabledFocused() {
        return this.disabledFocused;
    }

    public WidgetSprites(Identifier $$0) {
        this($$0, $$0, $$0, $$0);
    }

    public WidgetSprites(Identifier $$0, Identifier $$1) {
        this($$0, $$0, $$1, $$1);
    }

    public WidgetSprites(Identifier $$0, Identifier $$1, Identifier $$2) {
        this($$0, $$1, $$2, $$1);
    }

    public Identifier get(boolean $$0, boolean $$1) {
        return $$0 ? $$1 ? this.enabledFocused : this.enabled : $$1 ? this.disabledFocused : this.disabled;
    }
}
