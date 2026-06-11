package net.minecraft.util.parsing.packrat.commands;

import com.mojang.brigadier.StringReader;
import net.minecraft.util.parsing.packrat.CachedParseState;
import net.minecraft.util.parsing.packrat.ErrorCollector;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/parsing/packrat/commands/StringReaderParserState.class */
public class StringReaderParserState extends CachedParseState<StringReader> {
    private final StringReader input;

    public StringReaderParserState(ErrorCollector<StringReader> $$0, StringReader $$1) {
        super($$0);
        this.input = $$1;
    }

    @Override // net.minecraft.util.parsing.packrat.ParseState
    public StringReader input() {
        return this.input;
    }

    @Override // net.minecraft.util.parsing.packrat.ParseState
    public int mark() {
        return this.input.getCursor();
    }

    @Override // net.minecraft.util.parsing.packrat.ParseState
    public void restore(int $$0) {
        this.input.setCursor($$0);
    }
}
