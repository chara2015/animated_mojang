package net.minecraft.commands.execution.tasks;

import java.util.List;
import net.minecraft.commands.CommandResultCallback;
import net.minecraft.commands.ExecutionCommandSource;
import net.minecraft.commands.execution.CommandQueueEntry;
import net.minecraft.commands.execution.ExecutionContext;
import net.minecraft.commands.execution.Frame;
import net.minecraft.commands.execution.TraceCallbacks;
import net.minecraft.commands.execution.UnboundEntryAction;
import net.minecraft.commands.functions.InstantiatedFunction;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/execution/tasks/CallFunction.class */
public class CallFunction<T extends ExecutionCommandSource<T>> implements UnboundEntryAction<T> {
    private final InstantiatedFunction<T> function;
    private final CommandResultCallback resultCallback;
    private final boolean returnParentFrame;

    @Override // net.minecraft.commands.execution.UnboundEntryAction
    public /* synthetic */ void execute(Object obj, ExecutionContext executionContext, Frame frame) {
        execute((ExecutionCommandSource) obj, (ExecutionContext<ExecutionCommandSource>) executionContext, frame);
    }

    public CallFunction(InstantiatedFunction<T> $$0, CommandResultCallback $$1, boolean $$2) {
        this.function = $$0;
        this.resultCallback = $$1;
        this.returnParentFrame = $$2;
    }

    public void execute(T $$0, ExecutionContext<T> $$1, Frame $$2) {
        Frame.FrameControl frameControlFrameControlForDepth;
        $$1.incrementCost();
        List<UnboundEntryAction<T>> $$3 = this.function.entries();
        TraceCallbacks $$4 = $$1.tracer();
        if ($$4 != null) {
            $$4.onCall($$2.depth(), this.function.id(), this.function.entries().size());
        }
        int $$5 = $$2.depth() + 1;
        if (this.returnParentFrame) {
            frameControlFrameControlForDepth = $$2.frameControl();
        } else {
            frameControlFrameControlForDepth = $$1.frameControlForDepth($$5);
        }
        Frame.FrameControl $$6 = frameControlFrameControlForDepth;
        Frame $$7 = new Frame($$5, this.resultCallback, $$6);
        ContinuationTask.schedule($$1, $$7, $$3, ($$12, $$22) -> {
            return new CommandQueueEntry($$12, $$22.bind($$0));
        });
    }
}
