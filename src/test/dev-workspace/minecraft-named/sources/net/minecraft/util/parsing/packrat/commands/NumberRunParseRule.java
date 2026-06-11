package net.minecraft.util.parsing.packrat.commands;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.util.parsing.packrat.DelayedException;
import net.minecraft.util.parsing.packrat.ParseState;
import net.minecraft.util.parsing.packrat.Rule;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/parsing/packrat/commands/NumberRunParseRule.class */
public abstract class NumberRunParseRule implements Rule<StringReader, String> {
    private final DelayedException<CommandSyntaxException> noValueError;
    private final DelayedException<CommandSyntaxException> underscoreNotAllowedError;

    protected abstract boolean isAccepted(char c);

    public NumberRunParseRule(DelayedException<CommandSyntaxException> $$0, DelayedException<CommandSyntaxException> $$1) {
        this.noValueError = $$0;
        this.underscoreNotAllowedError = $$1;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.minecraft.util.parsing.packrat.Rule
    public String parse(ParseState<StringReader> $$0) {
        StringReader $$1 = $$0.input();
        $$1.skipWhitespace();
        String $$2 = $$1.getString();
        int $$3 = $$1.getCursor();
        int $$4 = $$3;
        while ($$4 < $$2.length() && isAccepted($$2.charAt($$4))) {
            $$4++;
        }
        int $$5 = $$4 - $$3;
        if ($$5 == 0) {
            $$0.errorCollector().store($$0.mark(), this.noValueError);
            return null;
        }
        if ($$2.charAt($$3) == '_' || $$2.charAt($$4 - 1) == '_') {
            $$0.errorCollector().store($$0.mark(), this.underscoreNotAllowedError);
            return null;
        }
        $$1.setCursor($$4);
        return $$2.substring($$3, $$4);
    }
}
