package net.minecraft.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/arguments/ColorArgument.class */
public class ColorArgument implements ArgumentType<ChatFormatting> {
    private static final Collection<String> EXAMPLES = Arrays.asList("red", "green");
    public static final DynamicCommandExceptionType ERROR_INVALID_VALUE = new DynamicCommandExceptionType($$0 -> {
        return Component.translatableEscape("argument.color.invalid", $$0);
    });

    private ColorArgument() {
    }

    public static ColorArgument color() {
        return new ColorArgument();
    }

    public static ChatFormatting getColor(CommandContext<CommandSourceStack> $$0, String $$1) {
        return (ChatFormatting) $$0.getArgument($$1, ChatFormatting.class);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.brigadier.exceptions.CommandSyntaxException */
    /* JADX INFO: renamed from: parse, reason: merged with bridge method [inline-methods] */
    public ChatFormatting m1214parse(StringReader $$0) throws CommandSyntaxException {
        String $$1 = $$0.readUnquotedString();
        ChatFormatting $$2 = ChatFormatting.getByName($$1);
        if ($$2 == null || $$2.isFormat()) {
            throw ERROR_INVALID_VALUE.createWithContext($$0, $$1);
        }
        return $$2;
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> $$0, SuggestionsBuilder $$1) {
        return SharedSuggestionProvider.suggest(ChatFormatting.getNames(true, false), $$1);
    }

    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}
