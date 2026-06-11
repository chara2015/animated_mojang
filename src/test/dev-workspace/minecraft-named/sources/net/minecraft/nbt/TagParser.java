package net.minecraft.nbt;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import java.util.Objects;
import net.minecraft.network.chat.Component;
import net.minecraft.util.parsing.packrat.commands.Grammar;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/TagParser.class */
public class TagParser<T> {
    public static final char ELEMENT_SEPARATOR = ',';
    public static final char NAME_VALUE_SEPARATOR = ':';
    private final DynamicOps<T> ops;
    private final Grammar<T> grammar;
    public static final SimpleCommandExceptionType ERROR_TRAILING_DATA = new SimpleCommandExceptionType(Component.translatable("argument.nbt.trailing"));
    public static final SimpleCommandExceptionType ERROR_EXPECTED_COMPOUND = new SimpleCommandExceptionType(Component.translatable("argument.nbt.expected.compound"));
    private static final TagParser<Tag> NBT_OPS_PARSER = create(NbtOps.INSTANCE);
    public static final Codec<CompoundTag> FLATTENED_CODEC = Codec.STRING.comapFlatMap($$0 -> {
        try {
            Tag $$1 = NBT_OPS_PARSER.parseFully($$0);
            if ($$1 instanceof CompoundTag) {
                CompoundTag $$2 = (CompoundTag) $$1;
                return DataResult.success($$2, Lifecycle.stable());
            }
            return DataResult.error(() -> {
                return "Expected compound tag, got " + String.valueOf($$1);
            });
        } catch (CommandSyntaxException $$3) {
            Objects.requireNonNull($$3);
            return DataResult.error($$3::getMessage);
        }
    }, (v0) -> {
        return v0.toString();
    });
    public static final Codec<CompoundTag> LENIENT_CODEC = Codec.withAlternative(FLATTENED_CODEC, CompoundTag.CODEC);

    private TagParser(DynamicOps<T> $$0, Grammar<T> $$1) {
        this.ops = $$0;
        this.grammar = $$1;
    }

    public DynamicOps<T> getOps() {
        return this.ops;
    }

    public static <T> TagParser<T> create(DynamicOps<T> $$0) {
        return new TagParser<>($$0, SnbtGrammar.createParser($$0));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.brigadier.exceptions.CommandSyntaxException */
    private static CompoundTag castToCompoundOrThrow(StringReader $$0, Tag $$1) throws CommandSyntaxException {
        if ($$1 instanceof CompoundTag) {
            CompoundTag $$2 = (CompoundTag) $$1;
            return $$2;
        }
        throw ERROR_EXPECTED_COMPOUND.createWithContext($$0);
    }

    public static CompoundTag parseCompoundFully(String $$0) throws CommandSyntaxException {
        StringReader $$1 = new StringReader($$0);
        return castToCompoundOrThrow($$1, NBT_OPS_PARSER.parseFully($$1));
    }

    public T parseFully(String $$0) throws CommandSyntaxException {
        return parseFully(new StringReader($$0));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.brigadier.exceptions.CommandSyntaxException */
    public T parseFully(StringReader $$0) throws CommandSyntaxException {
        T $$1 = this.grammar.parseForCommands($$0);
        $$0.skipWhitespace();
        if ($$0.canRead()) {
            throw ERROR_TRAILING_DATA.createWithContext($$0);
        }
        return $$1;
    }

    public T parseAsArgument(StringReader $$0) throws CommandSyntaxException {
        return this.grammar.parseForCommands($$0);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.brigadier.exceptions.CommandSyntaxException */
    public static CompoundTag parseCompoundAsArgument(StringReader $$0) throws CommandSyntaxException {
        Tag $$1 = NBT_OPS_PARSER.parseAsArgument($$0);
        return castToCompoundOrThrow($$0, $$1);
    }
}
