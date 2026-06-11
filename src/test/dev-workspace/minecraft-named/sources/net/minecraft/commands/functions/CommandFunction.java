package net.minecraft.commands.functions;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.context.ContextChain;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.List;
import java.util.Optional;
import net.minecraft.SharedConstants;
import net.minecraft.commands.Commands;
import net.minecraft.commands.ExecutionCommandSource;
import net.minecraft.commands.FunctionInstantiationException;
import net.minecraft.commands.execution.UnboundEntryAction;
import net.minecraft.commands.execution.tasks.BuildContexts;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/functions/CommandFunction.class */
public interface CommandFunction<T> {
    Identifier id();

    InstantiatedFunction<T> instantiate(CompoundTag compoundTag, CommandDispatcher<T> commandDispatcher) throws FunctionInstantiationException;

    private static boolean shouldConcatenateNextLine(CharSequence $$0) {
        int $$1 = $$0.length();
        return $$1 > 0 && $$0.charAt($$1 - 1) == '\\';
    }

    /* JADX WARN: Incorrect types in method signature: <T::Lnet/minecraft/commands/ExecutionCommandSource<TT;>;>(Lnet/minecraft/resources/Identifier;Lcom/mojang/brigadier/CommandDispatcher<TT;>;TT;Ljava/util/List<Ljava/lang/String;>;)Lnet/minecraft/commands/functions/CommandFunction<TT;>; */
    static CommandFunction fromLines(Identifier $$0, CommandDispatcher commandDispatcher, ExecutionCommandSource executionCommandSource, List list) {
        String $$11;
        FunctionBuilder<T> $$4 = new FunctionBuilder<>();
        int $$5 = 0;
        while ($$5 < list.size()) {
            int $$6 = $$5 + 1;
            String $$7 = ((String) list.get($$5)).trim();
            if (shouldConcatenateNextLine($$7)) {
                StringBuilder $$8 = new StringBuilder($$7);
                do {
                    $$5++;
                    if ($$5 == list.size()) {
                        throw new IllegalArgumentException("Line continuation at end of file");
                    }
                    $$8.deleteCharAt($$8.length() - 1);
                    String $$9 = ((String) list.get($$5)).trim();
                    $$8.append($$9);
                    checkCommandLineLength($$8);
                } while (shouldConcatenateNextLine($$8));
                $$11 = $$8.toString();
            } else {
                $$11 = $$7;
            }
            checkCommandLineLength($$11);
            StringReader $$12 = new StringReader($$11);
            if ($$12.canRead() && $$12.peek() != '#') {
                if ($$12.peek() == '/') {
                    $$12.skip();
                    if ($$12.peek() == '/') {
                        throw new IllegalArgumentException("Unknown or invalid command '" + $$11 + "' on line " + $$6 + " (if you intended to make a comment, use '#' not '//')");
                    }
                    String $$13 = $$12.readUnquotedString();
                    throw new IllegalArgumentException("Unknown or invalid command '" + $$11 + "' on line " + $$6 + " (did you mean '" + $$13 + "'? Do not use a preceding forwards slash.)");
                }
                if ($$12.peek() == '$') {
                    $$4.addMacro($$11.substring(1), $$6, executionCommandSource);
                } else {
                    try {
                        $$4.addCommand(parseCommand(commandDispatcher, executionCommandSource, $$12));
                    } catch (CommandSyntaxException $$14) {
                        throw new IllegalArgumentException("Whilst parsing command on line " + $$6 + ": " + $$14.getMessage());
                    }
                }
            }
            $$5++;
        }
        return $$4.build($$0);
    }

    static void checkCommandLineLength(CharSequence $$0) {
        if ($$0.length() > 2000000) {
            CharSequence $$1 = $$0.subSequence(0, Math.min(512, SharedConstants.MAX_FUNCTION_COMMAND_LENGTH));
            throw new IllegalStateException("Command too long: " + $$0.length() + " characters, contents: " + String.valueOf($$1) + "...");
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.brigadier.exceptions.CommandSyntaxException */
    /* JADX WARN: Incorrect types in method signature: <T::Lnet/minecraft/commands/ExecutionCommandSource<TT;>;>(Lcom/mojang/brigadier/CommandDispatcher<TT;>;TT;Lcom/mojang/brigadier/StringReader;)Lnet/minecraft/commands/execution/UnboundEntryAction<TT;>; */
    static UnboundEntryAction parseCommand(CommandDispatcher commandDispatcher, ExecutionCommandSource executionCommandSource, StringReader $$2) throws CommandSyntaxException {
        ParseResults<T> $$3 = commandDispatcher.parse($$2, executionCommandSource);
        Commands.validateParseResults($$3);
        Optional<ContextChain<T>> $$4 = ContextChain.tryFlatten($$3.getContext().build($$2.getString()));
        if ($$4.isEmpty()) {
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownCommand().createWithContext($$3.getReader());
        }
        return new BuildContexts.Unbound($$2.getString(), $$4.get());
    }
}
