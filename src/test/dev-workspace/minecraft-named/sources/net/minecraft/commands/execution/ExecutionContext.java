package net.minecraft.commands.execution;

import com.google.common.collect.Queues;
import com.mojang.brigadier.context.ContextChain;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import net.minecraft.commands.CommandResultCallback;
import net.minecraft.commands.ExecutionCommandSource;
import net.minecraft.commands.execution.Frame;
import net.minecraft.commands.execution.tasks.BuildContexts;
import net.minecraft.commands.execution.tasks.CallFunction;
import net.minecraft.commands.functions.InstantiatedFunction;
import net.minecraft.util.profiling.ProfilerFiller;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/execution/ExecutionContext.class */
public class ExecutionContext<T> implements AutoCloseable {
    private static final int MAX_QUEUE_DEPTH = 10000000;
    private static final Logger LOGGER = LogUtils.getLogger();
    private final int commandLimit;
    private final int forkLimit;
    private final ProfilerFiller profiler;
    private TraceCallbacks tracer;
    private int commandQuota;
    private boolean queueOverflow;
    private final Deque<CommandQueueEntry<T>> commandQueue = Queues.newArrayDeque();
    private final List<CommandQueueEntry<T>> newTopCommands = new ObjectArrayList();
    private int currentFrameDepth;

    public ExecutionContext(int $$0, int $$1, ProfilerFiller $$2) {
        this.commandLimit = $$0;
        this.forkLimit = $$1;
        this.profiler = $$2;
        this.commandQuota = $$0;
    }

    private static <T extends ExecutionCommandSource<T>> Frame createTopFrame(ExecutionContext<T> $$0, CommandResultCallback $$1) {
        if (((ExecutionContext) $$0).currentFrameDepth == 0) {
            Deque<CommandQueueEntry<T>> deque = ((ExecutionContext) $$0).commandQueue;
            Objects.requireNonNull(deque);
            return new Frame(0, $$1, deque::clear);
        }
        int $$2 = ((ExecutionContext) $$0).currentFrameDepth + 1;
        return new Frame($$2, $$1, $$0.frameControlForDepth($$2));
    }

    /* JADX WARN: Incorrect types in method signature: <T::Lnet/minecraft/commands/ExecutionCommandSource<TT;>;>(Lnet/minecraft/commands/execution/ExecutionContext<TT;>;Lnet/minecraft/commands/functions/InstantiatedFunction<TT;>;TT;Lnet/minecraft/commands/CommandResultCallback;)V */
    public static void queueInitialFunctionCall(ExecutionContext executionContext, InstantiatedFunction instantiatedFunction, ExecutionCommandSource executionCommandSource, CommandResultCallback $$3) {
        executionContext.queueNext(new CommandQueueEntry<>(createTopFrame(executionContext, $$3), new CallFunction(instantiatedFunction, executionCommandSource.callback(), false).bind(executionCommandSource)));
    }

    /* JADX WARN: Incorrect types in method signature: <T::Lnet/minecraft/commands/ExecutionCommandSource<TT;>;>(Lnet/minecraft/commands/execution/ExecutionContext<TT;>;Ljava/lang/String;Lcom/mojang/brigadier/context/ContextChain<TT;>;TT;Lnet/minecraft/commands/CommandResultCallback;)V */
    public static void queueInitialCommandExecution(ExecutionContext executionContext, String $$1, ContextChain contextChain, ExecutionCommandSource executionCommandSource, CommandResultCallback $$4) {
        executionContext.queueNext(new CommandQueueEntry<>(createTopFrame(executionContext, $$4), new BuildContexts.TopLevel($$1, contextChain, executionCommandSource)));
    }

    private void handleQueueOverflow() {
        this.queueOverflow = true;
        this.newTopCommands.clear();
        this.commandQueue.clear();
    }

    public void queueNext(CommandQueueEntry<T> $$0) {
        if (this.newTopCommands.size() + this.commandQueue.size() > MAX_QUEUE_DEPTH) {
            handleQueueOverflow();
        }
        if (!this.queueOverflow) {
            this.newTopCommands.add($$0);
        }
    }

    public void discardAtDepthOrHigher(int $$0) {
        while (!this.commandQueue.isEmpty() && this.commandQueue.peek().frame().depth() >= $$0) {
            this.commandQueue.removeFirst();
        }
    }

    public Frame.FrameControl frameControlForDepth(int $$0) {
        return () -> {
            discardAtDepthOrHigher($$0);
        };
    }

    public void runCommandQueue() {
        pushNewCommands();
        while (true) {
            if (this.commandQuota <= 0) {
                LOGGER.info("Command execution stopped due to limit (executed {} commands)", Integer.valueOf(this.commandLimit));
                break;
            }
            CommandQueueEntry<T> $$0 = this.commandQueue.pollFirst();
            if ($$0 == null) {
                return;
            }
            this.currentFrameDepth = $$0.frame().depth();
            $$0.execute(this);
            if (this.queueOverflow) {
                LOGGER.error("Command execution stopped due to command queue overflow (max {})", Integer.valueOf(MAX_QUEUE_DEPTH));
                break;
            }
            pushNewCommands();
        }
        this.currentFrameDepth = 0;
    }

    private void pushNewCommands() {
        for (int $$0 = this.newTopCommands.size() - 1; $$0 >= 0; $$0--) {
            this.commandQueue.addFirst(this.newTopCommands.get($$0));
        }
        this.newTopCommands.clear();
    }

    public void tracer(TraceCallbacks $$0) {
        this.tracer = $$0;
    }

    public TraceCallbacks tracer() {
        return this.tracer;
    }

    public ProfilerFiller profiler() {
        return this.profiler;
    }

    public int forkLimit() {
        return this.forkLimit;
    }

    public void incrementCost() {
        this.commandQuota--;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        if (this.tracer != null) {
            this.tracer.close();
        }
    }
}
