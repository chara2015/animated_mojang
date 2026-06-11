package net.minecraft.commands.execution;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/execution/CommandQueueEntry.class */
public final class CommandQueueEntry<T> extends Record {
    private final Frame frame;
    private final EntryAction<T> action;

    public CommandQueueEntry(Frame $$0, EntryAction<T> $$1) {
        this.frame = $$0;
        this.action = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, CommandQueueEntry.class), CommandQueueEntry.class, "frame;action", "FIELD:Lnet/minecraft/commands/execution/CommandQueueEntry;->frame:Lnet/minecraft/commands/execution/Frame;", "FIELD:Lnet/minecraft/commands/execution/CommandQueueEntry;->action:Lnet/minecraft/commands/execution/EntryAction;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, CommandQueueEntry.class), CommandQueueEntry.class, "frame;action", "FIELD:Lnet/minecraft/commands/execution/CommandQueueEntry;->frame:Lnet/minecraft/commands/execution/Frame;", "FIELD:Lnet/minecraft/commands/execution/CommandQueueEntry;->action:Lnet/minecraft/commands/execution/EntryAction;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, CommandQueueEntry.class, Object.class), CommandQueueEntry.class, "frame;action", "FIELD:Lnet/minecraft/commands/execution/CommandQueueEntry;->frame:Lnet/minecraft/commands/execution/Frame;", "FIELD:Lnet/minecraft/commands/execution/CommandQueueEntry;->action:Lnet/minecraft/commands/execution/EntryAction;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Frame frame() {
        return this.frame;
    }

    public EntryAction<T> action() {
        return this.action;
    }

    public void execute(ExecutionContext<T> $$0) {
        this.action.execute($$0, this.frame);
    }
}
