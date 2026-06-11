package net.minecraft.util.parsing.packrat.commands;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.util.parsing.packrat.DelayedException;
import net.minecraft.util.parsing.packrat.Dictionary;
import net.minecraft.util.parsing.packrat.ErrorCollector;
import net.minecraft.util.parsing.packrat.ErrorEntry;
import net.minecraft.util.parsing.packrat.NamedRule;
import net.minecraft.util.parsing.packrat.ParseState;
import net.minecraft.util.parsing.packrat.SuggestionSupplier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/parsing/packrat/commands/Grammar.class */
public final class Grammar<T> extends Record implements CommandArgumentParser<T> {
    private final Dictionary<StringReader> rules;
    private final NamedRule<StringReader, T> top;

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Grammar.class), Grammar.class, "rules;top", "FIELD:Lnet/minecraft/util/parsing/packrat/commands/Grammar;->rules:Lnet/minecraft/util/parsing/packrat/Dictionary;", "FIELD:Lnet/minecraft/util/parsing/packrat/commands/Grammar;->top:Lnet/minecraft/util/parsing/packrat/NamedRule;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Grammar.class), Grammar.class, "rules;top", "FIELD:Lnet/minecraft/util/parsing/packrat/commands/Grammar;->rules:Lnet/minecraft/util/parsing/packrat/Dictionary;", "FIELD:Lnet/minecraft/util/parsing/packrat/commands/Grammar;->top:Lnet/minecraft/util/parsing/packrat/NamedRule;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Grammar.class, Object.class), Grammar.class, "rules;top", "FIELD:Lnet/minecraft/util/parsing/packrat/commands/Grammar;->rules:Lnet/minecraft/util/parsing/packrat/Dictionary;", "FIELD:Lnet/minecraft/util/parsing/packrat/commands/Grammar;->top:Lnet/minecraft/util/parsing/packrat/NamedRule;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Dictionary<StringReader> rules() {
        return this.rules;
    }

    public NamedRule<StringReader, T> top() {
        return this.top;
    }

    public Grammar(Dictionary<StringReader> $$0, NamedRule<StringReader, T> $$1) {
        $$0.checkAllBound();
        this.rules = $$0;
        this.top = $$1;
    }

    public Optional<T> parse(ParseState<StringReader> $$0) {
        return $$0.parseTopRule(this.top);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.brigadier.exceptions.CommandSyntaxException */
    @Override // net.minecraft.util.parsing.packrat.commands.CommandArgumentParser
    public T parseForCommands(StringReader stringReader) throws CommandSyntaxException {
        ErrorCollector.LongestOnly longestOnly = new ErrorCollector.LongestOnly();
        Optional<T> optional = parse(new StringReaderParserState(longestOnly, stringReader));
        if (optional.isPresent()) {
            return optional.get();
        }
        List listEntries = longestOnly.entries();
        List<T> list = listEntries.stream().mapMulti(($$1, $$2) -> {
            Object $$3 = $$1.reason();
            if ($$3 instanceof DelayedException) {
                DelayedException<?> $$4 = (DelayedException) $$3;
                $$2.accept($$4.create(stringReader.getString(), $$1.cursor()));
                return;
            }
            Object $$5 = $$1.reason();
            if ($$5 instanceof Exception) {
                Exception $$6 = (Exception) $$5;
                $$2.accept($$6);
            }
        }).toList();
        for (T t : list) {
            if (t instanceof CommandSyntaxException) {
                throw t;
            }
        }
        if (list.size() == 1) {
            T t2 = list.get(0);
            if (t2 instanceof RuntimeException) {
                throw ((RuntimeException) t2);
            }
        }
        throw new IllegalStateException("Failed to parse: " + ((String) listEntries.stream().map((v0) -> {
            return v0.toString();
        }).collect(Collectors.joining(ComponentUtils.DEFAULT_SEPARATOR_TEXT))));
    }

    @Override // net.minecraft.util.parsing.packrat.commands.CommandArgumentParser
    public CompletableFuture<Suggestions> parseForSuggestions(SuggestionsBuilder $$0) {
        StringReader $$1 = new StringReader($$0.getInput());
        $$1.setCursor($$0.getStart());
        ErrorCollector.LongestOnly<StringReader> $$2 = new ErrorCollector.LongestOnly<>();
        StringReaderParserState $$3 = new StringReaderParserState($$2, $$1);
        parse($$3);
        List<ErrorEntry<StringReader>> $$4 = $$2.entries();
        if ($$4.isEmpty()) {
            return $$0.buildFuture();
        }
        SuggestionsBuilder $$5 = $$0.createOffset($$2.cursor());
        for (ErrorEntry<StringReader> $$6 : $$4) {
            SuggestionSupplier<StringReader> suggestionSupplierSuggestions = $$6.suggestions();
            if (suggestionSupplierSuggestions instanceof ResourceSuggestion) {
                ResourceSuggestion $$7 = (ResourceSuggestion) suggestionSupplierSuggestions;
                SharedSuggestionProvider.suggestResource($$7.possibleResources(), $$5);
            } else {
                SharedSuggestionProvider.suggest($$6.suggestions().possibleValues($$3), $$5);
            }
        }
        return $$5.buildFuture();
    }
}
