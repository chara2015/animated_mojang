package net.minecraft.commands.arguments.coordinates;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/arguments/coordinates/SwizzleArgument.class */
public class SwizzleArgument implements ArgumentType<EnumSet<Direction.Axis>> {
    private static final Collection<String> EXAMPLES = Arrays.asList("xyz", "x");
    private static final SimpleCommandExceptionType ERROR_INVALID = new SimpleCommandExceptionType(Component.translatable("arguments.swizzle.invalid"));

    public static SwizzleArgument swizzle() {
        return new SwizzleArgument();
    }

    public static EnumSet<Direction.Axis> getSwizzle(CommandContext<CommandSourceStack> $$0, String $$1) {
        return (EnumSet) $$0.getArgument($$1, EnumSet.class);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.brigadier.exceptions.CommandSyntaxException */
    /* JADX INFO: renamed from: parse, reason: merged with bridge method [inline-methods] */
    public EnumSet<Direction.Axis> m1300parse(StringReader $$0) throws CommandSyntaxException {
        Direction.Axis $$6;
        EnumSet<Direction.Axis> $$1 = EnumSet.noneOf(Direction.Axis.class);
        while ($$0.canRead() && $$0.peek() != ' ') {
            char $$2 = $$0.read();
            switch ($$2) {
                case 'x':
                    $$6 = Direction.Axis.X;
                    break;
                case 'y':
                    $$6 = Direction.Axis.Y;
                    break;
                case 'z':
                    $$6 = Direction.Axis.Z;
                    break;
                default:
                    throw ERROR_INVALID.createWithContext($$0);
            }
            if ($$1.contains($$6)) {
                throw ERROR_INVALID.createWithContext($$0);
            }
            $$1.add($$6);
        }
        return $$1;
    }

    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}
