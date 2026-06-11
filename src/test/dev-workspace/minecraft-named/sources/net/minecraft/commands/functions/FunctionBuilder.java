package net.minecraft.commands.functions;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.commands.ExecutionCommandSource;
import net.minecraft.commands.execution.UnboundEntryAction;
import net.minecraft.commands.functions.MacroFunction;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/functions/FunctionBuilder.class */
class FunctionBuilder<T extends ExecutionCommandSource<T>> {
    private List<MacroFunction.Entry<T>> macroEntries;
    private List<UnboundEntryAction<T>> plainEntries = new ArrayList();
    private final List<String> macroArguments = new ArrayList();

    FunctionBuilder() {
    }

    public void addCommand(UnboundEntryAction<T> $$0) {
        if (this.macroEntries != null) {
            this.macroEntries.add(new MacroFunction.PlainTextEntry($$0));
        } else {
            this.plainEntries.add($$0);
        }
    }

    private int getArgumentIndex(String $$0) {
        int $$1 = this.macroArguments.indexOf($$0);
        if ($$1 == -1) {
            $$1 = this.macroArguments.size();
            this.macroArguments.add($$0);
        }
        return $$1;
    }

    private IntList convertToIndices(List<String> $$0) {
        IntArrayList $$1 = new IntArrayList($$0.size());
        for (String $$2 : $$0) {
            $$1.add(getArgumentIndex($$2));
        }
        return $$1;
    }

    public void addMacro(String $$0, int $$1, T $$2) {
        try {
            StringTemplate $$5 = StringTemplate.fromString($$0);
            if (this.plainEntries != null) {
                this.macroEntries = new ArrayList(this.plainEntries.size() + 1);
                for (UnboundEntryAction<T> $$6 : this.plainEntries) {
                    this.macroEntries.add(new MacroFunction.PlainTextEntry($$6));
                }
                this.plainEntries = null;
            }
            this.macroEntries.add(new MacroFunction.MacroEntry($$5, convertToIndices($$5.variables()), $$2));
        } catch (Exception $$4) {
            throw new IllegalArgumentException("Can't parse function line " + $$1 + ": '" + $$0 + "'", $$4);
        }
    }

    public CommandFunction<T> build(Identifier $$0) {
        if (this.macroEntries != null) {
            return new MacroFunction($$0, this.macroEntries, this.macroArguments);
        }
        return new PlainTextFunction($$0, this.plainEntries);
    }
}
