package net.minecraft.commands.execution;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/execution/EntryAction.class */
@FunctionalInterface
public interface EntryAction<T> {
    void execute(ExecutionContext<T> executionContext, Frame frame);
}
