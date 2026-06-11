package net.minecraft.util.parsing.packrat;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.lang.Exception;
import net.minecraft.util.parsing.packrat.commands.StringReaderTerms;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/parsing/packrat/DelayedException.class */
public interface DelayedException<T extends Exception> {
    T create(String str, int i);

    static DelayedException<CommandSyntaxException> create(SimpleCommandExceptionType $$0) {
        return ($$1, $$2) -> {
            return $$0.createWithContext(StringReaderTerms.createReader($$1, $$2));
        };
    }

    static DelayedException<CommandSyntaxException> create(DynamicCommandExceptionType $$0, String $$1) {
        return ($$2, $$3) -> {
            return $$0.createWithContext(StringReaderTerms.createReader($$2, $$3), $$1);
        };
    }
}
