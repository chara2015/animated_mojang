package net.minecraft.commands;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Map;
import net.minecraft.network.chat.PlayerChatMessage;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/CommandSigningContext.class */
public interface CommandSigningContext {
    public static final CommandSigningContext ANONYMOUS = new CommandSigningContext() { // from class: net.minecraft.commands.CommandSigningContext.1
        @Override // net.minecraft.commands.CommandSigningContext
        public PlayerChatMessage getArgument(String $$0) {
            return null;
        }
    };

    PlayerChatMessage getArgument(String str);

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/CommandSigningContext$SignedArguments.class */
    public static final class SignedArguments extends Record implements CommandSigningContext {
        private final Map<String, PlayerChatMessage> arguments;

        public SignedArguments(Map<String, PlayerChatMessage> $$0) {
            this.arguments = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, SignedArguments.class), SignedArguments.class, "arguments", "FIELD:Lnet/minecraft/commands/CommandSigningContext$SignedArguments;->arguments:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, SignedArguments.class), SignedArguments.class, "arguments", "FIELD:Lnet/minecraft/commands/CommandSigningContext$SignedArguments;->arguments:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, SignedArguments.class, Object.class), SignedArguments.class, "arguments", "FIELD:Lnet/minecraft/commands/CommandSigningContext$SignedArguments;->arguments:Ljava/util/Map;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Map<String, PlayerChatMessage> arguments() {
            return this.arguments;
        }

        @Override // net.minecraft.commands.CommandSigningContext
        public PlayerChatMessage getArgument(String $$0) {
            return this.arguments.get($$0);
        }
    }
}
