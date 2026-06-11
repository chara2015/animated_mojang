package net.minecraft.commands.execution;

import net.minecraft.commands.ExecutionCommandSource;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/execution/ExecutionControl.class */
public interface ExecutionControl<T> {
    void queueNext(EntryAction<T> entryAction);

    void tracer(TraceCallbacks traceCallbacks);

    TraceCallbacks tracer();

    Frame currentFrame();

    static <T extends ExecutionCommandSource<T>> ExecutionControl<T> create(final ExecutionContext<T> $$0, final Frame $$1) {
        return new ExecutionControl<T>() { // from class: net.minecraft.commands.execution.ExecutionControl.1
            @Override // net.minecraft.commands.execution.ExecutionControl
            public void queueNext(EntryAction<T> $$02) {
                $$0.queueNext(new CommandQueueEntry<>($$1, $$02));
            }

            @Override // net.minecraft.commands.execution.ExecutionControl
            public void tracer(TraceCallbacks $$02) {
                $$0.tracer($$02);
            }

            @Override // net.minecraft.commands.execution.ExecutionControl
            public TraceCallbacks tracer() {
                return $$0.tracer();
            }

            @Override // net.minecraft.commands.execution.ExecutionControl
            public Frame currentFrame() {
                return $$1;
            }
        };
    }
}
