package net.minecraft.server;

import net.minecraft.commands.CommandSourceStack;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/ConsoleInput.class */
public class ConsoleInput {
    public final String msg;
    public final CommandSourceStack source;

    public ConsoleInput(String $$0, CommandSourceStack $$1) {
        this.msg = $$0;
        this.source = $$1;
    }
}
