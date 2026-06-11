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
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/arguments/HexColorArgument.class */
public class HexColorArgument implements ArgumentType<Integer> {
    private static final Collection<String> EXAMPLES = Arrays.asList("F00", "FF0000");
    public static final DynamicCommandExceptionType ERROR_INVALID_HEX = new DynamicCommandExceptionType($$0 -> {
        return Component.translatableEscape("argument.hexcolor.invalid", $$0);
    });

    private HexColorArgument() {
    }

    public static HexColorArgument hexColor() {
        return new HexColorArgument();
    }

    public static Integer getHexColor(CommandContext<CommandSourceStack> $$0, String $$1) {
        return (Integer) $$0.getArgument($$1, Integer.class);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.brigadier.exceptions.CommandSyntaxException */
    /* JADX INFO: renamed from: parse, reason: merged with bridge method [inline-methods] */
    public Integer m1233parse(StringReader $$0) throws CommandSyntaxException {
        String $$1 = $$0.readUnquotedString();
        switch ($$1.length()) {
            case 3:
                return Integer.valueOf(ARGB.color(duplicateDigit(Integer.parseInt($$1, 0, 1, 16)), duplicateDigit(Integer.parseInt($$1, 1, 2, 16)), duplicateDigit(Integer.parseInt($$1, 2, 3, 16))));
            case 6:
                return Integer.valueOf(ARGB.color(Integer.parseInt($$1, 0, 2, 16), Integer.parseInt($$1, 2, 4, 16), Integer.parseInt($$1, 4, 6, 16)));
            default:
                throw ERROR_INVALID_HEX.createWithContext($$0, $$1);
        }
    }

    private static int duplicateDigit(int $$0) {
        return $$0 * 17;
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> $$0, SuggestionsBuilder $$1) {
        return SharedSuggestionProvider.suggest(EXAMPLES, $$1);
    }

    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}
