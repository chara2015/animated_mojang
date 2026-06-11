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
import java.util.stream.Stream;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.scores.DisplaySlot;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/arguments/ScoreboardSlotArgument.class */
public class ScoreboardSlotArgument implements ArgumentType<DisplaySlot> {
    private static final Collection<String> EXAMPLES = Arrays.asList("sidebar", "foo.bar");
    public static final DynamicCommandExceptionType ERROR_INVALID_VALUE = new DynamicCommandExceptionType($$0 -> {
        return Component.translatableEscape("argument.scoreboardDisplaySlot.invalid", $$0);
    });

    private ScoreboardSlotArgument() {
    }

    public static ScoreboardSlotArgument displaySlot() {
        return new ScoreboardSlotArgument();
    }

    public static DisplaySlot getDisplaySlot(CommandContext<CommandSourceStack> $$0, String $$1) {
        return (DisplaySlot) $$0.getArgument($$1, DisplaySlot.class);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.brigadier.exceptions.CommandSyntaxException */
    /* JADX INFO: renamed from: parse, reason: merged with bridge method [inline-methods] */
    public DisplaySlot m1272parse(StringReader $$0) throws CommandSyntaxException {
        String $$1 = $$0.readUnquotedString();
        DisplaySlot $$2 = (DisplaySlot) DisplaySlot.CODEC.byName($$1);
        if ($$2 == null) {
            throw ERROR_INVALID_VALUE.createWithContext($$0, $$1);
        }
        return $$2;
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> $$0, SuggestionsBuilder $$1) {
        return SharedSuggestionProvider.suggest((Stream<String>) Arrays.stream(DisplaySlot.values()).map((v0) -> {
            return v0.getSerializedName();
        }), $$1);
    }

    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}
