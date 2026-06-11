package net.minecraft.util.parsing.packrat.commands;

import com.mojang.brigadier.StringReader;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import net.minecraft.nbt.TagParser;
import net.minecraft.util.parsing.packrat.ParseState;
import net.minecraft.util.parsing.packrat.Rule;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/parsing/packrat/commands/TagParseRule.class */
public class TagParseRule<T> implements Rule<StringReader, Dynamic<?>> {
    private final TagParser<T> parser;

    public TagParseRule(DynamicOps<T> $$0) {
        this.parser = TagParser.create($$0);
    }

    @Override // net.minecraft.util.parsing.packrat.Rule
    /* JADX INFO: renamed from: parse, reason: merged with bridge method [inline-methods] */
    public Dynamic<?> parse2(ParseState<StringReader> $$0) {
        $$0.input().skipWhitespace();
        int $$1 = $$0.mark();
        try {
            return new Dynamic<>(this.parser.getOps(), this.parser.parseAsArgument($$0.input()));
        } catch (Exception $$2) {
            $$0.errorCollector().store($$1, $$2);
            return null;
        }
    }
}
