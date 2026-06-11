package net.minecraft.util.parsing.packrat.commands;

import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.resources.Identifier;
import net.minecraft.util.parsing.packrat.DelayedException;
import net.minecraft.util.parsing.packrat.NamedRule;
import net.minecraft.util.parsing.packrat.ParseState;
import net.minecraft.util.parsing.packrat.Rule;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/parsing/packrat/commands/ResourceLookupRule.class */
public abstract class ResourceLookupRule<C, V> implements Rule<StringReader, V>, ResourceSuggestion {
    private final NamedRule<StringReader, Identifier> idParser;
    protected final C context;
    private final DelayedException<CommandSyntaxException> error = DelayedException.create(Identifier.ERROR_INVALID);

    protected abstract V validateElement(ImmutableStringReader immutableStringReader, Identifier identifier) throws Exception;

    protected ResourceLookupRule(NamedRule<StringReader, Identifier> $$0, C $$1) {
        this.idParser = $$0;
        this.context = $$1;
    }

    @Override // net.minecraft.util.parsing.packrat.Rule
    /* JADX INFO: renamed from: parse */
    public V parse2(ParseState<StringReader> $$0) {
        $$0.input().skipWhitespace();
        int $$1 = $$0.mark();
        Identifier $$2 = (Identifier) $$0.parse(this.idParser);
        if ($$2 != null) {
            try {
                return validateElement($$0.input(), $$2);
            } catch (Exception $$3) {
                $$0.errorCollector().store($$1, this, $$3);
                return null;
            }
        }
        $$0.errorCollector().store($$1, this, this.error);
        return null;
    }
}
