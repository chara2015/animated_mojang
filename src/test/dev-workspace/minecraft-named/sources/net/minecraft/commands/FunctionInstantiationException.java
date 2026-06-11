package net.minecraft.commands;

import net.minecraft.network.chat.Component;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/FunctionInstantiationException.class */
public class FunctionInstantiationException extends Exception {
    private final Component messageComponent;

    public FunctionInstantiationException(Component $$0) {
        super($$0.getString());
        this.messageComponent = $$0;
    }

    public Component messageComponent() {
        return this.messageComponent;
    }
}
