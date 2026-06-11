package net.minecraft.util.parsing.packrat.commands;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.concurrent.CompletableFuture;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/parsing/packrat/commands/ParserBasedArgument.class */
public abstract class ParserBasedArgument<T> implements ArgumentType<T> {
    private final CommandArgumentParser<T> parser;

    public ParserBasedArgument(CommandArgumentParser<T> $$0) {
        this.parser = $$0;
    }

    public T parse(StringReader $$0) throws CommandSyntaxException {
        return this.parser.parseForCommands($$0);
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> $$0, SuggestionsBuilder $$1) {
        return this.parser.parseForSuggestions($$1);
    }
}
