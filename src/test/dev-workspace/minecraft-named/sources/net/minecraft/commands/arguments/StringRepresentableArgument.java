package net.minecraft.commands.arguments;

import com.google.gson.JsonPrimitive;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import java.lang.Enum;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/arguments/StringRepresentableArgument.class */
public class StringRepresentableArgument<T extends Enum<T> & StringRepresentable> implements ArgumentType<T> {
    private static final DynamicCommandExceptionType ERROR_INVALID_VALUE = new DynamicCommandExceptionType($$0 -> {
        return Component.translatableEscape("argument.enum.invalid", $$0);
    });
    private final Codec<T> codec;
    private final Supplier<T[]> values;

    protected StringRepresentableArgument(Codec<T> $$0, Supplier<T[]> $$1) {
        this.codec = $$0;
        this.values = $$1;
    }

    /* JADX WARN: Incorrect return type in method signature: (Lcom/mojang/brigadier/StringReader;)TT; */
    /* JADX INFO: renamed from: parse, reason: merged with bridge method [inline-methods] */
    public Enum m1278parse(StringReader $$0) throws CommandSyntaxException {
        String $$1 = $$0.readUnquotedString();
        return (Enum) this.codec.parse(JsonOps.INSTANCE, new JsonPrimitive($$1)).result().orElseThrow(() -> {
            return ERROR_INVALID_VALUE.createWithContext($$0, $$1);
        });
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
        return SharedSuggestionProvider.suggest((Iterable<String>) Arrays.stream((Enum[]) this.values.get()).map($$0 -> {
            return ((StringRepresentable) $$0).getSerializedName();
        }).map(this::convertId).collect(Collectors.toList()), suggestionsBuilder);
    }

    public Collection<String> getExamples() {
        return (Collection) Arrays.stream((Enum[]) this.values.get()).map($$0 -> {
            return ((StringRepresentable) $$0).getSerializedName();
        }).map(this::convertId).limit(2L).collect(Collectors.toList());
    }

    protected String convertId(String $$0) {
        return $$0;
    }
}
