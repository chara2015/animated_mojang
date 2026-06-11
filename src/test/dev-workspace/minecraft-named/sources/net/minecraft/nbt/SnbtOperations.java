package net.minecraft.nbt;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.serialization.DynamicOps;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.util.parsing.packrat.DelayedException;
import net.minecraft.util.parsing.packrat.ParseState;
import net.minecraft.util.parsing.packrat.SuggestionSupplier;
import net.minecraft.world.level.levelgen.Density;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/SnbtOperations.class */
public class SnbtOperations {
    public static final String BUILTIN_TRUE = "true";
    public static final String BUILTIN_FALSE = "false";
    static final DelayedException<CommandSyntaxException> ERROR_EXPECTED_STRING_UUID = DelayedException.create(new SimpleCommandExceptionType(Component.translatable("snbt.parser.expected_string_uuid")));
    static final DelayedException<CommandSyntaxException> ERROR_EXPECTED_NUMBER_OR_BOOLEAN = DelayedException.create(new SimpleCommandExceptionType(Component.translatable("snbt.parser.expected_number_or_boolean")));
    public static final Map<BuiltinKey, BuiltinOperation> BUILTIN_OPERATIONS = Map.of(new BuiltinKey("bool", 1), new BuiltinOperation() { // from class: net.minecraft.nbt.SnbtOperations.1
        @Override // net.minecraft.nbt.SnbtOperations.BuiltinOperation
        public <T> T run(DynamicOps<T> dynamicOps, List<T> list, ParseState<StringReader> parseState) {
            Boolean boolConvert = convert(dynamicOps, list.getFirst());
            if (boolConvert == null) {
                parseState.errorCollector().store(parseState.mark(), SnbtOperations.ERROR_EXPECTED_NUMBER_OR_BOOLEAN);
                return null;
            }
            return (T) dynamicOps.createBoolean(boolConvert.booleanValue());
        }

        private static <T> Boolean convert(DynamicOps<T> $$0, T $$1) {
            Optional<Boolean> $$2 = $$0.getBooleanValue($$1).result();
            if ($$2.isPresent()) {
                return $$2.get();
            }
            Optional<Number> $$3 = $$0.getNumberValue($$1).result();
            if ($$3.isPresent()) {
                return Boolean.valueOf($$3.get().doubleValue() != Density.SURFACE);
            }
            return null;
        }
    }, new BuiltinKey("uuid", 1), new BuiltinOperation() { // from class: net.minecraft.nbt.SnbtOperations.2
        @Override // net.minecraft.nbt.SnbtOperations.BuiltinOperation
        public <T> T run(DynamicOps<T> dynamicOps, List<T> list, ParseState<StringReader> parseState) {
            Optional optionalResult = dynamicOps.getStringValue(list.getFirst()).result();
            if (optionalResult.isEmpty()) {
                parseState.errorCollector().store(parseState.mark(), SnbtOperations.ERROR_EXPECTED_STRING_UUID);
                return null;
            }
            try {
                return (T) dynamicOps.createIntList(IntStream.of(UUIDUtil.uuidToIntArray(UUID.fromString((String) optionalResult.get()))));
            } catch (IllegalArgumentException e) {
                parseState.errorCollector().store(parseState.mark(), SnbtOperations.ERROR_EXPECTED_STRING_UUID);
                return null;
            }
        }
    });
    public static final SuggestionSupplier<StringReader> BUILTIN_IDS = new SuggestionSupplier<StringReader>() { // from class: net.minecraft.nbt.SnbtOperations.3
        private final Set<String> keys = (Set) Stream.concat(Stream.of((Object[]) new String[]{SnbtOperations.BUILTIN_FALSE, SnbtOperations.BUILTIN_TRUE}), SnbtOperations.BUILTIN_OPERATIONS.keySet().stream().map((v0) -> {
            return v0.id();
        })).collect(Collectors.toSet());

        @Override // net.minecraft.util.parsing.packrat.SuggestionSupplier
        public Stream<String> possibleValues(ParseState<StringReader> $$0) {
            return this.keys.stream();
        }
    };

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/SnbtOperations$BuiltinOperation.class */
    public interface BuiltinOperation {
        <T> T run(DynamicOps<T> dynamicOps, List<T> list, ParseState<StringReader> parseState);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/SnbtOperations$BuiltinKey.class */
    public static final class BuiltinKey extends Record {
        private final String id;
        private final int argCount;

        public BuiltinKey(String $$0, int $$1) {
            this.id = $$0;
            this.argCount = $$1;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, BuiltinKey.class), BuiltinKey.class, "id;argCount", "FIELD:Lnet/minecraft/nbt/SnbtOperations$BuiltinKey;->id:Ljava/lang/String;", "FIELD:Lnet/minecraft/nbt/SnbtOperations$BuiltinKey;->argCount:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, BuiltinKey.class, Object.class), BuiltinKey.class, "id;argCount", "FIELD:Lnet/minecraft/nbt/SnbtOperations$BuiltinKey;->id:Ljava/lang/String;", "FIELD:Lnet/minecraft/nbt/SnbtOperations$BuiltinKey;->argCount:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public String id() {
            return this.id;
        }

        public int argCount() {
            return this.argCount;
        }

        @Override // java.lang.Record
        public String toString() {
            return this.id + "/" + this.argCount;
        }
    }
}
