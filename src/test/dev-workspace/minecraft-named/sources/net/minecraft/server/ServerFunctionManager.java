package net.minecraft.server;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.logging.LogUtils;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.commands.CommandResultCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.FunctionInstantiationException;
import net.minecraft.commands.execution.ExecutionContext;
import net.minecraft.commands.functions.CommandFunction;
import net.minecraft.commands.functions.InstantiatedFunction;
import net.minecraft.resources.Identifier;
import net.minecraft.server.permissions.LevelBasedPermissionSet;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/ServerFunctionManager.class */
public class ServerFunctionManager {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Identifier TICK_FUNCTION_TAG = Identifier.withDefaultNamespace("tick");
    private static final Identifier LOAD_FUNCTION_TAG = Identifier.withDefaultNamespace("load");
    private final MinecraftServer server;
    private List<CommandFunction<CommandSourceStack>> ticking = ImmutableList.of();
    private boolean postReload;
    private ServerFunctionLibrary library;

    public ServerFunctionManager(MinecraftServer $$0, ServerFunctionLibrary $$1) {
        this.server = $$0;
        this.library = $$1;
        postReload($$1);
    }

    public CommandDispatcher<CommandSourceStack> getDispatcher() {
        return this.server.getCommands().getDispatcher();
    }

    public void tick() {
        if (!this.server.tickRateManager().runsNormally()) {
            return;
        }
        if (this.postReload) {
            this.postReload = false;
            Collection<CommandFunction<CommandSourceStack>> $$0 = this.library.getTag(LOAD_FUNCTION_TAG);
            executeTagFunctions($$0, LOAD_FUNCTION_TAG);
        }
        executeTagFunctions(this.ticking, TICK_FUNCTION_TAG);
    }

    private void executeTagFunctions(Collection<CommandFunction<CommandSourceStack>> $$0, Identifier $$1) {
        ProfilerFiller profilerFiller = Profiler.get();
        Objects.requireNonNull($$1);
        profilerFiller.push($$1::toString);
        for (CommandFunction<CommandSourceStack> $$2 : $$0) {
            execute($$2, getGameLoopSender());
        }
        Profiler.get().pop();
    }

    public void execute(CommandFunction<CommandSourceStack> $$0, CommandSourceStack $$1) {
        ProfilerFiller $$2 = Profiler.get();
        $$2.push(() -> {
            return "function " + String.valueOf($$0.id());
        });
        try {
            try {
                InstantiatedFunction<CommandSourceStack> $$3 = $$0.instantiate(null, getDispatcher());
                Commands.executeCommandInContext($$1, $$22 -> {
                    ExecutionContext.queueInitialFunctionCall($$22, $$3, $$1, CommandResultCallback.EMPTY);
                });
                $$2.pop();
            } catch (FunctionInstantiationException e) {
                $$2.pop();
            } catch (Exception $$4) {
                LOGGER.warn("Failed to execute function {}", $$0.id(), $$4);
                $$2.pop();
            }
        } catch (Throwable th) {
            $$2.pop();
            throw th;
        }
    }

    public void replaceLibrary(ServerFunctionLibrary $$0) {
        this.library = $$0;
        postReload($$0);
    }

    private void postReload(ServerFunctionLibrary $$0) {
        this.ticking = List.copyOf($$0.getTag(TICK_FUNCTION_TAG));
        this.postReload = true;
    }

    public CommandSourceStack getGameLoopSender() {
        return this.server.createCommandSourceStack().withPermission(LevelBasedPermissionSet.GAMEMASTER).withSuppressedOutput();
    }

    public Optional<CommandFunction<CommandSourceStack>> get(Identifier $$0) {
        return this.library.getFunction($$0);
    }

    public List<CommandFunction<CommandSourceStack>> getTag(Identifier $$0) {
        return this.library.getTag($$0);
    }

    public Iterable<Identifier> getFunctionNames() {
        return this.library.getFunctions().keySet();
    }

    public Iterable<Identifier> getTagNames() {
        return this.library.getAvailableTags();
    }
}
