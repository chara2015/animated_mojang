package net.minecraft.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Arrays;
import java.util.Collection;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.commands.CommandSourceStack;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/arguments/RangeArgument.class */
public interface RangeArgument<T extends MinMaxBounds<?>> extends ArgumentType<T> {

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/arguments/RangeArgument$Ints.class */
    public static class Ints implements RangeArgument<MinMaxBounds.Ints> {
        private static final Collection<String> EXAMPLES = Arrays.asList("0..5", "0", "-5", "-100..", "..100");

        public static MinMaxBounds.Ints getRange(CommandContext<CommandSourceStack> $$0, String $$1) {
            return (MinMaxBounds.Ints) $$0.getArgument($$1, MinMaxBounds.Ints.class);
        }

        /* JADX INFO: renamed from: parse, reason: merged with bridge method [inline-methods] */
        public MinMaxBounds.Ints m1255parse(StringReader $$0) throws CommandSyntaxException {
            return MinMaxBounds.Ints.fromReader($$0);
        }

        public Collection<String> getExamples() {
            return EXAMPLES;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/arguments/RangeArgument$Floats.class */
    public static class Floats implements RangeArgument<MinMaxBounds.Doubles> {
        private static final Collection<String> EXAMPLES = Arrays.asList("0..5.2", "0", "-5.4", "-100.76..", "..100");

        public static MinMaxBounds.Doubles getRange(CommandContext<CommandSourceStack> $$0, String $$1) {
            return (MinMaxBounds.Doubles) $$0.getArgument($$1, MinMaxBounds.Doubles.class);
        }

        /* JADX INFO: renamed from: parse, reason: merged with bridge method [inline-methods] */
        public MinMaxBounds.Doubles m1253parse(StringReader $$0) throws CommandSyntaxException {
            return MinMaxBounds.Doubles.fromReader($$0);
        }

        public Collection<String> getExamples() {
            return EXAMPLES;
        }
    }

    static Ints intRange() {
        return new Ints();
    }

    static Floats floatRange() {
        return new Floats();
    }
}
