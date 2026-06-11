package net.minecraft.util.parsing.packrat.commands;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.util.parsing.packrat.DelayedException;
import net.minecraft.util.parsing.packrat.ParseState;
import net.minecraft.util.parsing.packrat.Rule;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/parsing/packrat/commands/UnquotedStringParseRule.class */
public class UnquotedStringParseRule implements Rule<StringReader, String> {
    private final int minSize;
    private final DelayedException<CommandSyntaxException> error;

    public UnquotedStringParseRule(int $$0, DelayedException<CommandSyntaxException> $$1) {
        this.minSize = $$0;
        this.error = $$1;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.minecraft.util.parsing.packrat.Rule
    public String parse(ParseState<StringReader> $$0) {
        $$0.input().skipWhitespace();
        int $$1 = $$0.mark();
        String $$2 = $$0.input().readUnquotedString();
        if ($$2.length() < this.minSize) {
            $$0.errorCollector().store($$1, this.error);
            return null;
        }
        return $$2;
    }
}
