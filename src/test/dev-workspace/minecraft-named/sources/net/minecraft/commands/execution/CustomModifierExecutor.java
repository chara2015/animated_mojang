package net.minecraft.commands.execution;

import com.mojang.brigadier.RedirectModifier;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.ContextChain;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/execution/CustomModifierExecutor.class */
public interface CustomModifierExecutor<T> {
    void apply(T t, List<T> list, ContextChain<T> contextChain, ChainModifiers chainModifiers, ExecutionControl<T> executionControl);

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/execution/CustomModifierExecutor$ModifierAdapter.class */
    public interface ModifierAdapter<T> extends RedirectModifier<T>, CustomModifierExecutor<T> {
        default Collection<T> apply(CommandContext<T> $$0) throws CommandSyntaxException {
            throw new UnsupportedOperationException("This function should not run");
        }
    }
}
