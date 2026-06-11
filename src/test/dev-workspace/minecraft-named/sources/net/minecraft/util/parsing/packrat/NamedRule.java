package net.minecraft.util.parsing.packrat;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/parsing/packrat/NamedRule.class */
public interface NamedRule<S, T> {
    Atom<T> name();

    Rule<S, T> value();
}
