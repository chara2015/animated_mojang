package net.minecraft.server.commands;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/commands/InCommandFunction.class */
@FunctionalInterface
public interface InCommandFunction<T, R> {
    R apply(T t) throws CommandSyntaxException;
}
