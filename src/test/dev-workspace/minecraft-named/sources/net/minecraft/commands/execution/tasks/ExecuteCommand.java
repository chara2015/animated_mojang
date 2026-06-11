package net.minecraft.commands.execution.tasks;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.ContextChain;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.ExecutionCommandSource;
import net.minecraft.commands.execution.ChainModifiers;
import net.minecraft.commands.execution.ExecutionContext;
import net.minecraft.commands.execution.Frame;
import net.minecraft.commands.execution.TraceCallbacks;
import net.minecraft.commands.execution.UnboundEntryAction;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/execution/tasks/ExecuteCommand.class */
public class ExecuteCommand<T extends ExecutionCommandSource<T>> implements UnboundEntryAction<T> {
    private final String commandInput;
    private final ChainModifiers modifiers;
    private final CommandContext<T> executionContext;

    @Override // net.minecraft.commands.execution.UnboundEntryAction
    public /* synthetic */ void execute(Object obj, ExecutionContext executionContext, Frame frame) {
        execute((ExecutionCommandSource) obj, (ExecutionContext<ExecutionCommandSource>) executionContext, frame);
    }

    public ExecuteCommand(String $$0, ChainModifiers $$1, CommandContext<T> $$2) {
        this.commandInput = $$0;
        this.modifiers = $$1;
        this.executionContext = $$2;
    }

    public void execute(T $$0, ExecutionContext<T> $$1, Frame $$2) {
        $$1.profiler().push(() -> {
            return "execute " + this.commandInput;
        });
        try {
            try {
                $$1.incrementCost();
                int $$3 = ContextChain.runExecutable(this.executionContext, $$0, ExecutionCommandSource.resultConsumer(), this.modifiers.isForked());
                TraceCallbacks $$4 = $$1.tracer();
                if ($$4 != null) {
                    $$4.onReturn($$2.depth(), this.commandInput, $$3);
                }
                $$1.profiler().pop();
            } catch (CommandSyntaxException $$5) {
                $$0.handleError($$5, this.modifiers.isForked(), $$1.tracer());
                $$1.profiler().pop();
            }
        } catch (Throwable th) {
            $$1.profiler().pop();
            throw th;
        }
    }
}
