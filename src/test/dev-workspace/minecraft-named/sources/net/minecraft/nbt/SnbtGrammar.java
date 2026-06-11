package net.minecraft.nbt;

import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.UnsignedBytes;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JavaOps;
import it.unimi.dsi.fastutil.bytes.ByteArrayList;
import it.unimi.dsi.fastutil.chars.CharList;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.lang.runtime.SwitchBootstraps;
import java.nio.ByteBuffer;
import java.util.HexFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import net.minecraft.nbt.SnbtOperations;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.util.Crypt;
import net.minecraft.util.parsing.packrat.Atom;
import net.minecraft.util.parsing.packrat.DelayedException;
import net.minecraft.util.parsing.packrat.Dictionary;
import net.minecraft.util.parsing.packrat.NamedRule;
import net.minecraft.util.parsing.packrat.ParseState;
import net.minecraft.util.parsing.packrat.Scope;
import net.minecraft.util.parsing.packrat.Term;
import net.minecraft.util.parsing.packrat.commands.Grammar;
import net.minecraft.util.parsing.packrat.commands.GreedyPatternParseRule;
import net.minecraft.util.parsing.packrat.commands.GreedyPredicateParseRule;
import net.minecraft.util.parsing.packrat.commands.NumberRunParseRule;
import net.minecraft.util.parsing.packrat.commands.StringReaderTerms;
import net.minecraft.util.parsing.packrat.commands.UnquotedStringParseRule;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/SnbtGrammar.class */
public class SnbtGrammar {
    private static final DynamicCommandExceptionType ERROR_NUMBER_PARSE_FAILURE = new DynamicCommandExceptionType($$0 -> {
        return Component.translatableEscape("snbt.parser.number_parse_failure", $$0);
    });
    static final DynamicCommandExceptionType ERROR_EXPECTED_HEX_ESCAPE = new DynamicCommandExceptionType($$0 -> {
        return Component.translatableEscape("snbt.parser.expected_hex_escape", $$0);
    });
    private static final DynamicCommandExceptionType ERROR_INVALID_CODEPOINT = new DynamicCommandExceptionType($$0 -> {
        return Component.translatableEscape("snbt.parser.invalid_codepoint", $$0);
    });
    private static final DynamicCommandExceptionType ERROR_NO_SUCH_OPERATION = new DynamicCommandExceptionType($$0 -> {
        return Component.translatableEscape("snbt.parser.no_such_operation", $$0);
    });
    static final DelayedException<CommandSyntaxException> ERROR_EXPECTED_INTEGER_TYPE = DelayedException.create(new SimpleCommandExceptionType(Component.translatable("snbt.parser.expected_integer_type")));
    private static final DelayedException<CommandSyntaxException> ERROR_EXPECTED_FLOAT_TYPE = DelayedException.create(new SimpleCommandExceptionType(Component.translatable("snbt.parser.expected_float_type")));
    static final DelayedException<CommandSyntaxException> ERROR_EXPECTED_NON_NEGATIVE_NUMBER = DelayedException.create(new SimpleCommandExceptionType(Component.translatable("snbt.parser.expected_non_negative_number")));
    private static final DelayedException<CommandSyntaxException> ERROR_INVALID_CHARACTER_NAME = DelayedException.create(new SimpleCommandExceptionType(Component.translatable("snbt.parser.invalid_character_name")));
    static final DelayedException<CommandSyntaxException> ERROR_INVALID_ARRAY_ELEMENT_TYPE = DelayedException.create(new SimpleCommandExceptionType(Component.translatable("snbt.parser.invalid_array_element_type")));
    private static final DelayedException<CommandSyntaxException> ERROR_INVALID_UNQUOTED_START = DelayedException.create(new SimpleCommandExceptionType(Component.translatable("snbt.parser.invalid_unquoted_start")));
    private static final DelayedException<CommandSyntaxException> ERROR_EXPECTED_UNQUOTED_STRING = DelayedException.create(new SimpleCommandExceptionType(Component.translatable("snbt.parser.expected_unquoted_string")));
    private static final DelayedException<CommandSyntaxException> ERROR_INVALID_STRING_CONTENTS = DelayedException.create(new SimpleCommandExceptionType(Component.translatable("snbt.parser.invalid_string_contents")));
    private static final DelayedException<CommandSyntaxException> ERROR_EXPECTED_BINARY_NUMERAL = DelayedException.create(new SimpleCommandExceptionType(Component.translatable("snbt.parser.expected_binary_numeral")));
    private static final DelayedException<CommandSyntaxException> ERROR_UNDESCORE_NOT_ALLOWED = DelayedException.create(new SimpleCommandExceptionType(Component.translatable("snbt.parser.underscore_not_allowed")));
    private static final DelayedException<CommandSyntaxException> ERROR_EXPECTED_DECIMAL_NUMERAL = DelayedException.create(new SimpleCommandExceptionType(Component.translatable("snbt.parser.expected_decimal_numeral")));
    private static final DelayedException<CommandSyntaxException> ERROR_EXPECTED_HEX_NUMERAL = DelayedException.create(new SimpleCommandExceptionType(Component.translatable("snbt.parser.expected_hex_numeral")));
    private static final DelayedException<CommandSyntaxException> ERROR_EMPTY_KEY = DelayedException.create(new SimpleCommandExceptionType(Component.translatable("snbt.parser.empty_key")));
    private static final DelayedException<CommandSyntaxException> ERROR_LEADING_ZERO_NOT_ALLOWED = DelayedException.create(new SimpleCommandExceptionType(Component.translatable("snbt.parser.leading_zero_not_allowed")));
    private static final DelayedException<CommandSyntaxException> ERROR_INFINITY_NOT_ALLOWED = DelayedException.create(new SimpleCommandExceptionType(Component.translatable("snbt.parser.infinity_not_allowed")));
    private static final HexFormat HEX_ESCAPE = HexFormat.of().withUpperCase();
    private static final NumberRunParseRule BINARY_NUMERAL = new NumberRunParseRule(ERROR_EXPECTED_BINARY_NUMERAL, ERROR_UNDESCORE_NOT_ALLOWED) { // from class: net.minecraft.nbt.SnbtGrammar.1
        @Override // net.minecraft.util.parsing.packrat.commands.NumberRunParseRule
        protected boolean isAccepted(char $$0) {
            switch ($$0) {
                case '0':
                case '1':
                case '_':
                    return true;
                default:
                    return false;
            }
        }
    };
    private static final NumberRunParseRule DECIMAL_NUMERAL = new NumberRunParseRule(ERROR_EXPECTED_DECIMAL_NUMERAL, ERROR_UNDESCORE_NOT_ALLOWED) { // from class: net.minecraft.nbt.SnbtGrammar.2
        @Override // net.minecraft.util.parsing.packrat.commands.NumberRunParseRule
        protected boolean isAccepted(char $$0) {
            switch ($$0) {
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case InputConstants.KEY_9 /* 57 */:
                case '_':
                    return true;
                default:
                    return false;
            }
        }
    };
    private static final NumberRunParseRule HEX_NUMERAL = new NumberRunParseRule(ERROR_EXPECTED_HEX_NUMERAL, ERROR_UNDESCORE_NOT_ALLOWED) { // from class: net.minecraft.nbt.SnbtGrammar.3
        @Override // net.minecraft.util.parsing.packrat.commands.NumberRunParseRule
        protected boolean isAccepted(char $$0) {
            switch ($$0) {
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case InputConstants.KEY_9 /* 57 */:
                case 'A':
                case 'B':
                case 'C':
                case 'D':
                case 'E':
                case 'F':
                case '_':
                case 'a':
                case LivingEntity.EQUIPMENT_SLOT_OFFSET /* 98 */:
                case Item.ABSOLUTE_MAX_STACK_SIZE /* 99 */:
                case 'd':
                case ClientboundGameEventPacket.DEMO_PARAM_HINT_1 /* 101 */:
                case ClientboundGameEventPacket.DEMO_PARAM_HINT_2 /* 102 */:
                    return true;
                case ':':
                case ';':
                case '<':
                case '=':
                case EntityEvent.SONIC_CHARGE /* 62 */:
                case '?':
                case '@':
                case InputConstants.KEY_G /* 71 */:
                case 'H':
                case InputConstants.KEY_I /* 73 */:
                case InputConstants.KEY_J /* 74 */:
                case 'K':
                case InputConstants.KEY_L /* 76 */:
                case InputConstants.KEY_M /* 77 */:
                case InputConstants.KEY_N /* 78 */:
                case InputConstants.KEY_O /* 79 */:
                case 'P':
                case InputConstants.KEY_Q /* 81 */:
                case InputConstants.KEY_R /* 82 */:
                case InputConstants.KEY_S /* 83 */:
                case InputConstants.KEY_T /* 84 */:
                case InputConstants.KEY_U /* 85 */:
                case InputConstants.KEY_V /* 86 */:
                case InputConstants.KEY_W /* 87 */:
                case 'X':
                case InputConstants.KEY_Y /* 89 */:
                case 'Z':
                case '[':
                case InputConstants.KEY_BACKSLASH /* 92 */:
                case ']':
                case '^':
                case InputConstants.KEY_GRAVE /* 96 */:
                default:
                    return false;
            }
        }
    };
    private static final GreedyPredicateParseRule PLAIN_STRING_CHUNK = new GreedyPredicateParseRule(1, ERROR_INVALID_STRING_CONTENTS) { // from class: net.minecraft.nbt.SnbtGrammar.4
        @Override // net.minecraft.util.parsing.packrat.commands.GreedyPredicateParseRule
        protected boolean isAccepted(char $$0) {
            switch ($$0) {
                case EntityEvent.STOP_OFFER_FLOWER /* 34 */:
                case '\'':
                case InputConstants.KEY_BACKSLASH /* 92 */:
                    return false;
                default:
                    return true;
            }
        }
    };
    private static final StringReaderTerms.TerminalCharacters NUMBER_LOOKEAHEAD = new StringReaderTerms.TerminalCharacters(CharList.of()) { // from class: net.minecraft.nbt.SnbtGrammar.5
        @Override // net.minecraft.util.parsing.packrat.commands.StringReaderTerms.TerminalCharacters
        protected boolean isAccepted(char $$0) {
            return SnbtGrammar.canStartNumber($$0);
        }
    };
    private static final Pattern UNICODE_NAME = Pattern.compile("[-a-zA-Z0-9 ]+");

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/SnbtGrammar$Base.class */
    enum Base {
        BINARY,
        DECIMAL,
        HEX
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/SnbtGrammar$SignedPrefix.class */
    enum SignedPrefix {
        SIGNED,
        UNSIGNED
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/SnbtGrammar$TypeSuffix.class */
    enum TypeSuffix {
        FLOAT,
        DOUBLE,
        BYTE,
        SHORT,
        INT,
        LONG
    }

