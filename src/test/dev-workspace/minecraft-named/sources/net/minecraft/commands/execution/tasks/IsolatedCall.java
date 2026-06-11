package net.minecraft.commands.execution.tasks;

import java.util.function.Consumer;
import net.minecraft.commands.CommandResultCallback;
import net.minecraft.commands.ExecutionCommandSource;
import net.minecraft.commands.execution.EntryAction;
import net.minecraft.commands.execution.ExecutionContext;
import net.minecraft.commands.execution.ExecutionControl;
import net.minecraft.commands.execution.Frame;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/execution/tasks/IsolatedCall.class */
public class IsolatedCall<T extends ExecutionCommandSource<T>> implements EntryAction<T> {
    private final Consumer<ExecutionControl<T>> taskProducer;
    private final CommandResultCallback output;

    public IsolatedCall(Consumer<ExecutionControl<T>> $$0, CommandResultCallback $$1) {
        this.taskProducer = $$0;
        this.output = $$1;
    }

    @Override // net.minecraft.commands.execution.EntryAction
    public void execute(ExecutionContext<T> $$0, Frame $$1) {
        int $$2 = $$1.depth() + 1;
        Frame $$3 = new Frame($$2, this.output, $$0.frameControlForDepth($$2));
        this.taskProducer.accept(ExecutionControl.create($$0, $$3));
    }
}
