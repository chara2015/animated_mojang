package net.minecraft.commands.functions;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntLists;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.SwitchBootstraps;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import net.minecraft.commands.ExecutionCommandSource;
import net.minecraft.commands.FunctionInstantiationException;
import net.minecraft.commands.execution.UnboundEntryAction;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.scores.Scoreboard;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/functions/MacroFunction.class */
public class MacroFunction<T extends ExecutionCommandSource<T>> implements CommandFunction<T> {
    private static final DecimalFormat DECIMAL_FORMAT = (DecimalFormat) Util.make(new DecimalFormat(Scoreboard.HIDDEN_SCORE_PREFIX, DecimalFormatSymbols.getInstance(Locale.ROOT)), $$0 -> {
        $$0.setMaximumFractionDigits(15);
    });
    private static final int MAX_CACHE_ENTRIES = 8;
    private final List<String> parameters;
    private final Object2ObjectLinkedOpenHashMap<List<String>, InstantiatedFunction<T>> cache = new Object2ObjectLinkedOpenHashMap<>(8, 0.25f);
    private final Identifier id;
    private final List<Entry<T>> entries;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/functions/MacroFunction$Entry.class */
    interface Entry<T> {
        IntList parameters();

        UnboundEntryAction<T> instantiate(List<String> list, CommandDispatcher<T> commandDispatcher, Identifier identifier) throws FunctionInstantiationException;
    }

    public MacroFunction(Identifier $$0, List<Entry<T>> $$1, List<String> $$2) {
        this.id = $$0;
        this.entries = $$1;
        this.parameters = $$2;
    }

    @Override // net.minecraft.commands.functions.CommandFunction
    public Identifier id() {
        return this.id;
    }

    @Override // net.minecraft.commands.functions.CommandFunction
    public InstantiatedFunction<T> instantiate(CompoundTag $$0, CommandDispatcher<T> $$1) throws FunctionInstantiationException {
        if ($$0 == null) {
            throw new FunctionInstantiationException(Component.translatable("commands.function.error.missing_arguments", Component.translationArg(id())));
        }
        List<String> $$2 = new ArrayList<>(this.parameters.size());
        for (String $$3 : this.parameters) {
            Tag $$4 = $$0.get($$3);
            if ($$4 == null) {
                throw new FunctionInstantiationException(Component.translatable("commands.function.error.missing_argument", Component.translationArg(id()), $$3));
            }
            $$2.add(stringify($$4));
        }
        InstantiatedFunction<T> $$5 = (InstantiatedFunction) this.cache.getAndMoveToLast($$2);
        if ($$5 != null) {
            return $$5;
        }
        if (this.cache.size() >= 8) {
            this.cache.removeFirst();
        }
        InstantiatedFunction<T> $$6 = substituteAndParse(this.parameters, $$2, $$1);
        this.cache.put($$2, $$6);
        return $$6;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    private static String stringify(Tag $$0) throws MatchException {
        String strValueOf;
        Objects.requireNonNull($$0);
        try {
            switch ((int) SwitchBootstraps.typeSwitch(MethodHandles.lookup(), "typeSwitch", MethodType.methodType(Integer.TYPE, Object.class, Integer.TYPE), FloatTag.class, DoubleTag.class, ByteTag.class, ShortTag.class, LongTag.class, StringTag.class).dynamicInvoker().invoke($$0, 0) /* invoke-custom */) {
                case 0:
                    float $$1 = ((FloatTag) $$0).value();
                    strValueOf = DECIMAL_FORMAT.format($$1);
                    break;
                case 1:
                    double $$2 = ((DoubleTag) $$0).value();
                    strValueOf = DECIMAL_FORMAT.format($$2);
                    break;
                case 2:
                    byte $$3 = ((ByteTag) $$0).value();
                    strValueOf = String.valueOf((int) $$3);
                    break;
                case 3:
                    short $$4 = ((ShortTag) $$0).value();
                    strValueOf = String.valueOf((int) $$4);
                    break;
                case 4:
                    long $$5 = ((LongTag) $$0).value();
                    strValueOf = String.valueOf($$5);
                    break;
                case 5:
                    String $$6 = ((StringTag) $$0).value();
                    strValueOf = $$6;
                    break;
                default:
                    return $$0.toString();
            }
            return strValueOf;
        } catch (Throwable th) {
            throw new MatchException(th.toString(), th);
        }
    }

    private static void lookupValues(List<String> $$0, IntList $$1, List<String> $$2) {
        $$2.clear();
        $$1.forEach($$22 -> {
            $$2.add((String) $$0.get($$22));
        });
    }

    private InstantiatedFunction<T> substituteAndParse(List<String> $$0, List<String> $$1, CommandDispatcher<T> $$2) throws FunctionInstantiationException {
        List<UnboundEntryAction<T>> $$3 = new ArrayList<>(this.entries.size());
        List<String> $$4 = new ArrayList<>($$1.size());
        for (Entry<T> $$5 : this.entries) {
            lookupValues($$1, $$5.parameters(), $$4);
            $$3.add($$5.instantiate($$4, $$2, this.id));
        }
        return new PlainTextFunction(id().withPath($$12 -> {
            return $$12 + "/" + $$0.hashCode();
        }), $$3);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/functions/MacroFunction$PlainTextEntry.class */
    static class PlainTextEntry<T> implements Entry<T> {
        private final UnboundEntryAction<T> compiledAction;

        public PlainTextEntry(UnboundEntryAction<T> $$0) {
            this.compiledAction = $$0;
        }

        @Override // net.minecraft.commands.functions.MacroFunction.Entry
        public IntList parameters() {
            return IntLists.emptyList();
        }

        @Override // net.minecraft.commands.functions.MacroFunction.Entry
        public UnboundEntryAction<T> instantiate(List<String> $$0, CommandDispatcher<T> $$1, Identifier $$2) {
            return this.compiledAction;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/functions/MacroFunction$MacroEntry.class */
    static class MacroEntry<T extends ExecutionCommandSource<T>> implements Entry<T> {
        private final StringTemplate template;
        private final IntList parameters;
        private final T compilationContext;

        public MacroEntry(StringTemplate $$0, IntList $$1, T $$2) {
            this.template = $$0;
            this.parameters = $$1;
            this.compilationContext = $$2;
        }

        @Override // net.minecraft.commands.functions.MacroFunction.Entry
        public IntList parameters() {
            return this.parameters;
        }

        @Override // net.minecraft.commands.functions.MacroFunction.Entry
        public UnboundEntryAction<T> instantiate(List<String> $$0, CommandDispatcher<T> $$1, Identifier $$2) throws FunctionInstantiationException {
            String $$3 = this.template.substitute($$0);
            try {
                return CommandFunction.parseCommand($$1, this.compilationContext, new StringReader($$3));
            } catch (CommandSyntaxException $$4) {
                throw new FunctionInstantiationException(Component.translatable("commands.function.error.parse", Component.translationArg($$2), $$3, $$4.getMessage()));
            }
        }
    }
}
