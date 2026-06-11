package net.minecraft.util.parsing.packrat.commands;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.util.parsing.packrat.DelayedException;
import net.minecraft.util.parsing.packrat.ParseState;
import net.minecraft.util.parsing.packrat.Rule;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/parsing/packrat/commands/GreedyPatternParseRule.class */
public final class GreedyPatternParseRule implements Rule<StringReader, String> {
    private final Pattern pattern;
    private final DelayedException<CommandSyntaxException> error;

    public GreedyPatternParseRule(Pattern $$0, DelayedException<CommandSyntaxException> $$1) {
        this.pattern = $$0;
        this.error = $$1;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.minecraft.util.parsing.packrat.Rule
    public String parse(ParseState<StringReader> $$0) {
        StringReader $$1 = $$0.input();
        String $$2 = $$1.getString();
        Matcher $$3 = this.pattern.matcher($$2).region($$1.getCursor(), $$2.length());
        if (!$$3.lookingAt()) {
            $$0.errorCollector().store($$0.mark(), this.error);
            return null;
        }
        $$1.setCursor($$3.end());
        return $$3.group(0);
    }
}
