package net.minecraft.commands.execution;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/execution/UnboundEntryAction.class */
@FunctionalInterface
public interface UnboundEntryAction<T> {
    void execute(T t, ExecutionContext<T> executionContext, Frame frame);

    default EntryAction<T> bind(T $$0) {
        return ($$1, $$2) -> {
            execute($$0, $$1, $$2);
        };
    }
}
