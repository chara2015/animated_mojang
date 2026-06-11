package net.minecraft.util.parsing.packrat.commands;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/parsing/packrat/commands/CommandArgumentParser.class */
public interface CommandArgumentParser<T> {
    T parseForCommands(StringReader stringReader) throws CommandSyntaxException;

    CompletableFuture<Suggestions> parseForSuggestions(SuggestionsBuilder suggestionsBuilder);

    default <S> CommandArgumentParser<S> mapResult(final Function<T, S> $$0) {
        return new CommandArgumentParser<S>() { // from class: net.minecraft.util.parsing.packrat.commands.CommandArgumentParser.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // net.minecraft.util.parsing.packrat.commands.CommandArgumentParser
            public S parseForCommands(StringReader stringReader) throws CommandSyntaxException {
                return (S) $$0.apply(CommandArgumentParser.this.parseForCommands(stringReader));
            }

            @Override // net.minecraft.util.parsing.packrat.commands.CommandArgumentParser
            public CompletableFuture<Suggestions> parseForSuggestions(SuggestionsBuilder $$02) {
                return CommandArgumentParser.this.parseForSuggestions($$02);
            }
        };
    }

    default <T, O> CommandArgumentParser<T> withCodec(final DynamicOps<O> $$0, final CommandArgumentParser<O> $$1, final Codec<T> $$2, final DynamicCommandExceptionType $$3) {
        return new CommandArgumentParser<T>() { // from class: net.minecraft.util.parsing.packrat.commands.CommandArgumentParser.2
            /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.brigadier.exceptions.CommandSyntaxException */
            @Override // net.minecraft.util.parsing.packrat.commands.CommandArgumentParser
            public T parseForCommands(StringReader stringReader) throws CommandSyntaxException {
                int cursor = stringReader.getCursor();
                DataResult dataResult = $$2.parse($$0, $$1.parseForCommands(stringReader));
                DynamicCommandExceptionType dynamicCommandExceptionType = $$3;
                return (T) dataResult.getOrThrow($$32 -> {
                    stringReader.setCursor(cursor);
                    return dynamicCommandExceptionType.createWithContext(stringReader, $$32);
                });
            }

            @Override // net.minecraft.util.parsing.packrat.commands.CommandArgumentParser
            public CompletableFuture<Suggestions> parseForSuggestions(SuggestionsBuilder $$02) {
                return CommandArgumentParser.this.parseForSuggestions($$02);
            }
        };
    }
}
