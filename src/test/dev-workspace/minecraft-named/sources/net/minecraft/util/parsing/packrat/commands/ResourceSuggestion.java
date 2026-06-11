package net.minecraft.util.parsing.packrat.commands;

import com.mojang.brigadier.StringReader;
import java.util.stream.Stream;
import net.minecraft.resources.Identifier;
import net.minecraft.util.parsing.packrat.ParseState;
import net.minecraft.util.parsing.packrat.SuggestionSupplier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/parsing/packrat/commands/ResourceSuggestion.class */
public interface ResourceSuggestion extends SuggestionSupplier<StringReader> {
    Stream<Identifier> possibleResources();

    @Override // net.minecraft.util.parsing.packrat.SuggestionSupplier
    default Stream<String> possibleValues(ParseState<StringReader> $$0) {
        return possibleResources().map((v0) -> {
            return v0.toString();
        });
    }
}
