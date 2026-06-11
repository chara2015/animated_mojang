package net.minecraft.client.gui;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/ComponentPath.class */
public interface ComponentPath {
    GuiEventListener component();

    void applyFocus(boolean z);

    static ComponentPath leaf(GuiEventListener $$0) {
        return new Leaf($$0);
    }

    static ComponentPath path(ContainerEventHandler $$0, ComponentPath $$1) {
        if ($$1 == null) {
            return null;
        }
        return new Path($$0, $$1);
    }

    static ComponentPath path(GuiEventListener $$0, ContainerEventHandler... $$1) {
        ComponentPath $$2 = leaf($$0);
        for (ContainerEventHandler $$3 : $$1) {
            $$2 = path($$3, $$2);
        }
        return $$2;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/ComponentPath$Path.class */
    public static final class Path extends Record implements ComponentPath {
        private final ContainerEventHandler component;
        private final ComponentPath childPath;

        public Path(ContainerEventHandler $$0, ComponentPath $$1) {
            this.component = $$0;
            this.childPath = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Path.class), Path.class, "component;childPath", "FIELD:Lnet/minecraft/client/gui/ComponentPath$Path;->component:Lnet/minecraft/client/gui/components/events/ContainerEventHandler;", "FIELD:Lnet/minecraft/client/gui/ComponentPath$Path;->childPath:Lnet/minecraft/client/gui/ComponentPath;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Path.class), Path.class, "component;childPath", "FIELD:Lnet/minecraft/client/gui/ComponentPath$Path;->component:Lnet/minecraft/client/gui/components/events/ContainerEventHandler;", "FIELD:Lnet/minecraft/client/gui/ComponentPath$Path;->childPath:Lnet/minecraft/client/gui/ComponentPath;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Path.class, Object.class), Path.class, "component;childPath", "FIELD:Lnet/minecraft/client/gui/ComponentPath$Path;->component:Lnet/minecraft/client/gui/components/events/ContainerEventHandler;", "FIELD:Lnet/minecraft/client/gui/ComponentPath$Path;->childPath:Lnet/minecraft/client/gui/ComponentPath;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        @Override // net.minecraft.client.gui.ComponentPath
        public ContainerEventHandler component() {
            return this.component;
        }

        public ComponentPath childPath() {
            return this.childPath;
        }

        @Override // net.minecraft.client.gui.ComponentPath
        public void applyFocus(boolean $$0) {
            if (!$$0) {
                this.component.setFocused((GuiEventListener) null);
            } else {
                this.component.setFocused(this.childPath.component());
            }
            this.childPath.applyFocus($$0);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/ComponentPath$Leaf.class */
    public static final class Leaf extends Record implements ComponentPath {
        private final GuiEventListener component;

        public Leaf(GuiEventListener $$0) {
            this.component = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Leaf.class), Leaf.class, "component", "FIELD:Lnet/minecraft/client/gui/ComponentPath$Leaf;->component:Lnet/minecraft/client/gui/components/events/GuiEventListener;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Leaf.class), Leaf.class, "component", "FIELD:Lnet/minecraft/client/gui/ComponentPath$Leaf;->component:Lnet/minecraft/client/gui/components/events/GuiEventListener;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Leaf.class, Object.class), Leaf.class, "component", "FIELD:Lnet/minecraft/client/gui/ComponentPath$Leaf;->component:Lnet/minecraft/client/gui/components/events/GuiEventListener;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        @Override // net.minecraft.client.gui.ComponentPath
        public GuiEventListener component() {
            return this.component;
        }

        @Override // net.minecraft.client.gui.ComponentPath
        public void applyFocus(boolean $$0) {
            this.component.setFocused($$0);
        }
    }
}
