package net.minecraft.util.context;

import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/context/ContextKey.class */
public class ContextKey<T> {
    private final Identifier name;

    public ContextKey(Identifier $$0) {
        this.name = $$0;
    }

    public static <T> ContextKey<T> vanilla(String $$0) {
        return new ContextKey<>(Identifier.withDefaultNamespace($$0));
    }

    public Identifier name() {
        return this.name;
    }

    public String toString() {
        return "<parameter " + String.valueOf(this.name) + ">";
    }
}