    static DelayedException<CommandSyntaxException> createNumberParseError(NumberFormatException $$0) {
        return DelayedException.create(ERROR_NUMBER_PARSE_FAILURE, $$0.getMessage());
    }

    public static String escapeControlCharacters(char $$0) {
        switch ($$0) {
            case '\b':
                return "b";
            case '\t':
                return "t";
            case '\n':
                return "n";
            case 11:
            default:
                if ($$0 < ' ') {
                    return "x" + HEX_ESCAPE.toHexDigits((byte) $$0);
                }
                return null;
            case '\f':
                return "f";
            case '\r':
                return "r";
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/SnbtGrammar$Sign.class */
    enum Sign {
        PLUS,
        MINUS;

        public void append(StringBuilder $$0) {
            if (this == MINUS) {
                $$0.append("-");
            }
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/SnbtGrammar$IntegerSuffix.class */
    static final class IntegerSuffix extends Record {
        private final SignedPrefix signed;
        private final TypeSuffix type;
        public static final IntegerSuffix EMPTY = new IntegerSuffix(null, null);

        IntegerSuffix(SignedPrefix $$0, TypeSuffix $$1) {
            this.signed = $$0;
            this.type = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, IntegerSuffix.class), IntegerSuffix.class, "signed;type", "FIELD:Lnet/minecraft/nbt/SnbtGrammar$IntegerSuffix;->signed:Lnet/minecraft/nbt/SnbtGrammar$SignedPrefix;", "FIELD:Lnet/minecraft/nbt/SnbtGrammar$IntegerSuffix;->type:Lnet/minecraft/nbt/SnbtGrammar$TypeSuffix;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, IntegerSuffix.class), IntegerSuffix.class, "signed;type", "FIELD:Lnet/minecraft/nbt/SnbtGrammar$IntegerSuffix;->signed:Lnet/minecraft/nbt/SnbtGrammar$SignedPrefix;", "FIELD:Lnet/minecraft/nbt/SnbtGrammar$IntegerSuffix;->type:Lnet/minecraft/nbt/SnbtGrammar$TypeSuffix;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, IntegerSuffix.class, Object.class), IntegerSuffix.class, "signed;type", "FIELD:Lnet/minecraft/nbt/SnbtGrammar$IntegerSuffix;->signed:Lnet/minecraft/nbt/SnbtGrammar$SignedPrefix;", "FIELD:Lnet/minecraft/nbt/SnbtGrammar$IntegerSuffix;->type:Lnet/minecraft/nbt/SnbtGrammar$TypeSuffix;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public SignedPrefix signed() {
            return this.signed;
        }

        public TypeSuffix type() {
            return this.type;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/SnbtGrammar$ArrayPrefix.class */
    enum ArrayPrefix {
        BYTE(TypeSuffix.BYTE, new TypeSuffix[0]) { // from class: net.minecraft.nbt.SnbtGrammar.ArrayPrefix.1
            private static final ByteBuffer EMPTY_BUFFER = ByteBuffer.wrap(new byte[0]);

            @Override // net.minecraft.nbt.SnbtGrammar.ArrayPrefix
            public <T> T create(DynamicOps<T> dynamicOps) {
                return (T) dynamicOps.createByteList(EMPTY_BUFFER);
            }

            @Override // net.minecraft.nbt.SnbtGrammar.ArrayPrefix
            public <T> T create(DynamicOps<T> dynamicOps, List<IntegerLiteral> list, ParseState<?> parseState) {
                ByteArrayList byteArrayList = new ByteArrayList();
                Iterator<IntegerLiteral> it = list.iterator();
                while (it.hasNext()) {
                    Number numberBuildNumber = buildNumber(it.next(), parseState);
                    if (numberBuildNumber == null) {
                        return null;
                    }
                    byteArrayList.add(numberBuildNumber.byteValue());
                }
                return (T) dynamicOps.createByteList(ByteBuffer.wrap(byteArrayList.toByteArray()));
            }
        },
        INT(TypeSuffix.INT, TypeSuffix.BYTE, TypeSuffix.SHORT) { // from class: net.minecraft.nbt.SnbtGrammar.ArrayPrefix.2
            @Override // net.minecraft.nbt.SnbtGrammar.ArrayPrefix
            public <T> T create(DynamicOps<T> dynamicOps) {
                return (T) dynamicOps.createIntList(IntStream.empty());
            }

            @Override // net.minecraft.nbt.SnbtGrammar.ArrayPrefix
            public <T> T create(DynamicOps<T> dynamicOps, List<IntegerLiteral> list, ParseState<?> parseState) {
                IntStream.Builder builder = IntStream.builder();
                Iterator<IntegerLiteral> it = list.iterator();
                while (it.hasNext()) {
                    Number numberBuildNumber = buildNumber(it.next(), parseState);
                    if (numberBuildNumber == null) {
                        return null;
                    }
                    builder.add(numberBuildNumber.intValue());
                }
                return (T) dynamicOps.createIntList(builder.build());
            }
        },
        LONG(TypeSuffix.LONG, TypeSuffix.BYTE, TypeSuffix.SHORT, TypeSuffix.INT) { // from class: net.minecraft.nbt.SnbtGrammar.ArrayPrefix.3
            @Override // net.minecraft.nbt.SnbtGrammar.ArrayPrefix
            public <T> T create(DynamicOps<T> dynamicOps) {
                return (T) dynamicOps.createLongList(LongStream.empty());
            }

            @Override // net.minecraft.nbt.SnbtGrammar.ArrayPrefix
            public <T> T create(DynamicOps<T> dynamicOps, List<IntegerLiteral> list, ParseState<?> parseState) {
                LongStream.Builder builder = LongStream.builder();
                Iterator<IntegerLiteral> it = list.iterator();
                while (it.hasNext()) {
                    Number numberBuildNumber = buildNumber(it.next(), parseState);
                    if (numberBuildNumber == null) {
                        return null;
                    }
                    builder.add(numberBuildNumber.longValue());
                }
                return (T) dynamicOps.createLongList(builder.build());
            }
        };

        private final TypeSuffix defaultType;
        private final Set<TypeSuffix> additionalTypes;

        public abstract <T> T create(DynamicOps<T> dynamicOps);

        public abstract <T> T create(DynamicOps<T> dynamicOps, List<IntegerLiteral> list, ParseState<?> parseState);

        ArrayPrefix(TypeSuffix $$0, TypeSuffix... $$1) {
            this.additionalTypes = Set.of((Object[]) $$1);
            this.defaultType = $$0;
        }

        public boolean isAllowed(TypeSuffix $$0) {
            return $$0 == this.defaultType || this.additionalTypes.contains($$0);
        }

        protected Number buildNumber(IntegerLiteral $$0, ParseState<?> $$1) {
            TypeSuffix $$2 = computeType($$0.suffix);
            if ($$2 == null) {
                $$1.errorCollector().store($$1.mark(), SnbtGrammar.ERROR_INVALID_ARRAY_ELEMENT_TYPE);
                return null;
            }
            return (Number) $$0.create(JavaOps.INSTANCE, $$2, $$1);
        }

        private TypeSuffix computeType(IntegerSuffix $$0) {
            TypeSuffix $$1 = $$0.type();
            if ($$1 == null) {
                return this.defaultType;
            }
            if (!isAllowed($$1)) {
                return null;
            }
            return $$1;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/SnbtGrammar$SimpleHexLiteralParseRule.class */
    static class SimpleHexLiteralParseRule extends GreedyPredicateParseRule {
        public SimpleHexLiteralParseRule(int $$0) {
            super($$0, $$0, DelayedException.create(SnbtGrammar.ERROR_EXPECTED_HEX_ESCAPE, String.valueOf($$0)));
        }

        @Override // net.minecraft.util.parsing.packrat.commands.GreedyPredicateParseRule
        protected boolean isAccepted(char $$0) {
            switch ($$0) {
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case InputConstants.KEY_9 /* 57 */:
                case 'A':
                case 'B':
                case 'C':
                case 'D':
                case 'E':
                case 'F':
                case 'a':
                case LivingEntity.EQUIPMENT_SLOT_OFFSET /* 98 */:
                case Item.ABSOLUTE_MAX_STACK_SIZE /* 99 */:
                case 'd':
                case ClientboundGameEventPacket.DEMO_PARAM_HINT_1 /* 101 */:
                case ClientboundGameEventPacket.DEMO_PARAM_HINT_2 /* 102 */:
                    return true;
                case ':':
                case ';':
                case '<':
                case '=':
                case EntityEvent.SONIC_CHARGE /* 62 */:
                case '?':
                case '@':
                case InputConstants.KEY_G /* 71 */:
                case 'H':
                case InputConstants.KEY_I /* 73 */:
                case InputConstants.KEY_J /* 74 */:
                case 'K':
                case InputConstants.KEY_L /* 76 */:
                case InputConstants.KEY_M /* 77 */:
                case InputConstants.KEY_N /* 78 */:
                case InputConstants.KEY_O /* 79 */:
                case 'P':
                case InputConstants.KEY_Q /* 81 */:
                case InputConstants.KEY_R /* 82 */:
                case InputConstants.KEY_S /* 83 */:
                case InputConstants.KEY_T /* 84 */:
                case InputConstants.KEY_U /* 85 */:
                case InputConstants.KEY_V /* 86 */:
                case InputConstants.KEY_W /* 87 */:
                case 'X':
                case InputConstants.KEY_Y /* 89 */:
                case 'Z':
                case '[':
                case InputConstants.KEY_BACKSLASH /* 92 */:
                case ']':
                case '^':
                case '_':
                case InputConstants.KEY_GRAVE /* 96 */:
                default:
                    return false;
            }
        }
    }

    private static boolean isAllowedToStartUnquotedString(char $$0) {
        return !canStartNumber($$0);
    }

    static boolean canStartNumber(char $$0) {
        switch ($$0) {
            case '+':
            case '-':
            case '.':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case InputConstants.KEY_9 /* 57 */:
                return true;
            case ',':
            case '/':
            default:
                return false;
        }
    }

    static boolean needsUnderscoreRemoval(String $$0) {
        return $$0.indexOf(95) != -1;
    }

    private static void cleanAndAppend(StringBuilder $$0, String $$1) {
        cleanAndAppend($$0, $$1, needsUnderscoreRemoval($$1));
    }

    static void cleanAndAppend(StringBuilder $$0, String $$1, boolean $$2) {
        if ($$2) {
            for (char $$3 : $$1.toCharArray()) {
                if ($$3 != '_') {
                    $$0.append($$3);
                }
            }
            return;
        }
        $$0.append($$1);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/SnbtGrammar$IntegerLiteral.class */
    static final class IntegerLiteral extends Record {
        private final Sign sign;
        private final Base base;
        private final String digits;
        private final IntegerSuffix suffix;

        IntegerLiteral(Sign $$0, Base $$1, String $$2, IntegerSuffix $$3) {
            this.sign = $$0;
            this.base = $$1;
            this.digits = $$2;
            this.suffix = $$3;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, IntegerLiteral.class), IntegerLiteral.class, "sign;base;digits;suffix", "FIELD:Lnet/minecraft/nbt/SnbtGrammar$IntegerLiteral;->sign:Lnet/minecraft/nbt/SnbtGrammar$Sign;", "FIELD:Lnet/minecraft/nbt/SnbtGrammar$IntegerLiteral;->base:Lnet/minecraft/nbt/SnbtGrammar$Base;", "FIELD:Lnet/minecraft/nbt/SnbtGrammar$IntegerLiteral;->digits:Ljava/lang/String;", "FIELD:Lnet/minecraft/nbt/SnbtGrammar$IntegerLiteral;->suffix:Lnet/minecraft/nbt/SnbtGrammar$IntegerSuffix;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, IntegerLiteral.class), IntegerLiteral.class, "sign;base;digits;suffix", "FIELD:Lnet/minecraft/nbt/SnbtGrammar$IntegerLiteral;->sign:Lnet/minecraft/nbt/SnbtGrammar$Sign;", "FIELD:Lnet/minecraft/nbt/SnbtGrammar$IntegerLiteral;->base:Lnet/minecraft/nbt/SnbtGrammar$Base;", "FIELD:Lnet/minecraft/nbt/SnbtGrammar$IntegerLiteral;->digits:Ljava/lang/String;", "FIELD:Lnet/minecraft/nbt/SnbtGrammar$IntegerLiteral;->suffix:Lnet/minecraft/nbt/SnbtGrammar$IntegerSuffix;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, IntegerLiteral.class, Object.class), IntegerLiteral.class, "sign;base;digits;suffix", "FIELD:Lnet/minecraft/nbt/SnbtGrammar$IntegerLiteral;->sign:Lnet/minecraft/nbt/SnbtGrammar$Sign;", "FIELD:Lnet/minecraft/nbt/SnbtGrammar$IntegerLiteral;->base:Lnet/minecraft/nbt/SnbtGrammar$Base;", "FIELD:Lnet/minecraft/nbt/SnbtGrammar$IntegerLiteral;->digits:Ljava/lang/String;", "FIELD:Lnet/minecraft/nbt/SnbtGrammar$IntegerLiteral;->suffix:Lnet/minecraft/nbt/SnbtGrammar$IntegerSuffix;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Sign sign() {
            return this.sign;
        }

        public Base base() {
            return this.base;
        }

        public String digits() {
            return this.digits;
        }

        public IntegerSuffix suffix() {
            return this.suffix;
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
        private SignedPrefix signedOrDefault() throws MatchException {
            if (this.suffix.signed != null) {
                return this.suffix.signed;
            }
            switch (this.base) {
                case BINARY:
                case HEX:
                    return SignedPrefix.UNSIGNED;
                case DECIMAL:
                    return SignedPrefix.SIGNED;
                default:
                    throw new MatchException((String) null, (Throwable) null);
            }
        }

        private String cleanupDigits(Sign $$0) {
            boolean $$1 = SnbtGrammar.needsUnderscoreRemoval(this.digits);
            if ($$0 == Sign.MINUS || $$1) {
                StringBuilder $$2 = new StringBuilder();
                $$0.append($$2);
                SnbtGrammar.cleanAndAppend($$2, this.digits, $$1);
                return $$2.toString();
            }
            return this.digits;
        }

        public <T> T create(DynamicOps<T> dynamicOps, ParseState<?> parseState) {
            return (T) create(dynamicOps, (TypeSuffix) Objects.requireNonNullElse(this.suffix.type, TypeSuffix.INT), parseState);
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
        public <T> T create(DynamicOps<T> dynamicOps, TypeSuffix typeSuffix, ParseState<?> parseState) throws MatchException {
            int i;
            boolean z = signedOrDefault() == SignedPrefix.SIGNED;
            if (!z && this.sign == Sign.MINUS) {
                parseState.errorCollector().store(parseState.mark(), SnbtGrammar.ERROR_EXPECTED_NON_NEGATIVE_NUMBER);
                return null;
            }
            String strCleanupDigits = cleanupDigits(this.sign);
            switch (this.base) {
                case BINARY:
                    i = 2;
                    break;
                case DECIMAL:
                    i = 10;
                    break;
                case HEX:
                    i = 16;
                    break;
                default:
                    throw new MatchException((String) null, (Throwable) null);
            }
            int i2 = i;
            try {
                if (z) {
                    switch (typeSuffix.ordinal()) {
                        case 2:
                            return (T) dynamicOps.createByte(Byte.parseByte(strCleanupDigits, i2));
                        case 3:
                            return (T) dynamicOps.createShort(Short.parseShort(strCleanupDigits, i2));
                        case 4:
                            return (T) dynamicOps.createInt(Integer.parseInt(strCleanupDigits, i2));
                        case 5:
                            return (T) dynamicOps.createLong(Long.parseLong(strCleanupDigits, i2));
                        default:
                            parseState.errorCollector().store(parseState.mark(), SnbtGrammar.ERROR_EXPECTED_INTEGER_TYPE);
                            return null;
                    }
                }
                switch (typeSuffix.ordinal()) {
                    case 2:
                        return (T) dynamicOps.createByte(UnsignedBytes.parseUnsignedByte(strCleanupDigits, i2));
                    case 3:
                        return (T) dynamicOps.createShort(SnbtGrammar.parseUnsignedShort(strCleanupDigits, i2));
                    case 4:
                        return (T) dynamicOps.createInt(Integer.parseUnsignedInt(strCleanupDigits, i2));
                    case 5:
                        return (T) dynamicOps.createLong(Long.parseUnsignedLong(strCleanupDigits, i2));
                    default:
                        parseState.errorCollector().store(parseState.mark(), SnbtGrammar.ERROR_EXPECTED_INTEGER_TYPE);
                        return null;
                }
            } catch (NumberFormatException e) {
                parseState.errorCollector().store(parseState.mark(), SnbtGrammar.createNumberParseError(e));
                return null;
            }
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/SnbtGrammar$Signed.class */
    static final class Signed<T> extends Record {
        private final Sign sign;
        private final T value;

        Signed(Sign $$0, T $$1) {
            this.sign = $$0;
            this.value = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Signed.class), Signed.class, "sign;value", "FIELD:Lnet/minecraft/nbt/SnbtGrammar$Signed;->sign:Lnet/minecraft/nbt/SnbtGrammar$Sign;", "FIELD:Lnet/minecraft/nbt/SnbtGrammar$Signed;->value:Ljava/lang/Object;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Signed.class), Signed.class, "sign;value", "FIELD:Lnet/minecraft/nbt/SnbtGrammar$Signed;->sign:Lnet/minecraft/nbt/SnbtGrammar$Sign;", "FIELD:Lnet/minecraft/nbt/SnbtGrammar$Signed;->value:Ljava/lang/Object;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Signed.class, Object.class), Signed.class, "sign;value", "FIELD:Lnet/minecraft/nbt/SnbtGrammar$Signed;->sign:Lnet/minecraft/nbt/SnbtGrammar$Sign;", "FIELD:Lnet/minecraft/nbt/SnbtGrammar$Signed;->value:Ljava/lang/Object;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Sign sign() {
            return this.sign;
        }

        public T value() {
            return this.value;
        }
    }

    static short parseUnsignedShort(String $$0, int $$1) {
        int $$2 = Integer.parseInt($$0, $$1);
        if (($$2 >> 16) == 0) {
            return (short) $$2;
        }
        throw new NumberFormatException("out of range: " + $$2);
    }

    private static <T> T createFloat(DynamicOps<T> dynamicOps, Sign sign, String str, String str2, Signed<String> signed, TypeSuffix typeSuffix, ParseState<?> parseState) {
        StringBuilder sb = new StringBuilder();
        sign.append(sb);
        if (str != null) {
            cleanAndAppend(sb, str);
        }
        if (str2 != null) {
            sb.append('.');
            cleanAndAppend(sb, str2);
        }
        if (signed != null) {
            sb.append('e');
            signed.sign().append(sb);
            cleanAndAppend(sb, ((Signed) signed).value);
        }
        try {
            String string = sb.toString();
            switch ((int) SwitchBootstraps.enumSwitch(MethodHandles.lookup(), "enumSwitch", MethodType.methodType(Integer.TYPE, TypeSuffix.class, Integer.TYPE), "FLOAT", "DOUBLE").dynamicInvoker().invoke(typeSuffix, 0) /* invoke-custom */) {
                case -1:
                    break;
                case 0:
                    break;
                case 1:
                    break;
                default:
                    parseState.errorCollector().store(parseState.mark(), ERROR_EXPECTED_FLOAT_TYPE);
                    break;
            }
            return null;
        } catch (NumberFormatException e) {
            parseState.errorCollector().store(parseState.mark(), createNumberParseError(e));
            return null;
        }
    }

    private static <T> T convertFloat(DynamicOps<T> dynamicOps, ParseState<?> parseState, String str) {
        float f = Float.parseFloat(str);
        if (!Float.isFinite(f)) {
            parseState.errorCollector().store(parseState.mark(), ERROR_INFINITY_NOT_ALLOWED);
            return null;
        }
        return (T) dynamicOps.createFloat(f);
    }

    private static <T> T convertDouble(DynamicOps<T> dynamicOps, ParseState<?> parseState, String str) {
        double d = Double.parseDouble(str);
        if (!Double.isFinite(d)) {
            parseState.errorCollector().store(parseState.mark(), ERROR_INFINITY_NOT_ALLOWED);
            return null;
        }
        return (T) dynamicOps.createDouble(d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String joinList(List<String> $$0) {
        switch ($$0.size()) {
            case 0:
                return "";
            case 1:
                return (String) $$0.getFirst();
            default:
                return String.join("", $$0);
        }
    }

    public static <T> Grammar<T> createParser(DynamicOps<T> $$0) {
        Object objCreateBoolean = $$0.createBoolean(true);
        Object objCreateBoolean2 = $$0.createBoolean(false);
        Object objEmptyMap = $$0.emptyMap();
        Object objEmptyList = $$0.emptyList();
        Dictionary<StringReader> $$5 = new Dictionary<>();
        Atom<T> atomOf = Atom.of("sign");
        $$5.put(atomOf, Term.alternative(Term.sequence(StringReaderTerms.character('+'), Term.marker(atomOf, Sign.PLUS)), Term.sequence(StringReaderTerms.character('-'), Term.marker(atomOf, Sign.MINUS))), $$1 -> {
            return (Sign) $$1.getOrThrow(atomOf);
        });
        Atom<T> atomOf2 = Atom.of("integer_suffix");
        $$5.put(atomOf2, Term.alternative(Term.sequence(StringReaderTerms.characters('u', 'U'), Term.alternative(Term.sequence(StringReaderTerms.characters('b', 'B'), Term.marker(atomOf2, new IntegerSuffix(SignedPrefix.UNSIGNED, TypeSuffix.BYTE))), Term.sequence(StringReaderTerms.characters('s', 'S'), Term.marker(atomOf2, new IntegerSuffix(SignedPrefix.UNSIGNED, TypeSuffix.SHORT))), Term.sequence(StringReaderTerms.characters('i', 'I'), Term.marker(atomOf2, new IntegerSuffix(SignedPrefix.UNSIGNED, TypeSuffix.INT))), Term.sequence(StringReaderTerms.characters('l', 'L'), Term.marker(atomOf2, new IntegerSuffix(SignedPrefix.UNSIGNED, TypeSuffix.LONG))))), Term.sequence(StringReaderTerms.characters('s', 'S'), Term.alternative(Term.sequence(StringReaderTerms.characters('b', 'B'), Term.marker(atomOf2, new IntegerSuffix(SignedPrefix.SIGNED, TypeSuffix.BYTE))), Term.sequence(StringReaderTerms.characters('s', 'S'), Term.marker(atomOf2, new IntegerSuffix(SignedPrefix.SIGNED, TypeSuffix.SHORT))), Term.sequence(StringReaderTerms.characters('i', 'I'), Term.marker(atomOf2, new IntegerSuffix(SignedPrefix.SIGNED, TypeSuffix.INT))), Term.sequence(StringReaderTerms.characters('l', 'L'), Term.marker(atomOf2, new IntegerSuffix(SignedPrefix.SIGNED, TypeSuffix.LONG))))), Term.sequence(StringReaderTerms.characters('b', 'B'), Term.marker(atomOf2, new IntegerSuffix(null, TypeSuffix.BYTE))), Term.sequence(StringReaderTerms.characters('s', 'S'), Term.marker(atomOf2, new IntegerSuffix(null, TypeSuffix.SHORT))), Term.sequence(StringReaderTerms.characters('i', 'I'), Term.marker(atomOf2, new IntegerSuffix(null, TypeSuffix.INT))), Term.sequence(StringReaderTerms.characters('l', 'L'), Term.marker(atomOf2, new IntegerSuffix(null, TypeSuffix.LONG)))), $$12 -> {
            return (IntegerSuffix) $$12.getOrThrow(atomOf2);
        });
        Atom<T> atomOf3 = Atom.of("binary_numeral");
        $$5.put(atomOf3, BINARY_NUMERAL);
        Atom<T> atomOf4 = Atom.of("decimal_numeral");
        $$5.put(atomOf4, DECIMAL_NUMERAL);
        Atom<T> atomOf5 = Atom.of("hex_numeral");
        $$5.put(atomOf5, HEX_NUMERAL);
        Atom<T> atomOf6 = Atom.of("integer_literal");
        NamedRule<StringReader, T> namedRulePut = $$5.put(atomOf6, Term.sequence(Term.optional($$5.named(atomOf)), Term.alternative(Term.sequence(StringReaderTerms.character('0'), Term.cut(), Term.alternative(Term.sequence(StringReaderTerms.characters('x', 'X'), Term.cut(), $$5.named(atomOf5)), Term.sequence(StringReaderTerms.characters('b', 'B'), $$5.named(atomOf3)), Term.sequence($$5.named(atomOf4), Term.cut(), Term.fail(ERROR_LEADING_ZERO_NOT_ALLOWED)), Term.marker(atomOf4, "0"))), $$5.named(atomOf4)), Term.optional($$5.named(atomOf2))), $$52 -> {
            IntegerSuffix $$6 = (IntegerSuffix) $$52.getOrDefault(atomOf2, IntegerSuffix.EMPTY);
            Sign $$7 = (Sign) $$52.getOrDefault(atomOf, Sign.PLUS);
            String $$8 = (String) $$52.get(atomOf4);
            if ($$8 != null) {
                return new IntegerLiteral($$7, Base.DECIMAL, $$8, $$6);
            }
            String $$9 = (String) $$52.get(atomOf5);
            if ($$9 != null) {
                return new IntegerLiteral($$7, Base.HEX, $$9, $$6);
            }
            String $$10 = (String) $$52.getOrThrow(atomOf3);
            return new IntegerLiteral($$7, Base.BINARY, $$10, $$6);
        });
        Atom<T> atomOf7 = Atom.of("float_type_suffix");
        $$5.put(atomOf7, Term.alternative(Term.sequence(StringReaderTerms.characters('f', 'F'), Term.marker(atomOf7, TypeSuffix.FLOAT)), Term.sequence(StringReaderTerms.characters('d', 'D'), Term.marker(atomOf7, TypeSuffix.DOUBLE))), $$13 -> {
            return (TypeSuffix) $$13.getOrThrow(atomOf7);
        });
        Atom<T> atomOf8 = Atom.of("float_exponent_part");
        $$5.put(atomOf8, Term.sequence(StringReaderTerms.characters('e', 'E'), Term.optional($$5.named(atomOf)), $$5.named(atomOf4)), $$2 -> {
            return new Signed((Sign) $$2.getOrDefault(atomOf, Sign.PLUS), (String) $$2.getOrThrow(atomOf4));
        });
        Atom<T> atomOf9 = Atom.of("float_whole_part");
        Atom<T> atomOf10 = Atom.of("float_fraction_part");
        Atom<T> $$17 = Atom.of("float_literal");
        $$5.putComplex($$17, Term.sequence(Term.optional($$5.named(atomOf)), Term.alternative(Term.sequence($$5.namedWithAlias(atomOf4, atomOf9), StringReaderTerms.character('.'), Term.cut(), Term.optional($$5.namedWithAlias(atomOf4, atomOf10)), Term.optional($$5.named(atomOf8)), Term.optional($$5.named(atomOf7))), Term.sequence(StringReaderTerms.character('.'), Term.cut(), $$5.namedWithAlias(atomOf4, atomOf10), Term.optional($$5.named(atomOf8)), Term.optional($$5.named(atomOf7))), Term.sequence($$5.namedWithAlias(atomOf4, atomOf9), $$5.named(atomOf8), Term.cut(), Term.optional($$5.named(atomOf7))), Term.sequence($$5.namedWithAlias(atomOf4, atomOf9), Term.optional($$5.named(atomOf8)), $$5.named(atomOf7)))), $$6 -> {
            Scope $$7 = $$6.scope();
            Sign $$8 = (Sign) $$7.getOrDefault(atomOf, Sign.PLUS);
            String $$9 = (String) $$7.get(atomOf9);
            String $$10 = (String) $$7.get(atomOf10);
            Signed<String> $$11 = (Signed) $$7.get(atomOf8);
            TypeSuffix $$122 = (TypeSuffix) $$7.get(atomOf7);
            return createFloat($$0, $$8, $$9, $$10, $$11, $$122, $$6);
        });
        Atom<T> atomOf11 = Atom.of("string_hex_2");
        $$5.put(atomOf11, new SimpleHexLiteralParseRule(2));
        Atom<T> atomOf12 = Atom.of("string_hex_4");
        $$5.put(atomOf12, new SimpleHexLiteralParseRule(4));
        Atom<T> atomOf13 = Atom.of("string_hex_8");
        $$5.put(atomOf13, new SimpleHexLiteralParseRule(8));
        Atom<T> atomOf14 = Atom.of("string_unicode_name");
        $$5.put(atomOf14, new GreedyPatternParseRule(UNICODE_NAME, ERROR_INVALID_CHARACTER_NAME));
        Atom<T> atomOf15 = Atom.of("string_escape_sequence");
        $$5.putComplex(atomOf15, Term.alternative(Term.sequence(StringReaderTerms.character('b'), Term.marker(atomOf15, "\b")), Term.sequence(StringReaderTerms.character('s'), Term.marker(atomOf15, " ")), Term.sequence(StringReaderTerms.character('t'), Term.marker(atomOf15, "\t")), Term.sequence(StringReaderTerms.character('n'), Term.marker(atomOf15, Crypt.MIME_LINE_SEPARATOR)), Term.sequence(StringReaderTerms.character('f'), Term.marker(atomOf15, "\f")), Term.sequence(StringReaderTerms.character('r'), Term.marker(atomOf15, "\r")), Term.sequence(StringReaderTerms.character('\\'), Term.marker(atomOf15, "\\")), Term.sequence(StringReaderTerms.character('\''), Term.marker(atomOf15, "'")), Term.sequence(StringReaderTerms.character('\"'), Term.marker(atomOf15, "\"")), Term.sequence(StringReaderTerms.character('x'), $$5.named(atomOf11)), Term.sequence(StringReaderTerms.character('u'), $$5.named(atomOf12)), Term.sequence(StringReaderTerms.character('U'), $$5.named(atomOf13)), Term.sequence(StringReaderTerms.character('N'), StringReaderTerms.character('{'), $$5.named(atomOf14), StringReaderTerms.character('}'))), $$53 -> {
            Scope $$62 = $$53.scope();
            String $$7 = (String) $$62.getAny(atomOf15);
            if ($$7 != null) {
                return $$7;
            }
            String $$8 = (String) $$62.getAny(atomOf11, atomOf12, atomOf13);
            if ($$8 != null) {
                int $$9 = HexFormat.fromHexDigits($$8);
                if (!Character.isValidCodePoint($$9)) {
                    $$53.errorCollector().store($$53.mark(), DelayedException.create(ERROR_INVALID_CODEPOINT, String.format(Locale.ROOT, "U+%08X", Integer.valueOf($$9))));
                    return null;
                }
                return Character.toString($$9);
            }
            String $$10 = (String) $$62.getOrThrow(atomOf14);
            try {
                int $$132 = Character.codePointOf($$10);
                return Character.toString($$132);
            } catch (IllegalArgumentException e) {
                $$53.errorCollector().store($$53.mark(), ERROR_INVALID_CHARACTER_NAME);
                return null;
            }
        });
        Atom<T> atomOf16 = Atom.of("string_plain_contents");
        $$5.put(atomOf16, PLAIN_STRING_CHUNK);
        Atom<List<String>> $$24 = Atom.of("string_chunks");
        Atom<T> atomOf17 = Atom.of("string_contents");
        NamedRule<StringReader, T> namedRulePut2 = $$5.put(Atom.of("single_quoted_string_chunk"), Term.alternative($$5.namedWithAlias(atomOf16, atomOf17), Term.sequence(StringReaderTerms.character('\\'), $$5.namedWithAlias(atomOf15, atomOf17)), Term.sequence(StringReaderTerms.character('\"'), Term.marker(atomOf17, "\""))), $$14 -> {
            return (String) $$14.getOrThrow(atomOf17);
        });
        Atom<T> atomOf18 = Atom.of("single_quoted_string_contents");
        $$5.put(atomOf18, Term.repeated(namedRulePut2, $$24), $$15 -> {
            return joinList((List) $$15.getOrThrow($$24));
        });
        NamedRule<StringReader, T> namedRulePut3 = $$5.put(Atom.of("double_quoted_string_chunk"), Term.alternative($$5.namedWithAlias(atomOf16, atomOf17), Term.sequence(StringReaderTerms.character('\\'), $$5.namedWithAlias(atomOf15, atomOf17)), Term.sequence(StringReaderTerms.character('\''), Term.marker(atomOf17, "'"))), $$16 -> {
            return (String) $$16.getOrThrow(atomOf17);
        });
        Atom<T> atomOf19 = Atom.of("double_quoted_string_contents");
        $$5.put(atomOf19, Term.repeated(namedRulePut3, $$24), $$18 -> {
            return joinList((List) $$18.getOrThrow($$24));
        });
        Atom<T> atomOf20 = Atom.of("quoted_string_literal");
        $$5.put(atomOf20, Term.alternative(Term.sequence(StringReaderTerms.character('\"'), Term.cut(), Term.optional($$5.namedWithAlias(atomOf19, atomOf17)), StringReaderTerms.character('\"')), Term.sequence(StringReaderTerms.character('\''), Term.optional($$5.namedWithAlias(atomOf18, atomOf17)), StringReaderTerms.character('\''))), $$19 -> {
            return (String) $$19.getOrThrow(atomOf17);
        });
        Atom<T> atomOf21 = Atom.of("unquoted_string");
        $$5.put(atomOf21, new UnquotedStringParseRule(1, ERROR_EXPECTED_UNQUOTED_STRING));
        Atom<T> $$34 = Atom.of("literal");
        Atom<T> atomOf22 = Atom.of("arguments");
        $$5.put(atomOf22, Term.repeatedWithTrailingSeparator($$5.forward($$34), atomOf22, StringReaderTerms.character(',')), $$110 -> {
            return (List) $$110.getOrThrow(atomOf22);
        });
        Atom<T> $$36 = Atom.of("unquoted_string_or_builtin");
        $$5.putComplex($$36, Term.sequence($$5.named(atomOf21), Term.optional(Term.sequence(StringReaderTerms.character('('), $$5.named(atomOf22), StringReaderTerms.character(')')))), $$54 -> {
            Scope $$62 = $$54.scope();
            String $$7 = (String) $$62.getOrThrow(atomOf21);
            if ($$7.isEmpty() || !isAllowedToStartUnquotedString($$7.charAt(0))) {
                $$54.errorCollector().store($$54.mark(), SnbtOperations.BUILTIN_IDS, ERROR_INVALID_UNQUOTED_START);
                return null;
            }
            List list = (List) $$62.get(atomOf22);
            if (list != null) {
                SnbtOperations.BuiltinKey $$9 = new SnbtOperations.BuiltinKey($$7, list.size());
                SnbtOperations.BuiltinOperation $$10 = SnbtOperations.BUILTIN_OPERATIONS.get($$9);
                if ($$10 != null) {
                    return $$10.run($$0, list, $$54);
                }
                $$54.errorCollector().store($$54.mark(), DelayedException.create(ERROR_NO_SUCH_OPERATION, $$9.toString()));
                return null;
            }
            if ($$7.equalsIgnoreCase(SnbtOperations.BUILTIN_TRUE)) {
                return objCreateBoolean;
            }
            if ($$7.equalsIgnoreCase(SnbtOperations.BUILTIN_FALSE)) {
                return objCreateBoolean2;
            }
            return $$0.createString($$7);
        });
        Atom<T> atomOf23 = Atom.of("map_key");
        $$5.put(atomOf23, Term.alternative($$5.named(atomOf20), $$5.named(atomOf21)), $$22 -> {
            return (String) $$22.getAnyOrThrow(atomOf20, atomOf21);
        });
        NamedRule<StringReader, T> namedRulePutComplex = $$5.putComplex(Atom.of("map_entry"), Term.sequence($$5.named(atomOf23), StringReaderTerms.character(':'), $$5.named($$34)), $$23 -> {
            Scope $$3 = $$23.scope();
            String $$4 = (String) $$3.getOrThrow(atomOf23);
            if ($$4.isEmpty()) {
                $$23.errorCollector().store($$23.mark(), ERROR_EMPTY_KEY);
                return null;
            }
            return Map.entry($$4, $$3.getOrThrow($$34));
        });
        Atom<T> atomOf24 = Atom.of("map_entries");
        $$5.put(atomOf24, Term.repeatedWithTrailingSeparator(namedRulePutComplex, atomOf24, StringReaderTerms.character(',')), $$111 -> {
            return (List) $$111.getOrThrow(atomOf24);
        });
        Atom<T> $$41 = Atom.of("map_literal");
        $$5.put($$41, Term.sequence(StringReaderTerms.character('{'), $$5.named(atomOf24), StringReaderTerms.character('}')), $$3 -> {
            List<Map.Entry> list = (List) $$3.getOrThrow(atomOf24);
            if (list.isEmpty()) {
                return objEmptyMap;
            }
            ImmutableMap.Builder builderBuilderWithExpectedSize = ImmutableMap.builderWithExpectedSize(list.size());
            for (Map.Entry entry : list) {
                builderBuilderWithExpectedSize.put($$0.createString((String) entry.getKey()), entry.getValue());
            }
            return $$0.createMap(builderBuilderWithExpectedSize.buildKeepingLast());
        });
        Atom<T> atomOf25 = Atom.of("list_entries");
        $$5.put(atomOf25, Term.repeatedWithTrailingSeparator($$5.forward($$34), atomOf25, StringReaderTerms.character(',')), $$112 -> {
            return (List) $$112.getOrThrow(atomOf25);
        });
        Atom<T> atomOf26 = Atom.of("array_prefix");
        $$5.put(atomOf26, Term.alternative(Term.sequence(StringReaderTerms.character('B'), Term.marker(atomOf26, ArrayPrefix.BYTE)), Term.sequence(StringReaderTerms.character('L'), Term.marker(atomOf26, ArrayPrefix.LONG)), Term.sequence(StringReaderTerms.character('I'), Term.marker(atomOf26, ArrayPrefix.INT))), $$113 -> {
            return (ArrayPrefix) $$113.getOrThrow(atomOf26);
        });
        Atom<T> atomOf27 = Atom.of("int_array_entries");
        $$5.put(atomOf27, Term.repeatedWithTrailingSeparator(namedRulePut, atomOf27, StringReaderTerms.character(',')), $$114 -> {
            return (List) $$114.getOrThrow(atomOf27);
        });
        Atom<T> $$45 = Atom.of("list_literal");
        $$5.putComplex($$45, Term.sequence(StringReaderTerms.character('['), Term.alternative(Term.sequence($$5.named(atomOf26), StringReaderTerms.character(';'), $$5.named(atomOf27)), $$5.named(atomOf25)), StringReaderTerms.character(']')), $$55 -> {
            Scope $$62 = $$55.scope();
            ArrayPrefix $$7 = (ArrayPrefix) $$62.get(atomOf26);
            if ($$7 != null) {
                List<IntegerLiteral> $$8 = (List) $$62.getOrThrow(atomOf27);
                return $$8.isEmpty() ? $$7.create($$0) : $$7.create($$0, $$8, $$55);
            }
            List list = (List) $$62.getOrThrow(atomOf25);
            return list.isEmpty() ? objEmptyList : $$0.createList(list.stream());
        });
        NamedRule<StringReader, T> $$46 = $$5.putComplex($$34, Term.alternative(Term.sequence(Term.positiveLookahead(NUMBER_LOOKEAHEAD), Term.alternative($$5.namedWithAlias($$17, $$34), $$5.named(atomOf6))), Term.sequence(Term.positiveLookahead(StringReaderTerms.characters('\"', '\'')), Term.cut(), $$5.named(atomOf20)), Term.sequence(Term.positiveLookahead(StringReaderTerms.character('{')), Term.cut(), $$5.namedWithAlias($$41, $$34)), Term.sequence(Term.positiveLookahead(StringReaderTerms.character('[')), Term.cut(), $$5.namedWithAlias($$45, $$34)), $$5.namedWithAlias($$36, $$34)), $$4 -> {
            Scope $$56 = $$4.scope();
            String $$62 = (String) $$56.get(atomOf20);
            if ($$62 != null) {
                return $$0.createString($$62);
            }
            IntegerLiteral $$7 = (IntegerLiteral) $$56.get(atomOf6);
            if ($$7 != null) {
                return $$7.create($$0, $$4);
            }
            return $$56.getOrThrow($$34);
        });
        return new Grammar<>($$5, $$46);
    }
}
